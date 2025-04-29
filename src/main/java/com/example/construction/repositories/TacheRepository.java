package com.example.construction.repositories;

import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findByPlanningId(Long planningId);
    List<Tache>     findByPlanningIdAndStatus(Long planningId, TaskStatus status);
}
