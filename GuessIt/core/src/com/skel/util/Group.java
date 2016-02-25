package com.skel.util;

/**
 * Created by juanm on 24/02/2016.
 */
public class Group {

    private int id, id_idioma;
    private String nombre, profesor;

    public Group(){

    }

    public Group(int id, String nombre, String profesor, int id_idioma){
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
        this.id_idioma = id_idioma;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return nombre;
    }

    public String getTeacher(){
        return profesor;
    }

    public int getLanguage(){
        return id_idioma;
    }
}
