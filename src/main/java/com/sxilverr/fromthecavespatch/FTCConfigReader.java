package com.sxilverr.fromthecavespatch;

import java.lang.reflect.Method;

/**
 * Reads state from From The Caves at runtime using reflection.
 *
 * We target FromTheCavesToggleManagerProcedure.isAllSpawnsEnabled() — this is the
 * global spawn toggle that FTC exposes. When it returns false, there is no reason
 * to run any spawn-related tick handler, so we can safely skip the expensive searches.
 *
 * Reflection is used because we don't want to force a hard compile-time dependency
 * on a specific FTC version. We look up the method once and cache it.
 */
public class FTCConfigReader {

    private static volatile boolean initialized = false;
    private static Method isAllSpawnsEnabledMethod = null;

    private static void init() {
        if (initialized) return;
        initialized = true;
        try {
            Class<?> toggleManager = Class.forName(
                "net.mcreator.fromthecaves.procedures.FromTheCavesToggleManagerProcedure"
            );
            isAllSpawnsEnabledMethod = toggleManager.getMethod("isAllSpawnsEnabled");
            FromTheCavesPatch.LOGGER.info("[FromTheCavesPatch] Linked to From The Caves toggle manager.");
        } catch (ClassNotFoundException e) {
            FromTheCavesPatch.LOGGER.error("[FromTheCavesPatch] Could not find From The Caves toggle manager class. " +
                "Is From The Caves installed?");
        } catch (NoSuchMethodException e) {
            FromTheCavesPatch.LOGGER.error("[FromTheCavesPatch] From The Caves toggle manager found but isAllSpawnsEnabled() " +
                "method is missing. FTC version may have changed.");
        }
    }

    /**
     * Returns true if From The Caves has spawns globally enabled.
     * Returns true (safe default — don't skip) if the method cannot be found.
     */
    public static boolean isSpawnsEnabled() {
        init();
        if (isAllSpawnsEnabledMethod == null) return true;
        try {
            return (boolean) isAllSpawnsEnabledMethod.invoke(null);
        } catch (Exception e) {
            return true; // fail safe
        }
    }
}
