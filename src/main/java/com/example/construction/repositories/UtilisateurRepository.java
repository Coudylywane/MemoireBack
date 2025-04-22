package com.example.construction.repositories;

import com.example.construction.models.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query("SELECT u FROM Utilisateur u WHERE u.login=:login AND u.archive=false AND u.status=true")
    Optional<Utilisateur> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM Utilisateur u WHERE u.archive=false AND u.status=true AND u.role.libelle=:role")
    Optional<List<Utilisateur>> findByRole(@Param("role") String role);

    @Query("SELECT u FROM Utilisateur u WHERE u.archive=false")
    Page<Utilisateur> findByAllPeagable(Pageable pageable);
//
//    @Query("SELECT u FROM Utilisateur u WHERE u.archive=false AND u.status=true AND u.structure.id=:structure ")
//    Page<Utilisateur> findAllUtilisateurByStructure(@Param("structure") Long id, Pageable pageable);

    @Query("Select u from Utilisateur u where u.login=:login and u.status=:status")
    Optional<Utilisateur> connexion(@Param("login") String login, @Param("status") boolean status);

//    Optional<List<Utilisateur>> findAllByArchive(boolean archive);
//
//    Optional<Utilisateur> findByIdAndArchiveFalseAndStatusTrue(Long id);
//
//    Optional<List<Utilisateur>> findAllByArchiveAndStatus(boolean archive, boolean statut);
}
