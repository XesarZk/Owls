package magiclobby.owls;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import magiclobby.owls.entity.EntityOwl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	private static List<EntityEntry> entities = Lists.newArrayList();

	public static final EntityEntry OWL = createEntity(EntityOwl.class, EntityOwl.NAME, EntityOwl.EGG_BG_COL, EntityOwl.EGG_FG_COL);

	private static EntityEntry createEntity(Class<? extends Entity> entityClass, String name, int eggPrimary, int eggSecondary) {
		ResourceLocation location = new ResourceLocation(Owls.MODID, name);
		EntityEntry entry = new EntityEntry(entityClass, name);
		entry.setRegistryName(location);
		entry.setEgg(new EntityList.EntityEggInfo(location, eggPrimary, eggSecondary));
		entities.add(entry);
		return entry;
	}

	@SubscribeEvent
	public static void registerOwls(Register<EntityEntry> event) {
		int id = 0;
		for (EntityEntry entry : entities) {
			event.getRegistry().register(entry);
			id++;
			EntityRegistry.registerModEntity(entry.getRegistryName(), entry.getEntityClass(), entry.getName(), id, Owls.instance, 64, 1,
					true, entry.getEgg().primaryColor, entry.getEgg().secondaryColor);
		}
	}

	public static void addOwlSpawn(Class<? extends EntityAgeable> owlClass, EntityEntry owlEntry, int defaultWeight, int defaultMin, int defaultMax, Biome... biomes) {
		if (defaultWeight != 0) {
			EntityRegistry.addSpawn(owlClass, defaultWeight, defaultMin, defaultMax, EnumCreatureType.CREATURE, biomes);
		}
	}
	
	@SubscribeEvent
	public void onItemRegister(Register<Item> e) {
		e.getRegistry().registerAll(ITEMS.toArray(new Item[ITEMS.size()]));
	}
	
	@SubscribeEvent
	public void onSoundEventRegister(Register<SoundEvent> e) {
	    IForgeRegistry<SoundEvent> reg = e.getRegistry();
	    reg.register(EntityOwl.SND_HOOT);
	    reg.register(EntityOwl.SND_HOOT2);
	    reg.register(EntityOwl.SND_HURT);
	}
}
