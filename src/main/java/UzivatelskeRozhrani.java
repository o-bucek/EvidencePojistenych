import java.util.ArrayList;
import java.util.Scanner;

public class UzivatelskeRozhrani {
    private Scanner sc = new Scanner(System.in);
    private String vstupUzivatele;

    /**
     * Vypíše hlavní uživatelské menu
     */
    public void printMenu() {
        System.out.println("------------------------------");
        System.out.println("Evidence pojištěných");
        System.out.println("------------------------------");
        System.out.print("""
                Volba požadavku:
                1 - Evidování nového pojištěnce
                2 - Výpis všech pojištěnců
                3 - Vyhledat pojištěnce
                4 - Odstranit pojištěnce
                5 - Ukončit aplikaci
                """);
    }

    public String zpracujVstupUzivatele(String sdeleniUzivateli) {
        System.out.println(sdeleniUzivateli);
        vstupUzivatele = sc.nextLine().trim();
        return vstupUzivatele;
    }

    public String zpracujVstupUzivatele() {
        vstupUzivatele = sc.nextLine().trim();
        return vstupUzivatele;
    }

    public void informujUzivatele(String sdeleniUzivateli) {
        System.out.println("___________________________________________________________");
        System.out.println("!!!  " + sdeleniUzivateli + "  !!!");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
    }
    /**
     * Vypíše pojištěnce dle zadaného seznamu/výběru ArrayList<Pojistenec>
     *
     * @param seznamPojistencu
     */
    public void vypisPojistence(ArrayList<Pojistenec> seznamPojistencu){
        System.out.println("___________________________________________________________");
        System.out.println("ID    Jméno        Příjmení     Věk   Telefon        ");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        for (Pojistenec pojistenec : seznamPojistencu) {
            System.out.println(pojistenec);
        }
    }


}
