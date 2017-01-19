/*     */ package org.inventivetalent.bossbar;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.event.server.PluginEnableEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.inventivetalent.apihelper.API;
/*     */ import org.inventivetalent.apihelper.APIManager;
/*     */ import org.inventivetalent.bossbar.reflection.Reflection;
/*     */ import org.inventivetalent.reflection.minecraft.Minecraft;
/*     */ import org.inventivetalent.reflection.minecraft.Minecraft.Version;
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
/*     */ public class BossBarAPI
/*     */   implements API, Listener
/*     */ {
/*  57 */   protected static final Map<UUID, Collection<BossBar>> barMap = new ConcurrentHashMap();
/*     */   
/*  59 */   public static boolean is1_9 = Minecraft.VERSION.newerThan(Minecraft.Version.v1_9_R1);
/*     */   
/*     */   public static enum Color {
/*  62 */     PINK, 
/*  63 */     BLUE, 
/*  64 */     RED, 
/*  65 */     GREEN, 
/*  66 */     YELLOW, 
/*  67 */     PURPLE, 
/*  68 */     WHITE;
/*     */     
/*     */     private Color() {} }
/*     */   
/*  72 */   public static enum Style { PROGRESS, 
/*  73 */     NOTCHED_6, 
/*  74 */     NOTCHED_10, 
/*  75 */     NOTCHED_12, 
/*  76 */     NOTCHED_20;
/*     */     
/*     */     private Style() {} }
/*     */   
/*  80 */   public static enum Property { DARKEN_SKY, 
/*  81 */     PLAY_MUSIC, 
/*     */     
/*  83 */     CREATE_FOG;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Property() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BossBar addBar(Collection<Player> players, String message, Color color, Style style, float progress, Property... properties)
/*     */   {
/*  98 */     validate1_9();
/*  99 */     BossBar bossBar = new PacketBossBar(message, color, style, progress, properties);
/* 100 */     for (Player player : players) {
/* 101 */       addBarForPlayer(player, bossBar);
/*     */     }
/* 103 */     return bossBar;
/*     */   }
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
/*     */   public static BossBar addBar(Collection<Player> players, BaseComponent component, Color color, Style style, float progress, Property... properties)
/*     */   {
/* 118 */     validate1_9();
/* 119 */     BossBar bossBar = new PacketBossBar(component, color, style, progress, properties);
/* 120 */     for (Player player : players) {
/* 121 */       addBarForPlayer(player, bossBar);
/*     */     }
/* 123 */     return bossBar;
/*     */   }
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
/*     */   public static BossBar addBar(Collection<Player> players, BaseComponent component, Color color, Style style, float progress, int timeout, long interval, Property... properties)
/*     */   {
/* 140 */     validate1_9();
/* 141 */     BossBar bossBar = addBar(players, component, color, style, progress, properties);
/* 142 */     new BossBarTimer((PacketBossBar)bossBar, progress, timeout).runTaskTimer(BossBarPlugin.instance, interval, interval);
/* 143 */     return bossBar;
/*     */   }
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
/*     */   public static BossBar addBar(Player player, BaseComponent component, Color color, Style style, float progress, Property... properties)
/*     */   {
/* 159 */     if (is1_9) {
/* 160 */       BossBar bossBar = new PacketBossBar(component, color, style, progress, properties);
/* 161 */       addBarForPlayer(player, bossBar);
/* 162 */       return bossBar;
/*     */     }
/* 164 */     setMessage(player, component.toLegacyText(), progress * 100.0F);
/* 165 */     return getBossBar(player);
/*     */   }
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
/*     */   public static BossBar addBar(Player player, BaseComponent component, Color color, Style style, float progress, int timeout, long interval, Property... properties)
/*     */   {
/* 183 */     if (is1_9) {
/* 184 */       BossBar bossBar = addBar(player, component, color, style, progress, properties);
/* 185 */       new BossBarTimer((PacketBossBar)bossBar, progress, timeout).runTaskTimer(BossBarPlugin.instance, interval, interval);
/* 186 */       return bossBar;
/*     */     }
/* 188 */     setMessage(player, component.toLegacyText(), progress * 100.0F, timeout);
/* 189 */     return getBossBar(player);
/*     */   }
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
/*     */   public static BossBar addBar(BaseComponent component, Color color, Style style, float progress, Property... properties)
/*     */   {
/* 204 */     validate1_9();
/* 205 */     return new PacketBossBar(component, color, style, progress, properties);
/*     */   }
/*     */   
/*     */   public static Collection<BossBar> getBossBars(Player player) {
/* 209 */     if (!barMap.containsKey(player.getUniqueId())) return new ArrayList();
/* 210 */     return new ArrayList((Collection)barMap.get(player.getUniqueId()));
/*     */   }
/*     */   
/*     */   protected static void addBarForPlayer(Player player, BossBar bossBar) {
/* 214 */     bossBar.addPlayer(player);
/*     */     
/* 216 */     Collection<BossBar> collection = (Collection)barMap.get(player.getUniqueId());
/* 217 */     if (collection == null) collection = new ArrayList();
/* 218 */     collection.add(bossBar);
/* 219 */     barMap.put(player.getUniqueId(), collection);
/*     */   }
/*     */   
/*     */   protected static void removeBarForPlayer(Player player, BossBar bossBar) {
/* 223 */     bossBar.removePlayer(player);
/*     */     
/* 225 */     Collection<BossBar> collection = (Collection)barMap.get(player.getUniqueId());
/* 226 */     if (collection != null) {
/* 227 */       collection.remove(bossBar);
/* 228 */       if (!collection.isEmpty()) {
/* 229 */         barMap.put(player.getUniqueId(), collection);
/*     */       } else {
/* 231 */         barMap.remove(player.getUniqueId());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void removeAllBars(Player player) {
/* 237 */     for (BossBar bossBar : getBossBars(player)) {
/* 238 */       removeBarForPlayer(player, bossBar);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void setMessage(Player player, String message)
/*     */   {
/* 252 */     setMessage(player, message, 100.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void setMessage(Player player, String message, float percentage)
/*     */   {
/* 264 */     setMessage(player, message, percentage, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void setMessage(Player player, String message, float percentage, int timeout)
/*     */   {
/* 277 */     setMessage(player, message, percentage, timeout, 100.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void setMessage(Player player, String message, float percentage, int timeout, float minHealth)
/*     */   {
/* 291 */     if (is1_9) {
/* 292 */       removeAllBars(player);
/* 293 */       addBar(player, new TextComponent(message), Color.PURPLE, Style.PROGRESS, percentage / 100.0F, new Property[0]);
/*     */     } else {
/* 295 */       if (!barMap.containsKey(player.getUniqueId())) {
/* 296 */         ArrayList<BossBar> list = new ArrayList();
/* 297 */         list.add(new EntityBossBar(player, message, percentage, timeout, minHealth));
/* 298 */         barMap.put(player.getUniqueId(), list);
/*     */       }
/* 300 */       BossBar bar = (BossBar)((List)barMap.get(player.getUniqueId())).get(0);
/* 301 */       if (!bar.getMessage().equals(message)) {
/* 302 */         bar.setMessage(message);
/*     */       }
/* 304 */       float newHealth = percentage / 100.0F * bar.getMaxHealth();
/* 305 */       if (bar.getHealth() != newHealth) {
/* 306 */         bar.setHealth(percentage);
/*     */       }
/* 308 */       if (!bar.isVisible()) {
/* 309 */         bar.setVisible(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static String getMessage(Player player)
/*     */   {
/* 320 */     BossBar bar = getBossBar(player);
/* 321 */     if (bar == null) return null;
/* 322 */     return bar.getMessage();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static boolean hasBar(@Nonnull Player player)
/*     */   {
/* 331 */     return barMap.containsKey(player.getUniqueId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void removeBar(@Nonnull Player player)
/*     */   {
/* 341 */     BossBar bar = getBossBar(player);
/* 342 */     if (bar != null) bar.setVisible(false);
/* 343 */     removeAllBars(player);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void setHealth(Player player, float percentage)
/*     */   {
/* 354 */     BossBar bar = getBossBar(player);
/* 355 */     if (bar == null) return;
/* 356 */     bar.setHealth(percentage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static float getHealth(@Nonnull Player player)
/*     */   {
/* 365 */     BossBar bar = getBossBar(player);
/* 366 */     if (bar == null) return -1.0F;
/* 367 */     return bar.getHealth();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static BossBar getBossBar(@Nonnull Player player)
/*     */   {
/* 379 */     if (player == null) return null;
/* 380 */     List<BossBar> list = (List)barMap.get(player.getUniqueId());
/* 381 */     return list != null ? (BossBar)list.get(0) : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Collection<BossBar> getBossBars()
/*     */   {
/* 389 */     List<BossBar> list = new ArrayList();
/* 390 */     for (Collection<BossBar> collection : barMap.values()) {
/* 391 */       list.add((BossBar) ((List)collection).get(0));
/*     */     }
/* 393 */     return list;
/*     */   }
/*     */   
/*     */   protected static void sendPacket(Player p, Object packet) {
/* 397 */     if ((p == null) || (packet == null)) throw new IllegalArgumentException("player and packet cannot be null");
/*     */     try {
/* 399 */       Object handle = Reflection.getHandle(p);
/* 400 */       Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
/* 401 */       Reflection.getMethod(connection.getClass(), "sendPacket", new Class[] { Reflection.getNMSClass("Packet") }).invoke(connection, new Object[] { packet });
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   static void validate1_9() {
/* 407 */     if (!is1_9) {
/* 408 */       throw new RuntimeException(new UnsupportedOperationException("This method is not compatible with versions < 1.9"));
/*     */     }
/*     */   }
/*     */   
/* 412 */   Logger logger = Logger.getLogger("BossBarAPI");
/*     */   
/*     */ 
/*     */   public void load() {}
/*     */   
/*     */ 
/*     */   public void init(Plugin plugin)
/*     */   {
/* 420 */     APIManager.registerEvents(this, this);
/* 421 */     BossBarPlugin.instance = APIManager.getAPIHost(this);
/* 422 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 423 */       removeAllBars(player);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void disable(Plugin plugin) {}
/*     */   
/*     */   @EventHandler
/*     */   public void onPluginEnable(PluginEnableEvent e)
/*     */   {
/* 433 */     if (("BarAPI".equals(e.getPlugin().getName())) && (Bukkit.getPluginManager().isPluginEnabled("BarAPI"))) {
/*     */       try {
/* 435 */         Class<?> barAPI = Class.forName("me.confuser.barapi.BarAPI");
/*     */         
/* 437 */         Method method = barAPI.getDeclaredMethod("enabled", new Class[0]);
/* 438 */         method.setAccessible(true);
/* 439 */         if (((Boolean)method.invoke(null, new Object[0])).booleanValue()) {
/* 440 */           this.logger.info("Successfully replaced BarAPI.");
/* 441 */           return;
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/* 445 */       this.logger.warning("Failed to replace BarAPI.");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 451 */     removeBar(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onKick(PlayerKickEvent e) {
/* 456 */     removeBar(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onTeleport(PlayerTeleportEvent e) {
/* 461 */     if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
/* 462 */       handlePlayerTeleport(e.getPlayer(), e.getFrom(), e.getTo());
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onRespawn(PlayerRespawnEvent e) {
/* 468 */     if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
/* 469 */       handlePlayerTeleport(e.getPlayer(), e.getPlayer().getLocation(), e.getRespawnLocation());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void handlePlayerTeleport(Player player, Location from, Location to) {
/* 474 */     if (!hasBar(player)) return;
/* 475 */     final BossBar bar = getBossBar(player);
/* 476 */     bar.setVisible(false);
/* 477 */     new BukkitRunnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 481 */         bar.setVisible(true);
/*     */       }
/* 483 */     }.runTaskLater(APIManager.getAPIHost(this), 2L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(final PlayerMoveEvent e) {
/* 488 */     if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
/* 489 */       final BossBar bar = getBossBar(e.getPlayer());
/* 490 */       if (bar != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 498 */         new BukkitRunnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 495 */             if (!e.getPlayer().isOnline()) return;
/* 496 */             bar.updateMovement();
/*     */           }
/* 498 */         }.runTaskLater(APIManager.getAPIHost(this), 0L);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\BossBarAPI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */