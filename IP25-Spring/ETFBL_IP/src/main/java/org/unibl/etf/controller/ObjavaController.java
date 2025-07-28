package org.unibl.etf.controller;

import org.unibl.etf.model.Objava;
import org.unibl.etf.repository.ObjavaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/objave")
@CrossOrigin(origins = "http://localhost:4200") 
public class ObjavaController {

    private final ObjavaRepository objavaRepository;

    public ObjavaController(ObjavaRepository objavaRepository) {
        this.objavaRepository = objavaRepository;
    }

    @GetMapping
    public List<Objava> getAll() {
        return objavaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objava> getById(@PathVariable Integer id) {
        return objavaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pretraga")
    public List<Objava> searchByNaslov(@RequestParam String keyword) {
        return objavaRepository.findByNaslovContainingIgnoreCase(keyword);
    }

    @GetMapping("/datum")
    public List<Objava> getByDatumKreiranja(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return objavaRepository.findByDatumKreiranjaBetween(start, end);
    }

    @PostMapping
    public Objava create(@RequestBody Objava objava) {
        return objavaRepository.save(objava);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objava> update(@PathVariable Integer id, @RequestBody Objava objava) {
        return objavaRepository.findById(id)
                .map(existing -> {
                    objava.setId(id);
                    return ResponseEntity.ok(objavaRepository.save(objava));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        objavaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
