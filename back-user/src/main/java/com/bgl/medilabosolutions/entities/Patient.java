package com.bgl.medilabosolutions.entities;

import java.time.LocalDate;

import com.bgl.medilabosolutions.enums.Sexe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    @NotBlank(message = "Le prénom est obligatoire")
    private String lastName;

    @Past(message = "La date de naissance doit être dans le passé")
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false, length = 1)
    @NotBlank(message = "Le sexe est obligatoire")
    private Sexe sexe;

    private Adresse adresse;

    @Pattern(regexp = "^\\+?[0-9 .-]{8,20}$", message = "Numéro de téléphone invalide")
    @Column(name = "phone", length = 20)
    private String phoneNumber;


}
