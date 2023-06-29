package etu1932.model;

import etu1932.annotation.*;
import etu1932.framework.*;

public class Emp {

    @Url(key="Emp-find")
    public ModelView find(){
        return new ModelView("Emp.jsp");
    }
}
