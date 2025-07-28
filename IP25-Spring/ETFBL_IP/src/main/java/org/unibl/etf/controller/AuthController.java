package org.unibl.etf.controller;

import org.unibl.etf.model.Korisnik;
import org.unibl.etf.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Dozvoljavamo Angular frontend da pristupi API-ju
public class AuthController {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Korisnik> korisnik = korisnikRepository.findByKorisnickoIme(loginRequest.getUsername());

        if (korisnik.isPresent() && korisnik.get().getLozinka().equals(loginRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", korisnik.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Neispravni kredencijali");
        }
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Korisnik loginRequest) {
//        Optional<Korisnik> korisnikOpt = korisnikRepository.findByKorisnickoIme(loginRequest.getKorisnickoIme());
//
//        if (korisnikOpt.isPresent()) {
//            Korisnik korisnik = korisnikOpt.get();
//            if (korisnik.getLozinka().equals(loginRequest.getLozinka())) { 
//                return ResponseEntity.ok(korisnik);
//            }
//        }
//        return ResponseEntity.status(401).body("Pogrešno korisničko ime ili lozinka");
//    }
}

class LoginRequest {
    private String username;
    private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
