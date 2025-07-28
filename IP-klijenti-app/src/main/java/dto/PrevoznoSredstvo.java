package dto;

public class PrevoznoSredstvo {
    private String identifikator;
    private String proizvodjac;
    private String model;
    private String opis;
    private String trenutnaLokacija;

    public PrevoznoSredstvo(String identifikator, String proizvodjac, String model, String opis, String trenutnaLokacija) {
        this.identifikator = identifikator;
        this.proizvodjac = proizvodjac;
        this.model = model;
        this.opis = opis;
        this.trenutnaLokacija = trenutnaLokacija;
    }

    // Getteri i setteri
    public String getIdentifikator() { return identifikator; }
    public void setIdentifikator(String identifikator) { this.identifikator = identifikator; }

    public String getProizvodjac() { return proizvodjac; }
    public void setProizvodjac(String proizvodjac) { this.proizvodjac = proizvodjac; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }

    public String getTrenutnaLokacija() { return trenutnaLokacija; }
    public void setTrenutnaLokacija(String trenutnaLokacija) { this.trenutnaLokacija = trenutnaLokacija; }
}
