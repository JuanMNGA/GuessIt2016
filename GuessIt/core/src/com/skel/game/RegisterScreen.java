package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    Label labelLogin,labelPass,labelName,labelLastname,labelEmail;
    TextField userLogin, userPass, userName, userLastname, userEmail;
    TextButton LoginButton;

    //Container
    Table scroll_contenedor;

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
        labelName = new Label("Name", skin.get("default", Label.LabelStyle.class));
        labelLastname = new Label("Last name", skin.get("default", Label.LabelStyle.class));
        labelEmail = new Label("Email", skin.get("default", Label.LabelStyle.class));
        userName = new TextField("", skin.get("default",TextField.TextFieldStyle.class));
        userLastname = new TextField("", skin.get("default",TextField.TextFieldStyle.class));
        userEmail = new TextField("", skin.get("default",TextField.TextFieldStyle.class));

        LoginButton = new TextButton("Register",skin);

        //Activar caracteristicas de los actores
        //Labels

        labelLogin.setAlignment(Align.center);

        labelPass.setAlignment(Align.center);

        labelEmail.setAlignment(Align.center);

        labelLastname.setAlignment(Align.center);

        labelName.setAlignment(Align.center);

        //Text Fields
        userLogin.setAlignment(Align.center);
        userLogin.setMessageText("User");

        userPass.setPasswordMode(true);
        userPass.setMessageText("Password");
        userPass.setAlignment(Align.center);
        userPass.setPasswordCharacter('*');

        userName.setAlignment(Align.center);
        userName.setMessageText("Name");

        userLastname.setAlignment(Align.center);
        userLastname.setMessageText("Last name");

        userEmail.setAlignment(Align.center);
        userEmail.setMessageText("Email");

        //Funciones callback
        LoginButton.addListener(
                new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        String[] info = new String[5];
                        info[0] = userName.getText();
                        info[1] = userLastname.getText();
                        info[2] = userEmail.getText();
                        info[3] = userLogin.getText();
                        info[4] = userPass.getText();
                        con.createUser(info);
                        if(con.getCreated()){
                            g.setScreen(new MainScreen(g));
                        }else{
                            g.setScreen(new MainScreen(g));
                        }
                        return true;
                    }
                }
        );

        //Creamos la tabla contenedora de nuestra interfaz de registro
        //Se pueden "encadenar" funciones para el control del tamaño, la posicion nos la da la propia tabla

        scroll_contenedor = new Table();

        scroll_contenedor.add(labelName).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(userName).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(labelLastname).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(userLastname).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(labelEmail).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(userEmail).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(labelLogin).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row(); // Creamos una nueva fila

        scroll_contenedor.add(userLogin).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(labelPass).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(userPass).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        scroll_contenedor.row();

        scroll_contenedor.add(LoginButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);

        ScrollPane scroller = new ScrollPane(scroll_contenedor);

        // Esta tabla es contenedora del scroll pane, importante para visualizar los contenidos y centrar la interfaz

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        //Anadimos la tabla al stage
        stage.addActor(table);
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