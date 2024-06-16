/**
 *
 * @author Ferriol Baburés
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectefinal;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author fbabu
 */
@Entity
public class Usuaris implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger idUsuari;
    private String nom;
    private String cognoms;
    private String contrasenya;
    private String email;
    private String rol;
    
    public Usuaris(){
        this.rol = "Convidat";
    }
    
    public Usuaris(String nom, String cognoms, String contrasenya, String email) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.contrasenya = contrasenya;
        this.email = email;
        this.rol = "Convidat"; // Valor por defecto para el rol
    }

    public BigInteger getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(BigInteger idUsuari) {
        this.idUsuari = idUsuari;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
