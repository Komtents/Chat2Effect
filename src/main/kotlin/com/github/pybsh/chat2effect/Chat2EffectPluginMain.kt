/*
 * Copyright (c) 2021 PyBsh
 *
 *  Licensed under the General Public License, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/gpl-3.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pybsh.chat2effect

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import net.kyori.adventure.text.Component.text
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color
import kotlin.random.Random.Default.nextInt

/***
 * @author PyBsh
 */

class Chat2EffectPluginMain : JavaPlugin() {

    companion object {
        lateinit var instance: Chat2EffectPluginMain
            private set
        lateinit var client: TwitchClient
            private set
    }

    override fun onEnable() {
        instance = this
        logger.info("by PyBsh. For Celebrate Komq 150k Subs!")

        val credential = OAuth2Credential("twitch",Chat2EffectEnv.oauth)

        client = TwitchClientBuilder.builder()
            .withClientId(Chat2EffectEnv.clientId)
            .withClientSecret(Chat2EffectEnv.clientSecret)
            .withEnableChat(true)
            .withEnableHelix(true)
            .withChatAccount(credential)
            .withDefaultAuthToken(credential)
            .build()

        client.chat.joinChannel("pybsh")

        client.eventManager.onEvent(ChannelMessageEvent::class.java) { event ->
            Bukkit.getOnlinePlayers().forEach {
                it.sendMessage(text("${ChatColor.of(Color(nextInt(0xFF0000)))}${event.user.name}${ChatColor.WHITE}: ${event.message}"))
                Chat2EffectHandler.handle(event.message)
            }
        }
    }

    override fun onDisable() {
        client.close()
    }
}