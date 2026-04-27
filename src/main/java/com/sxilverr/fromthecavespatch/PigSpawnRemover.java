package com.sxilverr.fromthecavespatch;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;

public record PigSpawnRemover() implements BiomeModifier {

    static final Codec<PigSpawnRemover> CODEC = Codec.unit(PigSpawnRemover::new);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.REMOVE) return;
        if (!PatchConfig.patchEnabled || !PatchConfig.removePossessedPigSpawn) return;

        ResourceLocation possessedPigId = ResourceLocation.tryParse("from_the_caves:pig_possesed");
        if (possessedPigId == null) return;
        EntityType<?> possessedPig = ForgeRegistries.ENTITY_TYPES.getValue(possessedPigId);
        if (possessedPig == null) return;

        var spawnSettings = builder.getMobSpawnSettings();
        for (MobCategory category : MobCategory.values()) {
            spawnSettings.getSpawner(category).removeIf(spawn -> spawn.type == possessedPig);
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
