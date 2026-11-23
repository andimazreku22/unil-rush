package unil.rush.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import unil.rush.domain.Player;
import unil.rush.state.ApplicationState;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/players")
public class PlayerResource {

    @Inject
    private ApplicationState state;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getAllPlayers()
    {
        return(state.getAllPlayers());
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Player login(Map<String, String> loginfo)
    {
        Player player;
        String user = loginfo.get("username");
        String passwrod = loginfo.get("password") ;
        player = state.authenticate(user,passwrod);
        if (player == null)
            throw new WebApplicationException("the username or password are wrong", 401 );
        return  player;
    }




    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Player addPlayer(Player player)
    {
        if (state.usernameExists(player.getUsername()))
            throw new WebApplicationException("the username is already taken", 409 );

        state.addPlayer(player);
        return(player);
    }

    @Path ("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Player getPlayer(@PathParam("id") UUID id) {
        Player player = state.getPlayer(id);
        if (player == null)
            throw new NotFoundException("Player not found");
        else
            return (player);
    }
    @Path("/{id}")
    @DELETE
    public void removePlayer(@PathParam("id") UUID id)
    {
        if (!state.removePlayer(id)) {
            throw new NotFoundException("Player not found");
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void  updatePlayer(@PathParam("id") UUID id, Player player)
    {
        if(!state.updatePlayer(id,player))
            throw new NotFoundException("Player not found");
    }


}





