/**
 *
 * @author Ferriol Baburés
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectefinal;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author fbabu
 */

@RestController
@RequestMapping("/llibres")
public class LlibresController {
    @Autowired
    private LlibresRepository llibresRepository;
    
    @Autowired
    private LlibresService llibresService;
    
    @GetMapping
    public List<Llibres> getAllLlibres(){
        return llibresRepository.findAll();
    }
    
    // Buscar llibres per autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Llibres>> getLlibresByAutor(@PathVariable String autor){
        List<Llibres> llibres = llibresRepository.findByAutorContaining(autor);
        llibres.forEach(llibre -> {
            if (llibre.getImatge() != null) {
                llibre.setImatgeBase64(Base64.getEncoder().encodeToString(llibre.getImatge()));
            }
        });

        if (llibres.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(llibres);
        }
    }

    // Buscar els llibres per Gènere
    @GetMapping("/genere/{genere}")
    public ResponseEntity<List<Llibres>> getLlibresByGenere(@PathVariable String genere){
        List<Llibres> llibres = llibresRepository.findByGenere(genere);
        llibres.forEach(llibre -> {
            if (llibre.getImatge() != null) {
                llibre.setImatgeBase64(Base64.getEncoder().encodeToString(llibre.getImatge()));
            }
        });

        if (llibres.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(llibres);
        }
    }
    
    //Buscar els generes disponibles
    @GetMapping("/generes")
    public ResponseEntity<List<String>> getGeneres() {
        List<String> generes = llibresRepository.findAllGeneres();
        return ResponseEntity.ok(generes);
}


    // Buscar llibre per titol
    @GetMapping("/llibre/{titol}")
    public ResponseEntity<List<Llibres>> getLlibreByTitol(@PathVariable String titol) {
        List<Llibres> llibres = llibresRepository.findByTitolIgnoreCase(titol);
        llibres.forEach(llibre -> {
            if (llibre.getImatge() != null) {
                llibre.setImatgeBase64(Base64.getEncoder().encodeToString(llibre.getImatge()));
            }
        });

        if (llibres.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(llibres);
        }
    }

    //Crear llibre
    
    @PostMapping("/crear")
    public ResponseEntity<Llibres> crearLlibre(@RequestParam("titol") String titol,
                                               @RequestParam("autor") String autor,
                                               @RequestParam("editorial") String editorial,
                                               @RequestParam("anyEdicio") int anyEdicio,
                                               @RequestParam("genere") String genere,
                                               @RequestParam("ubicacio") String ubicacio,
                                               @RequestParam("idioma") String idioma,
                                               @RequestParam("estat") String estat,
                                               @RequestParam("coleccio") String coleccio,
                                               @RequestParam("imatge") MultipartFile imatge) {
        try {
            Llibres llibre = llibresService.saveLlibre(titol, autor, editorial, anyEdicio, genere, ubicacio, idioma, estat, coleccio, imatge);
            return new ResponseEntity<>(llibre, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Pujar imatge
    
    @PutMapping("/actualizarimatge")
    public ResponseEntity<Llibres> actualitzarImatge(@RequestParam("titol") String titol,
                                                    @RequestParam("imatge") MultipartFile imatge) {
        try {
            Llibres llibre = llibresService.updateImatgeLlibre(titol, imatge);
            return new ResponseEntity<>(llibre, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    
    //Eliminar llibre
    
    @DeleteMapping("/eliminar/{idLlibre}")
    public ResponseEntity<Void> eliminarLlibre(@PathVariable Long idLlibre) {
        Optional<Llibres> llibre = llibresRepository.findById(idLlibre);
        if(llibre.isPresent()){
            llibresRepository.delete(llibre.get());
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    
    
}
