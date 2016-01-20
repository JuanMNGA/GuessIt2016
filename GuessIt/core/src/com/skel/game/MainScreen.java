package com.skel.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

public class MainScreen extends ApplicationAdapter {
	Skin skin;
	BitmapFont font, font_label;
	Stage stage;
	
	@Override
	public void create () {
		stage = new Stage(new StretchViewport(320,500));
		Gdx.input.setInputProcessor(stage);
		//Setting up Basic Skin (default skin)
		skin = Utils.createBasicSkin();
		//Create button login
		TextButton newGameButton = new TextButton("Login",skin);
		newGameButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.5f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);
		newGameButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				System.out.println("HOLA");
				return true;
			}
		});
		stage.addActor(newGameButton);
		//Create button credits
		TextButton aboutButton = new TextButton("Credits",skin);
		aboutButton.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.4f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);
		stage.addActor(aboutButton);
		//Create label
		Label lab = new Label("GuessIt! Language Trainer",skin.get("default", Label.LabelStyle.class)); //Tomar como ejemplo para seleccionar skins diferentes.
		lab.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.8f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f);
		lab.setWrap(true);
		lab.setAlignment(Align.center);
		stage.addActor(lab);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	public void resize(int width, int height){
		stage.getViewport().update(width, height, false);
	}
}
