package com.sxilverr.fromthecavespatch;

import java.lang.reflect.Method;

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

    public static boolean isSpawnsEnabled() {
        init();
        if (isAllSpawnsEnabledMethod == null) return true;
        try {
            return (boolean) isAllSpawnsEnabledMethod.invoke(null);
        } catch (Exception e) {
            return true;
        }
    }
}
