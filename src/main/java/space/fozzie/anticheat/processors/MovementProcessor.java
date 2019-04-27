package space.fozzie.anticheat.processors;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.funkemunky.api.utils.BoundingBox;
import cc.funkemunky.api.utils.ReflectionsUtil;
import lombok.Getter;
import lombok.val;
import org.bukkit.entity.Vehicle;
import space.fozzie.anticheat.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 27/04/2019 at 18:13.
 */
@Getter
public class MovementProcessor {

    private boolean inLiquid, serverOnGround;
    private int liquidTicks;
    private List<BoundingBox> boxes = new ArrayList<>();

    public void update(PlayerData data, WrappedInFlyingPacket packet) {
        val player = packet.getPlayer();
        val timeStamp = System.currentTimeMillis();

        if (packet.isPos()) {
            data.setBoundingBox(ReflectionsUtil.toBoundingBox(ReflectionsUtil.getBoundingBox(packet.getPlayer())));

            List<BoundingBox> box = boxes = Atlas.getInstance().getBlockBoxManager().getBlockBox().getCollidingBoxes(player.getWorld(), data.getBoundingBox().grow(2f, 2f, 2f));

            CollisionAssessment assessment = new CollisionAssessment(data.getBoundingBox(), data);

            player.getNearbyEntities(1, 1, 1).stream().filter(entity -> entity instanceof Vehicle || entity.getType().name().toLowerCase().contains("shulker")).forEach(entity -> assessment.assessBox(ReflectionsUtil.toBoundingBox(ReflectionsUtil.getBoundingBox(entity)), player.getWorld(), true));

            box.forEach(bb -> assessment.assessBox(bb, player.getWorld(), false));

            serverOnGround = assessment.isOnGround();
            inLiquid = assessment.isInLiquid();

            liquidTicks = inLiquid ? Math.min(50, liquidTicks + 1) : Math.max(0, liquidTicks - 1);
        }
    }
}
