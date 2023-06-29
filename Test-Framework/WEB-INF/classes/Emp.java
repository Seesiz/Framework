package etu1932.model;

import etu1932.annotation.*;
import etu1932.framework.*;

public class Emp {

    @Url(key="Emp-find")
    public ModelView find(){
        ModelView mv = new ModelView("Emp.jsp");
        mv.addItem("Nom", "ANDRIAMPARANY");
        mv.addItem("Prenom", "Ny Aro");
        mv.addItem("Statut", "Etudiant");
        return mv;
    }
}
