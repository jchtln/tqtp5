package actions;

import com.opensymphony.xwork2.ActionProxy;
import facade.FacadeParis;
import facade.exceptions.MatchClosException;
import facade.exceptions.MontantNegatifOuNulException;
import facade.exceptions.ResultatImpossibleException;
import modele.Pari;
import org.apache.struts2.StrutsJUnit4TestCase;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestParier extends StrutsJUnit4TestCase {


    //test montant<0
    @Test
    public void montantSupAZero() throws MontantNegatifOuNulException, ResultatImpossibleException, MatchClosException {

        long montant = 10;
        long idmatch = 1;
        String resultat="nul";

        request.addParameter("montant", String.valueOf(montant));
        request.addParameter("idmatch", String.valueOf(idmatch));
        request.addParameter("resultat",resultat);


        Map<String,Object> sessions = new HashMap<>();
        Map<String,Object> applications = new HashMap<>();

        ActionProxy actionProxy = getActionProxy("/goParier");
        actionProxy.getInvocation().getInvocationContext().setApplication(applications);
        actionProxy.getInvocation().getInvocationContext().setSession(sessions);


        FacadeParis facadeParis = EasyMock.createMock(FacadeParis.class);
        Pari pari = EasyMock.createMock(Pari.class);

        EasyMock.expect(facadeParis.parier("julien",idmatch,resultat,montant)).andReturn(112l);
        EasyMock.expect(pari.getMatch()).andReturn(idmatch);



        Assert.assertEquals(resultat,"success");
        Assert.assertTrue(actionExecutee.getMatch()==match);
    }

    //test match = null
}
