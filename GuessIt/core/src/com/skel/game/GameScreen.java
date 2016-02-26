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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    // Game information to send
    private Definition definitionToSend;
    private int acierto = 0;
    private int pista = 0;
    private int reporte = 0;
    private int puntuacion = 0;

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
        // Final Round Window
        final Window pointWindow = new Window("Round stats", skin.get("default", Window.WindowStyle.class));
        pointWindow.setMovable(false);
        pointWindow.setFillParent(true);
        pointWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        pointWindow.getTitleLabel().setAlignment(Align.center);
        pointWindow.getTitleLabel().setWrap(true);
        pointWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        pointWindow.setVisible(false);

        // Add window actors
        // Label
        final Label finalPoints = new Label("", skin.get("point", Label.LabelStyle.class));
        finalPoints.setAlignment(Align.center);
        finalPoints.setWrap(true);
        // Create actors
        // Labels
        final Label numberStarsLabel = new Label("0 Stars",skin.get("default", Label.LabelStyle.class));
        numberStarsLabel.setWrap(true);
        numberStarsLabel.setAlignment(Align.center);
        Label orLabel = new Label("OR", skin.get("default", Label.LabelStyle.class));
        orLabel.setAlignment(Align.center);
        orLabel.setWrap(true);
        // Buttons
        TextButton oneStar = new TextButton("1",skin.get("point", TextButton.TextButtonStyle.class));
        TextButton twoStar = new TextButton("2",skin.get("point", TextButton.TextButtonStyle.class));
        TextButton threeStar = new TextButton("3",skin.get("point", TextButton.TextButtonStyle.class));
        TextButton reportButton = new TextButton("",skin.get("report", TextButton.TextButtonStyle.class));
        oneStar.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                numberStarsLabel.setText("1 Star");
                puntuacion = 1;
                reporte = 0;
                return true;
            }
        });
        twoStar.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                numberStarsLabel.setText("2 Stars");
                puntuacion = 2;
                reporte = 0;
                return true;
            }
        });
        threeStar.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                numberStarsLabel.setText("3 Stars");
                puntuacion = 3;
                reporte = 0;
                return true;
            }
        });
        reportButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                numberStarsLabel.setText("Reported");
                reporte = 1;
                puntuacion = 0;
                return true;
            }
        });
        TextButton sendButton = new TextButton("Send!",skin.get("default", TextButton.TextButtonStyle.class));
        sendButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.log("puntuacion","enviada");
                rateWindow.setVisible(false);
                sendRate();
                numIntentos = 3;
                tryLabel.setText("Chances: " + String.valueOf(numIntentos));
                if(engine.endRound()){
                    // Change to final rate screen
                    Gdx.app.log("cambio","a pantalla de puntuacion final");
                }
                return true;
            }
        });
        rateWindow.add(oneStar).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add(twoStar).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add(threeStar).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.row();
        rateWindow.add(numberStarsLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(5);
        rateWindow.row();
        rateWindow.add(orLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f).colspan(5);
        rateWindow.row();
        rateWindow.add().width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add(reportButton).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add().width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.row();
        rateWindow.add(sendButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f).colspan(5);
        rateWindow.row();
        rateWindow.pack();

        // TextButtons
        TextButton newRound = new TextButton("New Round!", skin.get("default", TextButton.TextButtonStyle.class));

        newRound.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new ConfigGameScreen(g,userInfo,grupo));
                return true;
            }
        });

        TextButton backMenu = new TextButton("Back to menu", skin.get("default", TextButton.TextButtonStyle.class));
        backMenu.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        pointWindow.add(finalPoints).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.5f);
        pointWindow.row();
        pointWindow.add(newRound).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        pointWindow.row();
        pointWindow.add(backMenu).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        pointWindow.row();

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
                    acierto = 1;
                    rateWindow.setVisible(true);
                    Gdx.app.log("abrir ventana","rate abierta");
                    definitionToSend = engine.getDefinition();
                    // Nueva palabra
                    engine.nextDefinition();
                    if(!engine.endRound()) {
                        definitionLabel.setText(engine.getPhrase());
                        hintLabel.setText(engine.getHint());
                        hintLabel.setVisible(false);
                        answerText.setText("");
                        answerText.setColor(Color.LIGHT_GRAY);
                    }else{
                        finalPoints.setText(engine.getResultPoints());
                        pointWindow.setVisible(true);
                    }
                }else{
                    Gdx.app.log("comparacion","fallo");
                    answerText.setColor(Color.RED);
                    numIntentos--;
                    tryLabel.setText("Chances: " + String.valueOf(numIntentos));
                    if(numIntentos == 0){
                        acierto = 0;
                        rateWindow.setVisible(true);
                        definitionToSend = engine.getDefinition();
                        // Nueva palabra
                        engine.nextDefinition();
                        if(!engine.endRound()) {
                            definitionLabel.setText(engine.getPhrase());
                            hintLabel.setText(engine.getHint());
                            hintLabel.setVisible(false);
                            answerText.setText("");
                            answerText.setColor(Color.LIGHT_GRAY);
                        }else{
                            finalPoints.setText(engine.getResultPoints());
                            pointWindow.setVisible(true);
                        }
                    }
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
                pista = 1;
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
        stage.addActor(pointWindow);
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
        String url = Utils.getUrl()+"getDefinitions.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,GameScreen.this);
    }

    public void sendRate(){
        inRate = true;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        parameters.put("id_palabra",String.valueOf(definitionToSend.getId()));
        parameters.put("puntuacion", String.valueOf(puntuacion));
        parameters.put("acierto", String.valueOf(acierto));
        parameters.put("pista", String.valueOf(pista));
        parameters.put("intentos", String.valueOf(3-numIntentos));
        parameters.put("reporte", String.valueOf(reporte));
        parameters.put("fecha",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(TimeUtils.millis())));
        String url = Utils.getUrl()+"sendRate.php?";
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
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    acierto = 0;
                    pista = 0;
                    reporte = 0;
                    puntuacion = 0;
                    inRate = false;
                }
            });
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
