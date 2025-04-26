package dev.cassiano.encurtador_de_url.domain.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.encurtador_de_url.domain.url.entity.URL;

@Repository
public interface URLRepository extends JpaRepository<URL, String> {
    Optional<URL> findByShortcode(String shortcode);
}
