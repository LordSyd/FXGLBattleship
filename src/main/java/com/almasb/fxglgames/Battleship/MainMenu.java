package com.almasb.fxglgames.Battleship;


import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.audio.Music;
import javafx.scene.layout.HBox;

import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Game Menu graphical design designed by Almas Baimagambetov. Various buttons like new game, options and exit are created.
 * Not fully functioning at the moment, game uses default menu as of now.
 */

public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);



        Runnable testAction = this::fireNewGame;

        var againstAI =  createActionButton("Against AI", testAction);
        var twoPlayer =  createActionButton("Two Player", testAction);



        EventHandler<ActionEvent> event = e -> {
            FXGL.getAudioPlayer().stopAllMusic();
            testAction.run();

        };
        EventHandler<ActionEvent> AIEvent = e -> {
            FXGL.getAudioPlayer().stopAllMusic();

            BattleshipMain.setAIActive(true);
            System.out.println(BattleshipMain.isAIActive());
            testAction.run();


        };


        Slider volume = new Slider(0.0, 1.0, 1.0);
        volume.valueProperty().bindBidirectional(FXGL.getSettings().globalMusicVolumeProperty());

        var textMusic =  FXGL.getUIFactoryService().newText(FXGL.localizedStringProperty("menu.music.volume").concat(": "));
        var percentMusic = FXGL.getUIFactoryService().newText("");
        percentMusic.textProperty().bind(volume.valueProperty().multiply(100).asString("%.0f"));

        HBox hBoxMusic = new HBox(15, textMusic, volume, percentMusic );
        hBoxMusic.setTranslateX(100);
        hBoxMusic.setTranslateY(450);



        againstAI.setOnAction(AIEvent);
        againstAI.setTranslateX(400);
        againstAI.setTranslateY(300);
        againstAI.setScaleX(2);
        againstAI.setScaleY(1.5);

        twoPlayer.setOnAction(event);
        twoPlayer.setTranslateX(200);
        twoPlayer.setTranslateY(300);
        twoPlayer.setScaleX(2);
        twoPlayer.setScaleY(1.5);



        getContentRoot().getChildren().addAll(againstAI, twoPlayer, hBoxMusic);


    }

    @Override
    protected void onUpdate(double tpf) {
        Music menuSong = FXGL.getAssetLoader().loadMusic("9. It's A Simulation.wav");
        FXGL.getAudioPlayer().loopMusic(menuSong);
    }

    @Override
    protected Button createActionButton(String name, Runnable action) {

        return new Button(name);
    }

    @Override
    protected Button createActionButton(StringBinding name, Runnable action) {
        return new Button(name.get());
    }

    @Override
    protected Node createBackground(double width, double height) {
        return new Rectangle(width, height, Color.GRAY);
    }

    @Override
    protected Node createTitleView(String title) {
        return new Text(title);
    }

    @Override
    protected Node createVersionView(String version) {
        return new Text(version);
    }

    @Override
    protected Node createProfileView(String profileName) {
        return null;
    }

}
