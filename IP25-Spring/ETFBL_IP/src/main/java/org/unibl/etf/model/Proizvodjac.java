package org.unibl.etf.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="proizvodjaci")
public class Proizvodjac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private String drzava;

    private String adresa;
    private String kontaktTelefon;
    private String kontaktFax;
    private String kontaktEmail;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getDrzava() {
		return drzava;
	}
	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getKontaktTelefon() {
		return kontaktTelefon;
	}
	public void setKontaktTelefon(String kontaktTelefon) {
		this.kontaktTelefon = kontaktTelefon;
	}
	public String getKontaktFax() {
		return kontaktFax;
	}
	public void setKontaktFax(String kontaktFax) {
		this.kontaktFax = kontaktFax;
	}
	public String getKontaktEmail() {
		return kontaktEmail;
	}
	public void setKontaktEmail(String kontaktEmail) {
		this.kontaktEmail = kontaktEmail;
	}
    
    
}
