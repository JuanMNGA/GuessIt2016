package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by juanm on 22/02/2016.
 */
public class UserGroupsScreen implements Screen, Net.HttpResponseListener {
    private UserInfo userInfo;
    private Game g;
    private Stage stage;
    private Skin skin;

    private Table upTable;
    private Table ScrollTable;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    // Group ID
    private int iGroupId = 0;

    public UserGroupsScreen(Game g, UserInfo UInfo) {
        this.g = g;
        userInfo = UInfo;
        create();
    }

    public void createStageActors(){
        upTable = new Table();
        ScrollTable = new Table();

        ImageButton searchIcon = new ImageButton(skin.get("search_icon", ImageButton.ImageButtonStyle.class));
        ImageButton joinIcon = new ImageButton(skin.get("join_icon", ImageButton.ImageButtonStyle.class));

        TextField searchInput = new TextField("",skin.get("default",TextField.TextFieldStyle.class));
        Label joinLabel = new Label("Join group",skin.get("default", Label.LabelStyle.class));

        joinLabel.setAlignment(Align.center);

        upTable.add(searchIcon).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        upTable.add(searchInput).width(Gdx.graphics.getWidth()*0.5f).height(Gdx.graphics.getHeight()*0.1f);
        upTable.row();
        upTable.add(joinIcon).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        upTable.add(joinLabel).width(Gdx.graphics.getWidth()*0.5f).height(Gdx.graphics.getHeight()*0.1f);
        upTable.row();
        upTable.setFillParent(true);
        upTable.top();
        // Add tables to stage
        stage.addActor(upTable);
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();
        //Llamar a connection y que devolviese el resultado de los grupos a los que el alumno ha sido validado
        createStageActors();

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        String url = "http://localhost/prueba/getGroupsJoined.php?";
        //solicitud_variables = "&nombre=suscribete&puntaje=222";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,UserGroupsScreen.this);
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
        Gdx.app.log("conexion","conectado");
        final String Response = httpResponse.getResultAsString();
        Gdx.app.log("conexion",Response);
        createScrollTable(Response);
    }

    // Hacer mañana
    public void createScrollTable(String Response){
        if(!Response.isEmpty()) {
            StringTokenizer stroker = new StringTokenizer(Response, ";");
            while (stroker.hasMoreElements()) {
                TextButton tmpTButton = new TextButton("", skin.get("default", TextButton.TextButtonStyle.class));
                iGroupId = Integer.getInteger(stroker.nextElement().toString());
                String groupName = stroker.nextElement().toString();
                String teacherName = stroker.nextElement().toString();
                tmpTButton.setText(groupName + " - " + teacherName);
                tmpTButton.getLabel().setAlignment(Align.center);
                tmpTButton.getLabel().setWrap(true);

                ScrollTable.add(tmpTButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f);
                ScrollTable.row();
            }
            Gdx.app.log("conexion", "tabla creada");
            ScrollPane scroller = new ScrollPane(ScrollTable);
            Gdx.app.log("conexion", "scroll creado");
            final Table table = new Table();
            table.setFillParent(true);
            table.add(scroller).fill().expand();

            //Anadimos la tabla al stage
            stage.addActor(table);
            Gdx.app.log("conexion", "tabla grupos creada");
        }else{
            Gdx.app.log("conexion","ningun resultado");
        }
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
}
