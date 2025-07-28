package org.unibl.etf.repository;

import org.unibl.etf.model.Promocija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PromocijaRepository extends JpaRepository<Promocija, Integer> {

    List<Promocija> findByDatumPocetkaBeforeAndDatumZavrsetkaAfter(LocalDate currentDate, LocalDate currentDate2);

    List<Promocija> findByNaslovContainingIgnoreCase(String keyword);
}
