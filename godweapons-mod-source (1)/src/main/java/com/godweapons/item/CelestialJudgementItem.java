package com.godweapons.item;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

/**
 * The meteor logic lives in CelestialEvents (it listens for kills made while this sword is held).
 */
public class CelestialJudgementItem extends SwordItem {
	public CelestialJudgementItem(ToolMaterial material, Item.Settings settings) {
		super(material, settings);
	}
}
