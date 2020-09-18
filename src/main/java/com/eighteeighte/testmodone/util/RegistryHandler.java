package com.eighteeighte.testmodone.util;

import com.eighteeighte.testmodone.blocks.BlockItemBase;
import com.eighteeighte.testmodone.blocks.UraniteOreBlock;
import com.eighteeighte.testmodone.damagesources.DamageSourceRadioactive;
import com.eighteeighte.testmodone.items.ItemBase;
import com.eighteeighte.testmodone.testmodone;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.util.registry.Registry.register;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, testmodone.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, testmodone.MOD_ID);

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // (FOR ITEMS)
    public static final RegistryObject<Item> YELLOW_CAKE = ITEMS.register("yellow_cake", ItemBase::new);
    public static final RegistryObject<Item> URANIUM_ROD = ITEMS.register("uranium_rod", ItemBase::new);

    // (FOR BLOCKS)
    public static final RegistryObject<Block> URANITE_ORE_BLOCK = BLOCKS.register("uranite_ore_block", UraniteOreBlock::new);

    // (FOR BLOCK ITEMS)
    public static final RegistryObject<Item> URANITE_ORE_BLOCK_ITEM =  ITEMS.register("uranite_ore_block", () -> new BlockItemBase(URANITE_ORE_BLOCK.get()));

    // (FOR RADIATION Effect)
    public static final Effect RADIATION = DamageSourceRadioactive.register(33, "radiation", new DamageSourceRadioactive(EffectType.HARMFUL, 4521796));
}
