package unil.rush.client;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unil.rush.domain.DefeatCause;
import unil.rush.domain.Faculty;
import unil.rush.domain.Player;
import unil.rush.domain.Run;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class UnilRushClient {
    private static final String BASE_URL = "http://localhost:8080/UnilRushService-1.0-SNAPSHOT/api";
    private WebTarget playersTarget;
    private WebTarget runsTarget;
    private WebTarget leaderboardTarget;
    @PostConstruct
    public void init()
    {
        Client client = ClientBuilder.newClient();
        playersTarget = client.target(BASE_URL).path("players");
        runsTarget = client.target(BASE_URL).path("runs");
        leaderboardTarget = client.target(BASE_URL).path("leaderboard");
    }
    public Player addPlayer (String username, Faculty faculty, String password)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("faculty", faculty);
        body.put("password", password);

        Response response = playersTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200)
            return (response.readEntity(Player.class));
        else
            return (null);
    }

    public  Player login (String username, String password)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        Response response = playersTarget.path("login")
                .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(body, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200)
            return (response.readEntity(Player.class));
        else
            return (null);
    }
    public Run createRun(UUID playerId)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("playerID", playerId);
        Response response = runsTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200)
            return (response.readEntity(Run.class));
        else
            return (null);
    }
    public Run addWarning(UUID runId)
    {
        Response response = runsTarget.path(runId.toString()).path("warnings")
                .request(MediaType.APPLICATION_JSON)
                .post(null);

        if (response.getStatus() == 200)
            return (response.readEntity(Run.class));
        else
            return (null);
    }

    public Run nextDay(UUID runId)
    {
        Response response = runsTarget.path(runId.toString()).path("nextday")
                .request(MediaType.APPLICATION_JSON)
                .post(null);

        if (response.getStatus() == 200)
            return (response.readEntity(Run.class));
        else
            return (null);
    }

    public Run abandon(UUID runId)
    {
        Response response = runsTarget.path(runId.toString()).path("abandon")
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        if (response.getStatus() == 200)
            return (response.readEntity(Run.class));
        else
            return (null);
    }

    public Run lose(UUID runId, DefeatCause cause)
    {
        Response response = runsTarget.path(runId.toString()).path("lose")
                .queryParam("cause", cause)
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        if (response.getStatus() == 200)
            return (response.readEntity(Run.class));
        else
            return (null);
    }

    public List<Run> getLeaderboard()
    {
        return leaderboardTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Run>>() {});
    }
    public List<Player> getAllPlayers()
    {
        return playersTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Player>>() {});
    }

}

