package fr.univ.tln.projet.planning.modele;

import java.util.Observable;
import java.util.logging.Logger;


public class AdminModele<A extends IAdmin> extends Observable implements IAdmin {
    private static Logger logger = Logger.getLogger(AdminModele.class.getName());
    private IAdmin Admin;


    public AdminModele(String nom, String prenom, String username, String password, String email, int dateNaissance, boolean isAdmin) {
        this.Admin = new Admin(nom, prenom, username,password,email, dateNaissance, isAdmin);
    }

    public AdminModele(A Admin) {
        this.Admin = Admin;
    }

    /**
     * Ajouter un étudiant.
     *
     *
     * @param etudiant
     * @return
     */
    public IAdmin addEtudiant(Etudiant etudiant) {
        Admin.addEtudiant(etudiant);
        setChanged();
        notifyObservers();
        return this;
    }

    /**
     * Ajouter un enseignant.
     *
     *
     * @param enseignant
     * @return
     */
    public IAdmin addEnseignant(Enseignant enseignant) {
        Admin.addEnseignant(enseignant);
        setChanged();
        notifyObservers();
        return this;
    }

    /**
     * Modifier un Etudiant.
     *
     *
     * @param etudiant
     * @return
     */
    public IAdmin modifierEtudiant(Etudiant etudiant) {
        Admin.modifierEtudiant(etudiant);
        setChanged();
        notifyObservers();
        return this;
    }

    /**
     * Modifier un Enseignant.
     *
     *
     * @param enseignant
     * @return
     */
    public IAdmin modifierEnseignant(Enseignant enseignant) {
        Admin.modifierEnseignant(enseignant);
        setChanged();
        notifyObservers();
        return this;
    }

    @Override
    public IAdmin listEtudiant() {
        return this;
    }

    @Override
    public IAdmin listEnseignant() {
       return this;
    }

    /**
     * Supprimer un Etudiant.
     *
     *
     * @param etudiant
     * @return
     */

    public IAdmin deleteEtudiant(Etudiant etudiant) {
        Admin.deleteEtudiant(etudiant);
        setChanged();
        notifyObservers();
        return this;
    }

    /**
     * Supprimer un Enseignant.
     *
     *
     * @param enseignant
     * @return
     */
    public IAdmin deleteEnseignant(Enseignant enseignant) {
        Admin.deleteEnseignant(enseignant);
        setChanged();
        notifyObservers();
        return this;
    }

}
