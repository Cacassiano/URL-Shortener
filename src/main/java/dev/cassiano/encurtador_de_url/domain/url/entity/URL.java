package dev.cassiano.encurtador_de_url.domain.url.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "urls")
@Entity(name = "url")
@Getter
@Setter
@NoArgsConstructor
public class URL {
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Column(nullable = false, unique = true)
    String id;

    @Column(nullable = false)
    OffsetDateTime update_date;
    
    @Column(nullable = false)
    OffsetDateTime create_date;
    
    @Column(nullable = false)
    OffsetDateTime expiresAt;

    @Column(nullable = false)
    String url;
    
    @Column(nullable = false, unique = true)
    String shortcode;
    
    @Column(nullable = false)
    int acesscount;

    @Column(nullable = false)
    ArrayList<String> owners;

    public ArrayList<String> getOwners() {
        if (owners == null) {
            this.owners = new ArrayList<>();
        }
        return owners;
    }
}
