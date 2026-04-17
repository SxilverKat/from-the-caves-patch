package com.sxilverr.fromthecavespatch.mixin.events;

import com.sxilverr.fromthecavespatch.PatchConfig;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.mcreator.fromthecaves.procedures.BedWakeDespawnProcedure", remap = false)
public class BedWakeDespawnMixin {

    @Inject(method = "onPlayerWake", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$cancelWake(PlayerWakeUpEvent event, CallbackInfo ci) {
        if (PatchConfig.patchEnabled && !PatchConfig.bedEvents) ci.cancel();
    }

    @Inject(method = "onBedEntityTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$cancelTick(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (PatchConfig.patchEnabled && !PatchConfig.bedEvents) ci.cancel();
    }
}
