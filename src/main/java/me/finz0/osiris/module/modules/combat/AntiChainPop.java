package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AntiChainPop extends Module {
    public AntiChainPop() {
        super("AntiChainPop", Category.COMBAT);
    }

    boolean shouldCancel = false;

    @EventHandler
    private Listener<LivingDamageEvent> listener = new Listener<>(event -> {
        if(mc.player != null || event.getEntity() != null) return;
        float hp = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        if(event.getEntity() == mc.player && event.getAmount() >= hp){
            mc.player.connection.sendPacket(new CPacketKeepAlive());
            shouldCancel = true;
        } else {
            shouldCancel = false;
        }
    });

    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketChatMessage || packet instanceof CPacketConfirmTeleport || packet instanceof CPacketKeepAlive || packet instanceof CPacketTabComplete || packet instanceof CPacketClientStatus || packet instanceof CPacketHeldItemChange)
            return;
        if(shouldCancel){
            event.cancel();
            //mc.player.connection.sendPacket(new CPacketConfirmTeleport());
            shouldCancel = false;
        }
    });

    boolean isHoldingTotem(){
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING || mc.player.getHeldItemMainhand().getItem() == Items.TOTEM_OF_UNDYING){
            return true;
        } else {
            return false;
        }
    }

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
        shouldCancel = false;
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
        shouldCancel = false;
    }

}