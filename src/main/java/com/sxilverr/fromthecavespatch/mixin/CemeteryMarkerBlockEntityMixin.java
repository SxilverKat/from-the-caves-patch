package com.sxilverr.fromthecavespatch.mixin;

import com.sxilverr.fromthecavespatch.PatchConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stops CemeteryMarkerBlockEntity.onLoad() from force-loading up to 17 chunks
 * every time the marker's chunk loads.
 *
 * FTC's onLoad() does: register marker → check phase → iterate all players →
 * sort/scan up to 289 candidate chunks → call forceChunk(true) up to 17 times.
 *
 * The chunk force-loading is unnecessary — the flickering effect only fires on
 * PlayerTickEvent anyway, so if a player is nearby the chunks are already loaded
 * by normal chunk loading. Without this patch, every Cemetery Marker permanently
 * anchors up to 17 chunks in memory for the lifetime of the server session.
 *
 * We inject immediately after addMarker() so the marker is still registered with
 * CemeteryMarkerManager (used by other FTC procedures to locate markers), then
 * cancel the rest of the method to skip the expensive player/chunk work.
 */
@Mixin(targets = "net.mcreator.fromthecaves.block.entity.CemeteryMarkerBlockEntity", remap = false)
public class CemeteryMarkerBlockEntityMixin {

    @Inject(
        method = "onLoad",
        at = @At(
            value = "INVOKE",
            target = "Lnet/mcreator/fromthecaves/util/CemeteryMarkerManager;addMarker(Lnet/minecraft/core/BlockPos;)V",
            shift = At.Shift.AFTER
        ),
        cancellable = true,
        remap = false
    )
    private void fromthecavespatch$cancelChunkForcing(CallbackInfo ci) {
        if (!PatchConfig.patchEnabled) return;
        ci.cancel();
    }
}
