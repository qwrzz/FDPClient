/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other

import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.ccbluex.liquidbounce.utils.misc.RandomUtils

class HypixelVelocity : VelocityMode("Hypixel") {

    override fun onVelocityPacket(event: PacketEvent) {
        val packet = event.packet
        if(packet is S12PacketEntityVelocity) {
          event.cancelEvent()
          mc.thePlayer.motionY = packet.getMotionY().toDouble() / 8000.0
          mc.thePlayer.motionX = 0.3 * packet.getMotionX().toDouble() / 8000.0
          mc.thePlayer.motionZ = 0.3 * packet.getMotionY().toDouble() / 8000.0
        }
    }
}
