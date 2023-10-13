package com.kraskaska.chunk_monster.mixin;

import com.kraskaska.chunk_monster.ChunkMonster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChunkStatus.class)
public class UnmodifiedChunkMixin {
    // god
    @Inject(method = "method_17033(Lnet/minecraft/world/chunk/ChunkStatus;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/List;Lnet/minecraft/world/chunk/Chunk;)V", at = @At("TAIL"))
    private static void markUnmodifiedChunk(ChunkStatus targetStatus, ServerWorld world, ChunkGenerator generator, List chunks, Chunk chunk, CallbackInfo ci) {
//        ChunkMonster.INSTANCE.getLogger().info("UNMODIFIED CHUNK {}, {}", chunk.getPos().x, chunk.getPos().z);
        ChunkMonster.INSTANCE.setChunkModified(chunk.getPos(), false);
    }
}
