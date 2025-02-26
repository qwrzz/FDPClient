/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.FDPClient
import net.ccbluex.liquidbounce.features.command.Command

class FakeNameCommand : Command("FakeName", emptyArray()){
    override fun execute(args: Array<String>) {
        if(args.size > 2) {
            val module = FDPClient.moduleManager.getModule(args[1]) ?: return
            module.name = args[2]
        } else
            chatSyntax("FakeName <Module> <Name>")
    }
    override fun tabComplete(args: Array<String>): List<String> {
        if (args.isEmpty()) return emptyList()

        val moduleName = args[0]

        return when (args.size) {
            1 -> FDPClient.moduleManager.modules
                    .map { it.name }
                    .filter { it.startsWith(moduleName, true) }
                    .toList()
            else -> emptyList()
        }
    }
}