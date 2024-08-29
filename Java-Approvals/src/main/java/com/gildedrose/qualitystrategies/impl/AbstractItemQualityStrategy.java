package com.gildedrose.qualitystrategies.impl;

import com.gildedrose.Item;
import com.gildedrose.qualitystrategies.QualityStrategy;

public abstract class AbstractItemQualityStrategy implements QualityStrategy {
	private final int MIN_ITEM_QUALITY = 0;
	private final int MAX_ITEM_QUALITY = 50;

	/**
	 * @param item the item which quality needs to be adjusted
	 * @return the difference between the current quality and the new
	 */
	protected abstract int getQualityDelta(Item item);

	private void updateItemQuality(Item item) {
		int qualityDelta = this.getQualityDelta(item);
		this.attemptUpdateQuality(item, qualityDelta);
	}

	private void updateItemSellIn(Item item) {
		item.sellIn -= 1;
	}

	@Override
	public final void updateItem(Item item) {
		this.updateItemQuality(item);
		this.updateItemSellIn(item);
	}

	private void attemptUpdateQuality(Item item, int qualityUpdate) {
		// If quality decreases
		if (qualityUpdate < 0) {
			// Only decrease the quality if it is higher than the minimum
			if (isQualityHigherThanMinimum(item)) {
				// Update the quality, but make sure it doesn't decrease below the minimum.
				item.quality = Math.max(item.quality + qualityUpdate, MIN_ITEM_QUALITY);
			}
		}
		// If quality increases
		else if (qualityUpdate > 0) {
			// Only increase the quality if it is lower than the maximum
			if (isQualityLowerThanMaximum(item)) {
				// Update the quality, but make sure it doesn't increase above the maximum
				item.quality = Math.min(item.quality + qualityUpdate, MAX_ITEM_QUALITY);
			}
		}
	}

	private boolean isQualityLowerThanMaximum(Item item) {
		return item.quality < MAX_ITEM_QUALITY;
	}

	private boolean isQualityHigherThanMinimum(Item item) {
		return item.quality > MIN_ITEM_QUALITY;
	}


}
