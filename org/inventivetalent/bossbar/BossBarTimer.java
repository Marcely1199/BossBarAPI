/*    */ package org.inventivetalent.bossbar;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
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
/*    */ public class BossBarTimer
/*    */   extends BukkitRunnable
/*    */ {
/*    */   private PacketBossBar bossBar;
/*    */   final float progressMinus;
/*    */   
/*    */   public BossBarTimer(PacketBossBar packetBossBar, float progress, int timeout)
/*    */   {
/* 40 */     this.bossBar = packetBossBar;
/* 41 */     this.progressMinus = (progress / timeout);
/*    */   }
/*    */   
/*    */   public void run()
/*    */   {
/* 46 */     float newProgress = this.bossBar.getProgress() - this.progressMinus;
/* 47 */     if (newProgress <= 0.0F) {
/* 48 */       for (Player player : this.bossBar.getPlayers()) {
/* 49 */         this.bossBar.removePlayer(player);
/*    */       }
/*    */     } else {
/* 52 */       this.bossBar.setProgress(newProgress);
/*    */     }
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\BossBarTimer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */