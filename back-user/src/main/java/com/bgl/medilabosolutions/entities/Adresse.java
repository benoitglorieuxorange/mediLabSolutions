package com.bgl.medilabosolutions.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Embeddable
public class Adresse {

    @NotBlank(message = "Le champ 'rue' ne peut pas être vide")
    private Integer numero;

    @NotBlank(message = "Le champ 'rue' ne peut pas être vide")
    private String line1;

    private String line2;

    @NotBlank(message = "Le champ 'ville' ne peut pas être vide")
    private String ville;

    @NotBlank(message = "Le champ 'code postal' ne peut pas être vide")
    @Pattern(regexp = "\\d{5}", message = "Code postal invalide")
    private String codePostal;

    private String pays;

    // Constructors

    public Adresse() {
    }

    public Adresse(Integer numero, String line1, String line2, String ville, String codePostal, String pays) {
        this.numero = numero;
        this.line1 = line1;
        this.line2 = line2;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
    }

    // getters and setters

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }


}
