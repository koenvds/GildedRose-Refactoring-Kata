package com.gildedrose;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

@UseReporter(DiffReporter.class)
public class GildedRoseApprovalTest {

	@Test
	public void foo() {

		Item[] items = new Item[] { new Item("foo", 0, 0) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void thirtyDays() {

		ByteArrayOutputStream fakeoutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(fakeoutput));
		System.setIn(new ByteArrayInputStream("a\n".getBytes()));

		Program.main();
		String output = fakeoutput.toString();

		Approvals.verify(output);
	}

	@Test
	public void testNormalItemQualityDecreasesByOneIfSelldateNotReached() {
		Item[] items = new Item[] { new Item("standardItem", 10, 10) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testNormalItemQualityDecreasesByTwoIfSelldateReached() {
		Item[] items = new Item[] { new Item("standardItem", 0, 10) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	//According to the requirements, this does not really happen as the minimum is 0, but it's not enforced by the code.
	public void testNormalItemQualityDoesNotChangeBelowZero() {
		Item[] items = new Item[] { new Item("standardItem", -100, -100) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testNormalItemQualityDecreasesNormallyIfItemQualityIsHigherThan50() {
		Item[] items = new Item[] { new Item("standardItem", 10, 100) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	
	@Test
	public void testItemQualityCannotGoBelowZero() {
		Item[] items = new Item[] { new Item("standardItem", -100, 0) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testAgedBrieQualityIncreasesByOne() {
		Item[] items = new Item[] { new Item("Aged Brie", 1, 11) , new Item("Aged Brie", 0, 11), new Item("Aged Brie", -1, 11)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}
	
	@Test
	public void testAgedBrieQualityDoesNotGoBeyond50() {
		Item[] items = new Item[] { new Item("Aged Brie", 10, 49) , new Item("Aged Brie", 0, 50), new Item("Aged Brie", -1, 50)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	//According to the requirements, this does not really happen as the max is 50, but it's not enforced by the code.
	public void testAgedBrieQualityDoesNotChangeBeyond51() {
		Item[] items = new Item[] { new Item("Aged Brie", 10, 51)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testSulfurasQualityRemainsStable() {
		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 1, 80) ,  new Item("Sulfuras, Hand of Ragnaros", 0, 80),  new Item("Sulfuras, Hand of Ragnaros", -1, 80)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}
	
	@Test
	public void testBackstagePassesQualityIncreasesByOneUntil11DaysToSellinDate() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10), new Item("Backstage passes to a TAFKAL80ETC concert", 30, 10)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}
	
	@Test
	public void testBackstagePassesQualityIncreasesBy2From10To6DaysToSellinDate() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2), new Item("Backstage passes to a TAFKAL80ETC concert", 6, 2)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testBackstagePassesQualityIncreasesBy3From5To1DaysToSellinDate() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 0), new Item("Backstage passes to a TAFKAL80ETC concert", 1, 0)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testBackstagePassesQualityGoesToZeroOnSellinDate() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 49), new Item("Backstage passes to a TAFKAL80ETC concert", -1, 49)};
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testConjuredItemQualityDecreasesByTwoIfSelldateNotReached() {
		Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 10) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}

	@Test
	public void testConjuredItemQualityDecreasesByFourIfSelldateReached() {
		Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 10) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Approvals.verifyAll("Items", items);
	}


}
