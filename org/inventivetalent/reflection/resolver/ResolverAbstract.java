/*    */ package org.inventivetalent.reflection.resolver;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ResolverAbstract<T>
/*    */ {
/* 46 */   protected final Map<ResolverQuery, T> resolvedObjects = new ConcurrentHashMap();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected T resolveSilent(ResolverQuery... queries)
/*    */   {
/*    */     try
/*    */     {
/* 56 */       return (T)resolve(queries);
/*    */     }
/*    */     catch (Exception e) {}
/* 59 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected T resolve(ResolverQuery... queries)
/*    */     throws ReflectiveOperationException
/*    */   {
/* 71 */     if ((queries == null) || (queries.length <= 0)) throw new IllegalArgumentException("Given possibilities are empty");
/* 72 */     for (ResolverQuery query : queries)
/*    */     {
/* 74 */       if (this.resolvedObjects.containsKey(query)) { return (T)this.resolvedObjects.get(query);
/*    */       }
/*    */       try
/*    */       {
/* 78 */         T resolved = resolveObject(query);
/*    */         
/* 80 */         this.resolvedObjects.put(query, resolved);
/* 81 */         return resolved;
/*    */       }
/*    */       catch (ReflectiveOperationException e) {}
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 88 */     throw notFoundException(Arrays.asList(queries).toString());
/*    */   }
/*    */   
/*    */   protected abstract T resolveObject(ResolverQuery paramResolverQuery) throws ReflectiveOperationException;
/*    */   
/*    */   protected ReflectiveOperationException notFoundException(String joinedNames) {
/* 94 */     return new ReflectiveOperationException("Objects could not be resolved: " + joinedNames);
/*    */   }
/*    */ }


/* Location:              E:\Users\Edik\Minecraft\eclipse\spigot2\plugins\BossBarAPI_v2.4.1.jar!\org\inventivetalent\reflection\resolver\ResolverAbstract.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */