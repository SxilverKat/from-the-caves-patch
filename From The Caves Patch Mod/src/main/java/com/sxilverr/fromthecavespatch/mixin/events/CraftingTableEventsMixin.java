package com.sxilverr.fromthecavespatch.mixin.events;

import com.sxilverr.fromthecavespatch.PatchConfig;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.mcreator.fromthecaves.procedures.CraftingTableEventsProcedure", remap = false)
public class CraftingTableEventsMixin {

    @Inject(method = "onPlayerTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$cancelPlayer(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (PatchConfig.patchEnabled && !PatchConfig.craftingTableEvents) ci.cancel();
    }

    @Inject(method = "onServerTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$cancelServer(TickEvent.ServerTickEvent event, CallbackInfo ci) {
        if (PatchConfig.patchEnabled && !PatchConfig.craftingTableEvents) ci.cancel();
    }
}
