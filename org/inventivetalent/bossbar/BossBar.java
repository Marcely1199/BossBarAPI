package org.inventivetalent.bossbar;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract interface BossBar
{
  public abstract Collection<? extends Player> getPlayers();
  
  public abstract void addPlayer(Player paramPlayer);
  
  public abstract void removePlayer(Player paramPlayer);
  
  public abstract BossBarAPI.Color getColor();
  
  public abstract void setColor(BossBarAPI.Color paramColor);
  
  public abstract BossBarAPI.Style getStyle();
  
  public abstract void setStyle(BossBarAPI.Style paramStyle);
  
  public abstract void setProperty(BossBarAPI.Property paramProperty, boolean paramBoolean);
  
  public abstract String getMessage();
  
  public abstract void setVisible(boolean paramBoolean);
  
  public abstract boolean isVisible();
  
  public abstract float getProgress();
  
  public abstract void setProgress(float paramFloat);
  
  @Deprecated
  public abstract float getMaxHealth();
  
  @Deprecated
  public abstract void setHealth(float paramFloat);
  
  @Deprecated
  public abstract float getHealth();
  
  @Deprecated
  public abstract void setMessage(String paramString);
  
  @Deprecated
  public abstract Player getReceiver();
  
  @Deprecated
  public abstract Location getLocation();
  
  @Deprecated
  public abstract void updateMovement();
}


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\BossBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */