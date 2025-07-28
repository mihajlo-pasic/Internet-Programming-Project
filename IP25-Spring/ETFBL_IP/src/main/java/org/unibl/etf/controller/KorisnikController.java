package org.unibl.etf.controller;

import org.unibl.etf.model.Korisnik;
import org.unibl.etf.model.UlogaKorisnika;
import org.unibl.etf.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/korisnici")
@CrossOrigin(origins = "http://localhost:4200")
public class KorisnikController {
	@Autowired
	private UserService userService;
    private final KorisnikRepository korisnikRepository;

    public KorisnikController(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @PostMapping("/{id}/toggle-block")
    public ResponseEntity<String> toggleBlockUser(@PathVariable Integer id) {
        userService.toggleBlockUser(id);
        return ResponseEntity.ok("Status bloka promenjen!");
    }
    
    
    @GetMapping
    public List<Korisnik> getAll() {
        return korisnikRepository.findAll();
    }
    
//    @GetMapping
//    public List<Korisnik> getAll() {
//        return userService.getAllUsersWithBlockStatus();
//    }


    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> getById(@PathVariable Integer id) {
        return korisnikRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/korisnicko-ime/{korisnickoIme}")
    public ResponseEntity<Korisnik> getByKorisnickoIme(@PathVariable String korisnickoIme) {
        return korisnikRepository.findByKorisnickoIme(korisnickoIme)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uloga/{uloga}")
    public ResponseEntity<?> getByUloga(@PathVariable UlogaKorisnika uloga) {
        return ResponseEntity.ok(korisnikRepository.findByUloga(uloga));
    }

    @PostMapping
    public Korisnik create(@RequestBody Korisnik korisnik) {
        return korisnikRepository.save(korisnik);
    }
    
    @PostMapping("/create")
    public ResponseEntity<Korisnik> createEmployee(@RequestBody Korisnik korisnik) {
        return ResponseEntity.ok(korisnikRepository.save(korisnik));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Korisnik> update(@PathVariable Integer id, @RequestBody Korisnik korisnik) {
        return korisnikRepository.findById(id)
                .map(existing -> {
                    korisnik.setId(id);
                    return ResponseEntity.ok(korisnikRepository.save(korisnik));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        korisnikRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
