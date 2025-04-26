package dev.cassiano.encurtador_de_url.domain.error;

public class NullRequestParam extends NullPointerException {
    public NullRequestParam (String message) {
        super(message);
    }
}
