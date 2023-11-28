import java.util.Scanner;

public class ProgramEvidencePojistencu {
    // Program method (běh cel= aplikace)
    Scanner scanner;

    public ProgramEvidencePojistencu(){}

    public void mainProgram() {

        SpravaPojistencu spravaPojistencu = new SpravaPojistencu();
        boolean pokaracovatVBehu = true;
        int volba;

        // Hlavní větev programu
        while (pokaracovatVBehu) {
            printMenu();
            try {
                volba = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Neplatná volba. Prosím zadejte číslici v rozmezí 1 - 6");
                continue;
            }
            switch (volba) {
                case 1 -> spravaPojistencu.evidujPojistence();
                case 2 -> spravaPojistencu.vypisVsechnyPojistence();
                case 3 -> spravaPojistencu.vyhledejUzivatele();
                case 4 -> spravaPojistencu.odstranPojistence();
                case 5 -> {
                    System.out.println("Ukončuji aplikaci");
                    pokaracovatVBehu = false;
                }
                default -> System.out.println("Neplatná volba. Akceptovatelné jsou volby 1 - 6");
            }
        }
    }

    /**
     * Vypíše hlavní uživatelské menu
     */
    public void printMenu() {
        System.out.println("------------------------------");
        System.out.println("Evidence pojistenych");
        System.out.println("------------------------------");
        System.out.print("""
                Volba požadavku:\s
                1 - Evidování nového pojištěnce
                2 - Výpis všech pojištěnců
                3 - Vyhledat pojištěnce
                4 - Odstranit pojištěnce
                5 - Ukončit aplikaci
                """);
    }
}
