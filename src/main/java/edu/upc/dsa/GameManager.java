package edu.upc.dsa;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Objeto;

import java.util.List;

public interface GameManager {
    public List<Usuario> listadoUsuariosOrdAlfab ();
    public int anadirUsuario(Usuario u); //int: Errores: Tabla llena, Mal formato
    public int modificarUsuario(String idusr, String nom, String ap1, String ap2); //int Errores: Usario no encontrado, Mal formato
    public int numeroUsuarios(); //int: Errores: Usuario no encontrado (404)
    public String consultarUsuario(String idusr); //Errores:
    public int anadirObjetoAUsuario(String idusr, Objeto o); //Errores:
    public List<Objeto> consultarObjetosUsuario(String idusr); //Errores:
    public int consultarNumObjUsuario(String idusr); //Errores:
    public void liberarRecursos();

    //Aux
    public void addObjeto(Objeto o);
    public Objeto getObjeto();
}
