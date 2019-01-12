import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.asset.AssetLoader;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.Map;

public class SimpleGame extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("SlimeSoccer2000");
        gameSettings.setWidth(1200);
        gameSettings.setHeight(600);
        gameSettings.setVersion("0.1");
        gameSettings.setMenuKey(KeyCode.M);
        gameSettings.getMenuKey();
        gameSettings.setMenuEnabled(true);


    }
    private Entity player;

    @Override
    protected void initGame() {
        Entities.builder()
                .type(EntityType.BACKGROUND)
                .at(0, 0)
                .viewFromTexture("background1.png")
                .buildAndAttach(getGameWorld());

        player = Entities.builder()
                .at(100, 499)
                .type(EntityType.PLAYER)
                //.viewFromNode(new Rectangle(25, 25, Color.DARKGREEN))
                .viewFromTextureWithBBox("slime1.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        player = Entities.builder()
                .at(400, 499)
                .type(EntityType.PLAYER2)
                //.viewFromNode(new Rectangle(25, 25, Color.DARKGREEN))
                .viewFromTextureWithBBox("slime2.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

                 Entities.builder()
                .type(EntityType.COIN)
                .at(500, 200)
                .viewFromNodeWithBBox(new Circle(15, Color.YELLOW))
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());


    }

    public enum EntityType {
        PLAYER, PLAYER2, COIN , BACKGROUND ,
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5);
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5); // move left 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5); // move up 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5); // move down 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Play Sound") {
            @Override
            protected void onActionBegin() {
                getAudioPlayer().playSound("muscle-car-daniel_simon.wav");
            }
        }, KeyCode.F);


    }
    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        getGameScene().addUINode(textPixels); // add to the scene graph
        textPixels.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
