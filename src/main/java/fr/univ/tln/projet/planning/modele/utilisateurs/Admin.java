package fr.univ.tln.projet.planning.modele.utilisateurs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;



@Setter
@SuperBuilder
@Getter
@ToString
public class Admin extends Utilisateur {
   private  int idAdmin;

}
