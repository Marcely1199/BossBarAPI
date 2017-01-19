/*     */ package org.inventivetalent.bossbar;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.chat.ComponentSerializer;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.inventivetalent.reflection.resolver.FieldResolver;
/*     */ import org.inventivetalent.reflection.resolver.MethodResolver;
/*     */ import org.inventivetalent.reflection.resolver.ResolverQuery;
/*     */ import org.inventivetalent.reflection.resolver.minecraft.NMSClassResolver;
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
/*     */ public class PacketBossBar
/*     */   implements BossBar
/*     */ {
/*  46 */   static NMSClassResolver nmsClassResolver = new NMSClassResolver();
/*     */   
/*  48 */   static Class<?> PacketPlayOutBoss = nmsClassResolver.resolveSilent(new String[] { "PacketPlayOutBoss" });
/*  49 */   static Class<?> PacketPlayOutBossAction = nmsClassResolver.resolveSilent(new String[] { "PacketPlayOutBoss$Action" });
/*  50 */   static Class<?> ChatSerializer = nmsClassResolver.resolveSilent(new String[] { "ChatSerializer", "IChatBaseComponent$ChatSerializer" });
/*  51 */   static Class<?> BossBattleBarColor = nmsClassResolver.resolveSilent(new String[] { "BossBattle$BarColor" });
/*  52 */   static Class<?> BossBattleBarStyle = nmsClassResolver.resolveSilent(new String[] { "BossBattle$BarStyle" });
/*     */   
/*  54 */   static FieldResolver PacketPlayOutBossFieldResolver = new FieldResolver(PacketPlayOutBoss);
/*     */   
/*  56 */   static MethodResolver ChatSerializerMethodResolver = new MethodResolver(ChatSerializer);
/*     */   
/*     */   private final UUID uuid;
/*  59 */   private Collection<Player> receivers = new ArrayList();
/*     */   private float progress;
/*     */   private String message;
/*     */   private BossBarAPI.Color color;
/*     */   private BossBarAPI.Style style;
/*     */   private boolean visible;
/*     */   private boolean darkenSky;
/*     */   private boolean playMusic;
/*     */   private boolean createFog;
/*     */   
/*     */   protected PacketBossBar(String message, BossBarAPI.Color color, BossBarAPI.Style style, float progress, BossBarAPI.Property... properties)
/*     */   {
/*  71 */     this.uuid = UUID.randomUUID();
/*     */     
/*  73 */     this.color = (color != null ? color : BossBarAPI.Color.PURPLE);
/*  74 */     this.style = (style != null ? style : BossBarAPI.Style.PROGRESS);
/*  75 */     setMessage(message);
/*  76 */     setProgress(progress);
/*     */     
/*  78 */     for (BossBarAPI.Property property : properties) {
/*  79 */       setProperty(property, true);
/*     */     }
/*     */   }
/*     */   
/*     */   protected PacketBossBar(BaseComponent message, BossBarAPI.Color color, BossBarAPI.Style style, float progress, BossBarAPI.Property... properties) {
/*  84 */     this(ComponentSerializer.toString(message), color, style, progress, properties);
/*     */   }
/*     */   
/*     */   public Collection<? extends Player> getPlayers()
/*     */   {
/*  89 */     return new ArrayList(this.receivers);
/*     */   }
/*     */   
/*     */   public void addPlayer(Player player)
/*     */   {
/*  94 */     if (!this.receivers.contains(player)) {
/*  95 */       this.receivers.add(player);
/*  96 */       sendPacket(0, player);
/*  97 */       BossBarAPI.addBarForPlayer(player, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePlayer(Player player)
/*     */   {
/* 103 */     if (this.receivers.contains(player)) {
/* 104 */       this.receivers.remove(player);
/* 105 */       sendPacket(1, player);
/* 106 */       BossBarAPI.removeBarForPlayer(player, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public BossBarAPI.Color getColor()
/*     */   {
/* 112 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(BossBarAPI.Color color)
/*     */   {
/* 117 */     if (color == null) throw new IllegalArgumentException("color cannot be null");
/* 118 */     if (color != this.color) {
/* 119 */       this.color = color;
/* 120 */       sendPacket(4, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public BossBarAPI.Style getStyle()
/*     */   {
/* 126 */     return this.style;
/*     */   }
/*     */   
/*     */   public void setStyle(BossBarAPI.Style style)
/*     */   {
/* 131 */     if (style == null) throw new IllegalArgumentException("style cannot be null");
/* 132 */     if (style != this.style) {
/* 133 */       this.style = style;
/* 134 */       sendPacket(4, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setProperty(BossBarAPI.Property property, boolean flag)
/*     */   {
/* 140 */     switch (property) {
/*     */     case DARKEN_SKY: 
/* 142 */       this.darkenSky = flag;
/* 143 */       break;
/*     */     case PLAY_MUSIC: 
/* 145 */       this.playMusic = flag;
/* 146 */       break;
/*     */     case CREATE_FOG: 
/* 148 */       this.createFog = flag;
/* 149 */       break;
/*     */     }
/*     */     
/*     */     
/* 153 */     sendPacket(5, null);
/*     */   }
/*     */   
/*     */   public String getMessage()
/*     */   {
/* 158 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(String message)
/*     */   {
/* 163 */     if (message == null) throw new IllegalArgumentException("message cannot be null");
/* 164 */     if ((!message.startsWith("{")) || (!message.endsWith("}"))) {
/* 165 */       throw new IllegalArgumentException("Invalid JSON");
/*     */     }
/* 167 */     if (!message.equals(this.message)) {
/* 168 */       this.message = message;
/* 169 */       sendPacket(3, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getProgress()
/*     */   {
/* 175 */     return this.progress;
/*     */   }
/*     */   
/*     */   public void setProgress(float progress)
/*     */   {
/* 180 */     if (progress > 1.0F) {
/* 181 */       progress /= 100.0F;
/*     */     }
/* 183 */     if (progress != this.progress) {
/* 184 */       this.progress = progress;
/* 185 */       sendPacket(2, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isVisible()
/*     */   {
/* 191 */     return this.visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean flag)
/*     */   {
/* 196 */     if (flag != this.visible) {
/* 197 */       this.visible = flag;
/* 198 */       sendPacket(flag ? 0 : 1, null);
/*     */     }
/*     */   }
/*     */   
/*     */   void sendPacket(int action, Player player) {
    try {
        Object packet = PacketPlayOutBoss.newInstance();
        PacketPlayOutBossFieldResolver.resolve("a").set(packet, this.uuid);
        PacketPlayOutBossFieldResolver.resolve("b").set(packet, PacketPlayOutBossAction.getEnumConstants()[action]);
        PacketPlayOutBossFieldResolver.resolve("c").set(packet, PacketBossBar.serialize(this.message));
        PacketPlayOutBossFieldResolver.resolve("d").set(packet, Float.valueOf(this.progress));
        PacketPlayOutBossFieldResolver.resolve("e").set(packet, BossBattleBarColor.getEnumConstants()[this.color.ordinal()]);
        PacketPlayOutBossFieldResolver.resolve("f").set(packet, BossBattleBarStyle.getEnumConstants()[this.style.ordinal()]);
        PacketPlayOutBossFieldResolver.resolve("g").set(packet, this.darkenSky);
        PacketPlayOutBossFieldResolver.resolve("h").set(packet, this.playMusic);
        PacketPlayOutBossFieldResolver.resolve("i").set(packet, this.createFog);
        if (player != null) {
            BossBarAPI.sendPacket(player, packet);
        } else {
            for (Player player1 : this.getPlayers()) {
                BossBarAPI.sendPacket(player1, packet);
            }
        }
    }
    catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
    }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getMaxHealth()
/*     */   {
/* 231 */     return 100.0F;
/*     */   }
/*     */   
/*     */   public void setHealth(float percentage)
/*     */   {
/* 236 */     setProgress(percentage / 100.0F);
/*     */   }
/*     */   
/*     */   public float getHealth()
/*     */   {
/* 241 */     return getProgress() * 100.0F;
/*     */   }
/*     */   
/*     */   public Player getReceiver()
/*     */   {
/* 246 */     return null;
/*     */   }
/*     */   
/*     */   public Location getLocation()
/*     */   {
/* 251 */     return null;
/*     */   }
/*     */   
/*     */   public void updateMovement() {}
/*     */   
/*     */   static Object serialize(String json)
/*     */     throws ReflectiveOperationException
/*     */   {
/* 259 */     return ChatSerializerMethodResolver.resolve(new ResolverQuery[] { new ResolverQuery("a", new Class[] { String.class }) }).invoke(null, new Object[] { json });
/*     */   }
/*     */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\PacketBossBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */