/*     */ package org.inventivetalent.bossbar;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetricsLite
/*     */ {
/*     */   private static final int REVISION = 7;
/*     */   private static final String BASE_URL = "http://report.mcstats.org";
/*     */   private static final String REPORT_URL = "/plugin/%s";
/*     */   private static final int PING_INTERVAL = 15;
/*     */   private final Plugin plugin;
/*     */   private final YamlConfiguration configuration;
/*     */   private final File configurationFile;
/*     */   private final String guid;
/*     */   private final boolean debug;
/*  97 */   private final Object optOutLock = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 102 */   private volatile BukkitTask task = null;
/*     */   
/*     */   public MetricsLite(Plugin plugin) throws IOException {
/* 105 */     if (plugin == null) {
/* 106 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 109 */     this.plugin = plugin;
/*     */     
/*     */ 
/* 112 */     this.configurationFile = getConfigFile();
/* 113 */     this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile);
/*     */     
/*     */ 
/* 116 */     this.configuration.addDefault("opt-out", Boolean.valueOf(false));
/* 117 */     this.configuration.addDefault("guid", UUID.randomUUID().toString());
/* 118 */     this.configuration.addDefault("debug", Boolean.valueOf(false));
/*     */     
/*     */ 
/* 121 */     if (this.configuration.get("guid", null) == null) {
/* 122 */       this.configuration.options().header("http://mcstats.org").copyDefaults(true);
/* 123 */       this.configuration.save(this.configurationFile);
/*     */     }
/*     */     
/*     */ 
/* 127 */     this.guid = this.configuration.getString("guid");
/* 128 */     this.debug = this.configuration.getBoolean("debug", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean start()
/*     */   {
/* 138 */     synchronized (this.optOutLock)
/*     */     {
/* 140 */       if (isOptOut()) {
/* 141 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 145 */       if (this.task != null) {
/* 146 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 150 */       this.task = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, new Runnable()
/*     */       {
/* 152 */         private boolean firstPost = true;
/*     */         
/*     */         public void run()
/*     */         {
/*     */           try {
/* 157 */             synchronized (MetricsLite.this.optOutLock)
/*     */             {
/* 159 */               if ((MetricsLite.this.isOptOut()) && (MetricsLite.this.task != null)) {
/* 160 */                 MetricsLite.this.task.cancel();
/* 161 */                 MetricsLite.this.task = null;
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 168 */             MetricsLite.this.postPlugin(!this.firstPost);
/*     */             
/*     */ 
/*     */ 
/* 172 */             this.firstPost = false;
/*     */           } catch (IOException e) {
/* 174 */             if (MetricsLite.this.debug)
/* 175 */               Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage()); } } }, 0L, 18000L);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 181 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOptOut()
/*     */   {
/* 191 */     synchronized (this.optOutLock)
/*     */     {
/*     */       try {
/* 194 */         this.configuration.load(getConfigFile());
/*     */       } catch (IOException ex) {
/* 196 */         if (this.debug) {
/* 197 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 199 */         return true;
/*     */       } catch (InvalidConfigurationException ex) {
/* 201 */         if (this.debug) {
/* 202 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 204 */         return true;
/*     */       }
/* 206 */       return this.configuration.getBoolean("opt-out", false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void enable()
/*     */     throws IOException
/*     */   {
/* 217 */     synchronized (this.optOutLock)
/*     */     {
/* 219 */       if (isOptOut()) {
/* 220 */         this.configuration.set("opt-out", Boolean.valueOf(false));
/* 221 */         this.configuration.save(this.configurationFile);
/*     */       }
/*     */       
/*     */ 
/* 225 */       if (this.task == null) {
/* 226 */         start();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void disable()
/*     */     throws IOException
/*     */   {
/* 238 */     synchronized (this.optOutLock)
/*     */     {
/* 240 */       if (!isOptOut()) {
/* 241 */         this.configuration.set("opt-out", Boolean.valueOf(true));
/* 242 */         this.configuration.save(this.configurationFile);
/*     */       }
/*     */       
/*     */ 
/* 246 */       if (this.task != null) {
/* 247 */         this.task.cancel();
/* 248 */         this.task = null;
/*     */       }
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
/*     */ 
/*     */ 
/*     */   public File getConfigFile()
/*     */   {
/* 264 */     File pluginsFolder = this.plugin.getDataFolder().getParentFile();
/*     */     
/*     */ 
/* 267 */     return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void postPlugin(boolean isPing)
/*     */     throws IOException
/*     */   {
/* 275 */     PluginDescriptionFile description = this.plugin.getDescription();
/* 276 */     String pluginName = description.getName();
/*     */     
/*     */ 
/* 279 */     if (pluginName.equals("BossBarAPI")) {
/* 280 */       pluginName = "InventiveBossBarAPI";
/*     */     }
/*     */     
/* 283 */     boolean onlineMode = Bukkit.getServer().getOnlineMode();
/* 284 */     String pluginVersion = description.getVersion();
/* 285 */     String serverVersion = Bukkit.getVersion();
/* 286 */     int playersOnline = Bukkit.getServer().getOnlinePlayers().size();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 291 */     StringBuilder json = new StringBuilder(1024);
/* 292 */     json.append('{');
/*     */     
/*     */ 
/* 295 */     appendJSONPair(json, "guid", this.guid);
/* 296 */     appendJSONPair(json, "plugin_version", pluginVersion);
/* 297 */     appendJSONPair(json, "server_version", serverVersion);
/* 298 */     appendJSONPair(json, "players_online", Integer.toString(playersOnline));
/*     */     
/*     */ 
/* 301 */     String osname = System.getProperty("os.name");
/* 302 */     String osarch = System.getProperty("os.arch");
/* 303 */     String osversion = System.getProperty("os.version");
/* 304 */     String java_version = System.getProperty("java.version");
/* 305 */     int coreCount = Runtime.getRuntime().availableProcessors();
/*     */     
/*     */ 
/* 308 */     if (osarch.equals("amd64")) {
/* 309 */       osarch = "x86_64";
/*     */     }
/*     */     
/* 312 */     appendJSONPair(json, "osname", osname);
/* 313 */     appendJSONPair(json, "osarch", osarch);
/* 314 */     appendJSONPair(json, "osversion", osversion);
/* 315 */     appendJSONPair(json, "cores", Integer.toString(coreCount));
/* 316 */     appendJSONPair(json, "auth_mode", onlineMode ? "1" : "0");
/* 317 */     appendJSONPair(json, "java_version", java_version);
/*     */     
/*     */ 
/* 320 */     if (isPing) {
/* 321 */       appendJSONPair(json, "ping", "1");
/*     */     }
/*     */     
/*     */ 
/* 325 */     json.append('}');
/*     */     
/*     */ 
/* 328 */     URL url = new URL("http://report.mcstats.org" + String.format("/plugin/%s", new Object[] { urlEncode(pluginName) }));
/*     */     
/*     */ 
/*     */     URLConnection connection;
/*     */     
/* 335 */     if (isMineshafterPresent()) {
/* 336 */       connection = url.openConnection(Proxy.NO_PROXY);
/*     */     } else {
/* 338 */       connection = url.openConnection();
/*     */     }
/*     */     
/* 341 */     byte[] uncompressed = json.toString().getBytes();
/* 342 */     byte[] compressed = gzip(json.toString());
/*     */     
/*     */ 
/* 345 */     connection.addRequestProperty("User-Agent", "MCStats/7");
/* 346 */     connection.addRequestProperty("Content-Type", "application/json");
/* 347 */     connection.addRequestProperty("Content-Encoding", "gzip");
/* 348 */     connection.addRequestProperty("Content-Length", Integer.toString(compressed.length));
/* 349 */     connection.addRequestProperty("Accept", "application/json");
/* 350 */     connection.addRequestProperty("Connection", "close");
/*     */     
/* 352 */     connection.setDoOutput(true);
/*     */     
/* 354 */     if (this.debug) {
/* 355 */       System.out.println("[Metrics] Prepared request for " + pluginName + " uncompressed=" + uncompressed.length + " compressed=" + compressed.length);
/*     */     }
/*     */     
/*     */ 
/* 359 */     OutputStream os = connection.getOutputStream();
/* 360 */     os.write(compressed);
/* 361 */     os.flush();
/*     */     
/*     */ 
/* 364 */     BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 365 */     String response = reader.readLine();
/*     */     
/*     */ 
/* 368 */     os.close();
/* 369 */     reader.close();
/*     */     
/* 371 */     if ((response == null) || (response.startsWith("ERR")) || (response.startsWith("7"))) {
/* 372 */       if (response == null) {
/* 373 */         response = "null";
/* 374 */       } else if (response.startsWith("7")) {
/* 375 */         response = response.substring(response.startsWith("7,") ? 2 : 1);
/*     */       }
/*     */       
/* 378 */       throw new IOException(response);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] gzip(String input)
/*     */   {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gzos = null;
    try {
        gzos = new GZIPOutputStream(baos);
        gzos.write(input.getBytes("UTF-8"));
    }
    catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        if (gzos != null) {
            try {
                gzos.close();
            }
            catch (IOException ignore) {}
        }
    }
    return baos.toByteArray();}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isMineshafterPresent()
/*     */   {
/*     */     try
/*     */     {
/* 416 */       Class.forName("mineshafter.MineServer");
/* 417 */       return true;
/*     */     } catch (Exception e) {}
/* 419 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void appendJSONPair(StringBuilder json, String key, String value)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 432 */     boolean isValueNumeric = false;
/*     */     try
/*     */     {
/* 435 */       if ((value.equals("0")) || (!value.endsWith("0"))) {
/* 436 */         Double.parseDouble(value);
/* 437 */         isValueNumeric = true;
/*     */       }
/*     */     } catch (NumberFormatException e) {
/* 440 */       isValueNumeric = false;
/*     */     }
/*     */     
/* 443 */     if (json.charAt(json.length() - 1) != '{') {
/* 444 */       json.append(',');
/*     */     }
/*     */     
/* 447 */     json.append(escapeJSON(key));
/* 448 */     json.append(':');
/*     */     
/* 450 */     if (isValueNumeric) {
/* 451 */       json.append(value);
/*     */     } else {
/* 453 */       json.append(escapeJSON(value));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String escapeJSON(String text)
/*     */   {
/* 464 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 466 */     builder.append('"');
/* 467 */     for (int index = 0; index < text.length(); index++) {
/* 468 */       char chr = text.charAt(index);
/*     */       
/* 470 */       switch (chr) {
/*     */       case '"': 
/*     */       case '\\': 
/* 473 */         builder.append('\\');
/* 474 */         builder.append(chr);
/* 475 */         break;
/*     */       case '\b': 
/* 477 */         builder.append("\\b");
/* 478 */         break;
/*     */       case '\t': 
/* 480 */         builder.append("\\t");
/* 481 */         break;
/*     */       case '\n': 
/* 483 */         builder.append("\\n");
/* 484 */         break;
/*     */       case '\r': 
/* 486 */         builder.append("\\r");
/* 487 */         break;
/*     */       default: 
/* 489 */         if (chr < ' ') {
/* 490 */           String t = "000" + Integer.toHexString(chr);
/* 491 */           builder.append("\\u" + t.substring(t.length() - 4));
/*     */         } else {
/* 493 */           builder.append(chr);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 498 */     builder.append('"');
/*     */     
/* 500 */     return builder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String urlEncode(String text)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 510 */     return URLEncoder.encode(text, "UTF-8");
/*     */   }
/*     */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\MetricsLite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */