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

    public static BitmapFont CreateFont(int size, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont tmpFont = generator.generateFont(parameter);
        tmpFont.getData().markupEnabled = true;
        generator.dispose();
        return tmpFont;
    }

    public static BitmapFont CreateResultFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont tmpFont = generator.generateFont(parameter);
        tmpFont.getData().markupEnabled = true;
        generator.dispose();
        return tmpFont;
    }

    public static Skin createBasicSkin(){
        TextureAtlas basic_atlas = new TextureAtlas(Gdx.files.internal("images_packed/basic.atlas"));
        Skin tmpSkin = new Skin();
        //Add font
        tmpSkin.add("default",CreateFont(30, Color.WHITE));
        tmpSkin.add("label",CreateFont(24, Color.BLACK));
        tmpSkin.add("group",CreateFont(18, Color.WHITE));
        tmpSkin.add("point",CreateFont(40, Color.BLACK));
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
        // TextButton points
        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.up = new TextureRegionDrawable(tmpSkin.getRegion("puntuacion"));
        tbStyle.down = new TextureRegionDrawable(tmpSkin.getRegion("puntuacion"));
        tbStyle.checked = null;
        tbStyle.over = null;
        tbStyle.font = tmpSkin.getFont("group");
        tmpSkin.add("point",tbStyle);
        // TextButton report
        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.up = new TextureRegionDrawable(tmpSkin.getRegion("report"));
        tbStyle.down = new TextureRegionDrawable(tmpSkin.getRegion("report"));
        tbStyle.checked = null;
        tbStyle.over = null;
        tbStyle.font = tmpSkin.getFont("group");
        tmpSkin.add("report",tbStyle);
        //Label Style
        Label.LabelStyle lStyle = new Label.LabelStyle();
        lStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_label"));
        lStyle.font = tmpSkin.getFont("label");
        //tmpSkin.getFont("label").getData().markupEnabled = true;
        tmpSkin.add("default",lStyle);
        // Puntuacion style
        lStyle = new Label.LabelStyle();
        lStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_label"));
        lStyle.font = tmpSkin.getFont("point");
        //tmpSkin.getFont("point").getData().markupEnabled = true;
        tmpSkin.add("point", lStyle);
        //TextArea Style
        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_text_area"));
        tfStyle.font = tmpSkin.getFont("default");
        tfStyle.fontColor = Color.WHITE;
        tfStyle.cursor = new TextureRegionDrawable(tmpSkin.getRegion("cursor"));
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

    public static Skin createResultSkin(){
        TextureAtlas basic_atlas = new TextureAtlas(Gdx.files.internal("images_packed/basic.atlas"));
        Skin tmpSkin = new Skin();
        tmpSkin.add("result", CreateResultFont(20));
        tmpSkin.addRegions(basic_atlas);
        Label.LabelStyle lStyle = new Label.LabelStyle();
        lStyle.background = new TextureRegionDrawable(tmpSkin.getRegion("basic_label"));
        lStyle.font = tmpSkin.getFont("result");
        tmpSkin.add("result",lStyle);
        return tmpSkin;
    }

    public static String getUrl(){
        return "http://192.168.1.107/GuessIt/game/";
        //return "http://localhost/GuessIt/game/";
    }
}
