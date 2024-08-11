package com.gildedrose;

import com.gildedrose.qualitystrategies.QualityStrategyFactory;
import com.gildedrose.qualitystrategies.QualityStrategy;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
        	Item item = items[i];
        	QualityStrategy strategy = QualityStrategyFactory.createQualityStrategy(item);
        	strategy.updateItem(item);
        }
    }
}