package actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;
import facade.FacadeParis;
import facade.FacadeParisStaticImpl;
import modele.Utilisateur;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class Environnement extends ActionSupport implements  SessionAware {

    private Map<String, Object> session;

    private FacadeParis facade;

    @Inject("facade")
    public void setFacade(FacadeParis facade){
        this.facade= facade;
    }

    public FacadeParis getFacade() {
        return facade;
    }

    public Utilisateur getUtilisateur() {
        return (Utilisateur) session.get("user");
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        session.put("user", utilisateur);
    }

    public void unsetUtilisateur() {
        session.remove("user");
    }


    @Override
    public void setSession(Map<String, Object> map) {
        session = map;
    }

}
