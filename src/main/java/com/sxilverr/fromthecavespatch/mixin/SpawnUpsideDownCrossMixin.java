package com.sxilverr.fromthecavespatch.mixin;

import com.sxilverr.fromthecavespatch.FTCConfigReader;
import com.sxilverr.fromthecavespatch.LoadedChunkStructureSearch;
import com.sxilverr.fromthecavespatch.PatchConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.mcreator.fromthecaves.procedures.SpawnUpsideDownCrossInVillageProcedure", remap = false)
public class SpawnUpsideDownCrossMixin {

    @Inject(method = "onPlayerTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fromthecavespatch$skipIfDisabled(CallbackInfo ci) {
        if (!PatchConfig.patchEnabled) return;
        if (!FTCConfigReader.isSpawnsEnabled()) {
            ci.cancel();
        }
    }

    @Redirect(
        method = "onPlayerTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;m_215011_(Lnet/minecraft/tags/TagKey;Lnet/minecraft/core/BlockPos;IZ)Lnet/minecraft/core/BlockPos;"
        ),
        remap = false
    )
    @SuppressWarnings("unchecked")
    private static BlockPos fromthecavespatch$redirectStructureSearch(
            ServerLevel level, TagKey<Structure> tag, BlockPos center, int radius, boolean skipKnown) {
        if (!PatchConfig.patchEnabled || !PatchConfig.onlyLoadedChunks) {
            return level.findNearestMapStructure(tag, center, radius, skipKnown);
        }
        return LoadedChunkStructureSearch.search(level, tag, center, radius);
    }
}
