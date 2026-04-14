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

/**
 * Removes From The Caves' possessed pig from all biome spawn lists.
 *
 * This does not affect vanilla pig spawning — normal pigs are unaffected.
 *
 * FTC's pig_possesed_biome_modifier.json adds the possessed pig entity to every
 * biome with weight 20 in groups of 4. Unlike every other FTC possessed mob
 * (cow, sheep, wolf, villager, iron golem), the possessed pig has no custom
 * model or animations, so it renders as a plain vanilla pig. This makes pig
 * counts abnormally high everywhere. Removing it brings pigs back to vanilla
 * levels, matching how the other possessed mobs work — they still have their
 * own biome modifiers but are visually distinct from their vanilla counterparts.
 *
 * This modifier is loaded during world data loading when configs are already
 * available, so it correctly respects the removePossessedPigSpawn config option.
 */
public record PigSpawnRemover() implements BiomeModifier {

    static final Codec<PigSpawnRemover> CODEC = Codec.unit(PigSpawnRemover::new);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.REMOVE) return;
        if (!PatchConfig.patchEnabled || !PatchConfig.removePossessedPigSpawn) return;

        EntityType<?> possessedPig = ForgeRegistries.ENTITY_TYPES.getValue(
                new ResourceLocation("from_the_caves", "pig_possesed"));
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
