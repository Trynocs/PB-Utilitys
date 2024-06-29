package com.trynocs.primeblocks.config.subconfig;

import com.trynocs.primeblocks.PrimeblocksAddon;
import com.trynocs.primeblocks.PrimeblocksServer;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DiscordRPCSubconfig extends Config {
  @ParentSwitch
  private final ConfigProperty<Boolean> enabled = new ConfigProperty(true);
  @SwitchSetting
  @SpriteSlot(
      size = 32,
      x = 2
  )
  private final ConfigProperty<Boolean> showSubServer = new ConfigProperty(true);
  @SwitchSetting
  @SpriteSlot(
      size = 32,
      x = 3
  )
  private final ConfigProperty<Boolean> showPlayerCount = new ConfigProperty(true);

  public DiscordRPCSubconfig() {
    this.enabled.addChangeListener((property, oldValue, newValue) -> {
      PrimeblocksAddon.updateRPC();
    });
    this.showSubServer.addChangeListener((property, oldValue, newValue) -> {
      PrimeblocksAddon.updateRPC();
    });
    this.showPlayerCount.addChangeListener((property, oldValue, newValue) -> {
      PrimeblocksAddon.updateRPC();
    });
  }

  public boolean enabled() {
    return (Boolean)this.enabled.get();
  }

  public ConfigProperty<Boolean> showSubServer() {
    return this.showSubServer;
  }

  public ConfigProperty<Boolean> showPlayerCount() {
    return this.showPlayerCount;
  }
}