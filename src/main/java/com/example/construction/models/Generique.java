package com.example.construction.models;

public abstract class Generique {
    private int status;

    public void softDelete() {
        this.status = 1;
    }
}
