package com.kraskaska.chunk_monster.mixin;

import com.kraskaska.chunk_monster.ChunkMonster;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ChunkSaveMixin {

    @Inject(method = "Lnet/minecraft/server/world/ThreadedAnvilChunkStorage;save(Lnet/minecraft/world/chunk/Chunk;)Z", at = @At("HEAD"))
    public void dontSaveUnmodified(Chunk chunk, CallbackInfoReturnable<Boolean> cir) {
//        if(!ChunkMonster.INSTANCE.shouldChunkSave(chunk.getPos())) ChunkMonster.INSTANCE.getLogger().info("Chunk will not be saved! >:(");
        chunk.setNeedsSaving(chunk.needsSaving() && ChunkMonster.INSTANCE.isChunkModified(chunk.getPos()));
    }
}
