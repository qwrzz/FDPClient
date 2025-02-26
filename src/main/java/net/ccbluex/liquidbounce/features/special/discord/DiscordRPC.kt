/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.special.discord

import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import com.jagrosh.discordipc.entities.pipe.PipeStatus
import net.ccbluex.liquidbounce.FDPClient
import net.ccbluex.liquidbounce.features.module.modules.client.DiscordRPCModule
import net.ccbluex.liquidbounce.utils.*
import net.ccbluex.liquidbounce.utils.ClientUtils.mc
import org.json.JSONObject
import java.time.OffsetDateTime
import kotlin.concurrent.thread

object DiscordRPC {                   
    private val ipcClient = IPCClient(1183085624195022942)
    private val timestamp = OffsetDateTime.now()
    private var running = false
    private var fdpwebsite = "fdpinfo.github.io - "

    fun run() {
        ipcClient.setListener(object : IPCListener {
            override fun onReady(client: IPCClient?) {
                running = true
                thread {
                    while (running) {
                        update()
                        try {
                            Thread.sleep(1000L)
                        } catch (ignored: InterruptedException) {
                        }
                    }
                }
            }

            override fun onClose(client: IPCClient?, json: JSONObject?) {
                running = false
            }
        })
        try {
            ipcClient.connect()
        } catch (e: Exception) {
            ClientUtils.logError("DiscordRPC failed to start")
        } catch (e: RuntimeException) {
            ClientUtils.logError("DiscordRPC failed to start")
        }
    }

    private fun update() {
        val builder = RichPresence.Builder()
        val discordRPCModule = FDPClient.moduleManager[DiscordRPCModule::class.java]!!
        builder.setStartTimestamp(timestamp)
        builder.setLargeImage(if (discordRPCModule.animated.get()){"https://skiddermc.github.io/repo/skiddermc/FDPclient/dcrpc/fdp.gif"} else {"https://skiddermc.github.io/repo/skiddermc/FDPclient/dcrpc/fdp.png"})
        builder.setDetails(fdpwebsite + FDPClient.CLIENT_VERSION)
        ServerUtils.getRemoteIp().also { it ->
            val str = (if(discordRPCModule.showServerValue.get()) "Server: $it\n" else "\n") + (if(discordRPCModule.showNameValue.get()) "IGN: ${if(mc.thePlayer != null) mc.thePlayer.name else mc.session.username}\n" else "\n") + (if(discordRPCModule.showHealthValue.get()) "HP: ${mc.thePlayer.health}\n" else "\n") + (if(discordRPCModule.showModuleValue.get()) "Enable: ${FDPClient.moduleManager.modules.count{ it.state }} of ${FDPClient.moduleManager.modules.size} Modules\n" else "\n") + (if(discordRPCModule.showOtherValue.get()) "Time: ${if(mc.isSingleplayer) "SinglePlayer\n" else SessionUtils.getFormatSessionTime()}\n" else "\n")
            builder.setState(if(it.equals("Injecting", true)) "Injecting" else str)
        }

        // Check ipc client is connected and send rpc
        if (ipcClient.status == PipeStatus.CONNECTED)
            ipcClient.sendRichPresence(builder.build())
    }

    fun stop() {
        if (ipcClient.status == PipeStatus.CONNECTED) ipcClient.close()
    }
}
