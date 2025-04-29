package dev.cassiano.encurtador_de_url.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "User")
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Column(nullable = false, unique = true)
    String id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password; 

    @Column(nullable = false)
    String username;

}
