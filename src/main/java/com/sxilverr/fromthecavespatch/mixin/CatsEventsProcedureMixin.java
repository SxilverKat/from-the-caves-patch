package com.sxilverr.fromthecavespatch.mixin;

import com.sxilverr.fromthecavespatch.PatchConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(targets = "net.mcreator.fromthecaves.procedures.CatsEventsProcedure", remap = false)
public class CatsEventsProcedureMixin {

    @Inject(method = "findCats", at = @At("RETURN"), cancellable = true, remap = false)
    private static void fromthecavespatch$excludeTamedCats(
            ServerLevel level, Player player, CallbackInfoReturnable<List<Entity>> cir) {
        if (!PatchConfig.patchEnabled || !PatchConfig.protectTamedCats) return;
        List<Entity> result = cir.getReturnValue();
        if (result == null || result.isEmpty()) return;
        List<Entity> filtered = new ArrayList<>(result.size());
        for (Entity e : result) {
            if (e instanceof Cat cat && cat.isTame()) continue;
            filtered.add(e);
        }
        cir.setReturnValue(filtered);
    }
}
