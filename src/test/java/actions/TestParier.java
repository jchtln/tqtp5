package actions;

import com.opensymphony.xwork2.ActionProxy;
import facade.FacadeParis;
import facade.exceptions.MatchClosException;
import facade.exceptions.MontantNegatifOuNulException;
import facade.exceptions.ResultatImpossibleException;
import modele.Match;
import modele.Pari;
import modele.Utilisateur;
import org.apache.struts2.StrutsJUnit4TestCase;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TestParier extends StrutsJUnit4TestCase {




    //test montant<0
//    @Test
//    public void montantSupAZero() throws MontantNegatifOuNulException, ResultatImpossibleException, MatchClosException {
//
//        long montant = 10;
//        long idmatch = 1;
//        String resultat="nul";
//
//        request.addParameter("montant", String.valueOf(montant));
//        request.addParameter("idmatch", String.valueOf(idmatch));
//        request.addParameter("resultat",resultat);
//
//
//        Map<String,Object> sessions = new HashMap<>();
//        Map<String,Object> applications = new HashMap<>();
//
//        ActionProxy actionProxy = getActionProxy("/goParier");
//        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
//        actionProxy.getInvocation().getInvocationContext().setSession(sessions);
//
//
//        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
//        Pari pari = EasyMock.createMock(Pari.class);
//
//        EasyMock.expect(facadeParis.parier("julien",idmatch,resultat,montant)).andReturn(112l);
//        EasyMock.expect(pari.getMatch()).andReturn(idmatch);
//
//
//
//        Assert.assertEquals(resultat,"success");
//        Assert.assertTrue(actionExecutee.getMatch()==match);
//    }

    //test match = null
//    @Test
//    public void montantInfAZero() throws MontantNegatifOuNulException, ResultatImpossibleException, MatchClosException {
//        long montant = -10;
//        long idmatch = 1l;
//        String resultat="nul";
//
//        request.addParameter("montant", String.valueOf(montant));
//        request.addParameter("idmatch", String.valueOf(idmatch));
//        request.addParameter("resultat",resultat);
//
//
//        Map<String,Object> sessions = new HashMap<>();
//        Map<String,Object> applications = new HashMap<>();
//
//        ActionProxy actionProxy = getActionProxy("/goParier");
//        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
//        actionProxy.getInvocation().getInvocationContext().setSession(sessions);
//
//
//
//        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
//        Pari pari = EasyMock.createMock(Pari.class);
//
//        EasyMock.expect(facadeParis.parier("julien",idmatch,resultat,montant)).andReturn(112l);
//        EasyMock.expect(pari.getMatch()).andReturn(idmatch);
//
//
//
//        Assert.assertEquals(resultat,"input");
//        Assert.assertTrue(actionExecutee.getMatch()==match);
//    }








    @Test
    public void testOK() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        Double montant=10d;
        long idMatch = 1l;
        String res="nul";
        request.addParameter("montant", String.valueOf(montant));
        request.addParameter("idMatch", String.valueOf(idMatch));
        request.addParameter("resultat",res);

        Map<String,Object> sessions = new HashMap<>();
        Map<String,Object> applications = new HashMap<>();

        ActionProxy actionProxy = getActionProxy("/parier");
        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
        actionProxy.getInvocation().getInvocationContext().setSession(sessions);
        actionProxy.getInvocation().getInvocationContext().setLocale(new Locale("en","EN"));

        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
        Utilisateur utilisateur = EasyMock.createMock(Utilisateur.class);
        Pari pari = EasyMock.createMock(Pari.class);
        Match match = EasyMock.createMock(Match.class);

        EasyMock.expect(facadeParis.parier("yohan",idMatch,res,montant)).andReturn(1l);
        EasyMock.expect(pari.getMatch()).andReturn(match);
        EasyMock.expect(facadeParis.getPari(1l)).andReturn(pari);
        EasyMock.expect(match.getEquipe1()).andReturn("OM");
        EasyMock.expect(match.getEquipe2()).andReturn("PSG");
        EasyMock.expect(facadeParis.getMatch(idMatch)).andReturn(match);
        EasyMock.expect(utilisateur.getLogin()).andReturn("yohan");
        EasyMock.replay(facadeParis, utilisateur, pari,match);

        sessions.put("user",utilisateur);
        applications.put("facade",facadeParis);

        String resultat = actionProxy.execute();

        Parier actionExecutee = (Parier) actionProxy.getAction();

        Assert.assertEquals(resultat,"success");
        Assert.assertTrue(actionExecutee.getPari()==pari);

    }
}
