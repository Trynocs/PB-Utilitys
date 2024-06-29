package com.trynocs.primeblocks;

import com.trynocs.primeblocks.config.PrimeblocksConfig;
import com.trynocs.primeblocks.context.ClanInviteContext;
import com.trynocs.primeblocks.context.PayContext;
import com.trynocs.primeblocks.listeners.ChatReceiveListener;
import com.trynocs.primeblocks.listeners.PlayerInfo;
import com.trynocs.primeblocks.managers.DiscordRPCManager;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;

@AddonMain
public class PrimeblocksAddon extends LabyAddon<PrimeblocksConfig> {
  public DiscordRPCManager rpcManager;
  private static PrimeblocksAddon instance;
  private PrimeblocksServer server;

  public PrimeblocksAddon() {
  }

  protected void preConfigurationLoad() {
    Laby.references().revisionRegistry().register(new SimpleRevision("primeblocks", new SemanticVersion("1.0.0"), "2024-06-29"));
  }

  protected void enable() {
    this.rpcManager = new DiscordRPCManager(this);
    instance = this;
    this.registerSettingCategory();
    this.registerListener(new ChatReceiveListener(this));
    this.registerListener(new PlayerInfo(this));
    this.labyAPI().interactionMenuRegistry().register(new ClanInviteContext(this));
    this.labyAPI().interactionMenuRegistry().register(new PayContext(this));
    this.labyAPI().serverController().registerServer(this.server = new PrimeblocksServer(this));
  }

  public static void updateRPC() {
    if (instance != null) {
      instance.rpcManager.updateCustomRPC(false);
    }

  }

  public PrimeblocksServer server() {
    return this.server;
  }

  protected Class<? extends PrimeblocksConfig> configurationClass() {
    return PrimeblocksConfig.class;
  }
}
