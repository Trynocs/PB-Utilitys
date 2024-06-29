package com.trynocs.primeblocks.config;

import com.trynocs.primeblocks.config.subconfig.ContextSubconfig;
import com.trynocs.primeblocks.config.subconfig.DiscordRPCSubconfig;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
@SpriteTexture("settings.png")
public class PrimeblocksConfig extends AddonConfig {
  @SpriteSlot(
      size = 32
  )
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty(true);
  @SpriteSlot(
      size = 32,
      x = 1
  )
  private final DiscordRPCSubconfig discordRPCSubconfig = new DiscordRPCSubconfig();
  @SpriteSlot(
      size = 32,
      y = 1
  )
  private final ContextSubconfig contextSubconfig = new ContextSubconfig();
  @SpriteSlot(
      size = 32,
      y = 2
  )
  @SwitchSetting
  private final ConfigProperty<Boolean> autoFly = new ConfigProperty(true);
  @SettingSection("chat")
  @SpriteSlot(
      size = 32,
      y = 2,
      x = 1
  )
  @SwitchSetting
  private final ConfigProperty<Boolean> coloredMentions = new ConfigProperty(true);

  public PrimeblocksConfig() {
  }

  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public DiscordRPCSubconfig discordRPCSubconfig() {
    return this.discordRPCSubconfig;
  }

  public ContextSubconfig contextSubconfig() {
    return this.contextSubconfig;
  }

  public ConfigProperty<Boolean> autoFly() {
    return this.autoFly;
  }

  public ConfigProperty<Boolean> coloredMentions() {
    return this.coloredMentions;
  }
}
