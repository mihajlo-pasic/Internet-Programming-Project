package org.unibl.etf.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="iznajmljivanja")
public class Iznajmljivanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "prevozno_sredstvo_id", nullable = false)
    private PrevoznoSredstvo prevoznoSredstvo;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    @Column(nullable = false)
    private LocalDateTime datumPocetka;

    private LocalDateTime datumZavrsetka;

    @Column(nullable = false)
    private String lokacijaPreuzimanja;

    private String lokacijaVracanja;

    private Integer trajanje;

    private String identifikacioniDokument;

    private String vozackaDozvola;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PrevoznoSredstvo getPrevoznoSredstvo() {
		return prevoznoSredstvo;
	}

	public void setPrevoznoSredstvo(PrevoznoSredstvo prevoznoSredstvo) {
		this.prevoznoSredstvo = prevoznoSredstvo;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public LocalDateTime getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(LocalDateTime datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public LocalDateTime getDatumZavrsetka() {
		return datumZavrsetka;
	}

	public void setDatumZavrsetka(LocalDateTime datumZavrsetka) {
		this.datumZavrsetka = datumZavrsetka;
	}

	public String getLokacijaPreuzimanja() {
		return lokacijaPreuzimanja;
	}

	public void setLokacijaPreuzimanja(String lokacijaPreuzimanja) {
		this.lokacijaPreuzimanja = lokacijaPreuzimanja;
	}

	public String getLokacijaVracanja() {
		return lokacijaVracanja;
	}

	public void setLokacijaVracanja(String lokacijaVracanja) {
		this.lokacijaVracanja = lokacijaVracanja;
	}

	public Integer getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(Integer trajanje) {
		this.trajanje = trajanje;
	}

	public String getIdentifikacioniDokument() {
		return identifikacioniDokument;
	}

	public void setIdentifikacioniDokument(String identifikacioniDokument) {
		this.identifikacioniDokument = identifikacioniDokument;
	}

	public String getVozackaDozvola() {
		return vozackaDozvola;
	}

	public void setVozackaDozvola(String vozackaDozvola) {
		this.vozackaDozvola = vozackaDozvola;
	}
    
    
}
