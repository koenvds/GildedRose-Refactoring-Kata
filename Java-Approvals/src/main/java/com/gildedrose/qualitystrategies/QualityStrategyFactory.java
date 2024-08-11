package com.gildedrose.qualitystrategies;

import java.util.HashMap;
import java.util.Map;

import com.gildedrose.Item;
import com.gildedrose.qualitystrategies.impl.AgedItemQualityStrategy;
import com.gildedrose.qualitystrategies.impl.BackStagePassQualityStrategy;
import com.gildedrose.qualitystrategies.impl.ConjuredItemQualityStrategy;
import com.gildedrose.qualitystrategies.impl.NormalItemQualityStrategy;
import com.gildedrose.qualitystrategies.impl.LegendaryItemQualityStrategy;

public class QualityStrategyFactory {
	private static Map<String, QualityStrategy> qualityStrategies = new HashMap<String, QualityStrategy>();

	private QualityStrategyFactory() {
	}

	private static QualityStrategy defaultStrategy;
	static {
		defaultStrategy = new NormalItemQualityStrategy();

		qualityStrategies.put(ItemType.AGED_BRIE, new AgedItemQualityStrategy());
		qualityStrategies.put(ItemType.BACK_STAGE_PASS, new BackStagePassQualityStrategy());
		qualityStrategies.put(ItemType.CONJURED, new ConjuredItemQualityStrategy());
		qualityStrategies.put(ItemType.SULFURAS, new LegendaryItemQualityStrategy());
	}

	/**
	 * Returns the quality strategy depending on the item type received. Defaults to
	 * the NormalItemStrategy if no strategy is found.
	 * 
	 * @param item the item that a quality strategy needs to be found for.
	 * @return the quality strategy.
	 */
	public static QualityStrategy createQualityStrategy(Item item) {
		QualityStrategy strategy = qualityStrategies.get(item.name);
		if (strategy == null) {
			strategy = defaultStrategy;
		}
		return strategy;
	}

}
