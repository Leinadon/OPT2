package OPT2;

import java.util.Scanner;
import java.util.*;

abstract class Uitgave {
    private String naam;
    private double bedrag;

    public Uitgave(String naam, double bedrag) {
        this.naam = naam;
        this.bedrag = bedrag;
    }

    public String getNaam() {
        return naam;
    }

    public double getBedrag() {
        return bedrag;
    }

    public abstract String getCategorie();
}

class UitgaveCategorie extends Uitgave {
    private String categorie;
    private Date datum;

    public UitgaveCategorie(String naam, double bedrag, String categorie) {
        super(naam, bedrag);
        this.categorie = categorie;
        this.datum = datum;
    }

    @Override
    public String getCategorie() {
        return categorie;
    }

}

class UitgavenLijst {
    private List<Uitgave> uitgaven;

    public UitgavenLijst() {
        this.uitgaven = new ArrayList<>();
    }

    public void voegUitgaveToe(Uitgave uitgave) {
        uitgaven.add(uitgave);
    }

    public List<Uitgave> bekijkUitgaven() {
        return uitgaven;
    }

    public List<Uitgave> filterUitgavenOpCategorie(String categorie) {
        List<Uitgave> result = new ArrayList<>();
        for (Uitgave uitgave : uitgaven) {
            if (uitgave.getCategorie().equals(categorie)) {
                result.add(uitgave);
            }
        }
        return result;
    }

    public Set<String> bekijkCategorieen() {
        Set<String> categorieen = new HashSet<>();
        for (Uitgave uitgave : uitgaven) {
            categorieen.add(uitgave.getCategorie());
        }
        return categorieen;
    }
}

class Budget {
    private double beschikbaarBudget;

    public Budget(double beschikbaarBudget) {
        this.beschikbaarBudget = beschikbaarBudget;
    }

    public double getBeschikbaarBudget() {
        return beschikbaarBudget;
    }

    public void setBeschikbaarBudget(double beschikbaarBudget) {
        this.beschikbaarBudget = beschikbaarBudget;
    }

    public boolean isOverschreden(double uitgaven) {
        return uitgaven > beschikbaarBudget;
    }
}

class UitgavenAnalyser {
    public double berekenTotaleUitgaven(List<Uitgave> uitgaven) {
        double totaleUitgaven = 0.0;
        for (Uitgave uitgave : uitgaven) {
            totaleUitgaven += uitgave.getBedrag();
        }
        return totaleUitgaven;
    }

    // Andere analysemethoden kunnen hier worden toegevoegd, zoals het berekenen van gemiddelde, hoogste, laagste uitgaven, etc.
}

class Menu {

    public Menu(Scanner UserInput, List<Uitgave> alleUitgaven, List<Uitgave> middelenUitgaven, Budget budget,
                double totaleUitgaven, UitgavenLijst uitgavenLijst) {
        boolean Eind = true;
        while (Eind) {
            System.out.println("--------------------------------" +
                    "\nWelkom in het uitgave beheer systeem." +
                    "\nGraag ALLEEN de aangegeven waardes invullen." +
                    "\nWat kan ik voor u doen vandaag?" +
                    "\n1: Uitgave toevoegen" +
                    "\n2: Uitgave bekijken" +
                    "\n3: Budget instellen" +
                    "\n4: Uitgaven Rapport" +
                    "\n5: Programma stoppen" +
                    "\n--------------------------------");
            try {
                int keuze = Integer.parseInt(UserInput.nextLine());
                System.out.println(totaleUitgaven);
                if (keuze == 1) {
                    boolean Eind_loop = true;
                    while (Eind_loop) {
                        System.out.println("Welke categorie wilt u iets aan toevoegen?");
                        System.out.println("Beschikbare categorieën:");
                        Set<String> categorieen = uitgavenLijst.bekijkCategorieen();
                        for (String categorie : categorieen) {
                            System.out.println(categorie);
                        }
                        String gekozenCategorie = UserInput.nextLine();
                        if (categorieen.contains(gekozenCategorie)) {
                            System.out.println("Wat is het bedrag dat u wilt toevoegen aan categorie: " + gekozenCategorie);
                            double gekozenBedrag = Double.parseDouble(UserInput.nextLine());
                            System.out.println("Als wat wilt u het opslaaan?");
                            String opslaaan = UserInput.nextLine();
                            uitgavenLijst.voegUitgaveToe(new UitgaveCategorie(opslaaan, gekozenBedrag, gekozenCategorie));
                            Eind_loop = false;
                        } else {
                            System.out.println("Ongeldige categorie. Kies een categorie uit de lijst.");
                        }
                    }
                } else if (keuze == 2) {
                    System.out.println("Alle uitgaven:");
                    for (Uitgave uitgave : alleUitgaven) {
                        System.out.println(uitgave.getNaam() + ": €" + uitgave.getBedrag());
                    }
                } else if (keuze == 3) {
                    boolean Eind_loop = true;
                    while (Eind_loop) {
                        System.out.println("Wat is het bedrag dat u wilt instellen (gelieve alleen ronde getallen):");
                        int keuze_loop = Integer.parseInt(UserInput.nextLine());
                        budget.setBeschikbaarBudget(keuze_loop);
                        System.out.println("Uw budget is nu geüpdate naar: €" + budget.getBeschikbaarBudget());
                        Eind_loop = false;
                    }
                } else if (keuze == 4) {
                    System.out.println("Uw analyse wordt hier gedaan:");
                } else if (keuze == 5) {
                    System.out.println("\n\nFijne dag nog verder!");
                    Eind = false;
                } else {
                    System.out.println("Graag een geldig nummer opgeven");
                }
            } catch (NumberFormatException e) {
                System.out.println("Dat is geen geldige invoer. Voer alstublieft alleen in wat er gevraagd wordt. " +
                        "U wordt nu weer naar het begin gestuurd.\n");
            }
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UitgavenLijst uitgavenLijst = new UitgavenLijst();
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Boodschappen", 50.0, "Levensmiddelen"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Benzine", 40.0, "Vervoer"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Taxi", 10.0, "Vervoer"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("APK", 40.0, "Vervoer"));


        List<Uitgave> alleUitgaven = uitgavenLijst.bekijkUitgaven();
        System.out.println("Alle uitgaven:");
        for (Uitgave uitgave : alleUitgaven) {
            System.out.println(uitgave.getNaam() + ": €" + uitgave.getBedrag());
        }

        List<Uitgave> levensmiddelenUitgaven = uitgavenLijst.filterUitgavenOpCategorie("Levensmiddelen");
        System.out.println("\nLevensmiddelen uitgaven:");
        for (Uitgave uitgave : levensmiddelenUitgaven) {
            System.out.println(uitgave.getNaam() + ": €" + uitgave.getBedrag());
        }

        Budget budget = new Budget(200.0);
        double totaleUitgaven = new UitgavenAnalyser().berekenTotaleUitgaven(alleUitgaven);
        if (budget.isOverschreden(totaleUitgaven)) {
            System.out.println("\nWaarschuwing: Budget overschreden!");
        } else {
            System.out.println("\nTotale uitgaven: €" + totaleUitgaven);
            System.out.println("Beschikbaar budget: €" + budget.getBeschikbaarBudget());
        }
        new Menu(scanner, alleUitgaven, levensmiddelenUitgaven, budget, totaleUitgaven, uitgavenLijst);
    }
}




