package org.unibl.etf.controller;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.Description;
import org.unibl.etf.model.Objava;
import org.unibl.etf.model.Promocija;
import org.unibl.etf.repository.ObjavaRepository;
import org.unibl.etf.repository.PromocijaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rss")
@CrossOrigin(origins = "http://localhost:4200") 
public class RssFeedController {

    private final PromocijaRepository promocijaRepository;
    private final ObjavaRepository objavaRepository;

    public RssFeedController(PromocijaRepository promocijaRepository, ObjavaRepository objavaRepository) {
        this.promocijaRepository = promocijaRepository;
        this.objavaRepository = objavaRepository;
    }

    @GetMapping(produces = "application/rss+xml")
    public Channel getRssFeed() {
        Channel channel = new Channel("rss_2.0");
        channel.setTitle("Promocije i Objave - RSS Feed");
        channel.setDescription("Najnovije informacije o promocijama i objavama.");
        channel.setLink("http://localhost:8080/api/rss");

        List<Item> items = new ArrayList<>();

        
        List<Promocija> promocije = promocijaRepository.findAll();
        for (Promocija promocija : promocije) {
            Item item = new Item();
            item.setTitle(promocija.getNaslov());
            item.setLink("http://localhost:8080/api/promocije/" + promocija.getId());

            Description description = new Description();
            description.setValue(promocija.getOpis());
            item.setDescription(description);

            item.setPubDate(java.sql.Date.valueOf(promocija.getDatumPocetka()));
            items.add(item);
        }

        
        List<Objava> objave = objavaRepository.findAll();
        for (Objava objava : objave) {
            Item item = new Item();
            item.setTitle(objava.getNaslov());
            item.setLink("http://localhost:8080/api/objave/" + objava.getId());

            Description description = new Description();
            description.setValue(objava.getSadrzaj());
            item.setDescription(description);

            item.setPubDate(java.sql.Timestamp.valueOf(objava.getDatumKreiranja()));
            items.add(item);
        }

        channel.setItems(items);
        return channel;
    }
}
