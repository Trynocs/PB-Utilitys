package com.trynocs.primeblocks.context;

import com.trynocs.primeblocks.PrimeblocksAddon;
import com.trynocs.primeblocks.PrimeblocksServer;
import com.trynocs.primeblocks.config.PrimeblocksConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.I18n;

public class PayContext implements BulletPoint {
  private final PrimeblocksAddon addon;

  public PayContext(PrimeblocksAddon addon) {
    this.addon = addon;
  }

  public Component getTitle() {
    return Component.text(I18n.translate("primeblocks.context.pay", new Object[0]));
  }

  public Icon getIcon() {
    return null;
  }

  public void execute(Player player) {
    Laby.labyAPI().minecraft().executeNextTick(() -> {
      Laby.labyAPI().minecraft().openChat("/pay " + player.getName() + " ");
    });
  }

  public boolean isVisible(Player playerInfo) {
    return this.addon.server().isConnected() && (Boolean)((PrimeblocksConfig)this.addon.configuration()).contextSubconfig().payContext().get();
  }
}