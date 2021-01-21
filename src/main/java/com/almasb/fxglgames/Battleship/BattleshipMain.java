package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;


import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
//todo fix placement of boards

public class BattleshipMain extends GameApplication {


    /**
     * Because this App was written using an existing javaFX Battleship implementation by Almas Baimagambetov as
     * reference, some classes and methods may either be redundant or might me exchanged for simple variables. In the
     * sake of getting a finished App done this optimisation is viewed as optional, as long as the overall game works.
     *
     * Maybe if time remains the logic will be streamlined.
     *
     * Also for testing purposes the boards of player one and player two are spawned on different sides of the screen
     */

    static protected Player player1;
    static protected Player player2;
    static protected int player1ShipsToPlace;
    static protected int player2ShipsToPlace;
    static private boolean gameRunning;
    static private boolean player1Turn;
    static protected boolean betweenTurnMenuActive;
    static protected boolean GameOverMenuActive;
    static protected boolean AIActive;



    public static boolean isAIActive() {
        return AIActive;
    }

    public static void setAIActive(boolean AIActive) {
        BattleshipMain.AIActive = AIActive;
    }

    private int deadPlayer = 0;

    public static boolean isPlayer1Turn() {
        return player1Turn;
    }

    public static void setPlayer1Turn(boolean player1Turn) {
        BattleshipMain.player1Turn = player1Turn;
    }

    public static boolean isGameRunning() {
        return gameRunning;
    }

    @Override
    protected void initSettings(GameSettings settings) {


        settings.setMainMenuEnabled(true);


        settings.setSceneFactory(new SceneFactory()

        {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });


        settings.setTitle("Battleship");
        settings.setVersion("1.0");
        settings.setWidth(1200);
        settings.setHeight(800);


    }

    /**
     * adds Entity Factories and calls spawn method for boards, also starts main music loop. Initializes game Variables for resetting
     */
    @Override
    protected void initGame() {
        initializeVariables();


        Music mainSong = FXGL.getAssetLoader().loadMusic("Plasma_Connection.wav");
        FXGL.getAudioPlayer().loopMusic(mainSong);

        getGameWorld().addEntityFactory(new TileFactory());
        getGameWorld().addEntityFactory(new ShipFactory());

        //Spawn  hitBoard player 1
        spawnHitBoard(1);
        //Spawn shipBoard player1
        spawnShipBoard(1);

    }

    private void initializeVariables() {
        player1 = new Player();
        player2 = new Player();
        player1ShipsToPlace = 5;
        player2ShipsToPlace = 5;
        gameRunning = false;
        player1Turn = true;
        betweenTurnMenuActive = false;
        GameOverMenuActive = false;
        AIActive = false;
    }

    /**
     * Called on update of frame. checks if player is dead
     *
     * @param tpf double
     */

    @Override
    protected void onUpdate(double tpf) {
        deadPlayer = checkPlayerDead();

        if (deadPlayer != 0){
            GameOverMenuActive = true;
            showGameOverMenu();
            System.out.println("activated");
        }
    }

    /**
     * simple method to check if any player has died
     */
    private int checkPlayerDead() {

        if (player1.isDead()){
            return 1;
        }else if (player2.isDead()){
            return 2;
        }else{
            return 0;
        }
    }

    /**
     * not used at the moment, test code remains inside for the time being
     */
    @Override
    protected void initUI() {

        Text testText = FXGL.getUIFactoryService().newText(
                "This is a test\nThis is a new line", Color.BLUE, 20);
        testText.setTranslateY(100);
        testText.setTranslateX(1000);
        addUINode(testText);
    }
    /**
     * Some methods in here are only for future proofing and not in use at the moment
     */

    static protected void showStartMenu(){
        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        getSceneService().pushSubScene(new StartMenuSubScene());
    }

    /**
     * removes all spawned entities and checks turn boolean for which scene to push
     */
    static protected void showTurnMenu(){

        getGameScene().getUINodes().forEach(Node  -> Node.setVisible(false) );

        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);

        if (player1ShipsToPlace == 0 && player2ShipsToPlace == 0){

            gameRunning = true;
        }

        if (player1Turn){
            getSceneService().pushSubScene(new NewTurnSubScene(1, gameRunning));
        }else{
            getSceneService().pushSubScene(new NewTurnSubScene(2, gameRunning));
        }
    }

    protected void showGameOverMenu(){

        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);

        Music gameOver = FXGL.getAssetLoader().loadMusic("20. Rush.wav");

        FXGL.getAudioPlayer().stopAllMusic();
        FXGL.getAudioPlayer().playMusic(gameOver);


        getSceneService().pushSubScene(new GameOverScreen(deadPlayer));


    }

    /**
     * used to clear arraylists holding the tiles after each round
     */
    static protected void clearTileArrays(){

        TileFactory.player1shipTiles.clear();
        TileFactory.player2shipTiles.clear();
        TileFactory.player1hitTiles.clear();
        TileFactory.player2hitTiles.clear();
    }

    static protected void closeTurnMenu(){
        betweenTurnMenuActive = false;
        clearTileArrays();

        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        getSceneService().popSubScene();

        if (player1Turn){
            spawnHitBoard(1);
            spawnShipBoard(1);
        }else{
            spawnHitBoard(2);
            spawnShipBoard(2);
        }

        getGameScene().getUINodes().forEach(Node  -> Node.setVisible(true) );
    }


    static protected void closeStartMenu(){
        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        getSceneService().popSubScene();

        spawnHitBoard(1);
        spawnShipBoard(1);
    }


    /**
     * method spawning the boards for placing ships
     * @param player
     */
    private static void spawnShipBoard(int player){


        int startX = 0;
        int startY = 0;

        switch (player){
            case 1 -> {
                startX = 68;
                startY = 420;
            }
            case 2 -> {
                startX = 600;
                startY = 420;
            }
        }



        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {

                Entity tile = spawn("tile", x * 30 + startX, y * 30 + startY);
                tile.setProperty("x", x);
                tile.setProperty("y", y);
                tile.setProperty("boardType", "ship");
                tile.setProperty("Player", player);

                if (player == 1){
                    TileFactory.player1shipTiles.add(tile);
                }else{
                    TileFactory.player2shipTiles.add(tile);
                }
            }
        }
    }

    /**
     * method for spawning the boards for shooting at other player
     * @param player
     */
    private static void spawnHitBoard(int player){
        TileFactory.player1hitTiles.clear();
        TileFactory.player2hitTiles.clear();
        int startX = 0;
        int startY = 0;

        switch (player){
            case 1 -> {
                startX = 68;
                startY = 60;
            }

            case 2 -> {
                startX = 600;
                startY = 60;
            }
        }


        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {

                Entity tile = spawn("tile", x * 30 + startX, y * 30 + startY);

                tile.setProperty("x", x);
                tile.setProperty("y", y);
                tile.setProperty("boardType", "hit");
                tile.setProperty("Player", player);
                if (player == 1){
                    TileFactory.player1hitTiles.add(tile);
                }else{
                    TileFactory.player2hitTiles.add(tile);
                }

            }

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
