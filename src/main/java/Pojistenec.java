import java.time.LocalDate;
import java.time.Period;

public class Pojistenec {
    // Pro potřebu generování ID
    private static int evidenceId;

    // Attributy
    private String jmeno;
    private String prijmeni;
    private LocalDate datumNarozeni;
    private String telefonniCislo;
    private int id;


    //Konstruktor
    public Pojistenec(String jmeno, String prijmeni, LocalDate datumNarozeni, String telefonniCislo) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.datumNarozeni = datumNarozeni;
        this.telefonniCislo = telefonniCislo;

        // Přiřazení unikátního ID
        evidenceId++;
        id = evidenceId;
    }

    // Getters
    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public LocalDate getDatumNarozeni() {
        return datumNarozeni;
    }

    public String getTelefonniCislo() {
        return telefonniCislo;
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public void setTelefonniCislo(String telefonniCislo) {
        this.telefonniCislo = telefonniCislo;
    }

    /**
     * Vrací věk v letech vypočítaní jako Period.between .now() a uživatelem zadaným datem narození
     * @return
     */
    public int getVek(){
        int vek = Period.between(datumNarozeni, LocalDate.now()).getYears();
        return vek;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-12s %-12s %-5d %-15s", id, jmeno, prijmeni, getVek(), telefonniCislo);
    }
}
