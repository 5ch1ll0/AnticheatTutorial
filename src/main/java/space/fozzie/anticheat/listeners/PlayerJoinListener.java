package space.fozzie.anticheat.listeners;

import cc.funkemunky.api.utils.Init;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.fozzie.anticheat.AnticheatTutorial;

/**
 * Created by George on 27/04/2019 at 18:34.
 */
@Init
public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        AnticheatTutorial.getInstance().getDataManager().addData(event.getPlayer().getUniqueId());
    }
}
