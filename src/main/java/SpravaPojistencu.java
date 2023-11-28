import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Scanner;


public class SpravaPojistencu {

    private ArrayList<Pojistenec> pojistenci = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    /**
     * Formátuje/validuje uživatelské vstupy pro potřebu inicializace konstruktoru třídy Pojištěnec a přidání nové instance do kolekce.
     */
    public void evidujPojistence() {
        String jmeno = "";
        String prijmeni = "";
        LocalDate datumNarozeni = null;
        String telefonniCislo = "";
        String vstupUzivatele;

        // Zadejte jmeno
        do {
            System.out.println("Zadejte jméno: ");
            vstupUzivatele = sc.nextLine().trim();
        } while (!validujZnakyADelku(vstupUzivatele));
        jmeno = formatujJmenoPrijmeni(vstupUzivatele);

        // Zadejte prijmeni
        do {
            System.out.println("Zadejte příjmení: ");
            vstupUzivatele = sc.nextLine().trim();
        } while (!validujZnakyADelku(vstupUzivatele));
        prijmeni = formatujJmenoPrijmeni(vstupUzivatele);

        // Zadejte datum narození
        do {
            System.out.println("Zadejte datum narození: ");
            vstupUzivatele = sc.nextLine().trim();
            datumNarozeni = parsujDatum(vstupUzivatele);
        } while (datumNarozeni == null);

        // Zadejte telefonní číslo
        do {
            System.out.println("Zadejte telefonní číslo: ");
            vstupUzivatele = sc.nextLine();
            if (validujTelefon(vstupUzivatele))
                telefonniCislo = vstupUzivatele;
        } while (telefonniCislo.isEmpty());

        pojistenci.add(new Pojistenec(jmeno, prijmeni, datumNarozeni, telefonniCislo));
    }

    /**
     * Vypíše všechny evidované užvatele ve formátu ID, Jméno, příjmení, Věk, Telefon
     */
    public void vypisVsechnyPojistence() {
        vypisPojistence(pojistenci);
    }

    /**
     * Vypíše pojištěnce dle zadaného seznamu/výběru ArrayList<Pojistenec>
     *
     * @param seznamPojistencu
     */
    public void vypisPojistence(ArrayList<Pojistenec> seznamPojistencu) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("ID   Jméno        Příjmení     Věk   Telefon        ");
        System.out.println("-----------------------------------------------------------");
        for (Pojistenec pojistenec : seznamPojistencu) {
            System.out.println(pojistenec);
            System.out.println();
        }
    }

    /**
     * Na základě vstupu od uživatele vyhledá shody v evidenci pojištěnců.
     * Lze zadat ID, jméno, příjmení nebo kombinaci všech.
     * Metoda UPŘEDNOSTŇUJE ID (unikátní pro každého uživatele, připřazeno při evidenci).
     */
    public void vyhledejUzivatele() {
        String vstup = "";
        int id = 0;
        ArrayList<Pojistenec> vyhledaniPojistenci = new ArrayList();
        // Zadání vstupu uživatelem
        // Separuje jméno, příjmení a případně ID
        System.out.println("Zadejte jméno, příjmení nebo ID uživatele");
        vstup = sc.nextLine().trim();
        String[] vstupy = vstup.split(" ");
        // Kontroluje, zdali vstup obsahuje ID.
        for (String text : vstupy) {
            if (text.matches(".*\\d.*"))
                id = Integer.parseInt(text);
            break;
        }
        // ID 0 není přiděleno žádnému uživateli. Bylo-li ID zadáno, vyhledává užiatele právě dle ID.
        if (id > 0) {
            for (Pojistenec pojistenec : pojistenci) {
                if (pojistenec.getId() == id) {
                    vyhledaniPojistenci.add(pojistenec);
                    break;
                }
            }
        } else {
            for (Pojistenec pojistenec : pojistenci) {
                if (vstupy.length == 1) {
                    if (pojistenec.getJmeno().contains(vstupy[0]) || pojistenec.getPrijmeni().contains(vstupy[0])) {
                        vyhledaniPojistenci.add(pojistenec);
                    }
                } else if (pojistenec.getJmeno().contains(vstupy[0]) && pojistenec.getPrijmeni().contains(vstupy[1])) {
                    vyhledaniPojistenci.add(pojistenec);
                }
            }
        }
        if (vyhledaniPojistenci.size() == 0) {
            System.out.println("Nebyl nalezen žádný uživatel.");
        } else {
            vypisPojistence(vyhledaniPojistenci);
        }
    }

    /**
     * Odstraní uživatele na základě ID.
     * ID je přidělováno automaticky a uživatele je tak nutné alespoň jednou vypsat.
     */
    public void odstranPojistence() {
        System.out.println("Zadejte ID uživatele k odstranění:");
        int vstup = Integer.parseInt(sc.nextLine());
        for (Pojistenec pojistenec : pojistenci) {
            if (pojistenec.getId() == vstup) {
                pojistenci.remove(pojistenec);
                System.out.println("Uživatel odstraněn.");
                break;
            } else {
                System.out.println("Uživatel nenalezen.");
            }
        }
    }

    /**
     * Vrací true, pokud zadaný vstup obsahuje pouze povolené znaky (ČR!)
     *
     * @param vstupUzivatele
     * @return boolean
     */
    public boolean validujZnakyADelku(String vstupUzivatele) {
        String povoleneZnaky = "aeiouyáéěíóúůýbcčdďfghjklmnpqrřsštťvwxzžAEIOUYÁÉĚÍÓÚŮÝBCČDĎFGHJKLMNPQRŘSŠTŤVWXZŽ";
        for (char znak : vstupUzivatele.toCharArray()) {
            if (!povoleneZnaky.contains(String.valueOf(znak))) {
                System.out.println("Neplatný znak!");
                return false;
            }
        }
        if(vstupUzivatele.length()<3){
            System.out.println("Zadejte vstup o minimální délce 3 znaky.");
            return false;
        }
        return true;
    }

    /**
     * Vrací String s velkým počátečný písmenem.
     *
     * @param vstup
     * @return
     */
    public String formatujJmenoPrijmeni(String vstup) {
        vstup = vstup.toLowerCase();
        return vstup.substring(0, 1).toUpperCase() + vstup.substring(1);
    }

    /**
     * Parsuje String datum a vrací jej v lokálním formátu. V případě nesprávného vstupu vrací null;
     * Akceptované vstupy: [dd/MM/yyyy], [MM/dd/yyyy], [dd-MM-yyyy], [yyyy-MM-dd], [dd.MM.yyyy], [d.M.yyyy], [ddMMyyyy], [dMyyyy]
     * Zároveň kontroluje max věk (122, historicky nejstarší osoba) - isBefore()now()minusYears(122) a zadaání data v budoucnosti.
     *
     * @param vstup
     * @return LocalDate
     */
    public LocalDate parsujDatum(String vstup) {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("[dd/MM/yyyy]" + "[MM/dd/yyyy]" + "[dd-MM-yyyy]" + "[yyyy-MM-dd]" + "[dd.MM.yyyy]" + "[d.M.yyyy]" + "[ddMMyyyy]" + "[dMyyyy]"));
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();
        try {
            LocalDate parsovaneDatumNarozeni = LocalDate.parse(vstup,dateTimeFormatter);
            if (parsovaneDatumNarozeni.isBefore(LocalDate.now().minusYears(122)) || parsovaneDatumNarozeni.isAfter(LocalDate.now())){
                System.out.println("Nesprávné datum narození");
                return null;
            }else return parsovaneDatumNarozeni;
        } catch (DateTimeException dte) {
            System.out.println("Nesprávný formát data");
            return null;
        }
    }

    /**
     * Metoda ověří, že byla použita pouze čísla, případně mezery a znaménko "+" pro předvolbu a prověří délku.
     * Akceptuje min. 9ti místné číslo bez předvolby, min. 11ti místné s předvolbou.
     *
     * @param vstup
     * @return
     */
    public boolean validujTelefon(String vstup) {
        String povoleneZnakyTelefon = "0123456789 +";
        String vstupBezMezer = "";
        int minPocet = 9;
        if (vstup.contains("+")) minPocet = 11;
        for (char znak : vstup.toCharArray()) {
            if (!povoleneZnakyTelefon.contains(String.valueOf(znak))) {
                System.out.println("Neplatný znak!");
                return false;
            }
        }
        vstupBezMezer = vstup.replace(" ", "");
        if ((vstupBezMezer.length() < minPocet || vstupBezMezer.length() > 14)) {
            System.out.println("Neplatný formát!");
            return false;
        }
        return true;
    }
}
