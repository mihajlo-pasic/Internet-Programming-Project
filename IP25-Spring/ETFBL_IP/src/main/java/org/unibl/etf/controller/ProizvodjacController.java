package org.unibl.etf.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.model.Proizvodjac;
import org.unibl.etf.repository.ProizvodjacRepository;

@RestController
@RequestMapping("/api/proizvodjaci")
@CrossOrigin(origins = "http://localhost:4200")
public class ProizvodjacController {
	private ProizvodjacRepository proizvodjacRepository;

	public ProizvodjacController(ProizvodjacRepository proizvodjacRepository) {
		this.proizvodjacRepository = proizvodjacRepository;
	}
	
	@GetMapping
	public  ResponseEntity<?> getAll(){
		return ResponseEntity.ok(this.proizvodjacRepository.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Proizvodjac> getById(@PathVariable Integer id) {
        return proizvodjacRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping
    public Proizvodjac create(@RequestBody Proizvodjac proizvodjac) {
        return proizvodjacRepository.save(proizvodjac);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proizvodjac> update(@PathVariable Integer id, @RequestBody Proizvodjac proizvodjac) {
        return proizvodjacRepository.findById(id)
                .map(existing -> {
                    proizvodjac.setId(id);
                    return ResponseEntity.ok(proizvodjacRepository.save(proizvodjac));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        proizvodjacRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
	
}
