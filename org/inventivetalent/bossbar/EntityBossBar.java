/*     */ package org.inventivetalent.bossbar;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;

/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ import org.inventivetalent.bossbar.reflection.ClassBuilder;
/*     */ import org.inventivetalent.bossbar.reflection.NMSClass;
/*     */ import org.inventivetalent.reflection.minecraft.DataWatcher;
import org.inventivetalent.reflection.minecraft.Minecraft;
/*     */ import org.inventivetalent.reflection.minecraft.DataWatcher.V1_9.ValueType;
import org.inventivetalent.reflection.minecraft.Minecraft.Version;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityBossBar
/*     */   extends BukkitRunnable
/*     */   implements BossBar
/*     */ {
/*  48 */   protected static int ENTITY_DISTANCE = 32;
/*     */   
/*     */   protected final int ID;
/*     */   
/*     */   protected final UUID uuid;
/*     */   protected final Player receiver;
/*     */   protected String message;
/*     */   protected float health;
/*     */   protected float healthMinus;
/*  57 */   protected float minHealth = 1.0F;
/*     */   
/*     */   protected Location location;
/*     */   protected World world;
/*  61 */   protected boolean visible = false;
/*     */   protected Object dataWatcher;
/*     */   
/*     */   protected EntityBossBar(Player player, String message, float percentage, int timeout, float minHealth) {
/*  65 */     this.ID = new Random().nextInt();
/*  66 */     this.uuid = UUID.randomUUID();
/*     */     
/*  68 */     this.receiver = player;
/*  69 */     this.message = message;
/*  70 */     this.health = (percentage / 100.0F * getMaxHealth());
/*  71 */     this.minHealth = minHealth;
/*  72 */     this.world = player.getWorld();
/*  73 */     this.location = makeLocation(player.getLocation());
/*     */     
/*  75 */     if (percentage <= minHealth) {
/*  76 */       BossBarAPI.removeBar(player);
/*     */     }
/*     */     
/*  79 */     if (timeout > 0) {
/*  80 */       this.healthMinus = (getMaxHealth() / timeout);
/*  81 */       runTaskTimer(BossBarPlugin.instance, 20L, 20L);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Location makeLocation(Location base) {
/*  86 */     return base.getDirection().multiply(ENTITY_DISTANCE).add(base.toVector()).toLocation(this.world);
/*     */   }
/*     */   
/*     */   public Player getReceiver()
/*     */   {
/*  91 */     return this.receiver;
/*     */   }
/*     */   
/*     */   public float getMaxHealth()
/*     */   {
/*  96 */     return 300.0F;
/*     */   }
/*     */   
/*     */   public void setHealth(float percentage)
/*     */   {
/* 101 */     this.health = (percentage / 100.0F * getMaxHealth());
/* 102 */     if (this.health <= this.minHealth) {
/* 103 */       BossBarAPI.removeBar(this.receiver);
/*     */     } else {
/* 105 */       sendMetadata();
/*     */     }
/*     */   }
/*     */   
/*     */   public float getHealth()
/*     */   {
/* 111 */     return this.health;
/*     */   }
/*     */   
/*     */   public void setMessage(String message)
/*     */   {
/* 116 */     this.message = message;
/* 117 */     if (isVisible()) {
/* 118 */       sendMetadata();
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection<? extends Player> getPlayers()
/*     */   {
/* 124 */     return Collections.singletonList(getReceiver());
/*     */   }
/*     */   
/*     */ 
/*     */   public void addPlayer(Player player) {}
/*     */   
/*     */ 
/*     */   public void removePlayer(Player player)
/*     */   {
/* 133 */     setVisible(false);
/*     */   }
/*     */   
/*     */   public BossBarAPI.Color getColor()
/*     */   {
/* 138 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setColor(BossBarAPI.Color color) {}
/*     */   
/*     */ 
/*     */   public BossBarAPI.Style getStyle()
/*     */   {
/* 147 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStyle(BossBarAPI.Style style) {}
/*     */   
/*     */ 
/*     */   public void setProperty(BossBarAPI.Property property, boolean flag) {}
/*     */   
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 160 */     return this.message;
/*     */   }
/*     */   
/*     */   public Location getLocation()
/*     */   {
/* 165 */     return this.location;
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/* 170 */     this.health -= this.healthMinus;
/* 171 */     if (this.health <= this.minHealth) {
/* 172 */       BossBarAPI.removeBar(this.receiver);
/*     */     } else {
/* 174 */       sendMetadata();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setVisible(boolean flag)
/*     */   {
/* 180 */     if (flag == this.visible) return;
/* 181 */     if (flag) {
/* 182 */       spawn();
/*     */     } else {
/* 184 */       destroy();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isVisible()
/*     */   {
/* 190 */     return this.visible;
/*     */   }
/*     */   
/*     */   public void setProgress(float progress)
/*     */   {
/* 195 */     setHealth(progress * 100.0F);
/*     */   }
/*     */   
/*     */   public float getProgress()
/*     */   {
/* 200 */     return getHealth() / 100.0F;
/*     */   }
/*     */   
/*     */   public void updateMovement()
/*     */   {
/* 205 */     if (!this.visible) return;
/* 206 */     this.location = makeLocation(this.receiver.getLocation());
/*     */     try {
/* 208 */       Object packet = ClassBuilder.buildTeleportPacket(this.ID, getLocation(), false, false);
/* 209 */       BossBarAPI.sendPacket(this.receiver, packet);
/*     */     } catch (Exception e) {
/* 211 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void updateDataWatcher() {
/* 216 */   if(Minecraft.VERSION.olderThan(Version.v1_11_R1)){  
			if (this.dataWatcher == null) {
/*     */       try {
/* 218 */         this.dataWatcher = DataWatcher.newDataWatcher(null);
/* 219 */         DataWatcher.setValue(this.dataWatcher, 17, DataWatcher.V1_9.ValueType.ENTITY_WITHER_a, new Integer(0));
/* 220 */         DataWatcher.setValue(this.dataWatcher, 18, DataWatcher.V1_9.ValueType.ENTITY_WIHER_b, new Integer(0));
/* 221 */         DataWatcher.setValue(this.dataWatcher, 19, DataWatcher.V1_9.ValueType.ENTITY_WITHER_c, new Integer(0));
/*     */         
/* 223 */         DataWatcher.setValue(this.dataWatcher, 20, DataWatcher.V1_9.ValueType.ENTITY_WITHER_bw, new Integer(1000));
/* 224 */         DataWatcher.setValue(this.dataWatcher, 0, DataWatcher.V1_9.ValueType.ENTITY_FLAG, Byte.valueOf((byte)32));
/*     */       } catch (Exception e) {
/* 226 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/*     */     try {
/* 230 */       DataWatcher.setValue(this.dataWatcher, 6, DataWatcher.V1_9.ValueType.ENTITY_LIVING_HEALTH, Float.valueOf(this.health));
/*     */       
/* 232 */       DataWatcher.setValue(this.dataWatcher, 10, DataWatcher.V1_9.ValueType.ENTITY_NAME, this.message);
/* 233 */       DataWatcher.setValue(this.dataWatcher, 2, DataWatcher.V1_9.ValueType.ENTITY_NAME, this.message);
/*     */       
/* 235 */       DataWatcher.setValue(this.dataWatcher, 11, DataWatcher.V1_9.ValueType.ENTITY_NAME_VISIBLE, Byte.valueOf((byte)1));
/* 236 */       DataWatcher.setValue(this.dataWatcher, 3, DataWatcher.V1_9.ValueType.ENTITY_NAME_VISIBLE, Byte.valueOf((byte)1));
/*     */     } catch (Exception e) {
/* 238 */       e.printStackTrace();
/*     */     }
}else{
	if (this.dataWatcher == null) {
/*     */       try {
/* 218 */         this.dataWatcher = DataWatcher.newDataWatcher(null);
/* 219 */         DataWatcher.setValue(this.dataWatcher, 17, DataWatcher.V1_11.ValueType.ENTITY_WITHER_a, new Integer(0));
/* 220 */         DataWatcher.setValue(this.dataWatcher, 18, DataWatcher.V1_11.ValueType.ENTITY_WIHER_b, new Integer(0));
/* 221 */         DataWatcher.setValue(this.dataWatcher, 19, DataWatcher.V1_11.ValueType.ENTITY_WITHER_c, new Integer(0));
/*     */         
/* 223 */         DataWatcher.setValue(this.dataWatcher, 20, DataWatcher.V1_11.ValueType.ENTITY_WITHER_bw, new Integer(1000));
/* 224 */         DataWatcher.setValue(this.dataWatcher, 0, DataWatcher.V1_11.ValueType.ENTITY_FLAG, Byte.valueOf((byte)32));
/*     */       } catch (Exception e) {
/* 226 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/*     */     try {
/* 230 */       DataWatcher.setValue(this.dataWatcher, 6, DataWatcher.V1_11.ValueType.ENTITY_LIVING_HEALTH, Float.valueOf(this.health));
/*     */       
/* 232 */       DataWatcher.setValue(this.dataWatcher, 10, DataWatcher.V1_11.ValueType.ENTITY_NAME, this.message);
/* 233 */       DataWatcher.setValue(this.dataWatcher, 2, DataWatcher.V1_11.ValueType.ENTITY_NAME, this.message);
/*     */       
/* 235 */       DataWatcher.setValue(this.dataWatcher, 11, DataWatcher.V1_11.ValueType.ENTITY_NAME_VISIBLE, Byte.valueOf((byte)1));
/* 236 */       DataWatcher.setValue(this.dataWatcher, 3, DataWatcher.V1_11.ValueType.ENTITY_NAME_VISIBLE, Byte.valueOf((byte)1));
/*     */     } catch (Exception e) {
/* 238 */       e.printStackTrace();
/*     */     }
}
/*     */   }
/*     */   
/*     */   protected void sendMetadata() {
/* 243 */     updateDataWatcher();
/*     */     try {
/* 245 */       Object metaPacket = ClassBuilder.buildNameMetadataPacket(this.ID, this.dataWatcher, 2, 3, this.message);
/* 246 */       BossBarAPI.sendPacket(this.receiver, metaPacket);
/*     */     } catch (Exception e) {
/* 248 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void spawn() {
/*     */     try {
/* 254 */       updateMovement();
/* 255 */       updateDataWatcher();
/* 256 */       Object packet = ClassBuilder.buildWitherSpawnPacket(this.ID, this.uuid, getLocation(), this.dataWatcher);
/* 257 */       BossBarAPI.sendPacket(this.receiver, packet);
/* 258 */       this.visible = true;
/* 259 */       sendMetadata();
/* 260 */       updateMovement();
/*     */     } catch (Exception e) {
/* 262 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void destroy() {
/*     */     try {
/* 268 */       cancel();
/*     */     }
/*     */     catch (IllegalStateException e) {}
/*     */     try {
/* 272 */       Object packet = NMSClass.PacketPlayOutEntityDestroy.getConstructor(new Class[] { int[].class }).newInstance(new Object[] { this.ID });
/* 273 */       BossBarAPI.sendPacket(this.receiver, packet);
/* 274 */       this.visible = false;
/*     */     } catch (Exception e) {
/* 276 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\EntityBossBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */