package dto;

public class Korisnik {
    private int id;
    private String uloga;
    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private String brojLicneKarte;
    private String email;
    private String brojTelefona;
    private String avatar;
    private boolean blokiran;

    // Konstruktor bez parametara (potreban za neke framework-e)
    public Korisnik() {}

    // Konstruktor sa svim parametrima
    public Korisnik(int id, String uloga, String korisnickoIme, String lozinka, String ime, String prezime, 
                    String brojLicneKarte, String email, String brojTelefona, String avatar, boolean blokiran) {
        this.id = id;
        this.uloga = uloga;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.brojLicneKarte = brojLicneKarte;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.avatar = avatar;
        this.blokiran = blokiran;
    }

    // Getteri i setteri za sve atribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojLicneKarte() {
        return brojLicneKarte;
    }

    public void setBrojLicneKarte(String brojLicneKarte) {
        this.brojLicneKarte = brojLicneKarte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isBlokiran() {
        return blokiran;
    }

    public void setBlokiran(boolean blokiran) {
        this.blokiran = blokiran;
    }

    @Override
    public String toString() {
        return "Korisnik [id=" + id + ", uloga=" + uloga + ", korisnickoIme=" + korisnickoIme + 
               ", ime=" + ime + ", prezime=" + prezime + ", email=" + email + "]";
    }
}
