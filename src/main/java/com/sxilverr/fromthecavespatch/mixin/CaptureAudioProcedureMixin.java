package com.sxilverr.fromthecavespatch.mixin;

import com.sxilverr.fromthecavespatch.PatchConfig;
import net.mcreator.fromthecaves.AudioCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.mcreator.fromthecaves.procedures.CaptureAudioProcedure", remap = false)
public class CaptureAudioProcedureMixin {

    private static int fromthecavespatch$retryCountdown = 0;
    private static final int RETRY_INTERVAL = 200;

    @Redirect(
        method = "onClientTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/mcreator/fromthecaves/AudioCapture;isCapturing()Z"
        ),
        remap = false
    )
    private static boolean fromthecavespatch$throttleCaptureRetry() {
        if (!PatchConfig.patchEnabled || !PatchConfig.reduceAudioCalls) {
            return AudioCapture.isCapturing();
        }
        if (AudioCapture.isCapturing()) {
            fromthecavespatch$retryCountdown = 0;
            return true;
        }
        if (fromthecavespatch$retryCountdown > 0) {
            fromthecavespatch$retryCountdown--;
            return true;
        }
        fromthecavespatch$retryCountdown = RETRY_INTERVAL;
        return false;
    }
}
