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


    @Test
    public void testMontantSupAZero() throws Exception {


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

        EasyMock.expect(facadeParis.parier("julien",idMatch,res,montant)).andReturn(1l);
        EasyMock.expect(utilisateur.getLogin()).andReturn("julien");
        EasyMock.expect(pari.getMatch()).andReturn(match);
        EasyMock.expect(match.getEquipe1()).andReturn("OM");
        EasyMock.expect(match.getEquipe2()).andReturn("PSG");

        EasyMock.expect(facadeParis.getPari(1l)).andReturn(pari);
        EasyMock.expect(facadeParis.getMatch(idMatch)).andReturn(match);

        EasyMock.replay(facadeParis, utilisateur, pari,match);

        sessions.put("user",utilisateur);
        applications.put("facade",facadeParis);

        String resultat = actionProxy.execute();

        Parier actionExecutee = (Parier) actionProxy.getAction();

        Assert.assertEquals(resultat,"success");
        Assert.assertTrue(actionExecutee.getPari()==pari);

    }


    @Test
    public void testMontantInfAZero() throws Exception {
        Double montant = 0d;
        request.addParameter("montant", String.valueOf(montant));
        long idmatch = 2;
        request.addParameter("idMatch", String.valueOf(idmatch));
        String res="nul";
        request.addParameter("resultat",res);

        Map<String,Object> sessions = new HashMap<>();
        Map<String,Object> applications = new HashMap<>();

        ActionProxy actionProxy = getActionProxy("/parier");
        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
        actionProxy.getInvocation().getInvocationContext().setSession(sessions);
        actionProxy.getInvocation().getInvocationContext().setLocale(new Locale("en","EN"));

        Match match = EasyMock.createMock(Match.class);
        Pari pari = EasyMock.createMock(Pari.class);
        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
        Utilisateur utilisateur = EasyMock.createMock(Utilisateur.class);

        EasyMock.expect(facadeParis.getMatch(idmatch)).andReturn(match);
        EasyMock.expect(match.getEquipe1()).andReturn("PSG");
        EasyMock.expect(match.getEquipe2()).andReturn("Strasbourg");
     //   EasyMock.expect(match.getIdMatch()).andReturn(2l);
        EasyMock.expect(facadeParis.parier("julien",idmatch,res,montant)).andReturn(101l);
        EasyMock.expect(facadeParis.getPari(1l)).andReturn(pari);
        //EasyMock.expect(pari.getMatch()).andReturn(match);

        EasyMock.expect(utilisateur.getLogin()).andReturn("julien");
        EasyMock.replay(match,pari,facadeParis,utilisateur);

        sessions.put("user",utilisateur);
        applications.put("facade", facadeParis);

        String resultat = actionProxy.execute();

        Parier actionExecutee = (Parier) actionProxy.getAction();
        Assert.assertEquals(resultat, "input");
        Assert.assertTrue(actionExecutee.getPari()!=pari);

    }


    @Test
    public void testMatchNull() throws Exception {
        Double montant = 10d;
        request.addParameter("montant", String.valueOf(montant));
        long idmatch = 2;
        request.addParameter("idMatch", String.valueOf(idmatch));
        String res="nul";
        request.addParameter("resultat",res);

        Map<String,Object> applications = new HashMap<>();

        ActionProxy actionProxy = getActionProxy("/parier");
        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
        actionProxy.getInvocation().getInvocationContext().setLocale(new Locale("en","EN"));

        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
        Match match = EasyMock.createMock(Match.class);
        Pari pari = EasyMock.createMock(Pari.class);

        EasyMock.expect(facadeParis.getPari(1l)).andReturn(pari);
        EasyMock.expect(facadeParis.getMatch(idmatch)).andReturn(null);

        EasyMock.replay(match,pari,facadeParis);



        applications.put("facade",facadeParis);

        String resultat = actionProxy.execute();

        Parier actionExecutee = (Parier) actionProxy.getAction();
        Assert.assertEquals(resultat, "input");
        Assert.assertTrue(actionExecutee.getPari()!=pari);

    }

    @Test
    public void testCatch() throws Exception {
        Double montant = 10d;
        request.addParameter("montant", String.valueOf(montant));
        long idmatch = 2;
        request.addParameter("idMatch", String.valueOf(idmatch));
        String res="OM";
        request.addParameter("resultat",res);

        Map<String,Object> sessions = new HashMap<>();
        Map<String,Object> applications = new HashMap<>();

        ActionProxy actionProxy = getActionProxy("/parier");
        actionProxy.getInvocation().getInvocationContext().setSession(sessions);
        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
        actionProxy.getInvocation().getInvocationContext().setLocale(new Locale("en","EN"));

        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
        Match match = EasyMock.createMock(Match.class);
        Pari pari = EasyMock.createMock(Pari.class);
        Utilisateur utilisateur = EasyMock.createMock(Utilisateur.class);

        EasyMock.expect(facadeParis.getMatch(idmatch)).andReturn(match);
        EasyMock.expect(match.getEquipe1()).andReturn("PSG");
        EasyMock.expect(match.getEquipe2()).andReturn("Strasbourg");
        EasyMock.expect(utilisateur.getLogin()).andReturn("julien");
        EasyMock.expect(facadeParis.parier("julien",idmatch,res,montant)).andThrow(new ResultatImpossibleException(res));


        EasyMock.replay(facadeParis,match,pari,utilisateur);

        applications.put("facade",facadeParis);
        sessions.put("user",utilisateur);

        String resultat = actionProxy.execute();

        Parier actionExecutee = (Parier) actionProxy.getAction();
        Assert.assertEquals(resultat, "input");
        Assert.assertTrue(actionExecutee.getPari()!=pari);



    }


}
