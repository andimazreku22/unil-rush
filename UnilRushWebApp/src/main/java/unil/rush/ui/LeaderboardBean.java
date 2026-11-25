package unil.rush.ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unil.rush.client.UnilRushClient;
import unil.rush.domain.Player;
import unil.rush.domain.Run;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestScoped
@Named

public class LeaderboardBean {
    @Inject
    private UnilRushClient client;
    private List<Run> runs;
    private Map<UUID, String> usernames = new HashMap<>();

    @PostConstruct
    public void init()
    {
        runs = client.getLeaderboard();
        for (Player p : client.getAllPlayers())
            usernames.put(p.getPlayerID(), p.getUsername());
    }

    public List<Run> getRuns() {
        return runs;
    }
    public String username(UUID playerId)
    {
        return usernames.get(playerId);
    }
}
