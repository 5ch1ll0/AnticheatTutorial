package space.fozzie.anticheat.checks;

import cc.funkemunky.api.event.system.Listener;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.JsonMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import space.fozzie.anticheat.data.PlayerData;

/**
 * Created by George on 27/04/2019 at 18:10.
 */
@Getter
@Setter
public abstract class Check implements Listener, org.bukkit.event.Listener {

    private String name;
    private PlayerData data;
    private boolean enabled;
    private String alertsMessage;
    private int vl;

    public Check(String name) {
        this.name = name;

        enabled = true;

        alertsMessage = Color.translate("&8[&aTutorial&8] &c%player% &7has failed &c%check% &c(x%vl%)").replace("%check%", name);
    }

    protected void flag(String information) {
        vl++;

        JsonMessage message = new JsonMessage();
        message.addText(Color.translate(alertsMessage.replaceAll("%player%", data.getPlayer().getName()).replaceAll("%vl%", String.valueOf(vl)).replaceAll("%info%", information))).addHoverText(Color.Gray + information);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("tutorial.alerts")).forEach(message::sendToPlayer);
    }

    public abstract void onPacket(Object packet, String packetType, long timeStamp);
}
