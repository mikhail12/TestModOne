package com.eighteeighte.testmodone;


import com.eighteeighte.testmodone.events.ModClientEvents;
import com.eighteeighte.testmodone.util.RegistryHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("tmo")
public class testmodone
{

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tmo";

    public testmodone(){

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        int radiationLevel = 0;

        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void main(String[] args){
    }

    private void setup(final FMLCommonSetupEvent event) { }

    private void doClientStuff(final FMLClientSetupEvent event) { }

    public static final ItemGroup TAB = new ItemGroup("tmoTab"){
        @Override
        public ItemStack createIcon(){
            return new ItemStack(RegistryHandler.URANITE_ORE_BLOCK.get());
        }
    };

}