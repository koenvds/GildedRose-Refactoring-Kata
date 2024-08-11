package com.gildedrose.qualitystrategies.impl;

import com.gildedrose.Item;

/**
 * BackStagePasses quality goes up in incremental steps as the sellIn date gets
 *  closer to 0, then drops to 0;
 */
public class BackStagePassQualityStrategy extends AbstractItemQualityStrategy{
	/**
	 * The quality delta is +1 when the sellIn date is 10 days away or more.
	 * The quality delta is +2 when the sellIn date is 5 to 10 days away.
	 * The quality delta is +3 when the sellIn date is 0 to 5 days away.
	 * The quality drops to 0 when the sellIn date is reached.
	 * @param item the item which quality needs to be adjusted.
	 * @return the difference between the current quality and the new.
	 */
	@Override
	protected int getQualityDelta(final Item item) {
		if (isConcertDateMoreThan10DaysAway(item)) return 1;
		if (isConcertDateMoreThan5DaysAway(item)) return 2;
		if (isConcertDateMoreThan0DaysAway(item)) return 3;
		//Concert date has passed ==> pass quality drops to 0
		return -1 * item.quality;
	}

	/**
	 * Checks whether the concert date is more than 10 days away.
	 * @param item the backstage pass for the concert.
	 * @return if concert date is more than 10 days away.
	 */
	private boolean isConcertDateMoreThan10DaysAway(Item item) {
		return item.sellIn > 10;
	}
	
	/**
	 * Checks whether the concert date is more than 5 days away.
	 * @param item the backstage pass for the concert.
	 * @return if concert date is more than 5 days away.
	 */
	private boolean isConcertDateMoreThan5DaysAway(Item item) {
		return item.sellIn > 5;
	}
	
	/**
	 * Checks whether the concert date is more than 0 days away.
	 * @param item the backstage pass for the concert.
	 * @return if concert date is more than 0 days away.
	 */
	private boolean isConcertDateMoreThan0DaysAway(Item item) {
		return item.sellIn > 0;
	}
}
