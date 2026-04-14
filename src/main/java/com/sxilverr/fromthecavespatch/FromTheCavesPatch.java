package com.sxilverr.fromthecavespatch;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(FromTheCavesPatch.MOD_ID)
@Mod.EventBusSubscriber(modid = FromTheCavesPatch.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FromTheCavesPatch {

    public static final String MOD_ID = "fromthecavespatch";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FromTheCavesPatch(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, PatchConfig.SPEC);
        LOGGER.info("[FromTheCavesPatch] Loaded.");
    }

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
                helper -> helper.register("remove_pig_spawn", PigSpawnRemover.CODEC));
    }
}
