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
    public List<Usuario> getListadoUsuariosOrdAlfab(){ //ORDENAMOS ALFABETICAMENTE  POR NOMBRE
        if(this.tablausuarios != null) {
            List<Usuario> res = new LinkedList<Usuario>(tablausuarios.values());

            Collections.sort(res, new Comparator<Usuario>() {
                @Override
                public int compare(Usuario u1, Usuario u2) {
                    return u1.getNombreusr().compareToIgnoreCase(u2.getNombreusr()); //ToIgnoreCase: para no distinguir mayusculas y minusculas
                }
            });
            log.info("Lista ordenada alfabeticamente: " + res.toString());
            return res; //200 OK PETITION
        }
        else
            return null; //404 (TABLA VACIA)

        /*List<String> l2 = null;

        for(Usuario u : l1){
            assert false;
            l2.add(u.getNombreusr());
        }
        assert false;
        Collections.sort(l2);

        */
    }

    public int anadirUsuario(Usuario u){
        try {
            this.tablausuarios.put(u.getIDusr(), u);
            log.info("Usuario añadido: " + u);
            return 201; //OK CREATED
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
                return 201; //OK CREATED
            }
            catch (IllegalArgumentException e){
                log.error("Error en el formato de los parámetros");
                return 400; //BAD REQUEST
            }
        }
        else
            return 404; //Usuario NOT FOUND
    }

    public int consultarNumeroUsuarios(){
        return this.tablausuarios.size();
    }

    public String consultarUsuarioTXT(String id){ //Suponemos que el usuario introduce su ID para consultar sus datos
        Usuario u = tablausuarios.get(id);
        if(u != null) {
            try {
                log.info("Usuario consultado: " + u);
                return u.toString(); //200 OK PETITION
            }
            catch (IllegalArgumentException e){
                log.error("Error en el formato del parametro");
                return "400"; //BAD REQUEST
            }
        }
        else{
            log.error("Usuario no encontrado");
            return "404"; //Usuario NOT FOUND
        }
    }

    public Usuario consultarUsuarioUSR(String iduser){
        return this.tablausuarios.get(iduser);
    }

    public int anadirObjetoAUsuario(String idusr, Objeto o){
        Usuario u = tablausuarios.get(idusr);
        if(u != null){
            int err = u.setObjeto(o);
            if(err == 201){
                log.info("Objeto anadido al Usario " + u.getNombreusr() + " : " + o);
                return 201; //OK CREATED
            }
            else if(err == 400){
                log.error("Error formato de entrada");
                return 400; //BAD REQUEST
            }
            else{
                log.error("No caben mas objetos en el inventario del usuario");
                return 507; //INSUFFICIENT STORAGE
            }
        }
        else{
            log.error("Usuario no encontrado");
            return 404; //Usuario NOT FOUND
        }
    }

    public List<Objeto> consultarObjetosUsuario(String idusr){
        Usuario u = tablausuarios.get(idusr);
        if(u != null){
            List<Objeto> res = u.getListaObjetos();
            if(res != null){
                log.info("Objetos del usuario: " + u.getNombreusr() + " : " + res);
                return res; // 200 OK PETITION
            }
            else{
                log.error("Inventario del usuario vacío");
                return null; //404 Lista NOT FOUND
            }
        }
        else{
            log.error("Usuario no encontrado");
            return null; //404 Usuario NOT FOUND
        }
    }

    public int consultarNumObjUsuario(String idusr){
        Usuario u = tablausuarios.get(idusr);
        if(u != null){
            log.info("Numero de objetos del usuario " + u.getNombreusr() + " : " + u.getNumobj());
            return u.getNumobj(); // 200 OK PETITION
        }
        else{
            log.error("Usuario no encontrado");
            return 404; //Usuario NOT FOUND
        }
    }

    //----------------------------Funciones auxiliares---------------------------------//
    public int addObjeto(Objeto o){
        try {
            this.listaobj.add(this.numobjmanager, o);
            log.info("Objeto añadido correctamente al manager: " + o);
            return 201; //OK CREATED
        }
        catch (IllegalArgumentException e){
            log.error("Error formato parametros");
            return 400; //BAD REQUEST
        }
        catch (IndexOutOfBoundsException e){
            log.error("Lista de objetos del manager llena");
            return 507; //INSUFFICIENT STORAGE
        }
    }

    public Objeto getObjeto(String id){
        Objeto obj = null;
        for(Objeto o : this.listaobj){
            if (o.getIDobj().compareTo(id) == 0)
                obj = o;
        }
        return obj; //Null: 404 Objeto NOT FOUND
    }

    public List<Usuario> getListaUsuarios (){
        List<Usuario> res = null;
        if(this.tablausuarios != null){
            res = new LinkedList<>(tablausuarios.values());
        }
        return res; //Null: 404 Tabla de Usaurios vacía
    }

    public void liberarRecursos(){
        this.listaobj.clear();
        this.tablausuarios.clear();
        this.numobjmanager = 0;
    }

}
