package com.trynocs.primeblocks.context;

import com.trynocs.primeblocks.PrimeblocksAddon;
import com.trynocs.primeblocks.config.PrimeblocksConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.I18n;

public class ClanInviteContext implements BulletPoint {
  private final PrimeblocksAddon addon;

  public ClanInviteContext(PrimeblocksAddon addon) {
    this.addon = addon;
  }

  public Component getTitle() {
    return Component.text(I18n.translate("primeblocks.context.clanInvite", new Object[0]));
  }

  public Icon getIcon() {
    return null;
  }

  public void execute(Player player) {
    Laby.labyAPI().minecraft().executeNextTick(() -> {
      Laby.labyAPI().minecraft().chatExecutor().chat("/clan invite " + player.getName());
    });
  }

  public boolean isVisible(Player playerInfo) {
    return this.addon.server().isConnected() && (Boolean)((PrimeblocksConfig)this.addon.configuration()).contextSubconfig().clanInviteContext().get();
  }
}