/*     */ package org.inventivetalent.bossbar.reflection;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import org.bukkit.Bukkit;
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
/*     */ public abstract class Reflection
/*     */ {
/*     */   public static String getVersion()
/*     */   {
/*  39 */     String name = Bukkit.getServer().getClass().getPackage().getName();
/*  40 */     String version = name.substring(name.lastIndexOf('.') + 1) + ".";
/*  41 */     return version;
/*     */   }
/*     */   
/*     */   public static Class<?> getNMSClass(String className) {
/*  45 */     String fullName = "net.minecraft.server." + getVersion() + className;
/*  46 */     Class<?> clazz = null;
/*     */     try {
/*  48 */       clazz = Class.forName(fullName);
/*     */     } catch (Exception e) {
/*  50 */       e.printStackTrace();
/*     */     }
/*  52 */     return clazz;
/*     */   }
/*     */   
/*     */   public static Class<?> getNMSClassWithException(String className) throws Exception {
/*  56 */     String fullName = "net.minecraft.server." + getVersion() + className;
/*  57 */     Class<?> clazz = Class.forName(fullName);
/*  58 */     return clazz;
/*     */   }
/*     */   
/*     */   public static Class<?> getOBCClass(String className) {
/*  62 */     String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
/*  63 */     Class<?> clazz = null;
/*     */     try {
/*  65 */       clazz = Class.forName(fullName);
/*     */     } catch (Exception e) {
/*  67 */       e.printStackTrace();
/*     */     }
/*  69 */     return clazz;
/*     */   }
/*     */   
/*     */   public static Object getHandle(Object obj) {
/*     */     try {
/*  74 */       return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
/*     */     } catch (Exception e) {
/*  76 */       e.printStackTrace();
/*     */     }
/*  78 */     return null;
/*     */   }
/*     */   
/*     */   public static Field getField(Class<?> clazz, String name) {
/*     */     try {
/*  83 */       Field field = clazz.getDeclaredField(name);
/*  84 */       field.setAccessible(true);
/*  85 */       return field;
/*     */     } catch (Exception e) {
/*  87 */       e.printStackTrace();
/*     */     }
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
/*  93 */     for (Method m : clazz.getMethods()) {
/*  94 */       if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes())))) {
/*  95 */         m.setAccessible(true);
/*  96 */         return m;
/*     */       }
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
/* 103 */     boolean equal = true;
/* 104 */     if (l1.length != l2.length) return false;
/* 105 */     for (int i = 0; i < l1.length; i++) {
/* 106 */       if (l1[i] != l2[i]) {
/* 107 */         equal = false;
/* 108 */         break;
/*     */       }
/*     */     }
/* 111 */     return equal;
/*     */   }
/*     */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\reflection\Reflection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */