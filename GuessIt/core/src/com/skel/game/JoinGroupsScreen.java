package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Group;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by juanm on 24/02/2016.
 */
public class JoinGroupsScreen implements Screen, Net.HttpResponseListener {
    private UserInfo userInfo;
    private Game g;
    private Stage stage;
    private Skin skin;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    private Table ScrollTable = new Table();

    private boolean groupIsSelected = false;

    private Group selected_group;

    public JoinGroupsScreen(Game g, UserInfo UInfo){
        this.g = g;
        userInfo = UInfo;

        create();
    }

    public void createStageActors(){

    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();
        //Llamar a connection y que devolviese el resultado de los grupos a los que el alumno ha sido validado
        createStageActors();

        //HashMap<String, String> parameters = new HashMap<String, String>();
        //parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        String url = Utils.getUrl()+"getGroups.php?";
        //solicitud_variables = "&nombre=suscribete&puntaje=222";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        //httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,JoinGroupsScreen.this);
    }

    public void sendGroupInvite(Group grupo){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        parameters.put("id_aula",String.valueOf(grupo.getId()));
        String url = Utils.getUrl()+"inviteGroup.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,JoinGroupsScreen.this);
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String ResponseBefore = httpResponse.getResultAsString();
        final String Response = new String(ResponseBefore.getBytes(), Charset.forName("UTF-8"));
        if(groupIsSelected){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    groupIsSelected = false;
                }
            });
        }else {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if (!Response.isEmpty()) {
                        StringTokenizer stroker = new StringTokenizer(Response, ";");
                        while (stroker.hasMoreElements()) {
                            final TextButton tmpTButton = new TextButton("", skin.get("group", TextButton.TextButtonStyle.class));
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
                                    Group grupo = new Group(iGroupId, groupName, teacherName, iGroupLang, "");
                                    selected_group = grupo;
                                    groupIsSelected = true;
                                    tmpTButton.setVisible(false);
                                    sendGroupInvite(grupo);
                                    return true;
                                }
                            });
                            ScrollTable.add(tmpTButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.15f);
                            ScrollTable.row();
                        }
                        Gdx.app.log("conexion", "tabla creada");

                        // Boton cancelar para volver a la ventana anterior
                        TextButton CancelButton = new TextButton("Back", skin.get("default", TextButton.TextButtonStyle.class));
                        CancelButton.addListener(new InputListener() {
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                g.setScreen(new UserGroupsScreen(g, userInfo));
                                return true;
                            }
                        });
                        ScrollTable.add(CancelButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f);
                        ScrollTable.row();

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
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

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
}
