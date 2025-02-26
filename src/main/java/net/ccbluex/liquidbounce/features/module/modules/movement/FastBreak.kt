/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.other.Breaker
import net.ccbluex.liquidbounce.value.FloatValue

@ModuleInfo(name = "FastBreak", category = ModuleCategory.MOVEMENT)
object FastBreak : Module() {

    private val breakDamageValue = FloatValue("BreakDamage", 0.8F, 0.1F, 1F)

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        mc.playerController.blockHitDelay = 0

        if (mc.playerController.curBlockDamageMP > breakDamageValue.get()) {
            mc.playerController.curBlockDamageMP = 1F
        }

        if (Breaker.currentDamage > breakDamageValue.get()) {
            Breaker.currentDamage = 1F
        }
    }
}
