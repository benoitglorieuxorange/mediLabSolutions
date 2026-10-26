package com.bgl.medilabosolutions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bgl.medilabosolutions.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
