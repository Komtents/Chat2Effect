package com.github.pybsh.chat2effect

import org.bukkit.plugin.java.JavaPlugin

object Chat2EffectEnv {
    private fun getInstance(): JavaPlugin {
        return Chat2EffectPluginMain.instance
    }

    val channelName = getInstance().config.getString("channelName")
    val oauth = getInstance().config.getString("oauth")
    val clientId = getInstance().config.getString("clientId")
    val clientSecret = getInstance().config.getString("clientSecret")
}