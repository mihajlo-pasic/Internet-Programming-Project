package dto;

public class Iznajmljivanje {
    private String identifikator;
    private String datumPocetka;
    private int trajanje;
    private int cijena;
    private String platnaKartica;

    // Konstruktor bez parametara (za frameworks ako su potrebni)
    public Iznajmljivanje() {}

    // Konstruktor sa svim parametrima
    public Iznajmljivanje(String identifikator, String datumPocetka, int trajanje, int cijena, String platnaKartica) {
        this.identifikator = identifikator;
        this.datumPocetka = datumPocetka;
        this.trajanje = trajanje;
        this.cijena = cijena;
        this.platnaKartica = platnaKartica;
    }

    // Getteri i setteri
    public String getIdentifikator() {
        return identifikator;
    }

    public void setIdentifikator(String identifikator) {
        this.identifikator = identifikator;
    }

    public String getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(String datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public int getCijena() {
        return cijena;
    }

    public void setCijena(int cijena) {
        this.cijena = cijena;
    }

    public String getPlatnaKartica() {
        return platnaKartica;
    }

    public void setPlatnaKartica(String platnaKartica) {
        this.platnaKartica = platnaKartica;
    }

    @Override
    public String toString() {
        return "Iznajmljivanje [identifikator=" + identifikator + ", datumPocetka=" + datumPocetka +
               ", trajanje=" + trajanje + ", cijena=" + cijena + ", platnaKartica=" + platnaKartica + "]";
    }
    
    private String datumZavrsetka;  // Dodaj ovu liniju

 // Getter i setter za datumZavrsetka
 public String getDatumZavrsetka() {
     return datumZavrsetka;
 }

 public void setDatumZavrsetka(String datumZavrsetka) {
     this.datumZavrsetka = datumZavrsetka;
 }

}
