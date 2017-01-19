/*    */ package org.inventivetalent.bossbar.reflection;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ 
/*    */ 
/*    */ public abstract class NMUClass
/*    */ {
/*    */   private static boolean initialized;
/*    */   public static Class<?> gnu_trove_map_TIntObjectMap;
/*    */   public static Class<?> gnu_trove_map_hash_TIntObjectHashMap;
/*    */   public static Class<?> gnu_trove_impl_hash_THash;
/*    */   public static Class<?> io_netty_channel_Channel;
/*    */   
/*    */   static
/*    */   {
/* 43 */     if (!initialized) {
/* 44 */       for (Field f : NMUClass.class.getDeclaredFields()) {
/* 45 */         if (f.getType().equals(Class.class)) {
/*    */           try {
/* 47 */             String name = f.getName().replace("_", ".");
/* 48 */             if (Reflection.getVersion().contains("1_8")) {
/* 49 */               f.set(null, Class.forName(name));
/*    */             } else {
/* 51 */               f.set(null, Class.forName("net.minecraft.util." + name));
/*    */             }
/*    */           } catch (Exception e) {
/* 54 */             e.printStackTrace();
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\reflection\NMUClass.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */