package fr.univ.tln.projet.planning.modele;

public interface IAdmin {

    public IAdmin addEtudiant(Etudiant etudiant);
    public IAdmin deleteEtudiant(Etudiant etudiant);
    public IAdmin modifierEtudiant(Etudiant etudiant);
    public IAdmin addEnseignant(Enseignant enseignant);
    public IAdmin deleteEnseignant(Enseignant enseignant);
    public IAdmin modifierEnseignant(Enseignant enseignant);
    public IAdmin listEtudiant();
    public IAdmin listEnseignant();
}
