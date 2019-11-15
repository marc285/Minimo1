package edu.upc.dsa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.upc.dsa.models.*;

public class GameManagerTest {
    public GameManagerImpl manager = null;

    @Before
    public void setUp(){
        manager = GameManagerImpl.getInstance();

        manager.anadirUsuario(new Usuario("12345", "Marc", "Cayuelas", "Lobato"));
        manager.addObjeto(new Objeto("0001", "espada"));
        manager.addObjeto(new Objeto("1000", "escudo"));

        manager.anadirObjetoAUsuario("12345", this.manager.getObjeto("0001"));

    }

    @After
    public void tearDown(){
        manager.liberarRecursos();
    }

    @Test
    public void anadirUsuarioTest(){
        Assert.assertEquals(1, this.manager.consultarNumeroUsuarios()); //Comprobación inicial

        //Probamos a añadir otro usuario
        manager.anadirUsuario(new Usuario("01010", "Toni", "Oller", "Arcas"));
        Assert.assertEquals(2, manager.consultarNumeroUsuarios());
    }

    @Test
    public void anadirObjetoAsociadoTest(){
        Assert.assertEquals(1, manager.consultarNumObjUsuario("12345")); //Comprobación inicial

        //Probamos a añadirle al usuario Marc (id="12345") un nuevo objeto escudo (id = "1000")
        manager.anadirObjetoAUsuario("12345", manager.getObjeto("1000"));
        Assert.assertEquals(2, manager.consultarNumObjUsuario("12345"));
    }
}
