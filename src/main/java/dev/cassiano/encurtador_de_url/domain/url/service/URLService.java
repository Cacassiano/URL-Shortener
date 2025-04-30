package dev.cassiano.encurtador_de_url.domain.url.service;

import java.util.UUID;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cassiano.encurtador_de_url.domain.error.exceptions.ForbitenException;
import dev.cassiano.encurtador_de_url.domain.error.exceptions.NotFoundException;
import dev.cassiano.encurtador_de_url.domain.url.dtos.URLResponseDTO;
import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.repository.URLRepository;

@Service
public class URLService {

    @Autowired
    private URLRepository repository;

    public URL redirectToUrl(String shortcode) throws NotFoundException {
        URL url = this.findByShortcode(shortcode);
        url.setAcesscount(url.getAcesscount()+1);
        repository.save(url);
        return url;
    }

    public URLResponseDTO returnOriginalUrl(String shortcode, String subject) throws NotFoundException {
        URL url = this.findByShortcode(shortcode);
        if (url.getOwners().contains(subject)) {
            return new URLResponseDTO(url);
        }
        throw new ForbitenException("Don't have authorization to do this");
    }
    
    private URL findByShortcode(String shortcode) throws NotFoundException {
        return repository.findByShortcode(shortcode).orElseThrow(() -> new NotFoundException("This shortcode do not exists"));
    }

    public URL createNewUrl(String url, String owner) {      
        String shortcode;
        do{    
            char[] uuid = UUID.randomUUID().toString().replace("-", "").toCharArray();
            shortcode = String.valueOf(uuid, 0, 15);  
            System.out.println(shortcode);
        } while (repository.findByShortcode(shortcode).isPresent());

        URL myUrl = new URL();
        
        myUrl.setAcesscount(0);
        myUrl.setCreate_data(""+OffsetDateTime.now());
        myUrl.setUpdate_date(myUrl.getCreate_data());
        myUrl.setUrl(url);
        myUrl.setShortcode(shortcode); 
        myUrl.getOwners().add(owner);

        repository.save(myUrl);
        return myUrl;
    }
    
    public void deleteShortenUrl(String shortcode, String subject) throws NotFoundException {
        URL url = repository.findByShortcode(shortcode).orElseThrow(() -> new NotFoundException("This shortcode do not exists"));
        if (url.getOwners().contains(subject)) {
            repository.delete(url);
        } else {
            throw new ForbitenException("Don't have authorization to do this");
        }
        
    }
}
