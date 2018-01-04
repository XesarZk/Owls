package magiclobby.owls;

import static magiclobby.owls.Owls.MODID;
import static magiclobby.owls.Owls.MOD_NAME;
import static magiclobby.owls.Owls.VERSION;

import com.google.common.collect.Lists;
import magiclobby.owls.config.Config;
import magiclobby.owls.entity.EntityOwl;
import magiclobby.owls.item.ItemOwlEgg;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;
import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod(modid = MODID, name = MOD_NAME, version = VERSION)
public class Owls {

  public static final String MODID = "owls";
  public static final String MOD_NAME = "Owls";
  public static final String VERSION = "@VERSION@";

  @Instance(MODID)
  public static Owls instance;

  @SidedProxy(clientSide = "magiclobby.owls.ClientProxy", serverSide = "magiclobby.owls.CommonProxy")
  public static CommonProxy proxy;

  public static ItemOwlEgg itemOwlEgg;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
	  
	MinecraftForge.EVENT_BUS.register(new RegistryHandler());

    Config.load(event);
    itemOwlEgg = ItemOwlEgg.create();

    proxy.preInit();
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    List<Biome> spawnable_biomes = Lists.newArrayList();
    for (Biome biome : Biome.REGISTRY) {
      Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
      if (types.contains(FOREST) && !types.contains(NETHER) && !biome.getSpawnableList(EnumCreatureType.CREATURE).isEmpty()) {
        spawnable_biomes.add(biome);
      }
    }
    RegistryHandler.addOwlSpawn(EntityOwl.class, RegistryHandler.OWL, 2, 1, 4, spawnable_biomes.toArray(new Biome[spawnable_biomes.size()]));
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {
    instance = this;
    proxy.init();
  }
}
