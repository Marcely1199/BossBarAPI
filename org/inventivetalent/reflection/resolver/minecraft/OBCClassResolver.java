/*    */ package org.inventivetalent.reflection.resolver.minecraft;
/*    */ 
/*    */ import org.inventivetalent.reflection.minecraft.Minecraft;
/*    */ import org.inventivetalent.reflection.resolver.ClassResolver;
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
/*    */ public class OBCClassResolver
/*    */   extends ClassResolver
/*    */ {
/*    */   public Class resolve(String... names)
/*    */     throws ClassNotFoundException
/*    */   {
/* 41 */     for (int i = 0; i < names.length; i++) {
/* 42 */       if (!names[i].startsWith("org.bukkit.craftbukkit")) {
/* 43 */         names[i] = ("org.bukkit.craftbukkit." + Minecraft.getVersion() + names[i]);
/*    */       }
/*    */     }
/* 46 */     return super.resolve(names);
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\reflection\resolver\minecraft\OBCClassResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */