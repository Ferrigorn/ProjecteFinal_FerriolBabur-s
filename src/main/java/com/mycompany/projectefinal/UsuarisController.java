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
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author fbabu
 */
@RestController
@RequestMapping("/usuaris")
public class UsuarisController {
    @Autowired
    private UsuarisRepository usuarisRepository;
    
     @Autowired
    private UserDetailsService userDetailsService;
     
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
     
    private final Logger logger = LoggerFactory.getLogger(UsuarisController.class);
    
    @GetMapping
    public List<Usuaris> getAllUsuaris() {
        return usuarisRepository.findAll();
    }
    
    @GetMapping("/usuari/{nom}")
    public ResponseEntity<Usuaris> getUsuariByName(@PathVariable String nom){
        Optional<Usuaris> usuari = usuarisRepository.findByNom(nom);
        if (usuari.isPresent()){
            return ResponseEntity.ok(usuari.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
   @PostMapping("/registre")
    public ResponseEntity<String> registerUser(@RequestBody Usuaris usuari) {
        Optional<Usuaris> usuarioOpt = usuarisRepository.findByNom(usuari.getNom());
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.status(400).body("Usuario ya existe");
        }
        String contrasenya = usuari.getContrasenya();
        String contrasenyaEncriptada = customUserDetailsService.encriptarContrasenya(contrasenya);
        usuari.setContrasenya(contrasenyaEncriptada);
        usuarisRepository.save(usuari);
        return ResponseEntity.ok("Registro exitoso");
    }
    
   @PostMapping("/login")
@ResponseBody
public ResponseEntity<?> login(@RequestBody Usuaris usuari, HttpServletRequest request) {
    logger.info("Intento de login con usuario: {}", usuari.getEmail());
    Optional<Usuaris> usuarioOpt = usuarisRepository.findByEmail(usuari.getEmail());

    boolean isAuthenticated = customUserDetailsService.authenticate(usuari.getEmail(), usuari.getContrasenya());

    if (isAuthenticated) {
        Usuaris user = usuarioOpt.get();
        logger.info("Usuario encontrado: {}", user.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(userDetails, user.getContrasenya(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        Map<String, String> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("nom", user.getNom());

        return ResponseEntity.ok(response);
    } else {
        logger.error("Usuario o contraseña incorrectos para usuario: {}", usuari.getEmail());
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Usuario o contraseña incorrectos");
    }
}

    
    
    
    
    @PutMapping("/{idUsuari}")
    public ResponseEntity<Usuaris> updateUsuari(@PathVariable BigInteger idUsuari, @RequestBody Usuaris usuariDetails){
        Optional<Usuaris> usuari = usuarisRepository.findById(idUsuari);
        if (usuari.isPresent()){
            Usuaris usuariToUpdate = usuari.get();
            usuariToUpdate.setNom(usuariDetails.getNom());
            usuariToUpdate.setCognoms(usuariDetails.getCognoms());
            usuariToUpdate.setEmail(usuariDetails.getEmail());
            return ResponseEntity.ok(usuarisRepository.save(usuariToUpdate));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{idUsuari}/rol")
    public ResponseEntity<Usuaris> canviRolUsuari(@PathVariable BigInteger idUsuari, @RequestBody Usuaris usuariDetails) {
        Optional<Usuaris> usuari = usuarisRepository.findById(idUsuari);
        if (usuari.isPresent()){
            Usuaris usuariToUpdate = usuari.get();
            usuariToUpdate.setRol(usuariDetails.getRol());
            return ResponseEntity.ok(usuarisRepository.save(usuariToUpdate));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{idUsuari}/canvicontra")
    public ResponseEntity<Usuaris> updateContraUsuari(@PathVariable BigInteger idUsuari, @RequestBody Usuaris usuariDetails){
        Optional<Usuaris> usuari = usuarisRepository.findById(idUsuari);
        if (usuari.isPresent()){
            Usuaris usuariToUpdate = usuari.get();
            usuariToUpdate.setContrasenya(usuariDetails.getContrasenya());
            return ResponseEntity.ok(usuarisRepository.save(usuariToUpdate));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/nom/{nom}")
    public ResponseEntity<Usuaris> updateUsuariByNom(@PathVariable String nom, @RequestBody Usuaris usuariDetails){
        Optional<Usuaris> usuari = usuarisRepository.findByNom(nom);
        if (usuari.isPresent()){
            Usuaris usuariToUpdate = usuari.get();
            usuariToUpdate.setNom(usuariDetails.getNom());
            usuariToUpdate.setCognoms(usuariDetails.getCognoms());
            usuariToUpdate.setEmail(usuariDetails.getEmail());
            return ResponseEntity.ok(usuarisRepository.save(usuariToUpdate));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/nom/{nom}/canvicontra")
    public ResponseEntity<Usuaris> updateContraUsuariByNom(@PathVariable String nom, @RequestBody Usuaris usuariDetails){
        Optional<Usuaris> usuari = usuarisRepository.findByNom(nom);
        if (usuari.isPresent()){
            Usuaris usuariToUpdate = usuari.get();
            usuariToUpdate.setContrasenya(usuariDetails.getContrasenya());
            return ResponseEntity.ok(usuarisRepository.save(usuariToUpdate));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/{idUsuari}")
    public ResponseEntity<Void> deleteUsuari(@PathVariable BigInteger idUsuari){
        Optional<Usuaris> usuari = usuarisRepository.findById(idUsuari);
        if(usuari.isPresent()){
            usuarisRepository.delete(usuari.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    //Marcar llibre llegit
    
    
    
    //Marcar llibre per llegir
    
    
    
}
