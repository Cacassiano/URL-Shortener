package dev.cassiano.encurtador_de_url.domain.url.dtos;

import dev.cassiano.encurtador_de_url.domain.url.entity.URL;

public record URLStatsResponseDTO(
    String id,
    String update_date,
    String create_data,
    String expires_at,
    String url,
    String shortcode,
    int acesscount,
    String[] owners)  {

    public URLStatsResponseDTO(URL url) {
        this(url.getId(), 
             url.getUpdate_date().toString(), 
             url.getCreate_date().toString(), 
             url.getExpiresAt().toString(),
             url.getUrl(), 
             url.getShortcode(), 
             url.getAcesscount(), 
             url.getOwners().toArray(new String[url.getOwners().size()])
            );
    }
}
