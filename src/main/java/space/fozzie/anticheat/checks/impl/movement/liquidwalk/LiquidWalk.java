package space.fozzie.anticheat.checks.impl.movement.liquidwalk;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.BlockUtils;
import cc.funkemunky.api.utils.MathUtils;
import lombok.val;
import org.bukkit.event.Event;
import space.fozzie.anticheat.checks.Check;
import space.fozzie.anticheat.utils.Packets;
import space.fozzie.anticheat.utils.Verbose;

/**
 * Created by George on 28/04/2019 at 10:46.
 */
@Packets(packets = {Packet.Client.POSITION_LOOK,
        Packet.Client.POSITION,
        Packet.Client.LEGACY_POSITION_LOOK,
        Packet.Client.LEGACY_POSITION})
public class LiquidWalk extends Check {

    public LiquidWalk(String name) {
        super(name);
    }

    private Verbose verbose = new Verbose();

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {
        MovementProcessor move = getData().getMovementProcessor();

        if (BlockUtils.isLiquid(getData().getPlayer().getLocation().subtract(0, 0.1, 0).getBlock())
                && !BlockUtils.isLiquid(getData().getPlayer().getLocation().clone().add(0, 0.2, 0).getBlock()) && move.getWebTicks() == 0) {
            if (!move.isServerOnGround() && verbose.flag(10, 250L)) {
                flag(verbose.getVerbose() + " > 10");
            }
        }
    }

    @Override
    public void onBukkitEvent(Event event) {

    }
}
