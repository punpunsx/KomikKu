package com.komikatow.komiku.model;

public final class ModelGenre <T extends String> {

    private T genre;
    private T endpointgenre;

    public T getGenre() {
        return genre;
    }

    public void setGenre(T genre) {
        this.genre = genre;
    }

    public T getEndpointgenre() {
        return endpointgenre;
    }

    public void setEndpointgenre(T endpointgenre) {
        this.endpointgenre = endpointgenre;
    }
}

