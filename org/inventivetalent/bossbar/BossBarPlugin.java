/*    */ package org.inventivetalent.bossbar;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import org.inventivetalent.apihelper.APIManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BossBarPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   protected static Plugin instance;
/* 38 */   BossBarAPI apiInstance = new BossBarAPI();
/*    */   
/*    */   public void onLoad()
/*    */   {
/* 42 */     APIManager.registerAPI(this.apiInstance, this);
/*    */   }
/*    */   
/*    */   public void onEnable()
/*    */   {
/* 47 */     instance = this;
/*    */     try
/*    */     {
/* 50 */       MetricsLite metrics = new MetricsLite(this);
/* 51 */       if (metrics.start()) {
/* 52 */         getLogger().info("Metrics started");
/*    */       }
/*    */     }
/*    */     catch (Exception e) {}
/*    */     
/* 57 */     APIManager.initAPI(BossBarAPI.class);
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\BossBarPlugin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */