package com.ejemplo.rxjava;

class Materia {
    private String nombre;
    private boolean tieneCupo;

    public Materia(String nombre, boolean tieneCupo) {
        this.nombre = nombre;
        this.tieneCupo = tieneCupo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean tieneCupo() {
        return tieneCupo;
    }
}
