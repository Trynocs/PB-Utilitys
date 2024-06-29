package com.trynocs.primeblocks.managers;

import javax.inject.Singleton;
import com.trynocs.primeblocks.PrimeblocksAddon;
import com.trynocs.primeblocks.PrimeblocksServer;
import com.trynocs.primeblocks.config.PrimeblocksConfig;
import com.trynocs.primeblocks.config.subconfig.DiscordRPCSubconfig;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.thirdparty.discord.DiscordActivity.Asset;
import net.labymod.api.util.I18n;

@Singleton
public class DiscordRPCManager {
  private final PrimeblocksAddon addon;
  private boolean updating;
  private String subserver = "Primeblocks.net";
  private String players = getPlayerCount();

  public DiscordRPCManager(PrimeblocksAddon addon) {
    this.addon = addon;
  }

  public void removeCustomRPC() {
    this.addon.labyAPI().thirdPartyService().discord().displayDefaultActivity();
  }

  public void updateCustomRPC(boolean joining) {
    if (!this.updating) {
      DiscordRPCSubconfig rpcConfig = ((PrimeblocksConfig)this.addon.configuration()).discordRPCSubconfig();
      if (!rpcConfig.enabled()) {
        this.removeCustomRPC();
      } else {
        this.updating = true;
        DiscordActivity currentActivity = this.addon.labyAPI().thirdPartyService().discord().getDisplayedActivity();
        DiscordActivity.Builder builder = DiscordActivity.builder(this);
        if (currentActivity != null) {
          builder.start(currentActivity.getStartTime());
        }

        builder.largeAsset(Asset.of("https://raw.githubusercontent.com/LabyMod/server-media/master/minecraft_servers/primeblocks/icon.png", "Primeblocks.net"));
        builder.details(I18n.translate("primeblocks.rpc.on", new Object[]{(Boolean)rpcConfig.showSubServer().get() ? this.getSubServer() : "Primeblocks.net"}));
        builder.state(joining ? I18n.translate("primeblocks.rpc.loading", new Object[0]) : ((Boolean)rpcConfig.showPlayerCount().get() ? I18n.translate("primeblocks.rpc.players", new Object[]{this.getPlayerCount()}) : ""));
        this.addon.labyAPI().thirdPartyService().discord().displayActivity(builder.build());
        this.updating = false;
      }
    }
  }

  public String getSubServer() {
    String value = this.getScoreboardScoreByValue("server");
    if (value != null && !value.equals(this.subserver)) {
      this.subserver = value.toLowerCase();
      return this.subserver;
    } else {
      return this.subserver;
    }
  }

  public String getPlayerCount() {
    String value = this.getScoreboardScoreByValue("players");
    if (value != null && !value.equals(this.players)) {
      this.players = value;
      return this.players;
    } else {
      return this.players;
    }
  }

  private String getScoreboardScoreByValue(String teamname) {
    Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
    if (scoreboard == null) {
      return null;
    } else {
      ScoreboardTeam scoreboardTeam = (ScoreboardTeam)scoreboard.getTeams().stream().filter((team) -> {
        return team.getTeamName().equals(teamname);
      }).findFirst().orElse((ScoreboardTeam) null);
      if (scoreboardTeam == null) {
        return null;
      } else {
        Component var5 = scoreboardTeam.getSuffix();
        if (var5 instanceof TextComponent) {
          TextComponent suffixComponent = (TextComponent)var5;
          if (suffixComponent.getChildren().isEmpty()) {
            return null;
          } else {
            TextComponent suffixChild = (TextComponent)suffixComponent.getChildren().get(0);
            return suffixChild.getText();
          }
        } else {
          return null;
        }
      }
    }
  }
}