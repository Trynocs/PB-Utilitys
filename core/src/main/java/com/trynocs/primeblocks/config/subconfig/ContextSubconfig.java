package com.trynocs.primeblocks.config.subconfig;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class ContextSubconfig extends Config {
  @SpriteSlot(
      size = 32,
      y = 1,
      x = 1
  )
  @SwitchSetting
  private final ConfigProperty<Boolean> payContext = new ConfigProperty(true);
  @SpriteSlot(
      size = 32,
      y = 1,
      x = 2
  )
  @SwitchSetting
  private final ConfigProperty<Boolean> clanInviteContext = new ConfigProperty(true);

  public ContextSubconfig() {
  }

  public ConfigProperty<Boolean> payContext() {
    return this.payContext;
  }

  public ConfigProperty<Boolean> clanInviteContext() {
    return this.clanInviteContext;
  }
}