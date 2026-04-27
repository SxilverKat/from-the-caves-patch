package com.sxilverr.fromthecavespatch.mixin;

import com.sxilverr.fromthecavespatch.PatchConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
