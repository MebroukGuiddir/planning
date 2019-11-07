package fr.univ.tln.projet.planning.modele;
import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.util.*;

public class Admin extends Utilisateurs implements IAdmin{
    private boolean isAdmin;
    private List<Etudiant> et = new ArrayList<Etudiant>();
    private List<Enseignant> es = new ArrayList<Enseignant>();

    public Admin() {
    }

    public Admin(String nom, String prenom, String username, String password, String email, int dateNaissance, boolean isAdmin) {
        super(nom, prenom, username, password, email, dateNaissance);
        this.isAdmin = isAdmin;
    }

    @Override
    public IAdmin addEtudiant(Etudiant etudiant) {
        et.add(etudiant);
        return this;
    }

    @Override
    public  IAdmin  deleteEtudiant(Etudiant etudiant) {
        et.remove(etudiant);
        return this;
    }

    @Override
    public IAdmin modifierEtudiant(Etudiant etudiant) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Que voulez vous modifier?");
        String toModify = myObj.nextLine();
        switch(toModify) {
            case "nom":
                System.out.println("Enter nom");
                String nom = myObj.nextLine();
                etudiant.setNom(nom);
                break;
            case "prenom":
                System.out.println("Enter prenom");
                String prenom = myObj.nextLine();
                etudiant.setNom(prenom);
                break;
            case "username":
                System.out.println("Enter username");
                String username = myObj.nextLine();
                etudiant.setNom(username);
                break;
            case "password":
                System.out.println("Enter password");
                String password = myObj.nextLine();
                etudiant.setNom(password);
                break;
            case "email":
                System.out.println("Enter Email");
                String email = myObj.nextLine();
                etudiant.setNom(email);
                break;
            case "date de naissance":
                System.out.println("Enter date de naissance. format(aaaammjj)");
                String dateNaissance = myObj.nextLine();
                etudiant.setNom(dateNaissance);
                break;
            default:
                System.out.println("Que voulez vous modifier?");

        }
        return  this;
    }

    @Override
    public IAdmin addEnseignant(Enseignant enseignant) {
      es.add(enseignant);
      return this;
    }

    @Override
    public IAdmin deleteEnseignant(Enseignant enseignant) {
      es.remove(enseignant);
      return this;

    }

    @Override
    public  IAdmin  modifierEnseignant(Enseignant enseignant) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Que voulez vous modifier?");
        String toModify = myObj.nextLine();
        switch(toModify) {
            case "nom":
                System.out.println("Enter nom");
                String nom = myObj.nextLine();
                enseignant.setNom(nom);
                break;
            case "prenom":
                System.out.println("Enter prenom");
                String prenom = myObj.nextLine();
                enseignant.setNom(prenom);
                break;
            case "username":
                System.out.println("Enter username");
                String username = myObj.nextLine();
                enseignant.setNom(username);
                break;
            case "password":
                System.out.println("Enter password");
                String password = myObj.nextLine();
                enseignant.setNom(password);
                break;
            case "email":
                System.out.println("Enter Email");
                String email = myObj.nextLine();
                enseignant.setNom(email);
                break;
            case "date de naissance":
                System.out.println("Enter date de naissance. format(aaaammjj)");
                String dateNaissance = myObj.nextLine();
                enseignant.setNom(dateNaissance);
                break;
            default:
                System.out.println("Que voulez vous modifier?");
        }
        return this;
    }

    @Override
    public  IAdmin  listEtudiant() {
        for (int counter = 0; counter < et.size(); counter++) {
            System.out.println("Nom: " + et.get(counter).getNom() +"\r\n" +
                    "Prenom: " + et.get(counter).getPrenom() +"\r\n" +
                    "Username: " + et.get(counter).getUsername() +"\r\n" +
                    "Email: " + et.get(counter).getEmail() +"\r\n" +
                    "Date de naissance: " + et.get(counter).getDateNaissance() +"\r\n");
        }
        return this;
    }

    @Override
    public IAdmin listEnseignant() {
        for (int counter = 0; counter < es.size(); counter++) {
            System.out.println("Nom: " + es.get(counter).getNom() +"\r\n" +
                    "Prenom: " + es.get(counter).getPrenom() +"\r\n" +
                    "Username: " + es.get(counter).getUsername() +"\r\n" +
                    "Email: " + es.get(counter).getEmail() +"\r\n" +
                    "Date de naissance: " + es.get(counter).getDateNaissance() +"\r\n");
        }
        return this;
    }


}
