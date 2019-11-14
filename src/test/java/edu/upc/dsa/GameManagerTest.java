package edu.upc.dsa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.upc.dsa.models.*;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;

public class GameManagerTest {
    public GameManagerImpl manager = null;

    @Before
    public void setUp(){
        manager = GameManagerImpl.getInstance();

        manager.anadirUsuario(new Usuario("12345", "Marc", "Cayuelas", "Lobato"));
        manager.addObjeto(new Objeto("4321", "espada"));
        manager.addObjeto(new Objeto("0001", "escudo"));

        manager.anadirObjetoAUsuario("12345", this.manager.getObjeto("4321"));

    }

    @After
    public void tearDown(){
        manager.liberarRecursos();
    }

    @Test
    public void AnadirTest(){
        Assert.assertEquals(1, this.manager.numeroUsuarios());
    }

    @Test
    public void test2(){

    }
}
