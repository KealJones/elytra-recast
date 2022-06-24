package me.lunaluna.fabric.elytrarecast.compat.clothconfig;

import me.lunaluna.fabric.elytrarecast.Startup;
import me.lunaluna.fabric.elytrarecast.config.ConfigHelper;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.io.IOException;

public class ClothConfigScreen {
    public static Screen createScreen(Screen parent) {

        ConfigBuilder configBuilder = ConfigBuilder.create();

        configBuilder.setTitle(translate("title.elytra-recast.config"));
        configBuilder.setSavingRunnable(() -> {
                    try {
                        System.out.println(Startup.config);
                        ConfigHelper.write(Startup.config, Startup.configPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        ConfigCategory elytra = configBuilder.getOrCreateCategory(Text.of("elytra.elytra-recast.config"));
        ConfigCategory jumping = configBuilder.getOrCreateCategory(Text.of("jumping.elytra-recast.config"));
        elytra.addEntry(
                entryBuilder.startIntField(translate("cooldown.elytra.elytra-cast.config"), Startup.config.getCooldown())
                        .setSaveConsumer((cooldown) -> {
                            Startup.config.setCooldown(cooldown);
                        })
                        .build()
        );
        elytra.addEntry(
                entryBuilder.startBooleanToggle(translate("enabled.elytra.elytra-cast.config"), Startup.config.isEnabled())
                        .setSaveConsumer((enabled) -> {
                            Startup.config.setEnabled(enabled);
                        })
                        .build()
        );

        jumping.addEntry(
                entryBuilder.startIntField(translate("cooldown.jumping.elytra-cast.config"), Startup.config.getJumpCooldown())
                        .setSaveConsumer((jumpCooldown) -> {
                            Startup.config.setJumpCooldown(jumpCooldown);
                        })
                        .build()
        );
        jumping.addEntry(
                entryBuilder.startBooleanToggle(translate("enabled.jumping.elytra-cast.config"), Startup.config.isJumpEnabled())
                        .setSaveConsumer((enabled) -> {
                            Startup.config.setJumpEnabled(enabled);
                        })
                        .build()
        );

        return configBuilder.setParentScreen(parent).build();
    }

    private static Text translate(String key) {
        return new TranslatableText(key);
    }
}
