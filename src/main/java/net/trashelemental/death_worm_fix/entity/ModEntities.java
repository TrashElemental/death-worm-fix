package net.trashelemental.death_worm_fix.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.death_worm_fix.template;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, template.MOD_ID);






    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
