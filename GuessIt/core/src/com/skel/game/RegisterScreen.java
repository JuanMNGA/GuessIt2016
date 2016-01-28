package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.net.Connection;
import com.skel.util.Utils;

/**
 * Created by juanm on 28/01/2016.
 */
public class RegisterScreen implements Screen {

    Stage stage;
    Skin skin;
    Connection con;

    //Items de la pantalla
    Label labelLogin,labelPass;
    TextField userLogin, userPass;
    TextButton LoginButton;

    Game g;

    public RegisterScreen(Game g){
        skin = Utils.createBasicSkin();
        stage = new Stage(new StretchViewport(320,500));
        Gdx.input.setInputProcessor(stage);

        createStageActors();
        con = new Connection();
        this.g = g;
    }

    public void createStageActors(){
        //Creacion de los elementos de la pantalla
        labelLogin = new Label("Username",skin.get("default", Label.LabelStyle.class));
        userLogin = new TextField("", skin.get("default",TextField.TextFieldStyle.class));
        labelPass = new Label("Password",skin.get("default", Label.LabelStyle.class));
        userPass = new TextField("", skin.get("default",TextField.TextFieldStyle.class));
        LoginButton = new TextButton("Register",skin);

        //Activar caracteristicas de los actores
        //Labels

        labelLogin.setAlignment(Align.center);

        labelPass.setAlignment(Align.center);

        //Text Fields
        userLogin.setAlignment(Align.center);
        userLogin.setMessageText("User");

        userPass.setPasswordMode(true);
        userPass.setMessageText("Password");
        userPass.setAlignment(Align.center);
        userPass.setPasswordCharacter('*');

        //Alineacion en la escena
        labelLogin.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.6f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

        userLogin.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.5f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

        labelPass.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.4f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

        userPass.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.3f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

        LoginButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.1f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

        //Funciones callback
        LoginButton.addListener(
                new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        con.createUser(userLogin.getText(),userPass.getText());
                        if(con.getCreated()){
                            g.setScreen(new MainScreen(g));
                        }else{
                            g.setScreen(new MainScreen(g));
                        }
                        return true;
                    }
                }
        );

        //Anadimos actores al stage
        stage.addActor(labelLogin);
        stage.addActor(userLogin);
        stage.addActor(labelPass);
        stage.addActor(userPass);
        stage.addActor(LoginButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}