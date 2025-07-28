package org.unibl.etf.repository;

import org.unibl.etf.model.Kvar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface KvarRepository extends JpaRepository<Kvar, Integer> {

    List<Kvar> findByPrevoznoSredstvoId(Integer prevoznoSredstvoId);

    List<Kvar> findByDatumVrijemeBetween(LocalDateTime start, LocalDateTime end);
}
