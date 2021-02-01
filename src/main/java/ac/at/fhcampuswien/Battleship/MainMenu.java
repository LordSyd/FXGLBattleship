package ac.at.fhcampuswien.Battleship;


import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.audio.Music;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import com.almasb.fxgl.app.scene.MenuType;

import javafx.beans.binding.StringBinding;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import static com.almasb.fxgl.dsl.FXGL.*;


/**
 * Still a little work in progress, buttons for AI and 2 Player working and volume slider is also functional
 * */

public class MainMenu extends FXGLMenu {

    public MainMenu() {

        super(MenuType.MAIN_MENU);



        Runnable newGame = this::fireNewGame;

        var againstAI =  createActionButton("Against AI", newGame);
        var twoPlayer =  createActionButton("Two Player", newGame);

        EventHandler<ActionEvent> event = e -> {

            getAudioPlayer().stopAllMusic();
            newGame.run();

        };
        EventHandler<ActionEvent> AIEvent = e -> {
            getAudioPlayer().stopAllMusic();
            BattleshipMain.setAIActive(true);
            newGame.run();


        };

        getSettings().setGlobalMusicVolume(0.25);

        Slider volume = new Slider(0.0, 1.0, 1.0);
        volume.valueProperty().bindBidirectional(getSettings().globalMusicVolumeProperty());

        Slider sound = new Slider(0.0, 1.0, 1.0);
        sound.valueProperty().bindBidirectional(getSettings().globalSoundVolumeProperty());

        var textMusic =  getUIFactoryService().newText(localizedStringProperty("menu.music.volume").concat(": "));
        var percentMusic = getUIFactoryService().newText("");
        percentMusic.textProperty().bind(volume.valueProperty().multiply(100).asString("%.0f"));

        var textSound =  getUIFactoryService().newText(localizedStringProperty("menu.sound.volume").concat(": "));
        var percentSound = getUIFactoryService().newText("");
        percentSound.textProperty().bind(sound.valueProperty().multiply(100).asString("%.0f"));

        HBox hBoxMusic = new HBox(15, textMusic, volume, percentMusic );
        hBoxMusic.setTranslateX(424);
        hBoxMusic.setTranslateY(450);

        HBox hBoxSound = new HBox(15, textSound, sound, percentSound );
        hBoxSound.setTranslateX(420);
        hBoxSound.setTranslateY(500);

        againstAI.setOnAction(AIEvent);
        againstAI.setTranslateX(650);
        againstAI.setTranslateY(300);
        againstAI.setScaleX(2);
        againstAI.setScaleY(2);

        twoPlayer.setOnAction(event);
        twoPlayer.setTranslateX(450);
        twoPlayer.setTranslateY(300);
        twoPlayer.setScaleX(2);
        twoPlayer.setScaleY(2);



        Image image = getAssetLoader().loadImage("mainscreen.png");

        ImageView bg = new ImageView(image);

        bg.setFitHeight(getAppHeight());
        bg.setFitWidth(getAppWidth());

        StackPane background = new StackPane(bg);
        getContentRoot().getChildren().addAll(background, againstAI, twoPlayer, hBoxMusic, hBoxSound);


    }


    @Override
    public void onCreate() {
        BattleshipMain.setAIActive(false);
        Music menuSong = getAssetLoader().loadMusic("9. It's A Simulation.wav");
        getAudioPlayer().stopAllMusic();
        getAudioPlayer().loopMusic(menuSong);

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
        return new Rectangle(width, height, Color.DARKGRAY);
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
