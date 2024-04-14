package OPT2;

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

    public UitgaveCategorie(String naam, double bedrag, String categorie) {
        super(naam, bedrag);
        this.categorie = categorie;
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
}

class Menu {
    public Menu(Scanner userInput, List<Uitgave> alleUitgaven, Budget budget,
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
                    "\n4: Uitgaven Analyse" +
                    "\n5: Programma stoppen" +
                    "\n--------------------------------");
            try {
                int keuze = Integer.parseInt(userInput.nextLine());
                if (keuze == 1) {
                    while (true) {
                        if (budget.isOverschreden(totaleUitgaven)) {
                            System.out.println("Uw budget is te weinig verhoog deze eerst anders kan u niks toevoegen!!!");
                            break;
                        } else {
                            System.out.println("Welke categorie wilt u iets aan toevoegen?");
                            System.out.println("Beschikbare categorieën:");
                            Set<String> categorieen = uitgavenLijst.bekijkCategorieen();
                            for (String categorie : categorieen) {
                                System.out.println(categorie);
                            }
                            String gekozenCategorie = userInput.nextLine();
                            if (categorieen.contains(gekozenCategorie)) {
                                System.out.println("Wat is het bedrag dat u wilt toevoegen aan categorie: " + gekozenCategorie);
                                double gekozenBedrag = Double.parseDouble(userInput.nextLine());
                                System.out.println("Als wat wilt u het opslaan?");
                                String opslaan = userInput.nextLine();
                                uitgavenLijst.voegUitgaveToe(new UitgaveCategorie(opslaan, gekozenBedrag, gekozenCategorie));
                                totaleUitgaven = new UitgavenAnalyser().berekenTotaleUitgaven(alleUitgaven);
                                break;
                            } else {
                                System.out.println("Ongeldige categorie. Kies een categorie uit de lijst.");
                            }
                        }
                    }
                } else if (keuze == 2) {
                    System.out.println("Alle uitgaven:");
                    for (Uitgave uitgave : alleUitgaven) {
                        System.out.println(uitgave.getNaam() + ": €" + uitgave.getBedrag());
                    }
                } else if (keuze == 3) {
                    while (true) {
                        System.out.println("Wat is het bedrag dat u wilt instellen (gelieve alleen ronde getallen):");
                        int keuze_loop = Integer.parseInt(userInput.nextLine());
                        budget.setBeschikbaarBudget(keuze_loop);
                        System.out.println("Uw budget is nu geüpdate naar: €" + budget.getBeschikbaarBudget());
                        break;
                    }
                } else if (keuze == 4) {
                    if (budget.isOverschreden(totaleUitgaven)) {
                        System.out.println("\nWaarschuwing: Budget overschreden!");
                    } else {
                        System.out.println("\nTotale uitgaven: €" + totaleUitgaven);
                        System.out.println("Beschikbaar budget: €" + budget.getBeschikbaarBudget());
                    }
                } else if (keuze == 5) {
                    System.out.println("\n\nFijne dag nog verder!");
                    break;
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


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UitgavenLijst uitgavenLijst = new UitgavenLijst();
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Boodschappen", 50.0, "Levensmiddelen"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Benzine", 40.0, "Vervoer"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Pretpark Kaartjes", 50.0, "Overig"));
        uitgavenLijst.voegUitgaveToe(new UitgaveCategorie("Water Kosten", 150.0, "Vaste Kosten"));

        List<Uitgave> alleUitgaven = uitgavenLijst.bekijkUitgaven();

        Budget budget = new Budget(500.0);
        double totaleUitgaven = new UitgavenAnalyser().berekenTotaleUitgaven(alleUitgaven);
        new Menu(scanner, alleUitgaven, budget, totaleUitgaven, uitgavenLijst);
    }
}






