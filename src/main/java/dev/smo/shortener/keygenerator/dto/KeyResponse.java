package dev.smo.shortener.keygenerator.dto;

public class KeyResponse {
    private long id;
    private String key;

    public KeyResponse(long id, String key) {
        this.id = id;
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
