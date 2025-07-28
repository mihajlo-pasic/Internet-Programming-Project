package org.unibl.etf.controller;

import org.unibl.etf.model.Iznajmljivanje;
import org.unibl.etf.repository.IznajmljivanjeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/iznajmljivanja")
@CrossOrigin(origins = "http://localhost:4200") 
public class IznajmljivanjeController {

    private final IznajmljivanjeRepository iznajmljivanjeRepository;

    public IznajmljivanjeController(IznajmljivanjeRepository iznajmljivanjeRepository) {
        this.iznajmljivanjeRepository = iznajmljivanjeRepository;
    }

    @GetMapping
    public List<Iznajmljivanje> getAll() {
        return iznajmljivanjeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iznajmljivanje> getById(@PathVariable Integer id) {
        return iznajmljivanjeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/korisnik/{korisnikId}")
    public List<Iznajmljivanje> getByKorisnik(@PathVariable Integer korisnikId) {
        return iznajmljivanjeRepository.findByKorisnikId(korisnikId);
    }

    @GetMapping("/prevozno-sredstvo/{prevoznoSredstvoId}")
    public List<Iznajmljivanje> getByPrevoznoSredstvo(@PathVariable Integer prevoznoSredstvoId) {
        return iznajmljivanjeRepository.findByPrevoznoSredstvoId(prevoznoSredstvoId);
    }

    @GetMapping("/datum")
    public List<Iznajmljivanje> getByDatumPocetka(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return iznajmljivanjeRepository.findByDatumPocetkaBetween(start, end);
    }

    @PostMapping
    public Iznajmljivanje create(@RequestBody Iznajmljivanje iznajmljivanje) {
        return iznajmljivanjeRepository.save(iznajmljivanje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Iznajmljivanje> update(@PathVariable Integer id, @RequestBody Iznajmljivanje iznajmljivanje) {
        return iznajmljivanjeRepository.findById(id)
                .map(existing -> {
                    iznajmljivanje.setId(id);
                    return ResponseEntity.ok(iznajmljivanjeRepository.save(iznajmljivanje));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        iznajmljivanjeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
