package etu1932.model;

import etu1932.annotation.*;
import etu1932.framework.*;

public class Emp {

    String nom;
    String prenom;

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
