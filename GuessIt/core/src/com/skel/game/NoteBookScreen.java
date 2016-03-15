package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Utils;

/**
 * Created by juanm on 08/03/2016.
 */
public class NoteBookScreen implements Screen {
    Utils utilidades = new Utils();
    private MainGame g;
    private Stage stage;
    private Skin skin;

    private Preferences defSaved;

    private Table scroller;

    String[] keys;
    int i;

    public void createStageActors(){
        scroller = new Table();
        keys = defSaved.get().keySet().toArray(keys);
        for(i = 0; i < defSaved.get().size(); i++){
            final Label noteLabel = new Label(keys[i], skin.get("default", Label.LabelStyle.class));
            scroller.add(noteLabel).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.1f);
            scroller.row();
            TextArea tmpTA = new TextArea(defSaved.getString(keys[i]), skin.get("default", TextField.TextFieldStyle.class));
            tmpTA.setMaxLength(250);
            tmpTA.setPrefRows(4);
            tmpTA.addListener(new InputListener(){
                public boolean keyTyped (InputEvent event, char character) {
                    super.keyTyped(event,character);
                    defSaved.putString(noteLabel.getText().toString(),defSaved.getString(noteLabel.getText().toString())+character);
                    defSaved.flush();
                    return true;
                }
            });
            scroller.add(tmpTA).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.2f);
            scroller.row();
        }

        TextButton CancelButton = new TextButton("Back", skin.get("default", TextButton.TextButtonStyle.class));
        CancelButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                g.setScreen(new MainScreen(g));
                dispose();
                return true;
            }
        });
        scroller.add(CancelButton).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.1f);
        scroller.row();

        ScrollPane scrollerPane = new ScrollPane(scroller);
        Gdx.app.log("conexion", "scroll creado");

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scrollerPane).fill().expand();

        stage.addActor(table);

    }

    public void create(){
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = utilidades.createBasicSkin();

        createStageActors();
    }

    public NoteBookScreen(MainGame g){
        this.g = g;
        defSaved = Gdx.app.getPreferences("notebook");
        keys = new String[defSaved.get().size()];
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
