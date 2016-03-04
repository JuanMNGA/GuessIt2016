package com.skel.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

import java.util.HashMap;

/**
 * Created by juanm on 27/01/2016.
 */
public class LoginScreen implements Screen, Net.HttpResponseListener {

    Stage stage;
    Skin skin;

    //Items de la pantalla
    Label labelLogin,labelPass;
    TextField userLogin, userPass;
    TextButton LoginButton, backButton;

    Game g;

    UserInfo UInfo = new UserInfo();

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    public LoginScreen(Game g){
        this.g = g;
        create();
    }

    public void create(){
        skin = Utils.createBasicSkin();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight())){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    g.setScreen(new MainScreen(g));
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        createStageActors();
    }

    public void createStageActors(){
        //Creacion de los elementos de la pantalla
        labelLogin = new Label("Login",skin.get("default", Label.LabelStyle.class));
        userLogin = new TextField("", skin.get("default",TextField.TextFieldStyle.class));
        labelPass = new Label("Password",skin.get("default", Label.LabelStyle.class));
        userPass = new TextField("", skin.get("default",TextField.TextFieldStyle.class));
        LoginButton = new TextButton("Login",skin);
        backButton = new TextButton("Back",skin.get("default", TextButton.TextButtonStyle.class));

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

        LoginButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.2f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);
        backButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.1f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

        //Funciones callback
        LoginButton.addListener(
                new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        // Conexion http
                        HashMap<String, String> parameters = new HashMap<String, String>();
                        parameters.put("usuario",userLogin.getText());
                        parameters.put("password",userPass.getText());
                        String url = Utils.getUrl()+"login.php?";
                        //solicitud_variables = "&nombre=suscribete&puntaje=222";
                        httpsolicitud = new Net.HttpRequest(httpMethod);
                        httpsolicitud.setUrl(url);
                        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
                        Gdx.net.sendHttpRequest(httpsolicitud,LoginScreen.this);
                        // Fin conexion http
                        return true;
                    }
                }
        );

        backButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new MainScreen(g));
                return true;
            }
        });

        //Anadimos actores al stage
        stage.addActor(labelLogin);
        stage.addActor(userLogin);
        stage.addActor(labelPass);
        stage.addActor(userPass);
        stage.addActor(LoginButton);
        stage.addActor(backButton);
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

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String Response = httpResponse.getResultAsString();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if(Response.isEmpty()){
                    Gdx.app.log("conexion","fallida");
                    g.setScreen(new MainScreen(g));
                }else {
                    UInfo.setInfo(Response);
                    //Gdx.app.log("conexion",UInfo.getName());
                    Gdx.app.log("conexion","resuelta");
                    g.setScreen(new UserGroupsScreen(g,UInfo));
                }
            }
        });
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
}
