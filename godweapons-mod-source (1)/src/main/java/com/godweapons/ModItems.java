package com.godweapons;

import com.godweapons.item.CelestialJudgementItem;
import com.godweapons.item.GodslayerItem;
import com.godweapons.item.VoidReaperItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

	// Damage numbers: final hit = 1 (base) + 4 (netherite) + the number below
	public static final Item VOID_REAPER = register("void_reaper",
		new VoidReaperItem(ToolMaterials.NETHERITE,
			base(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 12, -2.2f))));

	public static final Item GODSLAYER = register("godslayer",
		new GodslayerItem(ToolMaterials.NETHERITE,
			base(AxeItem.createAttributeModifiers(ToolMaterials.NETHERITE, 12.0f, -2.8f))));

	public static final Item CELESTIAL_JUDGEMENT = register("celestial_judgement",
		new CelestialJudgementItem(ToolMaterials.NETHERITE,
			base(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 14, -2.0f))));

	private static Item.Settings base(AttributeModifiersComponent modifiers) {
		return new Item.Settings()
			.rarity(Rarity.EPIC)
			.fireproof()
			.attributeModifiers(modifiers)
			.component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
			.component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
	}

	private static <T extends Item> T register(String name, T item) {
		return Registry.register(Registries.ITEM, Identifier.of(GodWeaponsMod.MOD_ID, name), item);
	}

	public static void register() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(VOID_REAPER);
			entries.add(GODSLAYER);
			entries.add(CELESTIAL_JUDGEMENT);
		});
	}
}
