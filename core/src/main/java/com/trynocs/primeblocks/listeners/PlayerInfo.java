package com.trynocs.primeblocks.listeners;

import com.trynocs.primeblocks.PrimeblocksAddon;
import com.trynocs.primeblocks.PrimeblocksServer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.util.Debounce;

public class PlayerInfo {
  private final PrimeblocksAddon addon;

  public PlayerInfo(PrimeblocksAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
    if (this.addon.server().isConnected()) {
      Debounce.of("refresh-primeblocks-discord-rpc", 2000L, () -> {
        this.addon.rpcManager.updateCustomRPC(false);
      });
    }
  }

  @Subscribe
  public void onPlayerInfoRemove(PlayerInfoRemoveEvent event) {
    if (this.addon.server().isConnected()) {
      Debounce.of("refresh-primeblocks-discord-rpc", 2000L, () -> {
        this.addon.rpcManager.updateCustomRPC(false);
      });
    }
  }
}