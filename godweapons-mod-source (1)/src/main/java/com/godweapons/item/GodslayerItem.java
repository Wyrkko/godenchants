package com.godweapons.item;

import com.godweapons.effect.Util;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GodslayerItem extends AxeItem {
	public static final int LIGHTNING_EVERY = 5;
	public static final double LIGHTNING_RADIUS = 6.0;
	public static final float LIGHTNING_DAMAGE = 20.0f; // 10 hearts

	private static final Map<UUID, Integer> HITS = new HashMap<>();

	public GodslayerItem(ToolMaterial material, Item.Settings settings) {
		super(material, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker.getWorld() instanceof ServerWorld world) {
			bonusDamage(world, target, attacker);
			if (attacker instanceof ServerPlayerEntity player) countHit(world, target, player);
		}
		return super.postHit(stack, target, attacker);
	}

	/**
	 * +100% damage: an extra hit equal to the weapon's damage.
	 * Half of it is armor-piercing magic damage (so overall 50% of the bonus ignores armor).
	 */
	private void bonusDamage(ServerWorld world, LivingEntity target, LivingEntity attacker) {
		if (!target.isAlive()) return;
		float raw = (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

		DamageSource normal = attacker instanceof PlayerEntity p
			? world.getDamageSources().playerAttack(p)
			: world.getDamageSources().mobAttack(attacker);
		Util.hurt(world, target, normal, raw * 0.5f);

		if (target.isAlive()) {
			Util.hurt(world, target, Util.magic(world, attacker), raw * 0.5f);
		}
	}

	private void countHit(ServerWorld world, LivingEntity target, ServerPlayerEntity player) {
		int hits = HITS.merge(player.getUuid(), 1, Integer::sum);
		if (hits < LIGHTNING_EVERY) {
			player.sendMessage(Text.literal("Godslayer: " + hits + "/" + LIGHTNING_EVERY).formatted(Formatting.RED), true);
			return;
		}
		HITS.put(player.getUuid(), 0);
		player.sendMessage(Text.literal("GODSLAYER STRIKE!").formatted(Formatting.YELLOW, Formatting.BOLD), true);

		Vec3d c = target.getPos();
		Util.cosmeticBolt(world, c.x, c.y, c.z);
		List<LivingEntity> victims = Util.enemiesAround(world, c, LIGHTNING_RADIUS, player);
		int bolts = 0;
		for (LivingEntity e : victims) {
			if (bolts++ < 6) Util.cosmeticBolt(world, e.getX(), e.getY(), e.getZ());
			Util.hurt(world, e, Util.magic(world, player), LIGHTNING_DAMAGE);
			e.setOnFireFor(5);
		}
		world.playSound(null, target.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 6.0f, 0.9f);
	}
}
