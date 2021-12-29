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

import net.kyori.adventure.text.Component.text
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit.getOnlinePlayers
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.awt.Color
import kotlin.random.Random

object Chat2EffectHandler {
    private fun getInstance(): JavaPlugin {
        return Chat2EffectPluginMain.instance
    }

    fun handle(msg: String, user: String){
        var type: PotionEffectType? = null

        when(msg.replace(" ","")){
            "신속" -> type = PotionEffectType.SPEED
            "구속", "감속" -> type = PotionEffectType.SLOW
            "성급함" -> type = PotionEffectType.FAST_DIGGING
            "채굴피로" -> type = PotionEffectType.SLOW_DIGGING
            "힘" -> type = PotionEffectType.INCREASE_DAMAGE
            "도약","점프강화","점강" -> type = PotionEffectType.JUMP
            "재생", "회복" -> type = PotionEffectType.REGENERATION
            "저항" -> type = PotionEffectType.DAMAGE_RESISTANCE
            "화염저항" -> type = PotionEffectType.FIRE_RESISTANCE
            "수중호흡" -> type = PotionEffectType.WATER_BREATHING
            "투명" -> type = PotionEffectType.INVISIBILITY
            "야간투시","야투" -> type = PotionEffectType.NIGHT_VISION
            "허기", "배고픔" -> type = PotionEffectType.HUNGER
            "나약함" -> type = PotionEffectType.WEAKNESS
            "포화", "배부름" -> type = PotionEffectType.SATURATION
            "발광" -> type = PotionEffectType.GLOWING
            "행운" -> type = PotionEffectType.LUCK
            "불운" -> type = PotionEffectType.UNLUCK
            "느린낙하" -> type = PotionEffectType.SLOW_FALLING
            "돌고래","돌고래의가호" -> type = PotionEffectType.DOLPHINS_GRACE
            "흉조" -> type = PotionEffectType.BAD_OMEN
            "마을의영웅" -> type = PotionEffectType.HERO_OF_THE_VILLAGE
            "공중부양" -> type = PotionEffectType.LEVITATION
        }

        if(type == null) return
        getInstance().server.scheduler.scheduleSyncDelayedTask(getInstance(), {
            getOnlinePlayers().forEach {
                val potion = it.getPotionEffect(type)
                if(potion != null){
                    val amp = potion.amplifier
                    it.addPotionEffect(PotionEffect(type, 60, amp+1, false, false))
                }
                else{
                    it.addPotionEffect(PotionEffect(type, 60, 0, false, false))
                }

                it.sendMessage(text("${ChatColor.of(Color(Random.nextInt(0xFF0000)))}${user}${ChatColor.WHITE}: $msg"))
            }
        }, 0L)
    }

}