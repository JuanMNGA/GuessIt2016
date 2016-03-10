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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Group;
import com.skel.util.Strings_I18N;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by juanm on 01/03/2016.
 */
public class StatsScreen implements Screen, Net.HttpResponseListener {

    private UserInfo userInfo;
    private Game g;
    private Stage stage;
    private Skin skin;

    private Group grupo;

    private Strings_I18N locale;

    Net.HttpRequest httpsolicitud;
    String httpMethod = Net.HttpMethods.POST;

    private Table scroll_table;

    Label defPlayedLabel, numDefPlayedLabel, successesPlayedLabel, numSuccessesPlayedLabel, avgRatingPlayedLabel, numAvgRatingPlayedLabel, catMostPlayedLabel, stringCatMostPlayedLabel;

    public void createStageActors(){
        defPlayedLabel = new Label(locale.defPlayed(), skin.get("default", Label.LabelStyle.class));
        defPlayedLabel.setWrap(true);
        defPlayedLabel.setAlignment(Align.center);

        numDefPlayedLabel = new Label("", skin.get("default", Label.LabelStyle.class));
        numDefPlayedLabel.setWrap(true);
        numDefPlayedLabel.setAlignment(Align.center);

        successesPlayedLabel = new Label(locale.sucPlayed(), skin.get("default", Label.LabelStyle.class));
        successesPlayedLabel.setWrap(true);
        successesPlayedLabel.setAlignment(Align.center);

        numSuccessesPlayedLabel = new Label("", skin.get("default", Label.LabelStyle.class));
        numSuccessesPlayedLabel.setWrap(true);
        numSuccessesPlayedLabel.setAlignment(Align.center);

        avgRatingPlayedLabel = new Label(locale.avgRated(), skin.get("default", Label.LabelStyle.class));
        avgRatingPlayedLabel.setWrap(true);
        avgRatingPlayedLabel.setAlignment(Align.center);

        numAvgRatingPlayedLabel = new Label("", skin.get("default", Label.LabelStyle.class));
        numAvgRatingPlayedLabel.setWrap(true);
        numAvgRatingPlayedLabel.setAlignment(Align.center);

        catMostPlayedLabel = new Label(locale.mostCategory(), skin.get("default", Label.LabelStyle.class));
        catMostPlayedLabel.setAlignment(Align.center);
        catMostPlayedLabel.setWrap(true);

        stringCatMostPlayedLabel = new Label("", skin.get("default", Label.LabelStyle.class));
        stringCatMostPlayedLabel.setWrap(true);
        stringCatMostPlayedLabel.setAlignment(Align.center);

        Label reportedDefLabel = new Label(locale.reportedDef(), skin.get("default", Label.LabelStyle.class));
        reportedDefLabel.setAlignment(Align.center);
        reportedDefLabel.setWrap(true);

        scroll_table = new Table();

        scroll_table.add(defPlayedLabel).width(Gdx.graphics.getWidth()*0.6f).height(Gdx.graphics.getHeight()*0.15f);
        scroll_table.add(numDefPlayedLabel).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.15f);
        scroll_table.row();

        scroll_table.add(successesPlayedLabel).width(Gdx.graphics.getWidth()*0.6f).height(Gdx.graphics.getHeight()*0.15f);
        scroll_table.add(numSuccessesPlayedLabel).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.15f);
        scroll_table.row();

        scroll_table.add(avgRatingPlayedLabel).width(Gdx.graphics.getWidth()*0.6f).height(Gdx.graphics.getHeight()*0.15f);
        scroll_table.add(numAvgRatingPlayedLabel).width(Gdx.graphics.getWidth()*0.2f).height(Gdx.graphics.getHeight()*0.15f);
        scroll_table.row();

        scroll_table.add(catMostPlayedLabel).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.2f);
        scroll_table.add(stringCatMostPlayedLabel).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.2f);
        scroll_table.row();

        scroll_table.add(reportedDefLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(2);
        scroll_table.row();

        generateReportedDefs();
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();

        createStageActors();
    }

    public StatsScreen(Game g, UserInfo UInfo, Group group){
        this.g = g;
        userInfo = UInfo;
        this.grupo = group;

        locale = new Strings_I18N(grupo.getLanguageName());

        create();
    }

    public void generateReportedDefs(){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_usuario",String.valueOf(userInfo.getId()));
        parameters.put("id_aula", String.valueOf(grupo.getId()));
        String url = Utils.getUrl()+"getReportedDef.php?";
        httpsolicitud = new Net.HttpRequest(httpMethod);
        httpsolicitud.setUrl(url);
        httpsolicitud.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpsolicitud,StatsScreen.this);
    }
    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final String ResponseBefore = httpResponse.getResultAsString();
        final String Response = new String(ResponseBefore.getBytes(), Charset.forName("UTF-8"));
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if(!Response.isEmpty()){
                    // Stuff
                    StringTokenizer stroker = new StringTokenizer(Response, ";");
                    if(stroker.hasMoreElements()) {
                        numDefPlayedLabel.setText(stroker.nextToken());
                        numSuccessesPlayedLabel.setText(stroker.nextToken());
                        numAvgRatingPlayedLabel.setText(stroker.nextToken());
                        stringCatMostPlayedLabel.setText(stroker.nextToken());
                        while(stroker.hasMoreElements()){
                            scroll_table.add(new Label(stroker.nextToken(), skin.get("default", Label.LabelStyle.class))).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.1f);
                            scroll_table.add(new Label(stroker.nextToken(), skin.get("default", Label.LabelStyle.class))).width(Gdx.graphics.getWidth()*0.4f).height(Gdx.graphics.getHeight()*0.1f);
                            scroll_table.row();
                        }
                    }
                }

                TextButton backButton = new TextButton(locale.back(), skin.get("default", TextButton.TextButtonStyle.class));
                backButton.addListener(new InputListener(){
                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                       g.setScreen(new MenuGameScreen(g, userInfo, grupo));
                       return true;
                   }
                });
                scroll_table.add(backButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f).colspan(2);

                ScrollPane scroller = new ScrollPane(scroll_table);

                final Table table = new Table();
                table.setFillParent(true);
                table.add(scroller).expand().fill();

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
