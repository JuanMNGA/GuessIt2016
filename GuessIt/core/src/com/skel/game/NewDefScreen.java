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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Group;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by juanm on 03/03/2016.
 */
public class NewDefScreen implements Screen, Net.HttpResponseListener {

    Utils utilidades = new Utils();

    private UserInfo userInfo;
    private MainGame g;
    private Stage stage;
    private Skin skin;

    private Group grupo;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    private Table scrollTable;

    TextButton levelOne, levelTwo, levelThree, levelFour, sendButton, backButton;
    TextField word, article, sentence, hint;

    ButtonGroup categoriesGroup = new ButtonGroup();

    private ArrayList<Integer> categories = new ArrayList<Integer>();
    private int actualLevel = 0;
    private boolean sendingDefinition;
    private int actualCategory = 0;

    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void generateCategories(){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_aula",String.valueOf(grupo.getId()));
        String url = utilidades.getUrl()+"getCategories.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,NewDefScreen.this);
    }

    public void sendDefinition(){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("nivel", String.valueOf(actualLevel));
        parameters.put("palabra", new String(word.getText().getBytes(), Charset.forName("UTF-8")));
        parameters.put("articulo", new String(article.getText().getBytes(), Charset.forName("UTF-8")));
        parameters.put("frase", new String(sentence.getText().getBytes(), Charset.forName("UTF-8")));
        parameters.put("pista", new String(hint.getText().getBytes(), Charset.forName("UTF-8")));
        parameters.put("id_categoria", String.valueOf(actualCategory));
        parameters.put("id_usuario", String.valueOf(userInfo.getId()));
        parameters.put("id_aula",String.valueOf(grupo.getId()));
        parameters.put("fecha", dFormat.format(new Date(TimeUtils.millis())));
        String url = utilidades.getUrl()+"sendDefinition.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,NewDefScreen.this);
    }

    public void createStageActors(){
        scrollTable = new Table();

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

        scrollTable.add(levelOne).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add(levelTwo).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add(levelThree).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.add(levelFour).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        scrollTable.row();

        word = new TextField("", skin.get("default", TextField.TextFieldStyle.class));
        word.setMessageText("Enter the word");
        article = new TextField("", skin.get("default", TextField.TextFieldStyle.class));
        article.setMessageText("Enter the article");
        sentence = new TextField("", skin.get("default", TextField.TextFieldStyle.class));
        sentence.setMessageText("Enter the sentence");
        sentence.setMaxLength(250);
        hint = new TextField("", skin.get("default", TextField.TextFieldStyle.class));
        hint.setMessageText("Enter the hint");
        hint.setMaxLength(250);

        scrollTable.add(word).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
        scrollTable.row();
        scrollTable.add(article).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
        scrollTable.row();
        scrollTable.add(sentence).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
        scrollTable.row();
        scrollTable.add(hint).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
        scrollTable.row();

        generateCategories();
    }

    public void create(){
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = utilidades.createBasicSkin();

        createStageActors();
    }

    public NewDefScreen(MainGame g, UserInfo UInfo, Group grupo){
        this.g = g;
        userInfo = UInfo;
        this.grupo = grupo;
        sendingDefinition = false;
        create();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String ResponseBefore = httpResponse.getResultAsString();
        final String Response = new String(ResponseBefore.getBytes(), Charset.forName("UTF-8"));
        if(sendingDefinition){
            userInfo.addedNewDef();
            sendingDefinition = false;
        }
        else {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    StringTokenizer stroke = new StringTokenizer(Response, ";");
                    while (stroke.hasMoreElements()) {
                        final Integer idCat = new Integer(stroke.nextToken());
                        final CheckBox tmpCheck = new CheckBox(stroke.nextElement().toString(), skin.get("default", CheckBox.CheckBoxStyle.class));
                        tmpCheck.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                actualCategory = idCat;
                                Gdx.app.log("categoria seleccionada", String.valueOf(actualCategory));
                            }
                        });
                        categoriesGroup.add(tmpCheck);
                        scrollTable.add(tmpCheck).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
                        scrollTable.row();
                    }

                    categoriesGroup.setMaxCheckCount(1);
                    categoriesGroup.setMinCheckCount(0);
                    categoriesGroup.setUncheckLast(true);

                    sendButton = new TextButton("Send!", skin.get("default", TextButton.TextButtonStyle.class));
                    sendButton.addListener(new InputListener(){
                       public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                           sendingDefinition = true;
                           sendDefinition();
                           g.setScreen(new MenuGameScreen(g,userInfo,grupo));
                           Gdx.input.setOnscreenKeyboardVisible(false);
                           dispose();
                           return true;
                       }
                    });

                    scrollTable.add(sendButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);
                    scrollTable.row();

                    backButton = new TextButton("Back", skin.get("default", TextButton.TextButtonStyle.class));
                    backButton.addListener(new InputListener(){
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                            g.setScreen(new MenuGameScreen(g, userInfo, grupo));
                            Gdx.input.setOnscreenKeyboardVisible(false);
                            dispose();
                            return true;
                        }
                    });

                    scrollTable.add(backButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f).colspan(7);

                    final Table table = new Table();
                    table.setFillParent(true);
                    table.add(scrollTable).fill().expand();
                    stage.addActor(table);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 0.8f, 1);
        stage.act(delta);
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
        stage.dispose();
        skin.dispose();
    }
}
