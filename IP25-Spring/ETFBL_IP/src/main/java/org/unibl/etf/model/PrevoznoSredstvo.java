package org.unibl.etf.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="prevozna_sredstva")
public class PrevoznoSredstvo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String identifikator;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipPrevoznogSredstva tip;

    @ManyToOne
    @JoinColumn(name = "proizvodjac_id")
    private Proizvodjac proizvodjac;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double cijenaNabavke;

    @Column
    private String opis;

    @Column
    private String status;

    @Column
    private String slika;

    @Column
    private Integer autonomija; // Samo za bicikle
    @Column
    private Integer maksimalnaBrzina; // Samo za trotinete

    @Column
    private String trenutnaLokacija = "0,0";
    
    public String getTrenutnaLokacija() {
		return trenutnaLokacija;
	}

	public void setTrenutnaLokacija(String trenutnaLokacija) {
		this.trenutnaLokacija = trenutnaLokacija;
	}

	@Column
    private java.sql.Date datumNabavke;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public TipPrevoznogSredstva getTip() {
		return tip;
	}

	public void setTip(TipPrevoznogSredstva tip) {
		this.tip = tip;
	}

	public Proizvodjac getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(Proizvodjac proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getCijenaNabavke() {
		return cijenaNabavke;
	}

	public void setCijenaNabavke(double cijenaNabavke) {
		this.cijenaNabavke = cijenaNabavke;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public Integer getAutonomija() {
		return autonomija;
	}

	public void setAutonomija(Integer autonomija) {
		this.autonomija = autonomija;
	}

	public Integer getMaksimalnaBrzina() {
		return maksimalnaBrzina;
	}

	public void setMaksimalnaBrzina(Integer maksimalnaBrzina) {
		this.maksimalnaBrzina = maksimalnaBrzina;
	}

	public java.sql.Date getDatumNabavke() {
		return datumNabavke;
	}

	public void setDatumNabavke(java.sql.Date datumNabavke) {
		this.datumNabavke = datumNabavke;
	}
    
    
}


