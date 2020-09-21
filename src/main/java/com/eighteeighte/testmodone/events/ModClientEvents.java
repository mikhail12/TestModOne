package com.eighteeighte.testmodone.events;


import com.eighteeighte.testmodone.blocks.UraniteOreBlock;
import com.eighteeighte.testmodone.damagesources.DamageSourceRadioactive;
import com.eighteeighte.testmodone.testmodone;
import com.eighteeighte.testmodone.util.ChunkUtils;
import com.eighteeighte.testmodone.util.RegistryHandler;
import javafx.concurrent.Task;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.world.ClientWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;


@Mod.EventBusSubscriber(modid = testmodone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    public static int radiationAcceleration;
    public static int fullRadiationLevel;
    public static ArrayList<Integer> blockPossesXx = new ArrayList<Integer>();
    public static ArrayList<Integer> blockPossesYy = new ArrayList<Integer>();
    public static ArrayList<Integer> blockPossesZz = new ArrayList<Integer>();
    public static ArrayList<BlockState> uraniumLocations = new ArrayList<BlockState>();
    public static double playerZCoord;
    public static double playerYCoord;
    public static double playerXCoord;
    public static int radiationAmplifier;
    public static boolean eventIsTrue = false;
    private Object ClientWorld;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void checkIfEventIsTrue(WorldEvent.Load event){
        if (event.getWorld().getWorldInfo() != null) {
            eventIsTrue = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void radiationCheck(TickEvent.ClientTickEvent event) {
        System.out.println("This tickevent is running");
        if(event.phase == TickEvent.Phase.END) {
            LivingEntity player = Minecraft.getInstance().player;
            System.out.println("This tickevent is running");
            if (eventIsTrue){
                System.out.println("This tickevent is running");
                playerXCoord = player.chunkCoordX;
                playerYCoord = player.chunkCoordY;
                playerZCoord = player.chunkCoordZ;

                oreLocator(player);
                getRadiationDamageAcceleration(player);

                fullRadiationLevel = fullRadiationLevel + radiationAcceleration;

                radiationAmplifier = fullRadiationLevel / 1000;
                if (fullRadiationLevel >= 1000) {
                    DamageSourceRadioactive.doEffect(player, radiationAmplifier);
                }
                System.out.println("RadiationLevel is:" + fullRadiationLevel);
                Minecraft.getInstance().player.sendChatMessage("RadiationLevel is:" + fullRadiationLevel);
            }
        }
    }

    public static void getRadiationDamageAcceleration(LivingEntity player) {
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
    private static void oreLocator(LivingEntity player) {
        /*
        // Get the ChunkProviderClient from WorldClient
        ClientChunkProvider chunkProvider = null;
        Minecraft playerworld = Minecraft.getInstance();
        ClientWorld gameWorld = playerworld.world;
        if (gameWorld == null) {
            return;
        }
        chunkProvider = new ClientChunkProvider(gameWorld, 2);
        // Get the list of all loaded chunks
        List<?> chunks = ChunkUtils.stealAndGetField(chunkProvider, List.class );
        */
        World world = Minecraft.getInstance().world;
        /*if (world == null) {
            return;
        }
         */
        BlockPos playerPos = player.getPosition();
        /*
        if (playerPos == null) {
            return;
        }

         */
        Chunk chunkOfWorld = world.getChunkAt(playerPos);
        /*if (chunkOfWorld == null) {
            return;
        }
        */
        for (int xx = 0; xx < 16; xx++) {
            for (int zz = 0; zz < 16; zz++) {
                for (int yy = 0; yy < 256; yy++) {
                    BlockPos position = new BlockPos(xx+chunkOfWorld.getPos().x,yy,zz+chunkOfWorld.getPos().z);
                    BlockState block = chunkOfWorld.getBlockState(position);
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



