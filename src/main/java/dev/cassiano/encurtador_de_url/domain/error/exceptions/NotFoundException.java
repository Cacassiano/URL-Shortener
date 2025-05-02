package dev.cassiano.encurtador_de_url.domain.error.exceptions;

public class NotFoundException extends NullPointerException {
    public NotFoundException (String message) {
        super(message);
    }
}
