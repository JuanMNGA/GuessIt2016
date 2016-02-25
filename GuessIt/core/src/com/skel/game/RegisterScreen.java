package com.skel.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by juanm on 28/01/2016.
 */
public class RegisterScreen implements Screen, Net.HttpResponseListener {

    Stage stage;
    Skin skin;

    //Items de la pantalla
    Label labelLogin,labelPass,labelName,labelLastname,labelEmail;
    TextField userLogin, userPass, userName, userLastname, userEmail;
    TextButton LoginButton;

    //Container
    Table scroll_contenedor;

    Game g;

    //Utiles net
    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    public RegisterScreen(Game g){
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
                        HashMap<String, String> parameters = new HashMap<String, String>();
                        parameters.put("nombre", userName.getText());
                        parameters.put("apellidos", userLastname.getText());
                        parameters.put("email", userEmail.getText());
                        parameters.put("usuario",userLogin.getText());
                        parameters.put("password",userPass.getText());
                        parameters.put("alta",dFormat.format(new Date(TimeUtils.millis())));
                        String url = "http://localhost/prueba/register.php?";
                        //solicitud_variables = "&nombre=suscribete&puntaje=222";
                        httpsolicitud = new Net.HttpRequest(httpMethod);
                        httpsolicitud.setUrl(url);
                        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
                        Gdx.net.sendHttpRequest(httpsolicitud, RegisterScreen.this);

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

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String Response = httpResponse.getResultAsString();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if(Response.isEmpty()){
                    Gdx.app.log("conexion","fallida");
                }else{
                    Gdx.app.log("conexion",Response);
                    Gdx.app.log("time", dFormat.format(new Date(TimeUtils.millis()).toString()));
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