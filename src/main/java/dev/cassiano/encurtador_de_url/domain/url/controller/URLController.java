package dev.cassiano.encurtador_de_url.domain.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import dev.cassiano.encurtador_de_url.domain.error.exceptions.NotFoundException;
import dev.cassiano.encurtador_de_url.domain.url.dtos.*;
import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.service.URLService;
import dev.cassiano.encurtador_de_url.infra.security.TokenService;


@RestController
public class URLController {
    
    @Autowired
    private URLService service;

    @Autowired
    private TokenService tkService;

    @GetMapping("/{shortcode}")
    public RedirectView redirectByShortcode(@PathVariable String shortcode) throws NotFoundException {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(service.redirectToUrl(shortcode).getUrl());
        redirectView.setStatusCode(HttpStatusCode.valueOf(302));
        
        return redirectView;
    }

    @PostMapping(value = "/api/v1/shorten")
    public ResponseEntity<URLResponseDTO> shortenURL(@RequestBody URLRequestDTO url, @RequestHeader("Authorization") String header){
        if (url.url() == null || url.url().length() <= 1) {
            throw new NullPointerException("Required information 'url' is missing");
        }
        URL newUrl = service.createNewUrl(url.url(), tkService.getSubject(header.replace("Bearer ", "")));
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(new URLResponseDTO(newUrl));
    }

    @DeleteMapping(value = "/api/v1/shorten/{shortcode}")
    public ResponseEntity<String> deleteShorten(@PathVariable String shortcode, @RequestHeader("Authorization") String header) throws NotFoundException {
        service.deleteShortenUrl(shortcode, tkService.getSubject(header.replace("Bearer ", "")));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/api/v1/shorten/{shortcode}")
    public ResponseEntity<URLResponseDTO> getOriginalUrl(@PathVariable String shortcode, @RequestHeader("Authorization") String header) throws NotFoundException {
        return ResponseEntity
                .ok(service.returnOriginalUrl(shortcode, tkService.getSubject(header.replace("Bearer ", ""))));
    }
}
