package com.skel.util;

import java.util.ArrayList;

/**
 * Created by juanm on 25/02/2016.
 */
public class GEngine {
    private ArrayList<Definition> engine_definitions;

    private int current_def;
    private int max_def;

    public GEngine(){

    }

    public void setDefinitions(ArrayList<Definition> def){
        engine_definitions = def;
        current_def = 0;
        max_def = engine_definitions.size();
    }

    public String getPhrase(){
        // Hacer la transformacion de "Palabra" por "********"
        return engine_definitions.get(current_def).getFrase();
    }

    public String getHint(){
        return engine_definitions.get(current_def).getPista();
    }

    public boolean endRound(){
        return current_def==max_def;
    }

    public void nextDefinition(){
        current_def++;
    }

    public boolean compare(String answer){
        return engine_definitions.get(current_def).getPalabra().equals(answer);
    }

}
