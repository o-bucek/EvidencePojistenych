import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

public class ProgramEvidencePojistencu {

    UzivatelskeRozhrani ui = new UzivatelskeRozhrani();
    SpravaPojistencu spravaPojistencu = new SpravaPojistencu();

    public void mainProgram() {
        boolean pokaracovatVBehu = true;
        int volba;

        // Hlavní větev programu
        while (pokaracovatVBehu) {
            ui.printMenu();
            try {
                volba = Integer.parseInt(ui.zpracujVstupUzivatele());
            } catch (NumberFormatException nfe) {
                ui.informujUzivatele("Neplatná volba. Prosím zadejte číslici v rozmezí 1 - 5");
                continue;
            }
            switch (volba) {
                case 1 -> evidujPojistence();
                case 2 -> vypisVsechnyPojistence();
                case 3 -> vyhledejUzivatele();
                case 4 -> odstranPojistence();
                case 5 -> {
                    ui.informujUzivatele("Konec programu");
                    pokaracovatVBehu = false;
                }
                default -> ui.informujUzivatele("Neplatná volba. Prosím zadejte číslici v rozmezí 1 - 5");
            }
        }
    }

    /**
     * Formátuje/validuje uživatelské vstupy pro potřebu inicializace konstruktoru třídy Pojištěnec a přidání nové instance do kolekce.
     */
    public void evidujPojistence() {
        String jmeno;
        String prijmeni;
        LocalDate datumNarozeni;
        String telefonniCislo;

        // Zadání/validace jména
        do {
            jmeno = ui.zpracujVstupUzivatele("Zadejte jméno: ");
        } while (!validujZnakyADelku(jmeno));
        jmeno = formatujPocatecniPismeno(jmeno);

        // Zadání/validace příjmení
        do {
            prijmeni = ui.zpracujVstupUzivatele("Zadejte příjmení: ");
        } while (!validujZnakyADelku(prijmeni));
        prijmeni = formatujPocatecniPismeno(prijmeni);

        // Zadání/validace data narození
        do {
            datumNarozeni = parsujDatum(ui.zpracujVstupUzivatele("Zadejte datum narození: "));
        } while (datumNarozeni == null);

        // Zadejte telefonní číslo
        do {
            telefonniCislo = ui.zpracujVstupUzivatele("Zadejte telefonní číslo: ");
            if (!validujTelefon(telefonniCislo))
                telefonniCislo = "";
        } while (telefonniCislo.isEmpty());
        spravaPojistencu.pridejPojistence(jmeno, prijmeni, datumNarozeni, telefonniCislo);
        ui.informujUzivatele("Pojištěnec zaevidován");
    }

    /**
     * Vypíše všechny evidované pojištěnce.
     */
    public void vypisVsechnyPojistence() {
        ui.vypisPojistence(spravaPojistencu.getPojistenci());
    }

    /**
     * Vyhledá uživatele na základě uživatelského vstupu
     */
    public void vyhledejUzivatele() {
        String hledanyVyraz = ui.zpracujVstupUzivatele("Zadejte jméno, příjmení nebo ID uživatele: ");
        ArrayList<Pojistenec> vyhledaniPojistenci = spravaPojistencu.vyhledejUzivatele(hledanyVyraz);
        if (vyhledaniPojistenci.isEmpty()) {
            ui.informujUzivatele("Nebyla nalezena žádná shoda");
        } else {
            ui.vypisPojistence(vyhledaniPojistenci);
        }
    }

    public void odstranPojistence() {
        int id = -1;
        do {
            try {
                id = Integer.parseInt(ui.zpracujVstupUzivatele("Zadejte ID uživatele, který má být vymazán: "));
            } catch (NumberFormatException nfe) {
                ui.informujUzivatele("Nesprávný formát ID");
            }
        } while (id == -1);
        if (spravaPojistencu.odstranPojistence(id)) {
            ui.informujUzivatele("Pojištěnec vymazán");
        } else {
            ui.informujUzivatele("ID nebylo nalezeno");
        }
    }

    /**
     * Vrací true, pokud zadaný vstup obsahuje pouze povolené znaky (ČR!)
     * "aeiouyáéěíóúůýbcčdďfghjklmnpqrřsštťvwxzžAEIOUYÁÉĚÍÓÚŮÝBCČDĎFGHJKLMNPQRŘSŠTŤVWXZŽ"
     *
     * @param text
     * @return boolean
     */
    public boolean validujZnakyADelku(String text) {
        String povoleneZnaky = "aeiouyáéěíóúůýbcčdďfghjklmnpqrřsštťvwxzžAEIOUYÁÉĚÍÓÚŮÝBCČDĎFGHJKLMNPQRŘSŠTŤVWXZŽ";
        for (char znak : text.toCharArray()) {
            if (!povoleneZnaky.contains(String.valueOf(znak))) {
                ui.informujUzivatele("Neplatný znak");
                return false;
            }
        }
        if (text.length() < 3) {
            ui.informujUzivatele("Zadejte minimálně 3 znaky");
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
    public String formatujPocatecniPismeno(String vstup) {
        vstup = vstup.toLowerCase();
        return vstup.substring(0, 1).toUpperCase() + vstup.substring(1);
    }

    /**
     * Parsuje String datum a vrací jej v lokálním formátu. V případě nesprávného vstupu vrací null;
     * Akceptované vstupy: [dd/MM/yyyy], [MM/dd/yyyy], [dd-MM-yyyy], [yyyy-MM-dd], [dd.MM.yyyy], [d.M.yyyy], [ddMMyyyy], [dMyyyy]
     * Zároveň kontroluje max věk (122, historicky nejstarší osoba) - isBefore()now()minusYears(122) a zadaání data v budoucnosti.
     *
     * @param vstup Zadaný uživatelem
     * @return LocalDate v lokálním formátu
     */
    public LocalDate parsujDatum(String vstup) {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("[dd/MM/yyyy]" + "[MM/dd/yyyy]" + "[dd-MM-yyyy]" + "[yyyy-MM-dd]" + "[dd.MM.yyyy]" + "[d.M.yyyy]" + "[ddMMyyyy]" + "[dMyyyy]"));
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();
        try {
            LocalDate parsovaneDatumNarozeni = LocalDate.parse(vstup, dateTimeFormatter);
            if (parsovaneDatumNarozeni.isBefore(LocalDate.now().minusYears(122)) || parsovaneDatumNarozeni.isAfter(LocalDate.now())) {
                ui.informujUzivatele("Nereálné datum");
                return null;
            } else return parsovaneDatumNarozeni;
        } catch (DateTimeException dte) {
            ui.informujUzivatele("Neplatný formát");
            return null;
        }
    }

    /**
     * Ověří, že byla použita pouze čísla, případně mezery a znaménko "+" pro předvolbu a prověří délku.
     * Akceptuje min. 9ti místné číslo bez předvolby, min. 11ti místné s předvolbou. Max délka 14 znaků
     *
     * @param textTelefon
     * @return
     */
    public boolean validujTelefon(String textTelefon) {
        String povoleneZnakyTelefon = "0123456789 +";
        String vstupBezMezer = "";
        int minPocet = 9;
        if (textTelefon.contains("+")) minPocet = 11;
        for (char znak : textTelefon.toCharArray()) {
            if (!povoleneZnakyTelefon.contains(String.valueOf(znak))) {
                ui.informujUzivatele("Neplatný znak");
                return false;
            }
        }
        vstupBezMezer = textTelefon.replace(" ", "");
        if ((vstupBezMezer.length() < minPocet || vstupBezMezer.length() > 14)) {
            ui.informujUzivatele("Neplatný formát");
            return false;
        }
        return true;
    }
}
