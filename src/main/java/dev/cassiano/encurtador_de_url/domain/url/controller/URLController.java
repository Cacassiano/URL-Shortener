package dev.cassiano.encurtador_de_url.domain.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.encurtador_de_url.domain.error.NullRequestParam;
import dev.cassiano.encurtador_de_url.domain.url.dtos.*;
import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.service.URLService;


@RestController
public class URLController {
    
    @Autowired
    private URLService service;
    /*
    @Autowired 
    private URLRepository repository;
    */
    /*
    @GetMapping("/{shortcode}")
    public RedirectView redirectByShortcode(@PathVariable String shortcode) {
        URL url = repository.findByShortcode(shortcode).orElseThrow(() -> new RuntimeException());
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url.getUrl());

        return redirectView;
    }
    */
    @PostMapping(value = "/shorten")
    public ResponseEntity<URLResponseDTO> shortenURL(@RequestBody URLResquestDTO request){
        if (request.url() == null) {
            throw new NullRequestParam("The required information 'url' is NULL");
        }
        URL newUrl = service.createNewUrl(request.url());
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(new URLResponseDTO(newUrl));
    }
}
