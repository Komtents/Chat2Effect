package com.github.pybsh.chat2effect

import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.exception.KordInitializationException
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin

class KordClient {
    private val plugin = Chat2EffectPluginMain.instance

    companion object{
        lateinit var client: Kord
        var token: String? = Chat2EffectPluginMain.instance.config.getString("token")
    }

    private suspend fun start(plugin: JavaPlugin){
        plugin.server.scheduler.runTaskAsynchronously(plugin) { _ ->
            try{
                runBlocking {
                    launch {
                        init(token!!)
                    }
                }
            } catch (e: KordInitializationException){
                plugin.logger.warning("Discord token is invalid!")
                plugin.server.pluginManager.disablePlugin(plugin)
            }
        }
    }

    fun stop(plugin: JavaPlugin){
        runBlocking {
            launch {
                try {
                    client.shutdown()
                } catch (e: Exception){
                    plugin.logger.warning(e.message)
                }
            }
        }
    }

    private suspend fun init(token: String){
        client = Kord(token)
        client.on<MessageCreateEvent> {
            Chat2EffectHandler.handle(message.content, message.author!!.username)
        }
        client.login{
            @OptIn(PrivilegedIntent::class)
            intents += Intent.MessageContent
            intents += Intent.DirectMessages
        }
    }

    fun run(plugin: JavaPlugin){
        runBlocking {
            launch {
                start(plugin)
            }
        }
    }
}