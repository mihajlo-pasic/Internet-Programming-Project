package org.unibl.etf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.model.BlokiraniKorisnici;

public interface BlokiraniKorisniciRepository extends JpaRepository<BlokiraniKorisnici, Integer> {

	Optional<BlokiraniKorisnici> findByKorisnikId(Integer id);

}
