package com.sxilverr.fromthecavespatch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.Map;
import java.util.Optional;

public class LoadedChunkStructureSearch {

    @SuppressWarnings("unchecked")
    public static BlockPos search(ServerLevel level, TagKey<Structure> tag, BlockPos center, int radius) {
        int chunkRadius = Math.max(1, radius >> 4);
        int centerChunkX = SectionPos.blockToSectionCoord(center.getX());
        int centerChunkZ = SectionPos.blockToSectionCoord(center.getZ());

        BlockPos result = null;
        double bestDist = Double.MAX_VALUE;

        ServerChunkCache chunkSource = level.getChunkSource();
        Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);

        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                LevelChunk chunk = chunkSource.getChunkNow(centerChunkX + dx, centerChunkZ + dz);
                if (chunk == null) continue;

                for (Map.Entry<Structure, StructureStart> entry : chunk.getAllStarts().entrySet()) {
                    StructureStart start = entry.getValue();
                    if (!start.isValid()) continue;

                    Optional<ResourceKey<Structure>> key = registry.getResourceKey(entry.getKey());
                    if (key.isEmpty()) continue;

                    Holder<Structure> holder = registry.getHolderOrThrow(key.get());
                    if (!holder.is(tag)) continue;

                    BlockPos pos = new BlockPos(start.getBoundingBox().getCenter());
                    double dist = pos.distSqr(center);
                    if (dist < bestDist) {
                        bestDist = dist;
                        result = pos;
                    }
                }
            }
        }

        return result;
    }
}
