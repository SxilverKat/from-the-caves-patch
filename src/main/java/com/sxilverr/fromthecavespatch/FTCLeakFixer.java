package com.sxilverr.fromthecavespatch;

import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = FromTheCavesPatch.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FTCLeakFixer {

    private static volatile boolean initialized = false;

    private static Field playerTickCounters = null;
    private static Field playerVillageSearchCooldown = null;
    private static Field lastSoundTick = null;
    private static Field villagersWithCross = null;
    private static Field illagersWithCross = null;
    private static Field blockCooldowns = null;
    private static Field torchFlickerBlockCooldowns = null;
    private static Field rightClickWLastSoundTime = null;
    private static Field rightClickWILastSoundTime = null;
    private static Field playerChunkTimes = null;
    private static Field armorStandSearchProgress = null;
    private static Field windowSearchProgress = null;
    private static Field lightSearchProgress = null;
    private static Field noteBlockSearchProgress = null;
    private static Field itemFrameSearchProgress = null;
    private static Field chestSearchProgress = null;
    private static Field trapdoorSearchProgress = null;
    private static Field crossCompassPlayerCache = null;
    private static Field crossCompassSearchProgress = null;
    private static Field deepDarknessPlayerCooldown = null;
    private static Field deepDarknessGrabbedPlayers = null;
    private static Field flamingoLastCheckTime = null;
    private static Field flamingoCachedPos = null;
    private static Field stepsActiveMobs = null;

    private static void init() {
        if (initialized) return;
        initialized = true;

        try {
            Class<?> crossProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.SpawnUpsideDownCrossInVillageProcedure");
            playerTickCounters = accessField(crossProc, "playerTickCounters");
            playerVillageSearchCooldown = accessField(crossProc, "playerVillageSearchCooldown");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find SpawnUpsideDownCrossInVillageProcedure.");
        }

        try {
            Class<?> fearProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.AnimalFearManagerProcedure");
            lastSoundTick = accessField(fearProc, "lastSoundTick");
            villagersWithCross = accessField(fearProc, "villagersWithCross");
            illagersWithCross = accessField(fearProc, "illagersWithCross");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find AnimalFearManagerProcedure.");
        }

        try {
            Class<?> flickerProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.CemeteryMarkerFlickerProcedure");
            blockCooldowns = accessField(flickerProc, "blockCooldowns");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find CemeteryMarkerFlickerProcedure.");
        }

        try {
            Class<?> torchFlickerProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.TorchFlickerNearMobProcedure");
            torchFlickerBlockCooldowns = accessField(torchFlickerProc, "blockCooldowns");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find TorchFlickerNearMobProcedure.");
        }

        try {
            Class<?> rwcProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.RightClickWCrossProcedure");
            rightClickWLastSoundTime = accessField(rwcProc, "lastSoundTime");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find RightClickWCrossProcedure.");
        }

        try {
            Class<?> rwicProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.RightClickWICrossProcedure");
            rightClickWILastSoundTime = accessField(rwicProc, "lastSoundTime");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find RightClickWICrossProcedure.");
        }

        try {
            Class<?> angerProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.AngerSystemProcedure");
            playerChunkTimes = accessField(angerProc, "playerChunkTimes");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find AngerSystemProcedure.");
        }

        String[] searchProgressClasses = {
            "ArmorStandEventsProcedure",
            "WindowEventsProcedure",
            "LightEventsProcedure",
            "NoteBlockEventsProcedure",
            "ItemFrameEventsProcedure",
            "ChestEventsProcedure",
            "TrapdoorEventsProcedure"
        };
        Field[] searchProgressFields = {
            null, null, null, null, null, null, null
        };
        for (int i = 0; i < searchProgressClasses.length; i++) {
            String className = "net.mcreator.fromthecaves.procedures." + searchProgressClasses[i];
            try {
                Class<?> cls = Class.forName(className);
                searchProgressFields[i] = accessField(cls, "searchProgress");
            } catch (ClassNotFoundException e) {
                FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find {}.", searchProgressClasses[i]);
            }
        }
        armorStandSearchProgress = searchProgressFields[0];
        windowSearchProgress     = searchProgressFields[1];
        lightSearchProgress      = searchProgressFields[2];
        noteBlockSearchProgress  = searchProgressFields[3];
        itemFrameSearchProgress  = searchProgressFields[4];
        chestSearchProgress      = searchProgressFields[5];
        trapdoorSearchProgress   = searchProgressFields[6];

        try {
            Class<?> compassProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.CrossCompassBehaviorProcedure");
            crossCompassPlayerCache    = accessField(compassProc, "playerCache");
            crossCompassSearchProgress = accessField(compassProc, "searchProgress");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find CrossCompassBehaviorProcedure.");
        }

        try {
            Class<?> dragProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.DeepDarknessDragProcedure");
            deepDarknessPlayerCooldown = accessField(dragProc, "playerCooldown");
            deepDarknessGrabbedPlayers = accessField(dragProc, "grabbedPlayers");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find DeepDarknessDragProcedure.");
        }

        try {
            Class<?> flamingoProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.FlamingoHauntedProcedure");
            flamingoLastCheckTime = accessField(flamingoProc, "lastCheckTime");
            flamingoCachedPos     = accessField(flamingoProc, "cachedFlamingoPos");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find FlamingoHauntedProcedure.");
        }

        try {
            Class<?> stepsProc = Class.forName(
                "net.mcreator.fromthecaves.procedures.StepsAmbushProcedure");
            stepsActiveMobs = accessField(stepsProc, "activeMobs");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find StepsAmbushProcedure.");
        }

        FromTheCavesPatch.LOGGER.info("[FromTheCavesPatch] LeakFixer ready.");
    }

    private static Field accessField(Class<?> clazz, String name) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            FromTheCavesPatch.LOGGER.warn("[FromTheCavesPatch] LeakFixer: Could not find field '{}' in {}.", name, clazz.getSimpleName());
            return null;
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!PatchConfig.patchEnabled) return;
        init();
        String name = event.getEntity().getScoreboardName();
        UUID uuid = event.getEntity().getUUID();
        removeFromMap(playerTickCounters, name);
        removeFromMap(playerVillageSearchCooldown, name);
        removeFromMap(playerChunkTimes, name);
        removeFromMap(rightClickWLastSoundTime, uuid);
        removeFromMap(rightClickWILastSoundTime, uuid);
        removeFromMap(armorStandSearchProgress, uuid);
        removeFromMap(windowSearchProgress, uuid);
        removeFromMap(lightSearchProgress, uuid);
        removeFromMap(noteBlockSearchProgress, uuid);
        removeFromMap(itemFrameSearchProgress, uuid);
        removeFromMap(chestSearchProgress, uuid);
        removeFromMap(trapdoorSearchProgress, uuid);
        removeFromMap(crossCompassPlayerCache, uuid);
        removeFromMap(crossCompassSearchProgress, uuid);
        removeFromMap(deepDarknessPlayerCooldown, uuid);
        removeFromMap(deepDarknessGrabbedPlayers, uuid);
        removeFromMap(flamingoLastCheckTime, uuid);
        removeFromMap(flamingoCachedPos, uuid);
        removeFromMap(stepsActiveMobs, uuid);
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!PatchConfig.patchEnabled) return;
        init();
        UUID uuid = event.getEntity().getUUID();
        removeFromMap(lastSoundTick, uuid);
        removeFromSet(villagersWithCross, uuid);
        removeFromSet(illagersWithCross, uuid);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!PatchConfig.patchEnabled) return;
        init();
        removeFromMap(blockCooldowns, event.getPos());
        removeFromMap(torchFlickerBlockCooldowns, event.getPos());
    }

    @SuppressWarnings("unchecked")
    private static void removeFromMap(Field field, Object key) {
        if (field == null) return;
        try {
            Map<Object, Object> map = (Map<Object, Object>) field.get(null);
            if (map != null) map.remove(key);
        } catch (Exception ignored) {}
    }

    @SuppressWarnings("unchecked")
    private static void removeFromSet(Field field, Object value) {
        if (field == null) return;
        try {
            Set<Object> set = (Set<Object>) field.get(null);
            if (set != null) set.remove(value);
        } catch (Exception ignored) {}
    }
}
