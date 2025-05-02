package dev.cassiano.encurtador_de_url.domain.url.service;

import java.util.ArrayList;
import java.util.UUID;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cassiano.encurtador_de_url.domain.error.exceptions.ForbitenException;
import dev.cassiano.encurtador_de_url.domain.error.exceptions.NotFoundException;
import dev.cassiano.encurtador_de_url.domain.url.dtos.URLResponseDTO;
import dev.cassiano.encurtador_de_url.domain.url.dtos.URLStatsResponseDTO;
import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.repository.URLRepository;

@Service
public class URLService {

    private final long EXPIRATION_TIME_HOURS = 12;

    @Autowired
    private URLRepository repository;

    private void validateOwners(URL url, String subject) throws ForbitenException {
        if (!url.getOwners().contains(subject)) {        
            throw new ForbitenException("Don't have authorization to do this");
        }
    }

    public URL redirectToUrl(String shortcode) throws NotFoundException {
        URL url = this.findByShortcode(shortcode);
        url.setAcesscount(url.getAcesscount()+1);
        if (url.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new NotFoundException("The link has expires at all");
        }
        repository.save(url);
        return url;
    }

    public URLResponseDTO returnOriginalUrl(String shortcode, String subject) throws NotFoundException, ForbitenException {
        URL url = this.findByShortcode(shortcode);
        this.validateOwners(url, subject);
        
        return new URLResponseDTO(url);
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
        myUrl.setCreate_date(OffsetDateTime.now());
        myUrl.setUpdate_date(myUrl.getCreate_date());
        myUrl.setExpiresAt(myUrl.getCreate_date().plusHours(EXPIRATION_TIME_HOURS));
        myUrl.setUrl(url);
        myUrl.setShortcode(shortcode); 
        myUrl.getOwners().add(owner);

        repository.save(myUrl);
        return myUrl;
    }
    
    public void deleteShortenUrl(String shortcode, String subject) throws NotFoundException, ForbitenException {
        URL url = repository.findByShortcode(shortcode).orElseThrow(() -> new NotFoundException("This shortcode do not exists"));
        this.validateOwners(url, subject);
        repository.delete(url);
    }

    public URLStatsResponseDTO getAllStats(String shortcode, String subject) throws NotFoundException, ForbitenException {
        URL url = repository.findByShortcode(shortcode).orElseThrow(() -> new NotFoundException("This shortcode is invalid"));
        this.validateOwners(url, subject);
        return new URLStatsResponseDTO(url);
    }

    public void updateInfo(String shortcode, String subject, String[] addOwner, String[] removeOwner, String newUrl) throws NotFoundException, ForbitenException {
        URL url = this.findByShortcode(shortcode);
        ArrayList<String> urlOwners = url.getOwners();
        this.validateOwners(url, subject);

        if (removeOwner == null && addOwner == null && newUrl == null) {
            throw new NullPointerException("The required information is null or invalid");
        }

        if (addOwner != null ) {
            for (String owner : addOwner) {
                if (!urlOwners.contains(owner)) {
                    urlOwners.add(owner);
                }
            }
        }

        if (removeOwner != null ) {
            for (String owner : removeOwner) {
                if (!owner.equals(urlOwners.get(0))) {
                    urlOwners.remove(owner);
                }
            }
        }

        if (newUrl != null) {
            url.setUrl(newUrl);
        }
        
        url.setOwners(urlOwners);
        url.setUpdate_date(OffsetDateTime.now());
        repository.save(url);
    }
}
