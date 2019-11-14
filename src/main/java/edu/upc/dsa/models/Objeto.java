package edu.upc.dsa.models;

public class Objeto {
    private String idobj;
    private String nombreobj;

    public Objeto(){

    }

    public Objeto(String id, String nom){
        this();
        this.idobj = id;
        this.nombreobj = nom;
    }

    public String getIDobj() {
        return this.idobj;
    }

    public void setIDobj(String idobj) {
        this.idobj = idobj;
    }

    public String getNombreobj() {
        return this.nombreobj;
    }

    public void setNombreobj(String nombreobj) {
        this.nombreobj = nombreobj;
    }
}
