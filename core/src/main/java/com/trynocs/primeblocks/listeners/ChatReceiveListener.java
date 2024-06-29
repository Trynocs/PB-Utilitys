package com.trynocs.primeblocks.listeners;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import com.trynocs.primeblocks.PrimeblocksAddon;
import com.trynocs.primeblocks.config.PrimeblocksConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

public class ChatReceiveListener {
  private final PrimeblocksAddon addon;
  private final Pattern pattern = Pattern.compile("@\\w{3,16}", 2);

  public ChatReceiveListener(PrimeblocksAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onChatReceive(ChatReceiveEvent event) {
    if (this.addon.server().isConnected()) {
      Component message = event.message();
      String text = event.chatMessage().getPlainText();
      if ((Boolean)((PrimeblocksConfig)this.addon.configuration()).coloredMentions().get() && text.contains("@")) {
        Iterator var4 = this.pattern.matcher(text).results().toList().iterator();

        while(var4.hasNext()) {
          MatchResult matcher = (MatchResult)var4.next();
          if (!matcher.group().equals("@TEAM") && !matcher.group().equals("@CLAN")) {
            this.replaceComponent(message, matcher.group(), () -> {
              return Component.text(matcher.group(), NamedTextColor.AQUA).copy();
            });
          }
        }

        event.setMessage(message);
      }

    }
  }

  private void replaceComponent(Component component, String target, Supplier<Component> replacement) {
    Iterator var4 = component.getChildren().iterator();

    Component argument;
    while(var4.hasNext()) {
      argument = (Component)var4.next();
      this.replaceComponent(argument, target, replacement);
    }

    if (component instanceof TranslatableComponent) {
      var4 = ((TranslatableComponent)component).getArguments().iterator();

      while(var4.hasNext()) {
        argument = (Component)var4.next();
        this.replaceComponent(argument, target, replacement);
      }
    }

    if (component instanceof TextComponent textComponent) {
      String text = textComponent.getText();
      int next = text.indexOf(target);
      if (next != -1) {
        textComponent.text("");
        if (next == 0 && text.length() == target.length()) {
          component.append(0, (Component)replacement.get());
        } else {
          int lastTargetAt = 0;
          int childIndex = 0;

          for(int i = 0; i < text.length(); ++i) {
            if (i == next) {
              if (i > lastTargetAt) {
                component.append(childIndex++, Component.text(text.substring(lastTargetAt, i)));
              }

              component.append(childIndex++, (Component)replacement.get());
              lastTargetAt = i + target.length();
            }
          }

          if (lastTargetAt < text.length()) {
            component.append(childIndex, Component.text(text.substring(lastTargetAt)));
          }
        }
      }
    }

  }
}