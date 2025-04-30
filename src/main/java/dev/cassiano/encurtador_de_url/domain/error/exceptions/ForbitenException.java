package dev.cassiano.encurtador_de_url.domain.error.exceptions;

public class ForbitenException extends RuntimeException{
    public ForbitenException(String message) {
        super(message);
    }
}
