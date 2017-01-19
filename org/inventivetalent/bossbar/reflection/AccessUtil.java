/*    */ package org.inventivetalent.bossbar.reflection;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AccessUtil
/*    */ {
/*    */   public static Field setAccessible(Field f)
/*    */     throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
/*    */   {
/* 42 */     f.setAccessible(true);
/* 43 */     Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 44 */     modifiersField.setAccessible(true);
/* 45 */     modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
/* 46 */     return f;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Method setAccessible(Method m)
/*    */     throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
/*    */   {
/* 55 */     m.setAccessible(true);
/* 56 */     return m;
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\reflection\AccessUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */