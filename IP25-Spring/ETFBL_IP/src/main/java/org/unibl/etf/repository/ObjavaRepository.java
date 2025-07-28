package org.unibl.etf.repository;

import org.unibl.etf.model.Objava;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ObjavaRepository extends JpaRepository<Objava, Integer> {

    List<Objava> findByNaslovContainingIgnoreCase(String keyword);

    List<Objava> findByDatumKreiranjaBetween(LocalDateTime start, LocalDateTime end);
}
