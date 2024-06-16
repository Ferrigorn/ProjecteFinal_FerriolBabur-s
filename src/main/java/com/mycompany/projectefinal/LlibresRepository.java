
/**
 *
 * @author Ferriol Baburés
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.projectefinal;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author fbabu
 */
public interface LlibresRepository extends JpaRepository<Llibres, Long>{
    List<Llibres> findByAutorContaining(String autor);
   @Query("SELECT l FROM Llibres l WHERE LOWER(l.titol) = LOWER(:titol)")
    List<Llibres> findByTitolIgnoreCase(@Param("titol") String titol);
    @Query("SELECT l FROM Llibres l WHERE LOWER(l.titol) = LOWER(:titol)")
    Optional<Llibres> find1ByTitolIgnoreCase(@Param("titol") String titol);
   List<Llibres> findByGenere(String genere);
    @Query("SELECT DISTINCT l.genere FROM Llibres l")
    List<String> findAllGeneres();
}


