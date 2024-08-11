package com.gildedrose.qualitystrategies.impl;


import java.util.ArrayList;
import java.util.List;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gildedrose.Item;
import com.gildedrose.qualitystrategies.ItemType;

@UseReporter(DiffReporter.class)
public class BackStagePassQualityStrategyTest {
	private static BackStagePassQualityStrategy qualityStrategy;

	private static final String itemName = ItemType.BACK_STAGE_PASS;

	@BeforeAll
	private static void initialize() {
		qualityStrategy = new BackStagePassQualityStrategy();
	}

	@Test
	public void testBackstagePassesQualityIncreasesBy1Upto11DaysToSellinDate() {
		//Arrange
		List<Item> backstagePasses = new ArrayList<>();
		for(int sellinDays = 12; sellinDays >= 11; sellinDays--) {
			backstagePasses.add(new Item(itemName, sellinDays, 0));
		}

		//Act
		backstagePasses.stream().forEach(qualityStrategy::updateItem);

		//Assert
		Approvals.verifyAll("Item sellin going from 12 to 11 ==> quality up by 1 (from 0)", "items", backstagePasses);
	}

	@Test
	public void testBackstagePassesQualityIncreasesBy2Between10And6DaysToSellinDate() {
		//Arrange
		List<Item> backstagePasses = new ArrayList<>();
		for(int sellinDays = 10; sellinDays >= 6; sellinDays--) {
			backstagePasses.add(new Item(itemName, sellinDays, 0));
		}

		//Act
		backstagePasses.stream().forEach(qualityStrategy::updateItem);

		//Assert
		Approvals.verifyAll("Item sellin going from 10 to 6 ==> quality up by 2 (from 0)", "items", backstagePasses);
	}

	@Test
	public void testBackstagePassesQualityIncreasesBy3Between5And1DayToSellinDate() {
		//Arrange
		List<Item> backstagePasses = new ArrayList<>();
		for(int sellinDays = 5; sellinDays >= 1; sellinDays--) {
			backstagePasses.add(new Item(itemName, sellinDays, 0));
		}

		//Act
		backstagePasses.stream().forEach(qualityStrategy::updateItem);

		//Assert
		Approvals.verifyAll("Item sellin going from 5 to 1 ==> quality up by 3 (from 0)", "items", backstagePasses);
	}


	@Test
	public void testBackstagePassesQualityGoesToZeroPassedSellinDate() {
		//Arrange
		var expected = """
			Quality drops to 0 (sellIn: 0 -> -1, quality: 10 -> 0)
					""";

		int startSellIn = 0;
		int startQuality = 10;
	
		Item item = new Item(itemName, startSellIn, startQuality);

		//Act
		qualityStrategy.updateItem(item);

		//Assert
		Approvals.verify(String.format("Quality drops to 0 (sellIn: %s -> %s, quality: %s -> %s)", startSellIn, item.sellIn, startQuality, item.quality), new Options().inline(expected));
	}

}
