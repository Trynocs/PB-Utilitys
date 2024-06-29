package com.trynocs.primeblocks;

import com.trynocs.primeblocks.config.PrimeblocksConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.AbstractServer;
import net.labymod.api.event.Phase;

public class PrimeblocksServer extends AbstractServer {
  private final PrimeblocksAddon addon;
  private boolean connected;

  public PrimeblocksServer(PrimeblocksAddon addon) {
    super("primeblocks");
    this.addon = addon;
    this.connected = false;
  }

  public void loginOrSwitch(AbstractServer.LoginPhase phase) {
    if (phase == LoginPhase.LOGIN) {
      this.connected = true;
    } else if (phase == LoginPhase.SWITCH && (Boolean)((PrimeblocksConfig)this.addon.configuration()).autoFly().get()) {
      Laby.labyAPI().minecraft().executeNextTick(() -> {
        Laby.references().chatExecutor().chat("/fly", false);
      });
    }

    Laby.labyAPI().minecraft().executeNextTick(() -> {
      this.addon.rpcManager.updateCustomRPC(phase == LoginPhase.LOGIN);
    });
  }

  public void disconnect(Phase phase) {
    if (phase == Phase.POST) {
      this.connected = false;
    }

    this.addon.rpcManager.removeCustomRPC();
  }

  public boolean isConnected() {
    return this.connected;
  }
}