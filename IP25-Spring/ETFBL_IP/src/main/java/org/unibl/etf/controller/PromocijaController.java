package org.unibl.etf.controller;

import org.unibl.etf.model.Promocija;
import org.unibl.etf.repository.PromocijaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/promocije")
@CrossOrigin(origins = "http://localhost:4200") 
public class PromocijaController {

    private final PromocijaRepository promocijaRepository;

    public PromocijaController(PromocijaRepository promocijaRepository) {
        this.promocijaRepository = promocijaRepository;
    }

    @GetMapping
    public List<Promocija> getAll() {
        return promocijaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocija> getById(@PathVariable Integer id) {
        return promocijaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/trenutne")
    public List<Promocija> getCurrent() {
        LocalDate now = LocalDate.now();
        return promocijaRepository.findByDatumPocetkaBeforeAndDatumZavrsetkaAfter(now, now);
    }

    @GetMapping("/pretraga")
    public List<Promocija> searchByNaslov(@RequestParam String keyword) {
        return promocijaRepository.findByNaslovContainingIgnoreCase(keyword);
    }

    @PostMapping
    public Promocija create(@RequestBody Promocija promocija) {
        return promocijaRepository.save(promocija);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocija> update(@PathVariable Integer id, @RequestBody Promocija promocija) {
        return promocijaRepository.findById(id)
                .map(existing -> {
                    promocija.setId(id);
                    return ResponseEntity.ok(promocijaRepository.save(promocija));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        promocijaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
