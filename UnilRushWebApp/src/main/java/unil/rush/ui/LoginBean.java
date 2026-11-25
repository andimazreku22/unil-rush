package unil.rush.ui;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unil.rush.client.UnilRushClient;
import unil.rush.domain.Faculty;
import unil.rush.domain.Player;

import java.io.Serializable;

@SessionScoped
@Named

public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private UnilRushClient client;

    private String username;
    private String password;
    private String message;
    private Player currentPlayer;

    public String login()
    {
        Player player = client.login(username,password);
        if (player == null) {
            message = "Connexion refusée, nom d'utilisateur ou mot de passe faux";
            return null;
        }
        this.currentPlayer = player;
        password = null;
        message = null;
        return "Menu?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public String logout()
    {
        currentPlayer = null;
        username = null;
        password = null;
        message = null;
        return ("index?faces-redirect=true");
    }
}
