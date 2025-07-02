package com.ceatformacion.libropsi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_historial;
    private LocalDate fechaRecogida;
    private LocalDate fechaEntrega;
    private String estado;
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private Libro libro;

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public LocalDate getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(LocalDate fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Historial-> " +
                "\nId_historial: " + id_historial +
                "\nFechaRecogida: " + fechaRecogida +
                "\nFechaEntrega: " + fechaEntrega +
                "\nEstado: " + estado +
                "\nObservaciones: " + observaciones +
                "\nLibro: " + libro;
    }
}
