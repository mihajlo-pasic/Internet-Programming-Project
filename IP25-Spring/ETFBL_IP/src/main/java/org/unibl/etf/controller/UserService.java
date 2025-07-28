package org.unibl.etf.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.model.BlokiraniKorisnici;
import org.unibl.etf.model.Korisnik;
import org.unibl.etf.repository.BlokiraniKorisniciRepository;
import org.unibl.etf.repository.KorisnikRepository;

@Service
public class UserService {
	@Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private BlokiraniKorisniciRepository blokiraniKorisniciRepository;

    public List<Korisnik> getAllUsers() {
        return korisnikRepository.findAll();
    }
    
    public List<Korisnik> getAllUsersWithBlockStatus() {
        List<Korisnik> users = korisnikRepository.findAll();
        List<Integer> blockedUserIds = getBlockedUserIds();

        users.forEach(user -> user.setBlokiran(blockedUserIds.contains(user.getId())));

        return users;
    }

    private List<Integer> getBlockedUserIds() {
        return blokiraniKorisniciRepository.findAll()
                .stream()
                .map(BlokiraniKorisnici::getKorisnikId)
                .collect(Collectors.toList());
    }

   

    public void toggleBlockUser(Integer id) {
        Optional<Korisnik> korisnikOpt = korisnikRepository.findById(id);
        if (korisnikOpt.isPresent()) {
            Korisnik korisnik = korisnikOpt.get();
            korisnik.setBlokiran(!korisnik.isBlokiran());
            korisnikRepository.save(korisnik);
        }
    }
}
