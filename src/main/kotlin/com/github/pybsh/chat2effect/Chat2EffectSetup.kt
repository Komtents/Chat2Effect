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
import org.bukkit.Bukkit
import org.bukkit.ChatColor

object Chat2EffectSetup {
    lateinit var client: TwitchClient

    fun setup(){
        val credential = OAuth2Credential("twitch",Chat2EffectEnv.oauth)

        client = TwitchClientBuilder.builder()
            .withClientId(Chat2EffectEnv.clientId)
            .withClientSecret(Chat2EffectEnv.clientSecret)
            .withEnableChat(true)
            .withEnableHelix(true)
            .withChatAccount(credential)
            .withDefaultAuthToken(credential)
            .build()

        client.chat.joinChannel(Chat2EffectEnv.channelName)

        client.eventManager.onEvent(ChannelMessageEvent::class.java) { event ->
            Chat2EffectHandler.handle(event.message, event.user.name)
        }
    }
}