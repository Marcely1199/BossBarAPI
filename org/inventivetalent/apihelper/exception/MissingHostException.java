/*    */ package org.inventivetalent.apihelper.exception;
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
/*    */ public class MissingHostException
/*    */   extends RuntimeException
/*    */ {
/*    */   public MissingHostException() {}
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
/*    */   public MissingHostException(String message)
/*    */   {
/* 36 */     super(message);
/*    */   }
/*    */   
/*    */   public MissingHostException(String message, Throwable cause) {
/* 40 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public MissingHostException(Throwable cause) {
/* 44 */     super(cause);
/*    */   }
/*    */   
/*    */   public MissingHostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
/* 48 */     super(message, cause, enableSuppression, writableStackTrace);
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\apihelper\exception\MissingHostException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */