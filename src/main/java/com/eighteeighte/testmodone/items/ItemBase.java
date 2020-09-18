package com.eighteeighte.testmodone.items;

import com.eighteeighte.testmodone.testmodone;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {

    public ItemBase() {
        super(new Item.Properties().group(testmodone.TAB));
    }

}
