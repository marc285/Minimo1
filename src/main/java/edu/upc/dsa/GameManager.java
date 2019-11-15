package edu.upc.dsa;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Objeto;

import java.util.List;

public interface GameManager {
    public List<Usuario> getListadoUsuariosOrdAlfab(); //Errores: Tabla vacia
    public int anadirUsuario(Usuario u); //int: Errores: Tabla llena, Mal formato
    public int modificarUsuario(String idusr, String nom, String ap1, String ap2); //int Errores: Usario no encontrado, Mal formato
    public int consultarNumeroUsuarios();
    public String consultarUsuarioTXT(String idusr); //Errores: Mal formato, Usuario no encontrado
    public Usuario consultarUsuarioUSR(String iduser); //Errores: Usuario no encontrado
    public int anadirObjetoAUsuario(String idusr, Objeto o); //Errores: Usario no encontrado, Mal formato, Inventario lleno
    public List<Objeto> consultarObjetosUsuario(String idusr); //Errores: Usuario no encontrado, Inventario vacío
    public int consultarNumObjUsuario(String idusr); //Errores: Usuario not found

    //Aux
    public int addObjeto(Objeto o); //Errores: Mal formato, Lista llena
    public Objeto getObjeto(String id); //Errores: Objeto no econtrado
    public List<Usuario> getListaUsuarios (); //Errores: Tabla de Usuarios vacía
    public void liberarRecursos();
}
