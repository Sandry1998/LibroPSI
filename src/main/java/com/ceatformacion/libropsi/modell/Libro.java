package com.ceatformacion.libropsi.modell;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Collection;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLibro;
    private String titulo;
    private String autor;
    private String genero ;
    private String editorial;
    private int paginas;



    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", editorial='" + editorial + '\'' +
                ", paginas=" + paginas +
                ", historial=" + historial +
                '}';
    }

    @OneToMany(mappedBy = "libro")
    private Collection<Historial> historial;

    public Collection<Historial> getHistorial() {
        return historial;
    }

    public void setHistorial(Collection<Historial> historial) {
        this.historial = historial;
    }
}
