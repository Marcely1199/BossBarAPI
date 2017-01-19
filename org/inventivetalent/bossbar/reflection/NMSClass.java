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
/*    */ public abstract class NMSClass
/*    */ {
/*    */   private static boolean initialized;
/*    */   public static Class<?> Entity;
/*    */   public static Class<?> EntityLiving;
/*    */   public static Class<?> EntityInsentient;
/*    */   public static Class<?> EntityAgeable;
/*    */   public static Class<?> EntityHorse;
/*    */   public static Class<?> EntityArmorStand;
/*    */   public static Class<?> EntityWither;
/*    */   public static Class<?> EntityWitherSkull;
/*    */   public static Class<?> EntitySlime;
/*    */   public static Class<?> World;
/*    */   public static Class<?> PacketPlayOutSpawnEntityLiving;
/*    */   public static Class<?> PacketPlayOutSpawnEntity;
/*    */   public static Class<?> PacketPlayOutEntityDestroy;
/*    */   public static Class<?> PacketPlayOutAttachEntity;
/*    */   public static Class<?> PacketPlayOutEntityTeleport;
/*    */   public static Class<?> PacketPlayOutEntityMetadata;
/*    */   public static Class<?> DataWatcher;
/*    */   public static Class<?> WatchableObject;
/*    */   public static Class<?> ItemStack;
/*    */   public static Class<?> ChunkCoordinates;
/*    */   public static Class<?> BlockPosition;
/*    */   public static Class<?> Vector3f;
/*    */   public static Class<?> EnumEntityUseAction;
/*    */   
/*    */   static
/*    */   {
/* 62 */     if (!initialized) {
/* 63 */       for (Field f : NMSClass.class.getDeclaredFields()) {
/* 64 */         if (f.getType().equals(Class.class)) {
/*    */           try {
/* 66 */             f.set(null, Reflection.getNMSClassWithException(f.getName()));
/*    */           } catch (Exception e) {
/* 68 */             if (f.getName().equals("WatchableObject")) {
/*    */               try {
/* 70 */                 f.set(null, Reflection.getNMSClassWithException("DataWatcher$WatchableObject"));
/*    */               } catch (Exception e1) {
/* 72 */                 e1.printStackTrace();
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\reflection\NMSClass.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */