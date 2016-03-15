package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Group;
import com.skel.util.Strings_I18N;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

/**
 * Created by juanm on 25/02/2016.
 */
public class MenuGameScreen implements Screen {

    Utils utilidades = new Utils();

    private Stage stage;
    private Skin skin;
    private Strings_I18N locale;
    MainGame g;
    UserInfo userInfo;
    Group grupo;

    public void createStageActors(){
        TextButton playButton = new TextButton(locale.play(), skin.get("default", TextButton.TextButtonStyle.class));
        TextButton statsButton = new TextButton(locale.stats(), skin.get("default", TextButton.TextButtonStyle.class));
        TextButton backButton = new TextButton(locale.back(), skin.get("default", TextButton.TextButtonStyle.class));
        TextButton newDefButton = new TextButton(locale.addDef(), skin.get("default", TextButton.TextButtonStyle.class));

        if(userInfo.canAddDefinition()){
            newDefButton.setVisible(true);
        }else{
            newDefButton.setVisible(false);
        }

        playButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new ConfigGameScreen(g,userInfo, grupo));
                dispose();
                return true;
            }
        });

        statsButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new StatsScreen(g, userInfo, grupo));
                dispose();
                return true;
            }
        });

        newDefButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new NewDefScreen(g, userInfo, grupo));
                dispose();
                return true;
            }
        });

        backButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new UserGroupsScreen(g, userInfo));
                dispose();
                return true;
            }
        });

        Table layout = new Table();

        layout.add(playButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();
        layout.add(statsButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();
        layout.add(newDefButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();
        layout.add().width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
        layout.row();
        layout.add(backButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();

        layout.setFillParent(true);

        stage.addActor(layout);
    }

    public void create(){
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = utilidades.createBasicSkin();

        createStageActors();
    }

    public MenuGameScreen(MainGame g, UserInfo UInfo, Group grupo){
        this.g = g;
        userInfo = UInfo;
        this.grupo = grupo;
        locale = new Strings_I18N(grupo.getLanguageName());
        create();
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
