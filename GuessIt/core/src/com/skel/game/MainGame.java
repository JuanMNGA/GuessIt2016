package com.skel.game;

import com.badlogic.gdx.Game;

/**
 * Created by juanm on 27/01/2016.
 */
public class MainGame extends Game {

    public void create(){
        setScreen(new MainScreen(this));
    }
}
