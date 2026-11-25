package unil.rush.ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unil.rush.client.UnilRushClient;
import unil.rush.domain.DefeatCause;
import unil.rush.domain.Run;

import java.io.Serializable;
import java.util.UUID;

@SessionScoped
@Named
public class PlayBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private UnilRushClient client;
    @Inject
    private LoginBean loginBean;

    private Run currentRun;
    private String message;
    private DefeatCause selectedCause;

    public Run getCurrentRun() {
        return currentRun;
    }

    public String getMessage() {
        return message;
    }

    public void newRun()
    {
        UUID playerId = loginBean.getCurrentPlayer().getPlayerID();
        Run run = client.createRun(playerId);

        if (run == null)
            message = "impossible de lancer la partie";
        else
        {
            currentRun = run;
            message = null;
        }

    }

    public int getWarningPercent()
    {
        return ((currentRun.getWarnings()*100)/3) ;

    }

    public void warning()
    {
        Run run = client.addWarning(currentRun.getRunID());
        if(run == null)
            message = "impossible d'ajouter un warning";
        else
        {
            currentRun = run;
            message = null;
        }
    }
    public void nextDay()
    {
        Run run = client.nextDay(currentRun.getRunID());
        if(run == null)
            message = "impossible de passer un jour";
        else
        {
            currentRun = run;
            message = null;
        }
    }
    public DefeatCause[] getCauses()
    {
        return new DefeatCause[] { DefeatCause.LATE, DefeatCause.CAUGHT };
    }

    public void abandon()
    {
        Run run = client.abandon(currentRun.getRunID());
        if(run == null)
            message = "impossible d'abandonner cette partie";
        else
        {
            currentRun = run;
            message = null;
        }
    }

    public DefeatCause getSelectedCause() {
        return selectedCause;
    }

    public void setSelectedCause(DefeatCause selectedCause) {
        this.selectedCause = selectedCause;
    }

    public void lose()
    {
        Run run = client.lose(currentRun.getRunID(), selectedCause);
        if(run == null)
            message = "impossible de perdre cette partie de cette cause";
        else
        {
            currentRun = run;
            message = null;
        }
    }

}


