package space.fozzie.anticheat.checks;

import lombok.Getter;
import lombok.Setter;
import space.fozzie.anticheat.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 27/04/2019 at 18:09.
 */
@Getter
@Setter
public class CheckManager {

    private List<Check> checks;

    public CheckManager() {
        checks = loadChecks();
    }

    public List<Check> loadChecks() {
        List<Check> checks = new ArrayList<>();

        return checks;
    }

    public void loadChecksIntoData(PlayerData data) {
        List<Check> checks = loadChecks();

        data.getChecks().clear();

        checks.forEach(check -> check.setData(data));

        data.setChecks(checks);
    }

    public Check getCheck(String name) {
        return checks.stream().filter(check -> check.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
