package magiclobby.owls;

import static magiclobby.owls.Owls.MODID;
import static magiclobby.owls.Owls.MOD_NAME;
import static magiclobby.owls.Owls.VERSION;

import magiclobby.owls.config.Config;
import magiclobby.owls.item.ItemOwlEgg;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

  @EventHandler
  public void load(FMLInitializationEvent event) {
    instance = this;
    proxy.init();
  }
}
