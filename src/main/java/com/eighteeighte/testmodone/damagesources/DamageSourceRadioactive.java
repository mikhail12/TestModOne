package com.eighteeighte.testmodone.damagesources;

import com.eighteeighte.testmodone.util.RegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.registry.Registry;

@net.minecraftforge.registries.ObjectHolder("minecraft")

public class DamageSourceRadioactive extends Effect {

    public static void main(String[] args){
    }
    public DamageSourceRadioactive(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    public static Effect register(int id, String key, Effect effectIn) {
        return Registry.register(Registry.EFFECTS, id, key, effectIn);
    }

    public static void performEffect() {
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getHealth() > 1.0F) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }
    }
}

