package com.masanz.ldl.model;


public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private int anyo;
    private int paginas;

    public Libro(int id, String titulo, String autor, int anyo, int paginas) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anyo = anyo;
        this.paginas = paginas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
}