package etu1932.model;

import etu1932.annotation.*;
import etu1932.framework.*;

public class Emp {

    String nom;
    String prenom;
    Upload file;

    

    public Upload getFile() {
        return file;
    }

    public void setFile(Upload file) {
        this.file = file;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Url(key="Emp-find")
    public ModelView find(){
        ModelView mv = new ModelView("Emp.jsp");
        return mv;
    }

    @Url(key="Emp-Insert")
    public ModelView Insert(){
        nom = nom;
        prenom = prenom;
        ModelView mv = new ModelView("Affiche.jsp");
        mv.addItem("nom", this.nom);
        mv.addItem("prenom", this.prenom);
        return mv;
    }

    @Url(key="Emp-Update")
    public ModelView Update(@Param(key="nom") String nom, @Param(key="prenom") String prenom){
        ModelView mv=new ModelView("Affiche.jsp");
        mv.addItem("nom", nom);
        mv.addItem("prenom", prenom);
        return mv;
    }
}
