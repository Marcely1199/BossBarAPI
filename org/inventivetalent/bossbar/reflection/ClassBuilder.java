/*     */ package org.inventivetalent.bossbar.reflection;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.inventivetalent.reflection.minecraft.DataWatcher;
/*     */ import org.inventivetalent.reflection.minecraft.DataWatcher.V1_9.ValueType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ClassBuilder
/*     */ {
/*     */   public static Object buildWitherSpawnPacket(int id, UUID uuid, Location loc, Object dataWatcher)
/*     */     throws Exception
/*     */   {
/*  43 */     Object packet = NMSClass.PacketPlayOutSpawnEntityLiving.newInstance();
/*  44 */     if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
/*  45 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("a")).set(packet, Integer.valueOf(id));
/*  46 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("b")).set(packet, Integer.valueOf(64));
/*  47 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("c")).set(packet, Integer.valueOf((int)loc.getX()));
/*  48 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("d")).set(packet, Integer.valueOf(MathUtil.floor(loc.getY() * 32.0D)));
/*  49 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("e")).set(packet, Integer.valueOf((int)loc.getZ()));
/*     */       
/*  51 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("i")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getYaw() * 256.0F / 360.0F)));
/*  52 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("j")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getPitch() * 256.0F / 360.0F)));
/*  53 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("k")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getPitch() * 256.0F / 360.0F)));
/*  54 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("l")).set(packet, dataWatcher);  
			}else if(Minecraft.VERSION.olderThan(Minecraft.Version.v1_11_R1)){
/*  56 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("a")).set(packet, Integer.valueOf(id));
/*  57 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("b")).set(packet, uuid);
/*  58 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("c")).set(packet, Integer.valueOf(64));
/*  59 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("d")).set(packet, Double.valueOf(loc.getX()));
/*  60 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("e")).set(packet, Double.valueOf(loc.getY()));
/*  61 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("f")).set(packet, Double.valueOf(loc.getZ()));
/*     */       
/*  63 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("j")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getYaw() * 256.0F / 360.0F)));
/*  64 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("k")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getPitch() * 256.0F / 360.0F)));
/*  65 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("l")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getPitch() * 256.0F / 360.0F)));
/*  66 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("m")).set(packet, dataWatcher);
/*     */     }else{
	/*  56 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("a")).set(packet, Integer.valueOf(id));
	/*  57 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("b")).set(packet, uuid);
	/*  58 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("c")).set(packet, Integer.valueOf(64));
	/*  59 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("d")).set(packet, Double.valueOf(loc.getX()));
	/*  60 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("e")).set(packet, Double.valueOf(loc.getY()));
	/*  61 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("f")).set(packet, Double.valueOf(loc.getZ()));
	/*     */       
	/*  63 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("j")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getYaw() * 256.0F / 360.0F)));
	/*  64 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("k")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getPitch() * 256.0F / 360.0F)));
	/*  65 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("l")).set(packet, Byte.valueOf((byte)MathUtil.d(loc.getPitch() * 256.0F / 360.0F)));
	/*  66 */       AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("m")).set(packet, dataWatcher);
}
/*     */     
/*  69 */     return packet;
/*     */   }
/*     */   
/*     */   public static Object buildNameMetadataPacket(int id, Object dataWatcher, int nameIndex, int visibilityIndex, String name)
/*     */     throws Exception
/*     */   {
/*  75 */     DataWatcher.setValue(dataWatcher, nameIndex, DataWatcher.V1_9.ValueType.ENTITY_NAME, name != null ? name : "");
/*  76 */     DataWatcher.setValue(dataWatcher, visibilityIndex, DataWatcher.V1_9.ValueType.ENTITY_NAME_VISIBLE, Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1) ? Byte.valueOf((byte)((name != null) && (!name.isEmpty()) ? 1 : 0)) : Boolean.valueOf((name != null) && (!name.isEmpty())));
/*  77 */     Object metaPacket = NMSClass.PacketPlayOutEntityMetadata.getConstructor(new Class[] { Integer.TYPE, NMSClass.DataWatcher, Boolean.TYPE }).newInstance(new Object[] { Integer.valueOf(id), dataWatcher, Boolean.valueOf(true) });
/*     */     
/*  79 */     return metaPacket;
/*     */   }
/*     */   
/*     */   public static Object updateEntityLocation(Object entity, Location loc) throws Exception {
/*  83 */     NMSClass.Entity.getDeclaredField("locX").set(entity, Double.valueOf(loc.getX()));
/*  84 */     NMSClass.Entity.getDeclaredField("locY").set(entity, Double.valueOf(loc.getY()));
/*  85 */     NMSClass.Entity.getDeclaredField("locZ").set(entity, Double.valueOf(loc.getZ()));
/*  86 */     return entity;
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
/*     */   public static Object buildArmorStandSpawnPacket(Object armorStand)
/*     */     throws Exception
/*     */   {
/* 159 */     Object spawnPacket = NMSClass.PacketPlayOutSpawnEntityLiving.getConstructor(new Class[] { NMSClass.EntityLiving }).newInstance(new Object[] { armorStand });
/* 160 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("b")).setInt(spawnPacket, 30);
/*     */     
/* 162 */     return spawnPacket;
/*     */   }
/*     */   
/*     */   public static Object buildTeleportPacket(int id, Location loc, boolean onGround, boolean heightCorrection) throws Exception {
/* 166 */     Object packet = NMSClass.PacketPlayOutEntityTeleport.newInstance();
/* 167 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("a")).set(packet, Integer.valueOf(id));
/* 168 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("b")).set(packet, Integer.valueOf((int)(loc.getX() * 32.0D)));
/* 169 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("c")).set(packet, Integer.valueOf((int)(loc.getY() * 32.0D)));
/* 170 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("d")).set(packet, Integer.valueOf((int)(loc.getZ() * 32.0D)));
/* 171 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("e")).set(packet, Byte.valueOf((byte)(int)(loc.getYaw() * 256.0F / 360.0F)));
/* 172 */     AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("f")).set(packet, Byte.valueOf((byte)(int)(loc.getPitch() * 256.0F / 360.0F)));
/*     */     
/* 174 */     return packet;
/*     */   }
/*     */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\bossbar\reflection\ClassBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */