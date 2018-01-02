package magiclobby.owls.entity;

import magiclobby.owls.config.Config;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public enum MobInfo {
  OWL(EntityOwl.class, EntityOwl.NAME, EntityOwl.EGG_BG_COL, EntityOwl.EGG_FG_COL, true,
      Config.owlHealth, Config.owlAttachDamage, Config.owlId);

  public static boolean isDisabled(Class<? extends EntityLiving> clz) {
    if(clz == null) {
      return false;
    }    
    for(MobInfo info : values()) {
      if(clz == info.getClz() && !info.isEnabled()) {
        return true;
      }
    }    
    return false;
  }
  
  final Class<? extends EntityLiving> clz;
  final String name;
  final int bgCol;
  final int fgCol;
  final boolean enabled;
  final double maxHealth;
  final double attackDamage;
  final int entityId;

  private MobInfo(Class<? extends EntityLiving> clz, String name, int bgCol, int fgCol, boolean enabled, double baseHealth, double baseAttack, int entityId) {
    this.clz = clz;
    this.name = name;
    this.bgCol = bgCol;
    this.fgCol = fgCol;
    this.enabled = enabled;
    this.entityId = entityId;
    maxHealth = baseHealth;
    attackDamage = baseAttack;
  }

  public Class<? extends EntityLiving> getClz() {
    return clz;
  }

  public String getName() {
    return name;
  }

  public int getEggBackgroundColor() {
    return bgCol;
  }

  public int getEggForegroundColor() {
    return fgCol;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void applyAttributes(EntityLivingBase entity) {
    entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
    IAttributeInstance ai = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
    if(ai == null) {
      entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      ai = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
    }
    ai.setBaseValue(attackDamage);
  }

  public int getEntityId() {
    return entityId;
  }

}
