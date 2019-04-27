package space.fozzie.anticheat.data;

import cc.funkemunky.api.utils.BoundingBox;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import space.fozzie.anticheat.AnticheatTutorial;
import space.fozzie.anticheat.checks.Check;
import space.fozzie.anticheat.processors.MovementProcessor;
import space.fozzie.anticheat.utils.Packets;

import java.util.*;

/**
 * Created by George on 27/04/2019 at 18:11.
 */
@Getter
@Setter
public class PlayerData {

    private UUID uuid;
    private Player player;
    private List<Check> checks = new ArrayList<>();
    private Map<String, List<Check>> packetChecks = new HashMap<>();
    private Map<Class, List<Check>> bukkitChecks = new HashMap<>();

    private BoundingBox boundingBox;

    private MovementProcessor movementProcessor;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);

        movementProcessor = new MovementProcessor();

        AnticheatTutorial.getInstance().getCheckManager().loadChecksIntoData(this);

        checks.stream().filter(check -> check.getClass().isAnnotationPresent(Packets.class)).forEach(check -> {
            Packets packets = check.getClass().getAnnotation(Packets.class);

            Arrays.stream(packets.packets()).forEach(packet -> {
                List<Check> checks = packetChecks.getOrDefault(packet, new ArrayList<>());

                checks.add(check);

                packetChecks.put(packet, checks);
            });
        });
    }
}
