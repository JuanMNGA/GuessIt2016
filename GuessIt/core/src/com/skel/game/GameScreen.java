package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by juanm on 24/02/2016.
 */
public class GameScreen implements Screen, Net.HttpResponseListener {
    private UserInfo userInfo;
    private Game g;
    private Stage stage;
    private Skin skin;

    private Group grupo;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    private int selectedLv;
    private ArrayList<Integer> categorias;
    private boolean inRate = false;
    private int numIntentos = 3;
    private boolean useHint = false;

    private ArrayList<Definition> game_definitions = new ArrayList<Definition>();

    private Table layoutTable = new Table();

    private GEngine engine = new GEngine();

    // Actores
    Label definitionLabel, answerLabel, hintLabel, tryLabel;
    TextField answerText;

    public void createStageActors(){
        // Window
        final Window rateWindow = new Window("Rate this definition", skin.get("default", Window.WindowStyle.class));
        rateWindow.setMovable(false);
        rateWindow.setFillParent(true);
        rateWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        rateWindow.getTitleLabel().setAlignment(Align.center);
        rateWindow.getTitleLabel().setWrap(true);
        rateWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        rateWindow.setVisible(false);
        // Add window actors
        rateWindow.pack();
        // Main Game Actors
        definitionLabel = new Label("",skin.get("default", Label.LabelStyle.class));
        definitionLabel.setWrap(true);

        layoutTable.add(definitionLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.3f).colspan(3);
        layoutTable.row();

        answerLabel = new Label("Answer:", skin.get("default", Label.LabelStyle.class));
        answerLabel.setAlignment(Align.center);
        answerLabel.setWrap(true);

        layoutTable.add(answerLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(3);
        layoutTable.row();

        answerText = new TextField("", skin.get("default", TextField.TextFieldStyle.class));
        answerText.setAlignment(Align.center);

        layoutTable.add(answerText).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(3);
        layoutTable.row();

        TextButton guessButton = new TextButton("Guess",skin.get("default", TextButton.TextButtonStyle.class));
        // Button stuff
        guessButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                //rateWindow.setVisible(true);
                if(engine.compare(answerText.getText())){
                    Gdx.app.log("comparacion","exito");
                    answerText.setColor(Color.CYAN);
                }else{
                    Gdx.app.log("comparacion","fallo");
                    answerText.setColor(Color.RED);
                    numIntentos--;
                    tryLabel.setText("Chances: " + String.valueOf(numIntentos));
                }
                return true;
            }
        });
        layoutTable.add(guessButton).width(Gdx.graphics.getWidth()*0.35f).height(Gdx.graphics.getHeight()*0.1f);
        layoutTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);

        TextButton hintButton = new TextButton("Hint", skin.get("default", TextButton.TextButtonStyle.class));
        // Button stuff
        hintButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                hintLabel.setText(engine.getHint());
                useHint = true;
                return true;
            }
        });
        layoutTable.add(hintButton).width(Gdx.graphics.getWidth()*0.35f).height(Gdx.graphics.getHeight()*0.1f);
        layoutTable.row();

        hintLabel = new Label("", skin.get("default", Label.LabelStyle.class));
        hintLabel.setWrap(true);

        layoutTable.add(hintLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f).colspan(3);
        layoutTable.row();

        tryLabel = new Label("Chances: ", skin.get("default", Label.LabelStyle.class));
        tryLabel.setWrap(true);
        tryLabel.setAlignment(Align.center);
        layoutTable.add(tryLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(3);
        layoutTable.row();

        layoutTable.setFillParent(true);

        stage.addActor(layoutTable);
        stage.addActor(rateWindow);
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();

        createStageActors();
    }

    public GameScreen(Game g, UserInfo UInfo, Group grupo, int selectedLv, ArrayList<Integer> categorias){
        this.g = g;
        userInfo = UInfo;
        this.selectedLv = selectedLv;
        this.categorias = categorias;
        this.grupo = grupo;
        getDefinitions();
        create();
    }

    public void getDefinitions(){
        StringBuilder cadena_categorias = new StringBuilder();
        for(int i=0;i<categorias.size();++i){
            cadena_categorias.append(categorias.get(i)+";");
        }
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("categories",cadena_categorias.toString());
        parameters.put("level",String.valueOf(selectedLv));
        parameters.put("id_aula",String.valueOf(grupo.getId()));
        parameters.put("test",String.valueOf(userInfo.getType()));
        String url = "http://localhost/prueba/getDefinitions.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,GameScreen.this);
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String Response = httpResponse.getResultAsString();
        Gdx.app.log("conexion",Response);
        if(inRate){

        }else{
            if(!Response.isEmpty()) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        StringTokenizer stroke = new StringTokenizer(Response, ";");
                        while (stroke.hasMoreElements()) {
                            final int defId = Integer.parseInt(stroke.nextToken());
                            final int defLv = Integer.parseInt(stroke.nextToken());
                            final String defWord = stroke.nextToken();
                            final String defArticle = stroke.nextElement().toString();
                            final String defPhrase = stroke.nextToken();
                            final String defHint = stroke.nextToken();
                            final int defIdCat = Integer.parseInt(stroke.nextToken());
                            final int defIdGro = Integer.parseInt(stroke.nextToken());

                            game_definitions.add(new Definition(defId, defLv, defWord, defArticle, defPhrase, defHint, defIdCat, defIdGro));
                        }
                        GEngine tmpEngine = new GEngine();
                        tmpEngine.setDefinitions(game_definitions);
                        engine = tmpEngine;
                        definitionLabel.setText(engine.getPhrase());
                        tryLabel.setText("Chances: " + String.valueOf(numIntentos));
                    }
                });
            }
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
