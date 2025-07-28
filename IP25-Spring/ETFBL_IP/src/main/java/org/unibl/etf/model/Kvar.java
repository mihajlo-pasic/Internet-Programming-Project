package org.unibl.etf.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="kvarovi")
public class Kvar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "prevozno_sredstvo_id", nullable = false)
    private PrevoznoSredstvo prevoznoSredstvo;

    @Column(nullable = false)
    private String opis;

    @Column(nullable = false)
    private LocalDateTime datumVrijeme;

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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public LocalDateTime getDatumVrijeme() {
		return datumVrijeme;
	}

	public void setDatumVrijeme(LocalDateTime datumVrijeme) {
		this.datumVrijeme = datumVrijeme;
	}
    
    
}
