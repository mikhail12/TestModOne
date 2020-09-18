package com.eighteeighte.testmodone.events;

import com.eighteeighte.testmodone.blocks.UraniteOreBlock;
import com.eighteeighte.testmodone.damagesources.DamageSourceRadioactive;
import com.eighteeighte.testmodone.testmodone;
import com.eighteeighte.testmodone.util.ChunkUtils;
import com.eighteeighte.testmodone.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.world.ClientWorld;


import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = testmodone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    public static double radiationAcceleration;
    public static int fullRadiationLevel;
    public static ArrayList<Integer> blockPossesXx = new ArrayList<Integer>();
    public static ArrayList<Integer> blockPossesYy = new ArrayList<Integer>();
    public static ArrayList<Integer> blockPossesZz = new ArrayList<Integer>();
    public static ArrayList<BlockState> uraniumLocations = new ArrayList<BlockState>();
    public static double playerZCoord;
    public static double playerYCoord;
    public static double playerXCoord;

    public static void main(String[] args){
        oreLocator();
    }

    @SubscribeEvent
    public static void radiationCheck(TickEvent.PlayerTickEvent event) {

        oreLocator();
        fullRadiationLevel += Math.floor(radiationAcceleration);


    }
    @SubscribeEvent
    public static void radiationDamageAcceleration(LivingEvent.LivingUpdateEvent event) {
        LivingEntity player = event.getEntityLiving();
        playerXCoord = player.chunkCoordX;
        playerYCoord = player.chunkCoordY;
        playerZCoord = player.chunkCoordZ;

        double deltaXx;
        double deltaYy;
        double deltaZz;
        double distanceToObject;
        ArrayList<Double> arrayDistancesToObject = new ArrayList<Double>();

        radiationAcceleration = 0;

        for (int i = 1; i <= blockPossesXx.size(); i++) {
            deltaXx = playerXCoord - blockPossesXx.get(i);
            deltaYy = playerYCoord - blockPossesYy.get(i);
            deltaZz = playerZCoord - blockPossesZz.get(i);
            distanceToObject = Math.sqrt((deltaXx * deltaXx) + (deltaYy * deltaYy) + (deltaZz * deltaZz));
            arrayDistancesToObject.add(distanceToObject);

        }
        double radiationLevel;
        for (double distance : arrayDistancesToObject){
            radiationLevel = UraniteOreBlock.radiationLevel;
            radiationAcceleration += 1/distance*(radiationLevel);
        }
        if (fullRadiationLevel>= 1000) {
            RegistryHandler.RADIATION.performEffect(player, (int) fullRadiationLevel);
        }
    }
    private static void oreLocator() {

        // Get the ChunkProviderClient from WorldClient
        try {
            AbstractChunkProvider chunkProvider = Minecraft.getInstance().world.getChunkProvider();
        } catch(NullPointerException e){
            System.err.println("Yep, restart, its a NullPointerException:"+e);
        }
        // Get the list of all loaded chunks.
        List<?> chunks = ChunkUtils.stealAndGetField((Object)chunkProvider, List.class);


        for (Object chunk : chunks) {
            Chunk c = (Chunk) chunk;

            for (int xx = 0; xx < 16; xx++) {
                for (int zz = 0; zz < 16; zz++) {
                    for (int yy = 0; yy < 50; yy++) {

                        BlockState block = c.getBlockState(new BlockPos(yy, xx, zz));
                        if (block.getBlock() instanceof UraniteOreBlock) {
                            blockPossesXx.add(xx);
                            blockPossesYy.add(yy);
                            blockPossesZz.add(zz);
                            uraniumLocations.add(block);
                        }


                    }
                }
            }

        }
    }

}

