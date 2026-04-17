package com.sxilverr.fromthecavespatch.mixin.events;

import com.sxilverr.fromthecavespatch.PatchConfig;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.mcreator.fromthecaves.procedures.BedWatchingProcedure", remap = false)
public class BedWatchingMixin {

    @Inject(method = "onPlayerTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$cancelPlayer(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (PatchConfig.patchEnabled && !PatchConfig.bedEvents) ci.cancel();
    }

    @Inject(method = "onEntityDeath", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$cancelDeath(LivingDeathEvent event, CallbackInfo ci) {
        if (PatchConfig.patchEnabled && !PatchConfig.bedEvents) ci.cancel();
    }
}
