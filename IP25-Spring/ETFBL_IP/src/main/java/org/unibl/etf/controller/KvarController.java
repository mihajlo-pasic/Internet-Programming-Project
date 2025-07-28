package org.unibl.etf.controller;

import org.unibl.etf.model.Kvar;
import org.unibl.etf.model.PrevoznoSredstvo;
import org.unibl.etf.repository.KvarRepository;
import org.unibl.etf.repository.PrevoznoSredstvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/kvarovi")
@CrossOrigin(origins = "http://localhost:4200") 
public class KvarController {

    private final KvarRepository kvarRepository;
    @Autowired
	private PrevoznoSredstvoRepository prevoznoSredstvoRepository;

    public KvarController(KvarRepository kvarRepository) {
        this.kvarRepository = kvarRepository;
    }

    @GetMapping
    public List<Kvar> getAll() {
        return kvarRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kvar> getById(@PathVariable Integer id) {
        return kvarRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prevozno-sredstvo/{prevoznoSredstvoId}")
    public List<Kvar> getByPrevoznoSredstvo(@PathVariable Integer prevoznoSredstvoId) {
        return kvarRepository.findByPrevoznoSredstvoId(prevoznoSredstvoId);
    }

    @GetMapping("/datum")
    public List<Kvar> getByDatum(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return kvarRepository.findByDatumVrijemeBetween(start, end);
    }

//    @PostMapping
//    public Kvar create(@RequestBody Kvar kvar) {
//        return kvarRepository.save(kvar);
//    }
    
    @PostMapping
    public Kvar create(@RequestBody Kvar kvar) {
        // Prvo sačuvaj kvar
        Kvar savedKvar = kvarRepository.save(kvar);

        // Promeni status vozila na POKVARENO
        PrevoznoSredstvo vozilo = savedKvar.getPrevoznoSredstvo();
        if (!"POKVARENO".equals(vozilo.getStatus())) {  // Provera da li je status već POKVARENO
            vozilo.setStatus("POKVARENO");
            prevoznoSredstvoRepository.save(vozilo);  // Sačuvaj izmene u bazi
        }

        return savedKvar;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Kvar> update(@PathVariable Integer id, @RequestBody Kvar kvar) {
        return kvarRepository.findById(id)
                .map(existing -> {
                    kvar.setId(id);
                    return ResponseEntity.ok(kvarRepository.save(kvar));
                })
                .orElse(ResponseEntity.notFound().build());
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Integer id) {
//        kvarRepository.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return kvarRepository.findById(id).map(kvar -> {
            PrevoznoSredstvo vozilo = kvar.getPrevoznoSredstvo();  // Dohvati vozilo vezano za kvar

            // Obriši kvar
            kvarRepository.deleteById(id);

            // Proveri da li vozilo ima još kvarova
            List<Kvar> preostaliKvarovi = kvarRepository.findByPrevoznoSredstvoId(vozilo.getId());
            if (preostaliKvarovi.isEmpty()) {
                vozilo.setStatus("SLOBODNO");  // Ako nema kvarova, postavi status na SLOBODNO
                prevoznoSredstvoRepository.save(vozilo);  // Sačuvaj izmene u bazi
            }

            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
