package dev.cassiano.encurtador_de_url.domain.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import dev.cassiano.encurtador_de_url.domain.url.dtos.*;
import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.service.URLService;


@RestController
public class URLController {
    
    @Autowired
    private URLService service;

    @GetMapping("/{shortcode}")
    public RedirectView redirectByShortcode(@PathVariable String shortcode) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(service.findUrlByShortcode(shortcode));
        redirectView.setStatusCode(HttpStatusCode.valueOf(302));
        
        return redirectView;
    }

    @PostMapping(value = "/shorten")
    public ResponseEntity<URLResponseDTO> shortenURL(@RequestBody URLRequestDTO url){
        if (url.url() == null || url.url().length() <= 1) {
            throw new NullPointerException("Required information 'url' is missing");
        }
        URL newUrl = service.createNewUrl(url.url());
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(new URLResponseDTO(newUrl));
    }
}
