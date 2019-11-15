package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Usuario {
    private String idusr; //El ID del usuario coincidira con la key del HashMap
    private String nombreusr;
    private String apellido1;
    private String apellido2;

    private List<Objeto> objts = null; //Consideramos una cola, asi se ordenan por orden de insercion
    private int numobj = 0; //Numero de objetos en la lista

    public Usuario() {

    }

    public Usuario(String id, String nom, String ape1, String ape2){ /////////////???
        this(); //????????????????????
        this.idusr = id;
        this.nombreusr = nom;
        this.apellido1 = ape1;
        this.apellido2 = ape2;
        objts = new LinkedList<Objeto>();
        numobj = 0;
    }


    public String getIDusr() {
        return this.idusr;
    }

    public String getNombreusr() {
        return this.nombreusr;
    }

    public String getApellido1() {
        return this.apellido1;
    }

    public String getApellido2() {
        return this.apellido2;
    }

    public Objeto getObjeto(int index){
        return this.objts.get(index);
    }

    public int getNumobj(){ //Devuelve el numero de objetos en la lista
        return this.numobj;
    }

    public List<Objeto> getListaObjetos(){
        return this.objts;
    }

    public void setIDusr(String idusr) { //Suponemos inmodificable, ya que este ID le identifica en la tabla de Hash
        this.idusr = idusr;
    }

    public void setNombreusr(String nombreusr) {
        this.nombreusr = nombreusr;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int setObjeto(Objeto o){ //Lo anade al final de la lista, asi estan en orden de insercion
        try {
            this.objts.add(this.numobj, o);
            numobj++;
            return 201;
        }
        catch (IllegalArgumentException e){
            return 400;
        }
        catch (IndexOutOfBoundsException e){
            return 507;
        }
    }

    public int setListaObjetos(List<Objeto> lo){ //Recoge todos los objetos introducidos y los va metiendo al final de la lista
        try {
            for (Objeto o : lo) {
                this.setObjeto(o);
            }
            return 201;
        }
        catch (IllegalArgumentException e){
            return 400;
        }
        catch (IndexOutOfBoundsException e){
            return 507;
        }
    }

    public String toString(){
        return "ID: " + this.getIDusr() + " | Nombre: " + this.getNombreusr() + " | Apellido 1: " + this.getApellido1() + " | Apellido 2: " + this.getApellido2();
    }


}
