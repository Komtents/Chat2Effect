package com.github.pybsh.chat2effect

import org.bukkit.Bukkit
import org.bukkit.Bukkit.getOnlinePlayers
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Chat2EffectHandler {
    private fun getInstance(): JavaPlugin {
        return Chat2EffectPluginMain.instance
    }

    fun handle(msg: String){
        var type: PotionEffectType? = null

        val m = msg.replace(" ","")
        when(m){
            "신속" -> type = PotionEffectType.SPEED
            "구속", "감속" -> type = PotionEffectType.SLOW
            "성급함" -> type = PotionEffectType.FAST_DIGGING
            "채굴피로" -> type = PotionEffectType.SLOW_DIGGING
            "힘" -> type = PotionEffectType.INCREASE_DAMAGE
            "도약","점프강화","점강" -> type = PotionEffectType.JUMP
            "재생", "회복" -> type = PotionEffectType.HEAL
            "저항" -> type = PotionEffectType.DAMAGE_RESISTANCE
            "화염저항" -> type = PotionEffectType.FIRE_RESISTANCE
            "수중호흡" -> type = PotionEffectType.WATER_BREATHING
            "투명" -> type = PotionEffectType.INVISIBILITY
            "실명" -> type = PotionEffectType.BLINDNESS
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
        }

        if(type == null) return
        Bukkit.getServer().scheduler.scheduleSyncDelayedTask(getInstance(), {
            getOnlinePlayers().forEach { it.addPotionEffect(PotionEffect(type, 600, 0, true)) }
        }, 0L)
    }

}