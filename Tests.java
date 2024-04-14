package OPT2;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class Tests {

    @Test
    public void testVoegUitgaveToe() {
        UitgavenLijst uitgavenLijst = new UitgavenLijst();
        UitgaveCategorie uitgave = new UitgaveCategorie("Test", 20.0, "TestCategorie");
        uitgavenLijst.voegUitgaveToe(uitgave);

        List<Uitgave> alleUitgaven = uitgavenLijst.bekijkUitgaven();
        assertEquals(1, alleUitgaven.size());
        assertEquals("Test", alleUitgaven.get(0).getNaam());
        assertEquals(20.0, alleUitgaven.get(0).getBedrag(), 0.01);
        assertEquals("TestCategorie", ((UitgaveCategorie)alleUitgaven.get(0)).getCategorie());
    }

    @Test
    public void testIsOverschreden() {
        Budget budget = new Budget(100.0);

        assertTrue(budget.isOverschreden(150.0));
        assertFalse(budget.isOverschreden(50.0));
        assertFalse(budget.isOverschreden(100.0));
    }
}
