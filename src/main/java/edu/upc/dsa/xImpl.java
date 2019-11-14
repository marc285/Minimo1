package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class xImpl implements x{
    private static xImpl instance;

    private static Logger log = Logger.getLogger(TracksManagerImpl.class);
    //---Estructuras de datos (atributos) del gestor---//

    private xImpl(){
        //this. = ;
    }

    public xImpl getInstance(){
        if(instance == null) instance = new xImpl();
        return instance;
    }

    //Funciones
}
