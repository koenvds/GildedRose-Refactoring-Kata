package com.gildedrose.qualitystrategies.impl;

import com.gildedrose.Item;

/**
 * Aged item quality goes up as it gets older
 */
public class AgedItemQualityStrategy extends AbstractItemQualityStrategy {
	/**
	 * The quality delta is +1 when the sellIn date is more than zero. The quality
	 * delta is =2 when the sellIn date is zero or below.
	 * 
	 * @param item the item which quality needs to be adjusted
	 * @return the difference between the current quality and the new
	 */
	protected int getQualityDelta(final Item item) {
		// Increase quality by 1 every cycle
		int delta = 1;
		// If past due date, increase twice as fast
		if (item.sellIn <= 0) {
			delta *= 2;
		}

		return delta;
	}
}