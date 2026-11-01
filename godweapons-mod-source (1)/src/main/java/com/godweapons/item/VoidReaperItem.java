package com.godweapons.item;

import com.godweapons.effect.BlackHole;
import com.godweapons.effect.Effects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class VoidReaperItem extends SwordItem {
	public static final float CHANCE = 0.25f;
	public static final int COOLDOWN_TICKS = 100; // 5 seconds

	public VoidReaperItem(ToolMaterial material, Item.Settings settings) {
		super(material, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker instanceof PlayerEntity player && attacker.getWorld() instanceof ServerWorld world) {
			var cd = player.getItemCooldownManager();
			if (!cd.isCoolingDown(this) && world.random.nextFloat() < CHANCE) {
				cd.set(this, COOLDOWN_TICKS);
				Vec3d center = target.getPos().add(0, target.getHeight() * 0.5, 0);
				Effects.add(new BlackHole(world, center, player));
			}
		}
		return super.postHit(stack, target, attacker);
	}
}
