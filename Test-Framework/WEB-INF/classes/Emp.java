package etu1932.model;

import etu1932.annotation.*;
import etu1932.framework.*;

public class Emp {

    String nom;
    String prenom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        System.out.println(nom);
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
        System.out.println(prenom);
    }

    @Url(key="Emp-find")
    public ModelView find(){
        ModelView mv = new ModelView("Emp.jsp");
        mv.addItem("Nom", "ANDRIAMPARANY");
        mv.addItem("Prenom", "Ny Aro");
        mv.addItem("Statut", "Etudiant");
        return mv;
    }

    @Url(key="Emp-Insert")
    public ModelView Insert(String nom, String prenom){
        nom = nom;
        prenom = prenom;
        ModelView mv = new ModelView("Affiche.jsp");
        mv.addItem("nom", this.nom);
        mv.addItem("prenom", this.prenom);
        return mv;
    }
}
