package com.kraskaska.chunk_monster

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.entity.Entity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.math.ChunkPos
import org.slf4j.LoggerFactory

fun Entity.hasOwner(): Boolean = this is TameableEntity && (this as TameableEntity).owner != null

object ChunkMonster : ModInitializer {
    val logger = LoggerFactory.getLogger(ChunkMonster.javaClass)
    val unmodifiedChunks = mutableListOf<ChunkPos>()
    override fun onInitialize(){
        logger.info("Initializing Chunk Monster...")
        ServerLifecycleEvents.SERVER_STARTED.register {
            PlayerBlockBreakEvents.AFTER.register { world, player, blockPos, blockState, blockEnt ->
                if(!isChunkModified(player.chunkPos)) {
                    setChunkModified(player.chunkPos, true)
                    player.sendMessage(
                        Text.literal(
                            String.format(
                                "Chunk at %s, %s is now modified!",
                                player.chunkPos.x,
                                player.chunkPos.z
                            )
                        ).styled { it.withColor(Formatting.AQUA) })
                } else {
                    player.sendMessage(
                        Text.literal(
                            String.format(
                                "Chunk at %s, %s already modified",
                                player.chunkPos.x,
                                player.chunkPos.z
                            )
                        ).styled { it.withColor(Formatting.LIGHT_PURPLE) })
                }
            }
        }
        ServerLifecycleEvents.SERVER_STOPPING.register {
            it.worlds.forEach {
                it.iterateEntities().forEach {
                    if(it != null && !isChunkModified(it.chunkPos) && !it.hasCustomName() && !it.hasOwner()) {
                        it.discard() // bye bye!
                    }
                }
            }
        }
    }

    fun isChunkModified(pos: ChunkPos) = unmodifiedChunks.none {it==pos}
    fun setChunkModified(pos: ChunkPos, value: Boolean) {
        if(value) {
            if(!isChunkModified(pos)) unmodifiedChunks.remove(pos)
        } else {
            if(isChunkModified(pos)) unmodifiedChunks.add(pos)
        }
    }
}
