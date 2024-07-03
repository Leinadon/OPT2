package OPT2;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class Tests {

    private UitgavenLijst uitgavenLijst;
    private Budget budget;

    @Before
    public void setUp() {
        uitgavenLijst = new UitgavenLijst();
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Boodschappen", 50.0, "Levensmiddelen"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Benzine", 40.0, "Vervoer"));
        budget = new Budget(500.0);
    }

    @Test
    public void testUitgaveToevoegen() {
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Test Uitgave", 100.0, "Test Categorie"));
        List<Uitgave> uitgaven = uitgavenLijst.bekijkUitgaven();
        assertEquals(3, uitgaven.size());
        Uitgave testUitgave = uitgaven.get(2);
        assertEquals("Test Uitgave", testUitgave.getNaam());
        assertEquals(100.0, testUitgave.getBedrag(), 0.001);
        assertEquals("Test Categorie", testUitgave.getCategorie());
    }

    @Test
    public void testBekijkUitgaven() {
        List<Uitgave> uitgaven = uitgavenLijst.bekijkUitgaven();
        assertEquals(2, uitgaven.size());
        Uitgave firstUitgave = uitgaven.get(0);
        assertEquals("Boodschappen", firstUitgave.getNaam());
        assertEquals(50.0, firstUitgave.getBedrag(), 0.001);
        Uitgave secondUitgave = uitgaven.get(1);
        assertEquals("Benzine", secondUitgave.getNaam());
        assertEquals(40.0, secondUitgave.getBedrag(), 0.001);
    }

    @Test
    public void testBudgetInstellen() {
        assertEquals(500.0, budget.getBeschikbaarBudget(), 0.001);
        budget.setBeschikbaarBudget(1000.0);
        assertEquals(1000.0, budget.getBeschikbaarBudget(), 0.001);
    }

    @Test
    public void testBudgetOverschreden() {
        assertFalse(budget.isOverschreden(400.0));
        assertTrue(budget.isOverschreden(600.0));
    }

    @Test
    public void testBerekenTotaleUitgaven() {
        List<Uitgave> uitgaven = new ArrayList<>();
        uitgaven.add(new UitgaveCategorie("Boodschappen", 50.0, "Levensmiddelen"));
        uitgaven.add(new UitgaveCategorie("Benzine", 40.0, "Vervoer"));
        uitgaven.add(new UitgaveCategorie("Test Uitgave", 100.0, "Test Categorie"));

        UitgavenAnalyser analyser = new UitgavenAnalyser();
        double totaleUitgaven = analyser.berekenTotaleUitgaven(uitgaven);
        assertEquals(190.0, totaleUitgaven, 0.001);
    }

    @Test
    public void testBekijkCategorieen() {
        Set<String> categorieen = uitgavenLijst.bekijkCategorieen();
        assertEquals(2, categorieen.size());
        assertTrue(categorieen.contains("Levensmiddelen"));
        assertTrue(categorieen.contains("Vervoer"));
    }

    @Test
    public void testFilterUitgavenOpCategorie() {
        List<Uitgave> filteredUitgaven = uitgavenLijst.filterUitgavenOpCategorie("Levensmiddelen");
        assertEquals(1, filteredUitgaven.size());
        assertEquals("Boodschappen", filteredUitgaven.get(0).getNaam());
        assertEquals(50.0, filteredUitgaven.get(0).getBedrag(), 0.001);
    }
}
