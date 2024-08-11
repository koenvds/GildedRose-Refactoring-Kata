package com.gildedrose.qualitystrategies.impl;


import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gildedrose.Item;
import com.gildedrose.qualitystrategies.ItemType;

@UseReporter(DiffReporter.class)
public class LegendaryItemQualityStrategyTest {
	private static LegendaryItemQualityStrategy qualityStrategy;

	private static final String itemName = ItemType.SULFURAS;

	@BeforeAll
	private static void initialize() {
		qualityStrategy = new LegendaryItemQualityStrategy();
	}
	
	@Test
	public void testLegendaryItemQualityRemainsStable() {
		//Arrange
		var expected = """
			Quality and sellIn do not change (sellIn: 10 -> 10, quality: 80 -> 80)
					""";
		int startSellIn = 10;
		int startQuality = 80;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality and sellIn do not change (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testLegendaryItemQualityRemainsStableIfOverdue() {
		//Arrange
		var expected = """
			Quality and sellIn do not change (sellIn: -10 -> -10, quality: 80 -> 80)
					""";
		int startSellIn = -10;
		int startQuality = 80;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality and sellIn do not change (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
}
