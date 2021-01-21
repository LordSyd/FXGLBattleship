package com.almasb.fxglgames.Battleship;



import com.almasb.fxgl.app.scene.FXGLMenu;

import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.StringBinding;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Not fully functioning at the moment, game uses default menu as of now.
 */

public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);
        var startGame =  createActionButton("Start", this::fireNewGame);

        Runnable testAction = this::fireNewGame;

        EventHandler<ActionEvent> event = event1 -> testAction.run();

        startGame.setOnAction(event);











        getContentRoot().getChildren().add(startGame);


    };








    

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
