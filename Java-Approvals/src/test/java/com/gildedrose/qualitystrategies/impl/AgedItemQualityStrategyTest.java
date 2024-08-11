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
public class AgedItemQualityStrategyTest {
	private static AgedItemQualityStrategy qualityStrategy;

	private static final String itemName = ItemType.AGED_BRIE;
	
	@BeforeAll
	private static void initialize() {
		qualityStrategy = new AgedItemQualityStrategy();
	}
	
	@Test
	public void testAgedItemQualityIncreasesBy1IfSellinNotReached() {
		//Arrange
		var expected = """
			Quality is up by 1 (sellIn: 10 -> 9, quality: 10 -> 11)
					""";
		int startSellIn = 10;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is up by 1 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testAgedItemQualityIncreasesBy2IfSelIinOverdue() {
		//Arrange
		var expected = """
			Quality is up by 2 (sellIn: 0 -> -1, quality: 10 -> 12)
					""";
		int startSellIn = 0;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is up by 2 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testAgedItemQualityDoesNotGoBeyond50() {
		//Arrange
		var expected = """
			Quality does not got above 50 (sellIn: 10 -> 9, quality: 50 -> 50)
				""";
		
		int startSellIn = 10;
		int startQuality = 50;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality does not got above 50 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testAgedItemQualityAbove50DoesNotChange() {
		//Arrange
		var expected = """
			Quality does not change if starts above 50 (sellIn: 10 -> 9, quality: 100 -> 100)
				""";
		
		int startSellIn = 10;
		int startQuality = 100;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality does not change if starts above 50 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testAgedItemQualityBelow0IncreasesBy1OnValidItem() {
		//Arrange
		var expected = """
			Quality is up by 1 even if quality starts below minimum (sellIn: 10 -> 9, quality: -100 -> -99)
				""";
		
		int startSellIn = 10;
		int startQuality = -100;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is up by 1 even if quality starts below minimum (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}	


}
