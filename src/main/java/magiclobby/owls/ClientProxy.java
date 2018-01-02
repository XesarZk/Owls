package magiclobby.owls;

import magiclobby.owls.entity.EntityOwl;
import magiclobby.owls.entity.EntityOwlEgg;
import magiclobby.owls.entity.render.RenderEntityOwlEgg;
import magiclobby.owls.entity.render.RenderOwl;
import magiclobby.owls.item.ItemOwlEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

  @Override
  public World getClientWorld() {
    return FMLClientHandler.instance().getClient().world;
  }

  @Override
  public EntityPlayer getClientPlayer() {
    return Minecraft.getMinecraft().player;
  }

  @Override
  public void preInit() {
    super.preInit();
    
    MinecraftForge.EVENT_BUS.register(this);

    RenderingRegistry.registerEntityRenderingHandler(EntityOwl.class, RenderOwl.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(EntityOwlEgg.class, RenderEntityOwlEgg.FACTORY);

  }
  
  @SubscribeEvent
  public void onModelRegister(ModelRegistryEvent e) {
	  regRenderer(Owls.itemOwlEgg, ItemOwlEgg.NAME);
  }

  private void regRenderer(Item item, int meta, String name) {
    regRenderer(item, meta, Owls.MODID, name);
  }

  private void regRenderer(Item item, int meta, String modId, String name) {
    String resourceName;
    if (modId != null) {
      resourceName = modId + ":" + name;
    } else {
      resourceName = name;
    }
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resourceName, "inventory"));
  }

  private void regRenderer(Item item, String name) {
    regRenderer(item, 0, name);
  }

  
  @Override
  public String translate(String unlocalized) {
	  return I18n.format(unlocalized);
  }

}
