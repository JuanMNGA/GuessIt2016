package com.skel.util;

import java.util.StringTokenizer;

/**
 * Created by juanm on 22/02/2016.
 */
public class UserInfo {

    private String nombre,apellidos,email,usuario;

    private int testType,userId;

    public UserInfo(){}

    public UserInfo(String Info){
        StringTokenizer stroker = new StringTokenizer(Info,";");

        while(stroker.hasMoreElements()){
            userId = Integer.parseInt(stroker.nextElement().toString());
            nombre = stroker.nextElement().toString();
            apellidos = stroker.nextElement().toString();
            email = stroker.nextElement().toString();
            usuario = stroker.nextElement().toString();
            testType = Integer.parseInt(stroker.nextElement().toString());
        }
    }

    public void setInfo(String Info){
        StringTokenizer stroker = new StringTokenizer(Info,";");

        while(stroker.hasMoreElements()){
            userId = Integer.parseInt(stroker.nextElement().toString());
            nombre = stroker.nextElement().toString();
            apellidos = stroker.nextElement().toString();
            email = stroker.nextElement().toString();
            usuario = stroker.nextElement().toString();
            testType = Integer.parseInt(stroker.nextElement().toString());
        }
    }

    public int getId(){
        return userId;
    }

    public String getName(){
        return nombre;
    }

    public String getLastname(){
        return apellidos;
    }

    public String getEmail(){
        return email;
    }

    public String getUser(){
        return usuario;
    }

    public int getType(){
        return testType;
    }
}
