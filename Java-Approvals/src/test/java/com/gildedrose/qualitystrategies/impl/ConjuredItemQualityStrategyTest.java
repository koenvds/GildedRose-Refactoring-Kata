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
public class ConjuredItemQualityStrategyTest {
	private static ConjuredItemQualityStrategy qualityStrategy;

	private static final String itemName = ItemType.CONJURED;

	@BeforeAll
	private static void initialize() {
		qualityStrategy = new ConjuredItemQualityStrategy();
	}
	
	@Test
	public void testConjuredItemQualityDecreasesBy2OnValidItem() {
		//Arrange
		var expected = """
			Quality is down by 2 (sellIn: 10 -> 9, quality: 10 -> 8)
					""";
		int startSellIn = 10;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is down by 2 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testConjuredItemQualityDecreasesBy4OnPassedDueDateItem() {
		//Arrange
		var expected = """
			Quality is down by 2 (sellIn: 0 -> -1, quality: 10 -> 6)
					""";
		int startSellIn = 0;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is down by 2 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
}
