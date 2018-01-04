package magiclobby.owls.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import magiclobby.owls.Owls;
import magiclobby.owls.Log;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Config {

    public static class Section {
        public final String name;
        public final String lang;

        public Section(String name, String lang) {
            this.name = name;
            this.lang = lang;
            register();
        }

        private void register() {
            sections.add(this);
        }

        public String lc() {
            return name.toLowerCase();
        }
    }

    public static final List<Section> sections;

    static {
        sections = new ArrayList<Section>();
    }

    public static Configuration config;
    public static File configDirectory;

    public static final Section sectionOwl = new Section("Owl", "owl");
    public static boolean owlEnabled = true;
    public static int owlId = 689998;
    public static int owlHealth = 10;
    public static int owlAttackDamage = 4;
    public static float owlSpiderDamageMultiplier = 2;
    public static float owlHootVolumeMult = 0.8f;
    public static int owlHootInterval = 1000;
    public static int owlTimeBetweenEggsMin = 12000;
    public static int owlTimeBetweenEggsMax = 24000;
    public static int entityOwlEggId = 679991;

    public static void load(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Config());
        configDirectory = event.getModConfigurationDirectory();
        if (!configDirectory.exists()) {
            configDirectory.mkdir();
        }

        File configFile = new File(configDirectory, "Owls.cfg");
        config = new Configuration(configFile);
        syncConfig();
    }

    public static void syncConfig() {
        try {
            Config.processConfig(config);
        } catch (Exception e) {
            Log.error("Owls has a problem loading it's configuration");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if (event.getModID().equals(Owls.MODID)) {
            Log.info("Updating config...");
            syncConfig();
        }
    }

    public static void processConfig(Configuration config) {
        owlEnabled = config.getBoolean("owlEnabled", sectionOwl.name, owlEnabled, "If false Owl will be disabled");
        owlId = config.get(sectionOwl.name, "owlId", owlId, "Mob ID").getInt(owlId);
        owlHealth = config.get(sectionOwl.name, "owlHealth", owlHealth, "Owl Health").getInt(owlHealth);
        owlAttackDamage = config.get(sectionOwl.name, "owlAttackDamage", owlAttackDamage, "Owl Attack Damage").getInt(owlAttackDamage);
        owlSpiderDamageMultiplier = (float) config.get(sectionOwl.name, "owlSpiderDamageMultiplier", owlSpiderDamageMultiplier, "Damage multiplier against spiders")
                .getDouble(owlSpiderDamageMultiplier);
        owlHootVolumeMult = (float) config
                .get(sectionOwl.name, "owlHootVolumeMult", owlHootVolumeMult, "Adjusts the owls hoot volume. Higher value is loader")
                .getDouble(owlHootVolumeMult);
        owlHootInterval = config.get(sectionOwl.name, "owlHootInterval", owlHootInterval, "Aprox. number of ticks between hoots").getInt(owlHootInterval);
        owlTimeBetweenEggsMin = config.get(sectionOwl.name, "owlTimeBetweenEggsMin", owlTimeBetweenEggsMin, "Min ticks between egg laying")
                .getInt(owlTimeBetweenEggsMin);
        owlTimeBetweenEggsMax = config.get(sectionOwl.name, "owlTimeBetweenEggsMax", owlTimeBetweenEggsMax, "Max ticks between egg laying")
                .getInt(owlTimeBetweenEggsMax);

        entityOwlEggId = config.get(sectionOwl.name, "entityOwlEggId", entityOwlEggId, "ID for thrown owl egg Entity").getInt(entityOwlEggId);
    }
    private Config() {
    }
}
