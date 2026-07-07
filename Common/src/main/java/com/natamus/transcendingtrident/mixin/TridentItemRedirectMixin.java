package com.natamus.transcendingtrident.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.natamus.collective.functions.PlayerFunctions;
import com.natamus.transcendingtrident.config.ConfigHandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;

@Mixin(value = TridentItem.class, priority = 1001)
public class TridentItemRedirectMixin {
	@Redirect(method = "releaseUsing", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWaterOrRain()Z"))
	private boolean releaseUsing_isInWaterOrRain(Entity entity) {
		if (entity instanceof Player player) {
			if (!ConfigHandler.mustHoldBucketOfWater || PlayerFunctions.isHoldingWater(player)) {
				if (player.getMainHandItem().getItem() instanceof TridentItem) {
					return true;
				}
				if (player.getOffhandItem().getItem() instanceof TridentItem) {
					return true;
				}
			}
		}
		return entity.isInWaterOrRain();
	}
}