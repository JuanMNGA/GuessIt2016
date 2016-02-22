package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.UserInfo;
import com.skel.util.Utils;

/**
 * Created by juanm on 22/02/2016.
 */
public class UserGroupsScreen implements Screen, Net.HttpResponseListener {
    private UserInfo userInfo;
    private Game g;
    private Stage stage;
    private Skin skin;

    private Table upTable;
    private Table ScrollTable;

    public UserGroupsScreen(Game g, UserInfo UInfo) {
        this.g = g;
        userInfo = UInfo;
        create();
    }

    public void createStageActors(){
        upTable = new Table();
        ScrollTable = new Table();

        Image searchIcon = new Image(new Texture(Gdx.files.internal("images/search_icon.png")));
        Image joinIcon = new Image(new Texture(Gdx.files.internal("images/join_icon.png")));

        TextField searchInput = new TextField("",skin.get("default",TextField.TextFieldStyle.class));

        upTable.add(searchIcon).width(Gdx.graphics.getWidth()*0.1f).height(Gdx.graphics.getHeight()*0.1f);
        upTable.add(searchInput).width(Gdx.graphics.getWidth()*0.5f).height(Gdx.graphics.getHeight()*0.1f);
        upTable.row();
        upTable.setFillParent(true);
        upTable.top();
        // Add tables to stage
        stage.addActor(upTable);
    }

    public void create(){
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = Utils.createBasicSkin();
        //Llamar a connection y que devolviese el resultado de los grupos a los que el alumno ha sido validado
        createStageActors();
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

    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
}
