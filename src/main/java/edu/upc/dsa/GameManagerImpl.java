package edu.upc.dsa;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Objeto;

import org.apache.log4j.Logger;

import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.GameManager;

import java.util.*;

public class GameManagerImpl implements GameManager {
    //--Singleton: Referencia privada estatica a la unica instancia de la clase--//
    private static GameManagerImpl instancia;

    private static Logger log = Logger.getLogger(TracksManagerImpl.class);

    //---Estructuras de datos (atributos) del Manager---//
    private HashMap<String, Usuario> tablausuarios;
    private List<Objeto> listaobj;
    int numobjmanager; //Numero de objetos que hay en la lista de objetos del MANAGER

    //----------Singleton: constructor privado----------//
    private GameManagerImpl(){
        this.tablausuarios = new HashMap<>();
        this.listaobj = new LinkedList<>();
    }

    //---Singleton: metodo de acceso estatico que devuelve una referencia a la (unica) instancia---//
    public static GameManagerImpl getInstance(){
        if(instancia == null) instancia = new GameManagerImpl();
        return instancia;
    }

    //--------------------Metodos (no estaticos) del Manager---------------------//
    public List<Usuario> listadoUsuariosOrdAlfab (){ //ORDENAMOS ALFABETICAMENTE  POR NOMBRE

        List<Usuario> res = new ArrayList<Usuario>(tablausuarios.values());
        List<String> res2 = null;
        for(Usuario u : res){
            assert false;
            res2.add(u.getNombreusr());
        }
        /*
        res.sort(new Comparator<Usuario>() {
            @Override
            public int compare(Usuario p1, Usuario p2) {
                return Usuario.compare(p1.getNombreusr(), p2.getNombreusr());
            }
        });
         */
        return res;
    }

    public int anadirUsuario(Usuario u){
        try {
            this.tablausuarios.put(u.getIDusr(), u);
            log.info("Usuario añadido: " + u);
            return 200; //OK
        }
        catch (IndexOutOfBoundsException e){
            log.error("Error. Tabla de usuarios llena");
            return 507; //INSUFFICIENT STORAGE
        }
        catch (IllegalArgumentException e){
            log.error("Error en el formato de los parametros");
            return 400; //BAD REQUEST
        }
    }

    public int modificarUsuario(String id, String nomnuevo, String ap1nuevo, String ap2nuevo){ //ID del objeto + parametros nuevos
        //Suponemos que el ID del usuario no se puede cambiar: le identifica dentro de la tabla de Hash
        Usuario res = this.tablausuarios.get(id);
        if(res != null) {
            try {
                res.setNombreusr(nomnuevo);
                res.setApellido1(ap1nuevo);
                res.setApellido2(ap2nuevo);
                log.info("Parametros cambiados. Usuario actual:" + res);
                return 200;
            }
            catch (IllegalArgumentException e){
                log.error("Error en el formato de los parametros");
                return 400; //BAD REQUEST
            }
        }
        else
            return 404; //Usuario NOT FOUND
    }

    public int numeroUsuarios(){
        return this.tablausuarios.size();
    }

    public String consultarUsuario(String id){ //Suponemos que el usuario introduce su ID para consultar sus datos
        Usuario u = tablausuarios.get(id);
        if(u != null) {
            try {
                String res = "Nombre: " + u.getNombreusr() + " Apellido1 1: " + u.getApellido1() + " Apellido 2: " + u.getApellido2();
                log.info("Usuario consultado: " + res);
                return res;
            }
            catch (IllegalArgumentException e){
                log.error("Error en el formato de los parametros");
                return "400";
            }
        }
        else{
            log.error("Usuario no encontrado");
            return "404";
        }
    }

    public int anadirObjetoAUsuario(String idusr, Objeto o){
        Usuario u = tablausuarios.get(idusr);
        if(u != null){
            int err = u.setObjeto(o);
            if(err == 200){
                log.info("Objeto anadido al usario " + u.getNombreusr() + " : " + o);
                return 200;
            }
            else if(err == 400){
                log.error("Error formato de entrada");
                return 400;
            }
            else{
                log.error("No caben mas objetos en el inventario del usuario");
                return 507;
            }
        }
        else{
            log.error("Usuario no encontrado");
            return 404;
        }
    }

    public List<Objeto> consultarObjetosUsuario(String idusr){
        Usuario u = tablausuarios.get(idusr);
        if(u != null){
            List<Objeto> res = u.getListaObjetos();
            if(res != null){
                log.info("Objetos del usuario: " + u.getNombreusr() + " : " + res);
                return res;
            }
            else{
                log.error("Inventario del usuario vacío");
                return null; //404 DE LISTA
            }
        }
        else{
            log.error("Usuario no encontrado");
            return null; //404 DE USUARIO
        }
    }

    public int consultarNumObjUsuario(String idusr){
        Usuario u = tablausuarios.get(idusr);
        if(u != null){
            log.info("Numero de objetos del usuario " + u.getNombreusr());
            return u.getNumobj();
        }
        else{
            log.error("Usuario no encontrado");
            return 404;
        }
    }

    public void liberarRecursos(){
        this.listaobj.clear();
        this.tablausuarios.clear();
        this.numobjmanager = 0;
    }

    //----------------------------Funciones auxiliares---------------------------------//
    public void addObjeto(Objeto o){
        try {
            this.listaobj.add(this.numobjmanager, o);
            log.info("Objeto añadido correctamente al manager: " + o);
        }
        catch (IllegalArgumentException e){
            log.error("Error formato parametros");
        }
        catch (IndexOutOfBoundsException e){
            log.error("Lista de objetos del manager llena");
        }
    }

    public Objeto getObjeto(String id){
        for(Objeto o : this.listaobj){
            if (o.getIDobj().compareTo(id) == 0)
                return o;
        }

    }
}
