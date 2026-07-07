package com.natamus.transcendingtrident.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.natamus.collective.functions.PlayerFunctions;
import com.natamus.transcendingtrident.config.ConfigHandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;

@Mixin(value = LivingEntity.class, priority = 1001)
public class LivingEntityAiStepMixin {
	@Shadow private int autoSpinAttackTicks;

	@Redirect(method = "aiStep", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWaterOrRain()Z"))
	private boolean aiStep_isInWaterOrRain(Entity entity) {
		if (this.autoSpinAttackTicks > 0) {
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
		}
		return entity.isInWaterOrRain();
	}
}