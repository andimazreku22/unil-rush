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


public class RegisterBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private UnilRushClient client;

    private String username;
    private String password;
    private Faculty faculty;
    private String message;

    public void reset()
    {
        username = null;
        password = null;
        faculty =null;
    }
    public Faculty[] getFaculties(){
        return(Faculty.values());
    }

    public void register(){
        Player player = client.addPlayer(username,faculty,password);
        if (player == null) {
            message = "Inscription refusée";
        }
        else {
            message = "Inscription " + player.getUsername() + " réussie";
            reset();
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
