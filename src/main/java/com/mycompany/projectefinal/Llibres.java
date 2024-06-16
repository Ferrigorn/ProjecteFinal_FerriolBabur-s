/**
 *
 * @author Ferriol Baburés
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectefinal;

import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

/**
 *
 * @author fbabu
 */
@Entity
public class Llibres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLlibre;
    private String titol;
    private String autor;
    private String editorial;
    private int anyEdicio;
    private String genere;
    private String ubicacio;
    private String idioma;
    private String estat;
    private String coleccio;
    
    @Lob
    private byte[] imatge;
    
    @Transient
    private String imatgeBase64;

    // Constructor vacío
    public Llibres() {
    }

    // Constructor con todos los campos
    public Llibres(Long idLlibre, String titol, String autor, String editorial, int anyEdicio, String genere, String ubicacio, String idioma, String estat, String coleccio, byte[] imatge) {
        this.idLlibre = idLlibre;
        this.titol = titol;
        this.autor = autor;
        this.editorial = editorial;
        this.anyEdicio = anyEdicio;
        this.genere = genere;
        this.ubicacio = ubicacio;
        this.idioma = idioma;
        this.estat = estat;
        this.coleccio = coleccio;
        this.imatge = imatge;
    }

    public Long getIdLlibre() {
        return idLlibre;
    }

    public void setIdLlibre(Long idLlibre) {
        this.idLlibre = idLlibre;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnyEdicio() {
        return anyEdicio;
    }

    public void setAnyEdicio(int anyEdicio) {
        this.anyEdicio = anyEdicio;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getColeccio() {
        return coleccio;
    }

    public void setColeccio(String coleccio) {
        this.coleccio = coleccio;
    }

    public byte[] getImatge() {
        return imatge;
    }

    public void setImatge(byte[] imatge) {
        this.imatge = imatge;
    }
    
    public String getImatgeBase64() {
        return imatgeBase64;
    }

    public void setImatgeBase64(String imatgeBase64) {
        this.imatgeBase64 = imatgeBase64;
    }
}

