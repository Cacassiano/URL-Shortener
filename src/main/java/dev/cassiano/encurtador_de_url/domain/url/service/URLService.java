package dev.cassiano.encurtador_de_url.domain.url.service;

import java.util.UUID;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.repository.URLRepository;

@Service
public class URLService {

    @Autowired
    private URLRepository repository;

    public URL createNewUrl(String url) {      
        String shortcode;
        do{    
            char[] uuid = UUID.randomUUID().toString().replace("-", "").toCharArray();
            shortcode = String.valueOf(uuid, 0, 10);  
            System.out.println(shortcode);
        } while (repository.findByShortcode(shortcode).isPresent());

        URL myUrl = new URL();
        myUrl.setAcesscount(0);
        myUrl.setCreate_data(""+OffsetDateTime.now());
        myUrl.setUpdate_date(myUrl.getCreate_data());
        myUrl.setUrl(url);
        myUrl.setShortcode(shortcode);

        repository.save(myUrl);
        return myUrl;
    }
    
}
