package com.sxilverr.fromthecavespatch;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = FromTheCavesPatch.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PatchConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue PATCH_ENABLED = BUILDER
            .comment("When true, all patches are active. Set to false to disable this mod.")
            .define("patchEnabled", true);

    public static final ForgeConfigSpec.BooleanValue ONLY_LOADED_CHUNKS = BUILDER
            .comment("Controls how the cross and chest procedures search for nearby villages.",
                     "true  = only search already-loaded chunks.",
                     "false = use From The Caves' original search system")
            .define("onlyLoadedChunks", true);

    public static final ForgeConfigSpec.BooleanValue REDUCE_AUDIO_CALLS = BUILDER
            .comment("When true, throttles From The Caves' audio capture retry so it only",
                     "attempts to open the microphone once every 10 seconds instead of every",
                     "client tick. Fixes a performance issue where players without a working",
                     "microphone trigger an OS-level audio device scan 20 times per second.")
            .define("reduceAudioSystemCalls", true);

    public static final ForgeConfigSpec.BooleanValue REMOVE_POSSESSED_PIG_SPAWN = BUILDER
            .comment("There is an issue that may cause a lot of pigs to spawn.",
                     "When true, removes From The Caves' possessed pig from all biome spawn lists.",
                     "This does not affect vanilla pig spawning, normal pigs are unaffected.",
                     "This config is disabled by default.")
            .define("removePossessedPigSpawn", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean patchEnabled = true;
    public static boolean onlyLoadedChunks = true;
    public static boolean reduceAudioCalls = true;
    public static boolean removePossessedPigSpawn = false;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;
        patchEnabled = PATCH_ENABLED.get();
        onlyLoadedChunks = ONLY_LOADED_CHUNKS.get();
        reduceAudioCalls = REDUCE_AUDIO_CALLS.get();
        removePossessedPigSpawn = REMOVE_POSSESSED_PIG_SPAWN.get();
    }
}
