package fr.univ.tln.projet.planning.modele;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@SuperBuilder
@Getter
@ToString
public class Utilisateurs {

    private String nom, prenom, username, password, email,dateNaissance;
    private LocalDateTime dateCreation  ;















}
