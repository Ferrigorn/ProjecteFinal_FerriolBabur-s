/**
 *
 * @author Ferriol Baburés
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectefinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author fbabu
 */


@Service
public class LlibresService {

    @Autowired
    private LlibresRepository llibresRepository;

    public Llibres saveLlibre(String titol, String autor, String editorial, int anyEdicio, String genere, String ubicacio, String idioma, String estat, String coleccio, MultipartFile imatge) throws IOException {
        Llibres llibre = new Llibres();
        llibre.setTitol(titol);
        llibre.setAutor(autor);
        llibre.setEditorial(editorial);
        llibre.setAnyEdicio(anyEdicio);
        llibre.setGenere(genere);
        llibre.setUbicacio(ubicacio);
        llibre.setIdioma(idioma);
        llibre.setEstat(estat);
        llibre.setColeccio(coleccio);
        llibre.setImatge(imatge.getBytes());

        return llibresRepository.save(llibre);
    }
    
    public Llibres updateImatgeLlibre(String titol, MultipartFile imatge) throws IOException {
        Optional<Llibres> optionalLlibre = llibresRepository.find1ByTitolIgnoreCase(titol);
        if (optionalLlibre.isPresent()) {
            Llibres llibre = optionalLlibre.get();
            llibre.setImatge(imatge.getBytes());
            return llibresRepository.save(llibre);
        } else {
            throw new RuntimeException("No s'ha trobat el llibre " + titol);
        }
    }
}

