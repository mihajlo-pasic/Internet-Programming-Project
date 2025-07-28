package org.unibl.etf.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.model.PrevoznoSredstvo;
import org.unibl.etf.model.TipPrevoznogSredstva;
import org.unibl.etf.repository.PrevoznoSredstvoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/prevozna-sredstva")
@CrossOrigin(origins = "http://localhost:4200") 
public class PrevoznoSredstvoController {

    private final PrevoznoSredstvoRepository prevoznoSredstvoRepository;

    public PrevoznoSredstvoController(PrevoznoSredstvoRepository prevoznoSredstvoRepository) {
        this.prevoznoSredstvoRepository = prevoznoSredstvoRepository;
    }

    @GetMapping
    public List<PrevoznoSredstvo> getAll() {
        return prevoznoSredstvoRepository.findAll();
    }

    @GetMapping("/tip/{tip}")
    public List<PrevoznoSredstvo> getByTip(@PathVariable TipPrevoznogSredstva tip) {
        return prevoznoSredstvoRepository.findByTip(tip);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrevoznoSredstvo> getById(@PathVariable Integer id) {
        return prevoznoSredstvoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PrevoznoSredstvo create(@RequestBody PrevoznoSredstvo prevoznoSredstvo) {
        return prevoznoSredstvoRepository.save(prevoznoSredstvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrevoznoSredstvo> update(@PathVariable Integer id, @RequestBody PrevoznoSredstvo prevoznoSredstvo) {
        return prevoznoSredstvoRepository.findById(id)
                .map(existing -> {
                    prevoznoSredstvo.setId(id);
                    return ResponseEntity.ok(prevoznoSredstvoRepository.save(prevoznoSredstvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        prevoznoSredstvoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<PrevoznoSredstvo> updateStatus(@PathVariable Integer id, @RequestBody String status) {
        return prevoznoSredstvoRepository.findById(id)
            .map(vozilo -> {
                vozilo.setStatus(status);
                return ResponseEntity.ok(prevoznoSredstvoRepository.save(vozilo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
