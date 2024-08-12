package com.gildedrose.qualitystrategies.impl;

import com.gildedrose.Item;

/**
 * Conjured item quality degrades twice as fast as normal.
 */
public class ConjuredItemQualityStrategy extends AbstractItemQualityStrategy {
	/**
	 * The quality delta is -2 when the sellIn date is more than zero. The quality
	 * delta is -4 when the sellIn date is zero or below.
	 *
	 * @param item the item which quality needs to be adjusted
	 * @return the difference between the current quality and the new
	 */
	protected int getQualityDelta(Item item) {
		return item.sellIn > 0 ? -2 : -4;
	}
}
