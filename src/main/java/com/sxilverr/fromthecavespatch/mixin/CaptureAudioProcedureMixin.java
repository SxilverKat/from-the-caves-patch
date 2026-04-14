package com.sxilverr.fromthecavespatch.mixin;

import com.sxilverr.fromthecavespatch.PatchConfig;
import net.mcreator.fromthecaves.AudioCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Throttles CaptureAudioProcedure's audio capture retry so it doesn't call
 * AudioSystem.getMixerInfo() every client tick when capture is failing.
 *
 * When audio capture is not running (no microphone, wrong mixer, permissions
 * error, etc.), onClientTick calls AudioCapture.startCapture() every tick with
 * no cooldown. startCapture() calls AudioSystem.getMixerInfo() to scan audio
 * devices, which triggers Java's service loader and cascades into Forge's mod
 * classloader iterating JAR files on disk — I/O work on every tick.
 *
 * Fix: redirect the isCapturing() check to return true (skip retry) until a
 * 200-tick cooldown expires. Retries are attempted every 10 seconds instead of
 * 20 times per second. When capture is working normally, the cooldown resets
 * immediately and behaviour is identical to vanilla FTC.
 */
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
            return true; // skip startCapture this tick
        }
        fromthecavespatch$retryCountdown = RETRY_INTERVAL;
        return false; // allow startCapture to run
    }
}
