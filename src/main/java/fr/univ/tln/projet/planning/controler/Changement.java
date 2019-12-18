package fr.univ.tln.projet.planning.controler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Changement {
    public Type type;
    public Section section;
    public enum Type{ADD,DELETE,CHANGE,LOGIN}
    public enum Section{ETUDIANT,ENSEIGANT,ADMIN,RESPONSABLE,DOMAINE,SECTION,GROUPE,PROMOTION, MODULE, FORMATION}
}
