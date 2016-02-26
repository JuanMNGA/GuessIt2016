package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Group;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by juanm on 24/02/2016.
 */
public class ConfigGameScreen implements Screen, Net.HttpResponseListener {
    private UserInfo userInfo;
    private Game g;
    private Stage stage;
    private Skin skin;

    private Group grupo;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    TextButton levelOne, levelTwo, levelThree, levelFour;

    private ArrayList<Integer> categories = new ArrayList<Integer>();
    private int actualLevel = 0;

    private Table scrollTable = new Table();

    public ConfigGameScreen(Game g, UserInfo UInfo, Group grupo){
        this.g = g;
        userInfo = UInfo;
        this.grupo = grupo;
        // Hacer la transformacion del idioma de la interfaz dependiendo de grupo.getLanguage();
        create();
    }

    private void getCategories(){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_aula",String.valueOf(grupo.getId()));
        String url = Utils.getUrl()+"getCategories.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,ConfigGameScreen.this);
    }

    public void createStageActors(){
        // Label Select Level
        Label selectLevelLabel = new Label("Select level:",skin.get("default",Label.LabelStyle.class));
        selectLevelLabel.setAlignment(Align.center);
        selectLevelLabel.setWrap(true);

        // Level buttons
        levelOne = new TextButton("1",skin.get("group", TextButton.TextButtonStyle.class));
        levelTwo = new TextButton("2",skin.get("group", TextButton.TextButtonStyle.class));
        levelThree = new TextButton("3",skin.get("group", TextButton.TextButtonStyle.class));
        levelFour = new TextButton("4",skin.get("group", TextButton.TextButtonStyle.class));

        levelOne.getLabel().setAlignment(Align.center);
        levelTwo.getLabel().setAlignment(Align.center);
        levelThree.getLabel().setAlignment(Align.center);
        levelFour.getLabel().setAlignment(Align.center);

        levelOne.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                actualLevel = 1;
                Gdx.app.log("cambio level",String.valueOf(actualLevel));
                return true;
            }
        });

        levelTwo.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                actualLevel = 2;
                Gdx.app.log("cambio level",String.valueOf(actualLevel));
                return true;
            }
        });

        levelThree.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                actualLevel = 3;
                Gdx.app.log("cambio level",String.valueOf(actualLevel));
                return true;
            }
        });

        levelFour.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                actualLevel = 4;
                Gdx.app.log("cambio level",String.valueOf(actualLevel));
                return true;
            }
        });

        // Button Level group

        ButtonGroup levelButtonGroup = new ButtonGroup(levelOne,levelTwo,levelThree,levelFour);
        levelButtonGroup.setMaxCheckCount(1);
        levelButtonGroup.setMinCheckCount(0);
        levelButtonGroup.setUncheckLast(true);

        // Label Select Category
        Label selectCategoryLabel = new Label("Select category:",skin.get("default",Label.LabelStyle.class));
        selectCategoryLabel.setAlignment(Align.center);
        selectCategoryLabel.setWrap(true);

        // Add to table
        scrollTable.add(selectLevelLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(7);
        scrollTable.row();
        scrollTable.add(levelOne).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add(levelTwo).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add(levelThree).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add(levelFour).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.row();
        scrollTable.add(selectCategoryLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(7);
        scrollTable.row();

        getCategories();
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();

        createStageActors();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String Response = httpResponse.getResultAsString();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                StringTokenizer stroke = new StringTokenizer(Response,";");
                while(stroke.hasMoreElements()){
                    final Integer idCat = new Integer(stroke.nextToken());
                    final CheckBox tmpCheck = new CheckBox(stroke.nextElement().toString(),skin.get("default", CheckBox.CheckBoxStyle.class));
                    tmpCheck.addListener(new ChangeListener() {
                         @Override
                         public void changed(ChangeEvent event, Actor actor) {
                             if(categories.contains(idCat)){
                                 categories.remove(idCat);
                                 Gdx.app.log("creacion arraylist","borrado con exito");
                             }else{
                                 categories.add(idCat);
                                 Gdx.app.log("creacion arraylist","incluido con exito");
                             }
                         }
                     });
                    scrollTable.add(tmpCheck).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
                    scrollTable.row();
                }
                TextButton playButton = new TextButton("Play!", skin.get("default", TextButton.TextButtonStyle.class));

                playButton.addListener(new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        if((levelOne.isChecked() || levelTwo.isChecked() || levelThree.isChecked() || levelFour.isChecked()) && !categories.isEmpty()){
                            Gdx.app.log("configuracion","todo seleccionado");
                            g.setScreen(new GameScreen(g,userInfo,grupo,actualLevel,categories));
                        }
                        return true;
                    }
                });

                TextButton backButton = new TextButton("Back", skin.get("default", TextButton.TextButtonStyle.class));

                backButton.addListener(new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        g.setScreen(new UserGroupsScreen(g,userInfo));
                        return true;
                    }
                });
                scrollTable.add().height(Gdx.graphics.getHeight() * 0.2f);
                scrollTable.row();
                scrollTable.add(playButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
                scrollTable.row();
                scrollTable.add(backButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
                scrollTable.row();

                final Table table = new Table();
                table.setFillParent(true);
                table.add(scrollTable).fill().expand();
                stage.addActor(table);
            }
        });
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
