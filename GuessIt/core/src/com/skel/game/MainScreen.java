package com.skel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.skel.util.Utils;

public class MainScreen implements Screen {
	Skin skin;
	Stage stage;

	Game g;

	//Items del screen
	TextButton newGameButton;
	TextButton aboutButton;
	TextButton registerButton;
	Label lab;

	public MainScreen(Game g){
		create();
		this.g = g;
	}

	public MainScreen(){
		create();
	}

	public void createStageActors(){
		//Create button login
		newGameButton = new TextButton("Login",skin);
		aboutButton = new TextButton("Credits",skin);
		registerButton = new TextButton("Register",skin);
		lab = new Label("GuessIt! Language Trainer",skin.get("default", Label.LabelStyle.class)); //Tomar como ejemplo para seleccionar skins diferentes.

		// Posicionamiento
		newGameButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.5f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

		registerButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.4f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

		aboutButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.3f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

		lab.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.8f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);

		//Activar caracteristicas
		lab.setWrap(true);
		lab.setAlignment(Align.center);

		// Funciones callback

		newGameButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				g.setScreen(new LoginScreen(g));
				return true;
			}
		});

		registerButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				g.setScreen(new RegisterScreen(g));
				return true;
			}
		});

		// Anadir al stage
		stage.addActor(aboutButton);
		stage.addActor(newGameButton);
		stage.addActor(registerButton);
		stage.addActor(lab);
	}

	public void create () {
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight())){
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.BACK) {
					Gdx.app.exit();
				}
				return super.keyDown(keyCode);
			}
		};
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		//Setting up Basic Skin (default skin)
		skin = Utils.createBasicSkin();

		createStageActors();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void show() {

	}

	public void resize(int width, int height){
		stage.getViewport().update(width, height, false);
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
