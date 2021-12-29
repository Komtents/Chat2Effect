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

import org.bukkit.plugin.java.JavaPlugin
import com.github.pybsh.chat2effect.Chat2EffectEnv.oauth
import com.github.pybsh.chat2effect.Chat2EffectEnv.clientId
import com.github.pybsh.chat2effect.Chat2EffectEnv.clientSecret
import com.github.pybsh.chat2effect.Chat2EffectEnv.channelName
import org.bukkit.ChatColor

/***
 * @author PyBsh
 */

class Chat2EffectPluginMain : JavaPlugin() {
    companion object {
        lateinit var instance: Chat2EffectPluginMain
            private set
    }

    override fun onEnable() {
        instance = this
        logger.info("by PyBsh.")

        this.saveDefaultConfig()

        if(oauth.isNullOrBlank() || clientId.isNullOrBlank() || clientId.isNullOrBlank() || clientSecret.isNullOrBlank() || channelName.isNullOrBlank()){
            logger.info("${ChatColor.RED} CONFIG IS NOT SET. Chat2Effect Disabled.")
            this.server.pluginManager.disablePlugin(this)
        }
        else{
            Chat2EffectSetup.setup()
        }
    }

    override fun onDisable() {

    }
}