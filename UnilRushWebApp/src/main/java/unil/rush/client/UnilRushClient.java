package unil.rush.client;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unil.rush.domain.Faculty;
import unil.rush.domain.Player;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class UnilRushClient {
    private static final String BASE_URL = "http://localhost:8080/UnilRushService-1.0-SNAPSHOT/api";
    private WebTarget playersTarget;
    @PostConstruct
    public void init()
    {
        Client client = ClientBuilder.newClient();
        playersTarget = client.target(BASE_URL).path("players");
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
}

