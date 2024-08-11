package com.gildedrose.qualitystrategies.impl;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gildedrose.Item;
import com.gildedrose.qualitystrategies.QualityStrategy;

public class NormalItemQualtityStrategyTest {
	private static QualityStrategy qualityStrategy;

	private static final String itemName = "Normal";

	@BeforeAll
	private static void initialize() {
		qualityStrategy = new NormalItemQualityStrategy();
	}
	
	@Test
	public void testNormalItemQualityDecreasesBy1OnValidItem() {
		//Arrange
		var expected = """
			Quality is down by 1 (sellIn: 10 -> 9, quality: 10 -> 9)
					""";
		int startSellIn = 10;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);
		
		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is down by 1 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testNormalItemQualityDecreasesBy2OnPassedDueDateItem() {
		//Arrange
		var expected = """
			Quality is down by 2 (sellIn: 0 -> -1, quality: 10 -> 8)
				""";
		
		int startSellIn = 0;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is down by 2 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testItemQualityCannotGoBelowZero() {
		//Arrange
		var expected = """
			Quality does not got below zero (sellIn: 10 -> 9, quality: 0 -> 0)
				""";
		
		int startSellIn = 10;
		int startQuality = 0;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality does not got below zero (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testItemQualityBelowZeroDoesNotChange() {
		//Arrange
		var expected = """
			Quality does not change if starts below zero (sellIn: 10 -> 9, quality: -100 -> -100)
				""";
		
		int startSellIn = 10;
		int startQuality = -100;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality does not change if starts below zero (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
	
	@Test
	public void testItemQualityAbove50DecreasesBy1OnValidItem() {
		//Arrange
		var expected = """
			Quality is down by 1 even if quality starts above maximum (sellIn: 10 -> 9, quality: 100 -> 99)
				""";
		
		int startSellIn = 10;
		int startQuality = 100;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);
		
		//Assert
		Approvals.verify(String.format("Quality is down by 1 even if quality starts above maximum (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}
}