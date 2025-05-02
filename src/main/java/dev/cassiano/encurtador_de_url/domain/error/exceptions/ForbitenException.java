package dev.cassiano.encurtador_de_url.domain.error.exceptions;

public class ForbitenException extends Exception{
    public ForbitenException(String message) {
        super(message);
    }
}
