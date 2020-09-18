package com.eighteeighte.testmodone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class UraniteOreBlock extends OreBlock{
    static public double radiationLevel;
    public UraniteOreBlock(){
            super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f,8.0f)
                .sound(SoundType.STONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE));


        this.radiationLevel = Math.random()*1.5;
    }
}
