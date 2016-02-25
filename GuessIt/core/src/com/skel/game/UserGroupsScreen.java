package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Group;
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

    //private Table upTable;
    private Table ScrollTable;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    private Group selected_group;

    public UserGroupsScreen(Game g, UserInfo UInfo) {
        this.g = g;
        userInfo = UInfo;
        create();
    }

    public void createStageActors(){
        //upTable = new Table();
        ScrollTable = new Table();

        // Search icon
        ImageButton searchIcon = new ImageButton(skin.get("search_icon", ImageButton.ImageButtonStyle.class));
        searchIcon.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.log("botones","search pulsado");
                return true;
            }
        });
        // Join icon
        ImageButton joinIcon = new ImageButton(skin.get("join_icon", ImageButton.ImageButtonStyle.class));
        joinIcon.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.log("botones","join pulsado");
                g.setScreen(new JoinGroupsScreen(g,userInfo));
                return true;
            }
        });

        TextField searchInput = new TextField("",skin.get("default",TextField.TextFieldStyle.class));
        Label joinLabel = new Label("Join group",skin.get("default", Label.LabelStyle.class));

        joinLabel.setAlignment(Align.center);

        ScrollTable.add(searchIcon).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        ScrollTable.add(searchInput).width(Gdx.graphics.getWidth()*0.7f).height(Gdx.graphics.getHeight()*0.1f);
        ScrollTable.row();
        ScrollTable.add(joinIcon).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        ScrollTable.add(joinLabel).width(Gdx.graphics.getWidth()*0.7f).height(Gdx.graphics.getHeight()*0.1f);
        ScrollTable.row();
        ScrollTable.setFillParent(true);
        ScrollTable.top();
        // Add tables to stage
        //stage.addActor(upTable);
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();

        createStageActors();
        //Llamar a connection y que devolviese el resultado de los grupos a los que el alumno ha sido validado
        refreshGroups();
    }

    public void refreshGroups(){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        String url = "http://localhost/prueba/getGroupsJoined.php?";
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
        final String Response = httpResponse.getResultAsString();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (!Response.isEmpty()) {
                    StringTokenizer stroker = new StringTokenizer(Response, ";");
                    while (stroker.hasMoreElements()) {
                        TextButton tmpTButton = new TextButton("", skin.get("group", TextButton.TextButtonStyle.class));
                        final int iGroupId = Integer.parseInt(stroker.nextElement().toString());
                        final String groupName = stroker.nextElement().toString();
                        final String teacherName = stroker.nextElement().toString();
                        final int iGroupLang = Integer.parseInt(stroker.nextElement().toString());
                        tmpTButton.setText(groupName + " - " + teacherName);
                        tmpTButton.getLabel().setAlignment(Align.center);
                        tmpTButton.getLabel().setWrap(true);
                        // Añadir el funcionamiento de cada boton
                        tmpTButton.addListener(new InputListener() {
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                Group grupo = new Group(iGroupId, groupName, teacherName, iGroupLang);
                                selected_group = grupo;
                                g.setScreen(new ConfigGameScreen(g,userInfo,selected_group));
                                Gdx.app.log("probando grupo", grupo.getName());
                                // Llamada a nueva screen con la info del grupo

                                return true;
                            }
                        });
                        ScrollTable.add();
                        ScrollTable.add(tmpTButton).width(Gdx.graphics.getWidth() * 0.7f).height(Gdx.graphics.getHeight() * 0.15f);
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
                } else {
                    Gdx.app.log("conexion", "ningun resultado");
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
