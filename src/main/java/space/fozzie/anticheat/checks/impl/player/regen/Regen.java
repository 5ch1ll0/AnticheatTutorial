package space.fozzie.anticheat.checks.impl.player.regen;

import cc.funkemunky.api.tinyprotocol.api.ProtocolVersion;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import space.fozzie.anticheat.checks.Check;
import space.fozzie.anticheat.utils.BukkitEvents;
import space.fozzie.anticheat.utils.Verbose;

/**
 * Created by George on 27/04/2019 at 19:01.
 */
@BukkitEvents(events = {EntityRegainHealthEvent.class})
public class Regen extends Check {

    private Verbose verbose = new Verbose();

    public Regen(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {

    }

    @Override
    public void onBukkitEvent(Event event) {
        EntityRegainHealthEvent e = (EntityRegainHealthEvent) event;

        if (e.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)) {
            if (verbose.flag(2, ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9) ? 750L : 450L)) {
                flag("");
            }
        }
    }
}
