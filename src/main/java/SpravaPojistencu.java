import java.time.LocalDate;
import java.util.ArrayList;

public class SpravaPojistencu {

    private ArrayList<Pojistenec> pojistenci = new ArrayList<>();

    /**
     * Předá argumenty konstruktoru třídy Pojistenec a zařadí záznam do kolekce.
     *
     * @param jmeno
     * @param prijmeni
     * @param datumNarozeni
     * @param telefonniCislo
     */
    public void pridejPojistence(String jmeno, String prijmeni, LocalDate datumNarozeni, String telefonniCislo) {
        pojistenci.add(new Pojistenec(jmeno, prijmeni, datumNarozeni, telefonniCislo));
    }

    /**
     * Getter, pro generování výpisu všech evidovaných pojištěnců
     *
     * @return ArrayList<Pojistenec> pojistenci
     */
    public ArrayList<Pojistenec> getPojistenci() {
        return pojistenci;
    }

    /**
     * Vyhledá uživatele na základě zadaného ID, jména a/nebo příjmení.
     * při vyhledávání upřednostňuje ID
     *
     * @param hledanyVyraz zadaný uživatelem
     * @return ArrayList<Pojistenec> vyhledaniPojistenci
     */
    public ArrayList<Pojistenec> vyhledejUzivatele(String hledanyVyraz) {
        int id = 0;
        ArrayList<Pojistenec> vyhledaniPojistenci = new ArrayList();
        // Separuje jméno, příjmení a případně ID
        String[] slova = hledanyVyraz.split(" ");
        // Kontroluje, zdali vstup obsahuje ID.
        for (String text : slova) {
            if (text.matches(".*\\d.*")) {
                try {
                    id = Integer.parseInt(text);
                } catch (NumberFormatException nfe) {
                    break;
                }
                break;
            }
        }
        // ID "0" není přiděleno žádnému uživateli. Bylo-li ID zadáno, vyhledává užiatele právě dle ID.
        if (id > 0) {
            vyhledaniPojistenci.add(vyhledejPojistenceDleId(id));
        } else {
            for (Pojistenec pojistenec : pojistenci) {

                if (slova.length == 1) {
                    // Jeli zadáno pouze jméno/příjmení přidá případné shody v obou sloupcích.
                    if (pojistenec.getJmeno().contains(slova[0]) || pojistenec.getPrijmeni().contains(slova[0])) {
                        vyhledaniPojistenci.add(pojistenec);
                    }

                } else if (pojistenec.getJmeno().contains(slova[0]) && pojistenec.getPrijmeni().contains(slova[1])) {
                    // Hledá shody jména i příjmení
                    vyhledaniPojistenci.add(pojistenec);
                }
            }
        }
        return vyhledaniPojistenci;
    }

    /**
     * Porovná zadané ID s ID evidovaných pojištěnců.
     *
     * @param id zadané uživatelem
     * @return Pojistenec, v případně absence shody null
     */
    public Pojistenec vyhledejPojistenceDleId(int id) {
        for (Pojistenec pojistenec : pojistenci) {
            if (pojistenec.getId() == id) {
                return pojistenec;
            }
        }
        return null;
    }

    /**
     * Odstraní uživatele na základě ID.
     * ID je přidělováno automaticky a uživatele je tak nutné alespoň jednou vypsat.
     */
    public boolean odstranPojistence(int id) {
        Pojistenec pojistenecOdtsran = vyhledejPojistenceDleId(id);
        if (pojistenecOdtsran == null) {
            return false;
        }
        pojistenci.remove(pojistenecOdtsran);
        return true;
    }
}
