package org.unibl.etf.repository;

import org.unibl.etf.model.Korisnik;
import org.unibl.etf.model.UlogaKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);

	List<Korisnik> findByUloga(UlogaKorisnika uloga);
}
