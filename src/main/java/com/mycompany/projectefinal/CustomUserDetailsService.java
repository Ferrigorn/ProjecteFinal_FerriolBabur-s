/**
 *
 * @author Ferriol Baburés
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectefinal;

/**
 *
 * @author fbabu
 */
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarisRepository usuarisRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuaris> usuarioOpt = usuarisRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        Usuaris usuari = usuarioOpt.get();
        return org.springframework.security.core.userdetails.User.withUsername(usuari.getEmail())
                .password(usuari.getContrasenya())
                .roles("Convidat").build();
    }
    

    public boolean authenticate(String email, String contrasenya) {
        Optional<Usuaris> usuarioOpt = usuarisRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuaris usuario = usuarioOpt.get();
            return bCryptPasswordEncoder.matches(contrasenya, usuario.getContrasenya());
        }
        return false;
    }
    
    public String encriptarContrasenya(String contrasenya){
        return bCryptPasswordEncoder.encode(contrasenya);
    }
    
    public Optional<Usuaris> findByNom(String nom) {
        return usuarisRepository.findByNom(nom);
    }
    
    public Optional<Usuaris> findByEmail(String email) {
        return usuarisRepository.findByEmail(email);
    }
    
    public boolean checkPassword(Usuaris usuari, String rawPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, usuari.getContrasenya());
    }
}

