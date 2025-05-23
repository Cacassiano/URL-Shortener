package dev.cassiano.encurtador_de_url.domain.url.dtos;

import dev.cassiano.encurtador_de_url.domain.url.entity.URL;

public record URLResponseDTO(
    String id,
    String update_date,
    String create_data,
    String url,
    String shortcode) {
    
    public URLResponseDTO(URL url) {
        this(url.getId(), 
             url.getUpdate_date().toString(), 
             url.getCreate_date().toString(), 
             url.getUrl(), 
             url.getShortcode()
            );
    }
}
