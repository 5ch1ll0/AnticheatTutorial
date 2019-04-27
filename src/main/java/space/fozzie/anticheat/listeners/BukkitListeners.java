package space.fozzie.anticheat.listeners;

import cc.funkemunky.api.utils.Init;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import space.fozzie.anticheat.AnticheatTutorial;
import space.fozzie.anticheat.checks.Check;
import space.fozzie.anticheat.data.PlayerData;

import java.util.ArrayList;

/**
 * Created by George on 27/04/2019 at 18:57.
 */
@Init
public class BukkitListeners implements Listener {

    @EventHandler
    public void onEvent(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerData data = AnticheatTutorial.getInstance().getDataManager().getPlayerData(event.getEntity().getUniqueId());

            if (data != null) {
                callChecks(data, event);
            }
        }
    }

    private void callChecks(PlayerData data, Event event) {
        data.getBukkitChecks().getOrDefault(event.getClass(), new ArrayList<>()).stream().filter(Check::isEnabled).forEach(check -> check.onBukkitEvent(event));
    }
}
