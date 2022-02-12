package com.github.pybsh.chat2effect

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class Chat2EffectListener : Listener{

    @EventHandler
    fun onChat(e: AsyncChatEvent){
        val player = e.player.name
        val msg = (e.message() as TextComponent).content()
        Chat2EffectHandler.handle(msg, player)
    }
}