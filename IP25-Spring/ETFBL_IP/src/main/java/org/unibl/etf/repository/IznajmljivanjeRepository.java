package org.unibl.etf.repository;

import org.unibl.etf.model.Iznajmljivanje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IznajmljivanjeRepository extends JpaRepository<Iznajmljivanje, Integer> {

    List<Iznajmljivanje> findByKorisnikId(Integer korisnikId);

    List<Iznajmljivanje> findByPrevoznoSredstvoId(Integer prevoznoSredstvoId);

    List<Iznajmljivanje> findByDatumPocetkaBetween(LocalDateTime start, LocalDateTime end);
}
