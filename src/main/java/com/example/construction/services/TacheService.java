package com.example.construction.services;

import com.example.construction.models.Tache;
import com.example.construction.repositories.TacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TacheService {
    private final TacheRepository tacheRepository ;

    public Tache ajouterTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    // Lister toutes les tâches
    public List<Tache> listerTaches() {
        return tacheRepository.findAll();
    }

//    public List<Tache> listerTachesParProjet(Long projetId) {
//        return tacheRepository.findByProjetId(projetId);
//    }

}
