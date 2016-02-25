package com.skel.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by juanm on 20/01/2016.
 */
public class Utils {

    public static BitmapFont CreateFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.WHITE;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont tmpFont = generator.generateFont(parameter);
        generator.dispose();
        return tmpFont;
    }

    public static Skin createBasicSkin(){
        TextureAtlas basic_atlas = new TextureAtlas(Gdx.files.internal("images_packed/basic.atlas"));
        Skin tmpSkin = new Skin();
        //Add font
        tmpSkin.add("default",CreateFont(30));
        tmpSkin.add("label",CreateFont(24));
        tmpSkin.add("group",CreateFont(18));
        tmpSkin.addRegions(basic_atlas);
        //TextButton Style
        TextButton.TextButtonStyle tbStyle = new TextButton.TextButtonStyle();
        tbStyle.up = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_idle"));
        tbStyle.down = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_push"));
        tbStyle.checked = null;//new TextureRegionDrawable(tmpSkin.getRegion("basic_button_push"));
        tbStyle.over = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_push"));
        tbStyle.font = tmpSkin.getFont("default");
        tmpSkin.add("default",tbStyle);
        // TextButton groups
        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.up = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_idle"));
        tbStyle.down = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_push"));
        tbStyle.checked = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_push"));
        tbStyle.over = new TextureRegionDrawable(tmpSkin.getRegion("basic_button_push"));
        tbStyle.font = tmpSkin.getFont("group");
        tmpSkin.add("group",tbStyle);
        //Label Style
        Label.LabelStyle lStyle = new Label.LabelStyle();
        lStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_label"));
        lStyle.font = tmpSkin.getFont("label");
        lStyle.fontColor = Color.BLACK;
        tmpSkin.add("default",lStyle);
        //TextArea Style
        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_text_area"));
        tfStyle.font = tmpSkin.getFont("default");
        tfStyle.fontColor = Color.WHITE;
        tmpSkin.add("default",tfStyle);
        // ImageButton Style
        // Search Icon
        ImageButton.ImageButtonStyle ibStyle_Search = new ImageButton.ImageButtonStyle();
        ibStyle_Search.imageUp = new TextureRegionDrawable(tmpSkin.getRegion("search_icon"));
        tmpSkin.add("search_icon",ibStyle_Search);
        // Join Icon
        ImageButton.ImageButtonStyle ibStyle_Join = new ImageButton.ImageButtonStyle();
        ibStyle_Join.imageUp = new TextureRegionDrawable(tmpSkin.getRegion("join_icon"));
        tmpSkin.add("join_icon",ibStyle_Join);
        //Checkbox Style
        CheckBox.CheckBoxStyle cbStyle = new CheckBox.CheckBoxStyle();
        cbStyle.checkboxOff = new TextureRegionDrawable(tmpSkin.getRegion("uncheck"));
        cbStyle.checkboxOn = new TextureRegionDrawable(tmpSkin.getRegion("check"));
        cbStyle.font = tmpSkin.getFont("default");
        cbStyle.fontColor = Color.BLACK;
        tmpSkin.add("default",cbStyle);
        //Window Style
        Window.WindowStyle wStyle = new Window.WindowStyle();
        wStyle.titleFont = tmpSkin.getFont("default");
        wStyle.titleFontColor = Color.BLACK;
        wStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_label"));
        tmpSkin.add("default",wStyle);
        //
        return tmpSkin;
    }
}
