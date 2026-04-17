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

    // -------------------------------------------------------------------------
    // Events category — toggle individual From The Caves horror event types
    // -------------------------------------------------------------------------
    static {
        BUILDER.comment("Toggle individual From The Caves horror events on or off.",
                        "All events are enabled by default.")
               .push("events");
    }

    public static final ForgeConfigSpec.BooleanValue ANVIL_EVENTS = BUILDER
            .comment("Horror events triggered near anvils.")
            .define("anvilEvents", true);

    public static final ForgeConfigSpec.BooleanValue ARMOR_STAND_EVENTS = BUILDER
            .comment("Horror events triggered near armor stands.")
            .define("armorStandEvents", true);

    public static final ForgeConfigSpec.BooleanValue BAT_EVENTS = BUILDER
            .comment("Horror events involving bats.")
            .define("batEvents", true);

    public static final ForgeConfigSpec.BooleanValue BED_EVENTS = BUILDER
            .comment("Horror events triggered when sleeping in a bed.")
            .define("bedEvents", true);

    public static final ForgeConfigSpec.BooleanValue BEACON_EVENTS = BUILDER
            .comment("Horror events triggered near beacons.")
            .define("beaconEvents", true);

    public static final ForgeConfigSpec.BooleanValue BELL_EVENTS = BUILDER
            .comment("Horror events triggered when a bell rings.")
            .define("bellEvents", true);

    public static final ForgeConfigSpec.BooleanValue BOOK_EVENTS = BUILDER
            .comment("Horror events triggered when handling books.")
            .define("bookEvents", true);

    public static final ForgeConfigSpec.BooleanValue BOOKSHELF_EVENTS = BUILDER
            .comment("Horror events triggered near bookshelves.")
            .define("bookshelfEvents", true);

    public static final ForgeConfigSpec.BooleanValue BUTTON_EVENTS = BUILDER
            .comment("Horror events triggered when pressing buttons.")
            .define("buttonEvents", true);

    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_EVENTS = BUILDER
            .comment("Horror events triggered near campfires.")
            .define("campfireEvents", true);

    public static final ForgeConfigSpec.BooleanValue CAT_EVENTS = BUILDER
            .comment("Horror events involving cats.")
            .define("catEvents", true);

    public static final ForgeConfigSpec.BooleanValue CHEST_EVENTS = BUILDER
            .comment("Horror events triggered when opening chests.")
            .define("chestEvents", true);

    public static final ForgeConfigSpec.BooleanValue CRAFTING_TABLE_EVENTS = BUILDER
            .comment("Horror events triggered near crafting tables.")
            .define("craftingTableEvents", true);

    public static final ForgeConfigSpec.BooleanValue DOOR_EVENTS = BUILDER
            .comment("Horror events triggered when using doors.")
            .define("doorEvents", true);

    public static final ForgeConfigSpec.BooleanValue FURNACE_EVENTS = BUILDER
            .comment("Horror events triggered near furnaces.")
            .define("furnaceEvents", true);

    public static final ForgeConfigSpec.BooleanValue ITEM_ENTITY_EVENTS = BUILDER
            .comment("Horror events triggered near dropped items.")
            .define("itemEntityEvents", true);

    public static final ForgeConfigSpec.BooleanValue ITEM_FRAME_EVENTS = BUILDER
            .comment("Horror events triggered near item frames.")
            .define("itemFrameEvents", true);

    public static final ForgeConfigSpec.BooleanValue JUKEBOX_EVENTS = BUILDER
            .comment("Horror events triggered near jukeboxes.")
            .define("jukeboxEvents", true);

    public static final ForgeConfigSpec.BooleanValue LADDER_EVENTS = BUILDER
            .comment("Horror events triggered when using ladders.")
            .define("ladderEvents", true);

    public static final ForgeConfigSpec.BooleanValue LECTERN_EVENTS = BUILDER
            .comment("Horror events triggered near lecterns.")
            .define("lecternEvents", true);

    public static final ForgeConfigSpec.BooleanValue LEVER_EVENTS = BUILDER
            .comment("Horror events triggered when flipping levers.")
            .define("leverEvents", true);

    public static final ForgeConfigSpec.BooleanValue LIGHT_EVENTS = BUILDER
            .comment("Horror events triggered near torches and light sources.")
            .define("lightEvents", true);

    public static final ForgeConfigSpec.BooleanValue NOTE_BLOCK_EVENTS = BUILDER
            .comment("Horror events triggered near note blocks.")
            .define("noteBlockEvents", true);

    public static final ForgeConfigSpec.BooleanValue PAINTING_EVENTS = BUILDER
            .comment("Horror events triggered near paintings.")
            .define("paintingEvents", true);

    public static final ForgeConfigSpec.BooleanValue PARROT_EVENTS = BUILDER
            .comment("Horror events involving parrots.")
            .define("parrotEvents", true);

    public static final ForgeConfigSpec.BooleanValue PLAYER_HEAD_EVENTS = BUILDER
            .comment("Horror events triggered near player head blocks.")
            .define("playerHeadEvents", true);

    public static final ForgeConfigSpec.BooleanValue PRESSURE_PLATE_EVENTS = BUILDER
            .comment("Horror events triggered when stepping on pressure plates.")
            .define("pressurePlateEvents", true);

    public static final ForgeConfigSpec.BooleanValue SIGN_EVENTS = BUILDER
            .comment("Horror events triggered near signs.")
            .define("signEvents", true);

    public static final ForgeConfigSpec.BooleanValue STAIRS_EVENTS = BUILDER
            .comment("Horror events triggered near stairs.")
            .define("stairsEvents", true);

    public static final ForgeConfigSpec.BooleanValue STEPS_EVENTS = BUILDER
            .comment("Horror events triggered when walking (footstep events).")
            .define("stepsEvents", true);

    public static final ForgeConfigSpec.BooleanValue TRAPDOOR_EVENTS = BUILDER
            .comment("Horror events triggered when using trapdoors.")
            .define("trapdoorEvents", true);

    public static final ForgeConfigSpec.BooleanValue WINDOW_EVENTS = BUILDER
            .comment("Horror events triggered near windows (glass panes).")
            .define("windowEvents", true);

    public static final ForgeConfigSpec.BooleanValue WOLF_EVENTS = BUILDER
            .comment("Horror events involving wolves (dogs).")
            .define("wolfEvents", true);

    static {
        BUILDER.pop();
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean patchEnabled = true;
    public static boolean onlyLoadedChunks = true;
    public static boolean reduceAudioCalls = true;
    public static boolean removePossessedPigSpawn = false;

    public static boolean anvilEvents = true;
    public static boolean armorStandEvents = true;
    public static boolean batEvents = true;
    public static boolean bedEvents = true;
    public static boolean beaconEvents = true;
    public static boolean bellEvents = true;
    public static boolean bookEvents = true;
    public static boolean bookshelfEvents = true;
    public static boolean buttonEvents = true;
    public static boolean campfireEvents = true;
    public static boolean catEvents = true;
    public static boolean chestEvents = true;
    public static boolean craftingTableEvents = true;
    public static boolean doorEvents = true;
    public static boolean furnaceEvents = true;
    public static boolean itemEntityEvents = true;
    public static boolean itemFrameEvents = true;
    public static boolean jukeboxEvents = true;
    public static boolean ladderEvents = true;
    public static boolean lecternEvents = true;
    public static boolean leverEvents = true;
    public static boolean lightEvents = true;
    public static boolean noteBlockEvents = true;
    public static boolean paintingEvents = true;
    public static boolean parrotEvents = true;
    public static boolean playerHeadEvents = true;
    public static boolean pressurePlateEvents = true;
    public static boolean signEvents = true;
    public static boolean stairsEvents = true;
    public static boolean stepsEvents = true;
    public static boolean trapdoorEvents = true;
    public static boolean windowEvents = true;
    public static boolean wolfEvents = true;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;
        patchEnabled = PATCH_ENABLED.get();
        onlyLoadedChunks = ONLY_LOADED_CHUNKS.get();
        reduceAudioCalls = REDUCE_AUDIO_CALLS.get();
        removePossessedPigSpawn = REMOVE_POSSESSED_PIG_SPAWN.get();

        anvilEvents = ANVIL_EVENTS.get();
        armorStandEvents = ARMOR_STAND_EVENTS.get();
        batEvents = BAT_EVENTS.get();
        bedEvents = BED_EVENTS.get();
        beaconEvents = BEACON_EVENTS.get();
        bellEvents = BELL_EVENTS.get();
        bookEvents = BOOK_EVENTS.get();
        bookshelfEvents = BOOKSHELF_EVENTS.get();
        buttonEvents = BUTTON_EVENTS.get();
        campfireEvents = CAMPFIRE_EVENTS.get();
        catEvents = CAT_EVENTS.get();
        chestEvents = CHEST_EVENTS.get();
        craftingTableEvents = CRAFTING_TABLE_EVENTS.get();
        doorEvents = DOOR_EVENTS.get();
        furnaceEvents = FURNACE_EVENTS.get();
        itemEntityEvents = ITEM_ENTITY_EVENTS.get();
        itemFrameEvents = ITEM_FRAME_EVENTS.get();
        jukeboxEvents = JUKEBOX_EVENTS.get();
        ladderEvents = LADDER_EVENTS.get();
        lecternEvents = LECTERN_EVENTS.get();
        leverEvents = LEVER_EVENTS.get();
        lightEvents = LIGHT_EVENTS.get();
        noteBlockEvents = NOTE_BLOCK_EVENTS.get();
        paintingEvents = PAINTING_EVENTS.get();
        parrotEvents = PARROT_EVENTS.get();
        playerHeadEvents = PLAYER_HEAD_EVENTS.get();
        pressurePlateEvents = PRESSURE_PLATE_EVENTS.get();
        signEvents = SIGN_EVENTS.get();
        stairsEvents = STAIRS_EVENTS.get();
        stepsEvents = STEPS_EVENTS.get();
        trapdoorEvents = TRAPDOOR_EVENTS.get();
        windowEvents = WINDOW_EVENTS.get();
        wolfEvents = WOLF_EVENTS.get();
    }
}
