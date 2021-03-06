package com.skel.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.*;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by juanm on 24/02/2016.
 */
public class GameScreen implements Screen, Net.HttpResponseListener {

    Utils utilidades = new Utils();

    private UserInfo userInfo;
    private MainGame g;
    private Stage stage;
    private Skin skin;

    private Skin sec_Skin = utilidades.createResultSkin();

    private Group grupo;

    private Strings_I18N locale;

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
    private int puntuacion = 1;
    private String defToSave;

    // Actores
    Label definitionLabel, answerLabel, hintLabel, tryLabel, articleLabel, questionLabel;
    TextField answerText;

    private CheckBox questOne, questTwo;

    private String questionOne, questionTwo, reportReason = new String();

    private void setQuestions(){
        int previousResult = new Random().nextInt(locale.getQuestions().size());
        questionOne = locale.getQuestions().get(previousResult);
    }

    public void createStageActors(){
        // Window
        final Window rateWindow = new Window(locale.rate(), skin.get("default", Window.WindowStyle.class));
        rateWindow.setMovable(false);
        rateWindow.setFillParent(true);
        rateWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        rateWindow.getTitleLabel().setAlignment(Align.center);
        rateWindow.getTitleLabel().setWrap(true);
        rateWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        rateWindow.setVisible(false);
        // Add window actors
        // Final Round Window
        final Window pointWindow = new Window(locale.round(), skin.get("default", Window.WindowStyle.class));
        pointWindow.setMovable(false);
        pointWindow.setFillParent(true);
        pointWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        pointWindow.getTitleLabel().setAlignment(Align.center);
        pointWindow.getTitleLabel().setWrap(true);
        pointWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        pointWindow.setVisible(false);

        //Add reports reason window
        final Window reportReasonWindow = new Window("Reason for report", skin.get("default", Window.WindowStyle.class));
        reportReasonWindow.setMovable(false);
        reportReasonWindow.setFillParent(true);
        reportReasonWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        reportReasonWindow.getTitleLabel().setAlignment(Align.center);
        reportReasonWindow.getTitleLabel().setWrap(true);
        reportReasonWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        reportReasonWindow.setVisible(false);

        final Window chancesWindow = new Window("Failure", skin.get("default", Window.WindowStyle.class));
        chancesWindow.setMovable(false);
        chancesWindow.setFillParent(true);
        chancesWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        chancesWindow.getTitleLabel().setAlignment(Align.center);
        chancesWindow.getTitleLabel().setWrap(true);
        chancesWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        chancesWindow.setVisible(false);

        final Window hintWindow = new Window(locale.hint(), skin.get("default", Window.WindowStyle.class));
        hintWindow.setMovable(false);
        hintWindow.setFillParent(true);
        hintWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        hintWindow.getTitleLabel().setAlignment(Align.center);
        hintWindow.getTitleLabel().setWrap(true);
        hintWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        hintWindow.setVisible(false);

        final Window savedWindow = new Window("", skin.get("default", Window.WindowStyle.class));
        savedWindow.setMovable(false);
        savedWindow.setFillParent(true);
        savedWindow.padTop(Gdx.graphics.getHeight()*0.05f);
        savedWindow.getTitleLabel().setAlignment(Align.center);
        savedWindow.getTitleLabel().setWrap(true);
        savedWindow.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        savedWindow.setVisible(false);

        // Add window actors
        // Label
        final Label finalPoints = new Label("", skin.get("point", Label.LabelStyle.class));
        finalPoints.setAlignment(Align.center);
        finalPoints.setWrap(true);
        // Create actors
        // Labels

        final Label resultDefLabel = new Label("", sec_Skin.get("result", Label.LabelStyle.class));
        resultDefLabel.setWrap(true);

        setQuestions();

        questionLabel = new Label(questionOne, skin.get("question", Label.LabelStyle.class));
        questionLabel.setWrap(true);

        questOne = new CheckBox(locale.yes(), skin.get("questions", CheckBox.CheckBoxStyle.class));
        questOne.getLabel().setAlignment(Align.center);
        questOne.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(questOne.isChecked()) {
                    reporte = 0;
                    reportReason = "";
                    puntuacion = 3;
                    Gdx.app.log("puntuacion", String.valueOf(puntuacion));
                }
            }
        });
        questTwo = new CheckBox(locale.no(), skin.get("questions", CheckBox.CheckBoxStyle.class));
        questTwo.getLabel().setAlignment(Align.center);
        questTwo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(questTwo.isChecked()) {
                    reporte = 0;
                    reportReason = "";
                    puntuacion = 1;
                    Gdx.app.log("puntuacion", String.valueOf(puntuacion));
                }
            }
        });

        ButtonGroup<CheckBox> questionGroup = new ButtonGroup<CheckBox>(questOne, questTwo);
        questionGroup.setMaxCheckCount(1);
        questionGroup.setMinCheckCount(0);
        questionGroup.setUncheckLast(true);

        // Buttons
        ImageTextButton reportButton = new ImageTextButton("Report",skin.get("report", ImageTextButton.ImageTextButtonStyle.class));
        reportButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reporte = 1;
                questOne.setChecked(false);
                questTwo.setChecked(false);
                puntuacion = 0;
                Gdx.app.log("puntuacion", String.valueOf(puntuacion));
                rateWindow.setVisible(false);
                reportReasonWindow.setVisible(true);
            }
        });
        ImageTextButton sendButton = new ImageTextButton(locale.send(),skin.get("send", ImageTextButton.ImageTextButtonStyle.class));
        sendButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(questOne.isChecked() || questTwo.isChecked() || !reportReason.contentEquals("")) {
                    Gdx.app.log("puntuacion", "enviada");
                    rateWindow.setVisible(false);
                    if (acierto == 1)
                        userInfo.addDefPlayed(String.valueOf(grupo.getId()));
                    sendRate();
                    numIntentos = 3;
                    tryLabel.setText(locale.chances() + " " + String.valueOf(numIntentos));
                    questOne.setChecked(false);
                    questTwo.setChecked(false);
                    reportReason = "";
                    // Añadimos un punto al marcador oculto para permitir introducir nuevas definiciones.
                    if (engine.endRound()) {
                        // Change to final rate screen
                        Gdx.app.log("cambio", "a pantalla de puntuacion final");
                    }
                    answerText.setText("");
                }
            }
        });
        ImageTextButton saveButton = new ImageTextButton(locale.save(),skin.get("save", ImageTextButton.ImageTextButtonStyle.class));
        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Preferences tmpPrefs = Gdx.app.getPreferences("notebook");
                tmpPrefs.putString(defToSave,"");
                tmpPrefs.flush();
                rateWindow.setVisible(false);
                savedWindow.setVisible(true);
            }
        });
        rateWindow.add(resultDefLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.3f).colspan(2);
        rateWindow.row();
        rateWindow.add(questionLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f).colspan(2);
        rateWindow.row();
        rateWindow.add(questOne).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.add(questTwo).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.2f);
        rateWindow.row();
        // Preguntas label aqui
        rateWindow.add(reportButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(2);
        rateWindow.row();
        rateWindow.add(sendButton).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.15f);
        rateWindow.add(saveButton).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.15f);
        rateWindow.row();
        rateWindow.pack();

        // TextButtons
        TextButton newRound = new TextButton(locale.newRound(), skin.get("default", TextButton.TextButtonStyle.class));

        newRound.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                g.setScreen(new ConfigGameScreen(g,userInfo,grupo, skin));
                dispose();
            }
        });

        final TextButton backMenu = new TextButton(locale.backMenu(), skin.get("default", TextButton.TextButtonStyle.class));
        backMenu.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                g.setScreen(new MenuGameScreen(g,userInfo, grupo, skin));
                dispose();
            }
        });

        pointWindow.add(finalPoints).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.5f);
        pointWindow.row();
        pointWindow.add(newRound).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        pointWindow.row();
        pointWindow.add(backMenu).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        pointWindow.row();
        pointWindow.pack();

        // reportReasonWindow Actors
        TextButton wContent = new TextButton("Wrong content", skin.get("default", TextButton.TextButtonStyle.class));
        TextButton offensive = new TextButton("Offensive", skin.get("default", TextButton.TextButtonStyle.class));
        TextButton lMistakes = new TextButton("Linguistic mistakes", skin.get("default", TextButton.TextButtonStyle.class));
        TextButton difficult = new TextButton("Difficult", skin.get("default", TextButton.TextButtonStyle.class));
        ImageTextButton backReport = new ImageTextButton("Back", skin.get("back", ImageTextButton.ImageTextButtonStyle.class));
        wContent.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reportReason = "Wrong content";
                reportReasonWindow.setVisible(false);
                rateWindow.setVisible(true);
            }
        });
        offensive.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reportReason = "Offensive";
                reportReasonWindow.setVisible(false);
                rateWindow.setVisible(true);
            }
        });
        lMistakes.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reportReason = "Linguistic mistakes";
                reportReasonWindow.setVisible(false);
                rateWindow.setVisible(true);
            }
        });
        difficult.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reportReason = "Difficult";
                reportReasonWindow.setVisible(false);
                rateWindow.setVisible(true);
            }
        });

        backReport.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reportReasonWindow.setVisible(false);
                rateWindow.setVisible(true);
            }
        });

        reportReasonWindow.add(wContent).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        reportReasonWindow.row();
        reportReasonWindow.add(offensive).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        reportReasonWindow.row();
        reportReasonWindow.add(lMistakes).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        reportReasonWindow.row();
        reportReasonWindow.add(difficult).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        reportReasonWindow.row();
        reportReasonWindow.add().height(Gdx.graphics.getHeight()*0.1f);
        reportReasonWindow.row();
        reportReasonWindow.add(backReport).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        reportReasonWindow.row();
        reportReasonWindow.pack();

        Label saved = new Label("Word Saved!", skin.get("default", Label.LabelStyle.class));
        TextButton okButton = new TextButton("Ok", skin.get("default", TextButton.TextButtonStyle.class));
        okButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                savedWindow.setVisible(false);
                rateWindow.setVisible(true);
            }
        });

        savedWindow.add(saved).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        savedWindow.row();
        savedWindow.add(okButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        savedWindow.row();
        savedWindow.pack();

        // Main Game Actors
        definitionLabel = new Label("",skin.get("small", Label.LabelStyle.class));
        definitionLabel.setWrap(true);

        layoutTable.add(definitionLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.3f).colspan(3);
        layoutTable.row();

        answerLabel = new Label(locale.answer(), skin.get("default", Label.LabelStyle.class));
        answerLabel.setAlignment(Align.center);
        answerLabel.setWrap(true);

        layoutTable.add(answerLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(3);
        layoutTable.row();

        articleLabel = new Label("", sec_Skin.get("article", Label.LabelStyle.class));
        articleLabel.setAlignment(Align.center);

        answerText = new TextField("", skin.get("default", TextField.TextFieldStyle.class));
        answerText.setAlignment(Align.center);

        layoutTable.add(articleLabel).width(Gdx.graphics.getWidth()*0.3f).height(Gdx.graphics.getHeight()*0.1f);
        layoutTable.add(answerText).width(Gdx.graphics.getWidth()*0.45f).height(Gdx.graphics.getHeight()*0.1f).colspan(2);
        layoutTable.row();

        TextButton guessButton = new TextButton(locale.guess(),skin.get("default", TextButton.TextButtonStyle.class));
        // Button stuff
        guessButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //rateWindow.setVisible(true);
                if(engine.compare(answerText.getText())){
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    Gdx.app.log("comparacion","exito");
                    answerText.setColor(Color.CYAN);
                    acierto = 1;
                    rateWindow.setVisible(true);
                    Gdx.app.log("abrir ventana","rate abierta");
                    resultDefLabel.setText(engine.getPassPhrase());
                    definitionToSend = engine.getDefinition();
                    defToSave = definitionToSend.getPalabra();
                    // Nueva palabra
                    engine.nextDefinition();
                    if(!engine.endRound()) {
                        definitionLabel.setText(engine.getPhrase());
                        hintLabel.setText(engine.getHint());
                        hintLabel.setVisible(false);
                        String color = "";
                        if(engine.getArticle().equals("das") || engine.getArticle().equals(" das") || engine.getArticle().equals(" das ") || engine.getArticle().equals("das ")){
                            color = "[BLUE]";
                        }else{
                            if(engine.getArticle().equals("der") || engine.getArticle().equals(" der") || engine.getArticle().equals(" der ") || engine.getArticle().equals("der ")){
                                color = "[RED]";
                            }else{
                                if(engine.getArticle().equals("die") || engine.getArticle().equals(" die") || engine.getArticle().equals("die ")){
                                    color = "[OLIVE]";
                                }else{
                                    if(engine.getArticle().equals("der/ die Pl.") || engine.getArticle().equals(" der/ die Pl.") || engine.getArticle().equals("der/ die Pl. ") || engine.getArticle().equals("die Pl.") || engine.getArticle().equals("die PL.") || engine.getArticle().equals("die pl.") || engine.getArticle().equals("die pL.")){
                                        color = "[BLACK]";
                                    }
                                }
                            }
                        }
                        articleLabel.setText(color + engine.getArticle());
                        answerText.setText("");
                        answerText.setColor(Color.WHITE);
                        setQuestions();
                        questionLabel.setText(questionOne);
                    }else{
                        finalPoints.setText(engine.getResultPoints());
                        pointWindow.setVisible(true);
                    }
                }else{
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    Gdx.app.log("comparacion","fallo");
                    answerText.setColor(Color.RED);
                    numIntentos--;
                    tryLabel.setText(locale.chances() + " " + String.valueOf(numIntentos));
                    chancesWindow.setVisible(true);
                    if(numIntentos == 0){
                        Gdx.input.setOnscreenKeyboardVisible(false);
                        acierto = 0;
                        rateWindow.setVisible(true);
                        resultDefLabel.setText(engine.getWrongPhrase());
                        definitionToSend = engine.getDefinition();
                        defToSave = definitionToSend.getPalabra();
                        // Nueva palabra
                        engine.nextDefinition();
                        if(!engine.endRound()) {
                            definitionLabel.setText(engine.getPhrase());
                            hintLabel.setText(engine.getHint());
                            hintLabel.setVisible(false);
                            String color = "";
                            if(engine.getArticle().equals("das") || engine.getArticle().equals(" das") || engine.getArticle().equals(" das ") || engine.getArticle().equals("das ")){
                                color = "[BLUE]";
                            }else{
                                if(engine.getArticle().equals("der") || engine.getArticle().equals(" der") || engine.getArticle().equals(" der ") || engine.getArticle().equals("der ")){
                                    color = "[RED]";
                                }else{
                                    if(engine.getArticle().equals("die") || engine.getArticle().equals(" die") || engine.getArticle().equals("die ")){
                                        color = "[OLIVE]";
                                    }else{
                                        if(engine.getArticle().equals("der/ die Pl.") || engine.getArticle().equals(" der/ die Pl.") || engine.getArticle().equals("der/ die Pl. ") || engine.getArticle().equals("die Pl.") || engine.getArticle().equals("die PL.") || engine.getArticle().equals("die pl.") || engine.getArticle().equals("die pL.")){
                                            color = "[BLACK]";
                                        }
                                    }
                                }
                            }
                            articleLabel.setText(color + engine.getArticle());
                            answerText.setText("");
                            answerText.setColor(Color.WHITE);
                            setQuestions();
                            questionLabel.setText(questionOne);
                        }else{
                            finalPoints.setText(engine.getResultPoints());
                            pointWindow.setVisible(true);
                        }
                    }
                }
            }
        });
        layoutTable.add(guessButton).width(Gdx.graphics.getWidth()*0.35f).height(Gdx.graphics.getHeight()*0.1f);
        layoutTable.add().width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);

        TextButton hintButton = new TextButton(locale.hint(), skin.get("default", TextButton.TextButtonStyle.class));
        // Button stuff
        hintButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(numIntentos < 3) {
                    hintWindow.setVisible(true);
                    hintLabel.setText(engine.getHint());
                    hintLabel.setVisible(true);
                    pista = 1;
                }
            }
        });
        layoutTable.add(hintButton).width(Gdx.graphics.getWidth()*0.35f).height(Gdx.graphics.getHeight()*0.1f);
        layoutTable.row();

        hintLabel = new Label("", skin.get("small", Label.LabelStyle.class));
        hintLabel.setWrap(true);

        hintWindow.add(hintLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f).colspan(3);
        hintWindow.row();

        TextButton hintOk = new TextButton("Ok", skin.get("default", TextButton.TextButtonStyle.class));
        hintOk.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hintWindow.setVisible(false);
            }
        });

        hintWindow.add(hintOk).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        hintWindow.row();
        hintWindow.pack();

        tryLabel = new Label(locale.chances() + " ", skin.get("default", Label.LabelStyle.class));
        tryLabel.setWrap(true);
        tryLabel.setAlignment(Align.center);
        //layoutTable.add(tryLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(3);
        //layoutTable.row();
        chancesWindow.add(tryLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        chancesWindow.row();

        TextButton chancesOk = new TextButton("Ok", skin.get("default", TextButton.TextButtonStyle.class));
        chancesOk.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                chancesWindow.setVisible(false);
                answerText.setColor(Color.WHITE);
            }
        });

        chancesWindow.add(chancesOk).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        chancesWindow.row();
        chancesWindow.pack();

        layoutTable.setFillParent(true);
        layoutTable.top();

        stage.addActor(layoutTable);
        stage.addActor(rateWindow);
        stage.addActor(pointWindow);
        stage.addActor(reportReasonWindow);
        stage.addActor(chancesWindow);
        stage.addActor(hintWindow);
        stage.addActor(savedWindow);
    }

    public void create(){
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight())){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    g.setScreen(new ConfigGameScreen(g, userInfo, grupo, skin));
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    dispose();
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        createStageActors();
    }

    public GameScreen(MainGame g, UserInfo UInfo, Group grupo, int selectedLv, ArrayList<Integer> categorias, Skin skin){
        this.g = g;
        this.skin = skin;
        userInfo = UInfo;
        this.selectedLv = selectedLv;
        this.categorias = categorias;
        this.grupo = grupo;
        locale = new Strings_I18N(grupo.getLanguageName());

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
        parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        parameters.put("test",String.valueOf(userInfo.getType()));
        String url = utilidades.getUrl()+"getDefinitions.php?";
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
        parameters.put("motivo", new String(reportReason.getBytes(), Charset.forName("UTF-8")));
        parameters.put("fecha",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(TimeUtils.millis())));
        String url = utilidades.getUrl()+"sendRate.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,GameScreen.this);
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String ResponseBefore = httpResponse.getResultAsString();
        final String Response = new String(ResponseBefore.getBytes(), Charset.forName("UTF-8"));
        Gdx.app.log("conexion",Response);
        if(inRate){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    acierto = 0;
                    pista = 0;
                    reporte = 0;
                    puntuacion = 1;
                    inRate = false;
                }
            });
        }else{
            if(!Response.isEmpty()) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        StringTokenizer stroke = new StringTokenizer(Response, "|");
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
                        String color = "";
                        if(engine.getArticle().equals("das") || engine.getArticle().equals(" das") || engine.getArticle().equals(" das ") || engine.getArticle().equals("das ")){
                            color = "[BLUE]";
                        }else{
                            if(engine.getArticle().equals("der") || engine.getArticle().equals(" der") || engine.getArticle().equals(" der ") || engine.getArticle().equals("der ")){
                                color = "[RED]";
                            }else{
                                if(engine.getArticle().equals("die") || engine.getArticle().equals(" die") || engine.getArticle().equals("die ")){
                                    color = "[OLIVE]";
                                }else{
                                    if(engine.getArticle().equals("der/ die Pl.") || engine.getArticle().equals(" der/ die Pl.") || engine.getArticle().equals("der/ die Pl. ") || engine.getArticle().equals("die Pl.") || engine.getArticle().equals("die PL.") || engine.getArticle().equals("die pl.") || engine.getArticle().equals("die pL.")){
                                        color = "[BLACK]";
                                    }
                                }
                            }
                        }
                        articleLabel.setText(color + engine.getArticle());
                        tryLabel.setText(locale.chances() + " " + String.valueOf(numIntentos));
                    }
                });
            }else{
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        g.setScreen(new ConfigGameScreen(g,userInfo,grupo, skin));
                        dispose();
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
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
        //skin.dispose();
        sec_Skin.dispose();
    }
}
