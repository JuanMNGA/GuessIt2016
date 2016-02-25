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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

/**
 * Created by juanm on 25/02/2016.
 */
public class MenuGameScreen implements Screen {
    private Stage stage;
    private Skin skin;
    Game g;
    UserInfo userInfo;

    public void createStageActors(){
        TextButton playButton = new TextButton("Play!", skin.get("default", TextButton.TextButtonStyle.class));
        TextButton statsButton = new TextButton("Stats", skin.get("default", TextButton.TextButtonStyle.class));
        TextButton backButton = new TextButton("Back", skin.get("default", TextButton.TextButtonStyle.class));

        playButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new UserGroupsScreen(g,userInfo));
                return true;
            }
        });

        statsButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        backButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                g.setScreen(new LoginScreen(g));
                return true;
            }
        });

        Table layout = new Table();

        layout.add(playButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();
        layout.add(statsButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();
        layout.add().width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.3f);
        layout.row();
        layout.add(backButton).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
        layout.row();

        layout.setFillParent(true);

        stage.addActor(layout);
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();

        createStageActors();
    }

    public MenuGameScreen(Game g, UserInfo UInfo){
        this.g = g;
        userInfo = UInfo;

        create();
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
