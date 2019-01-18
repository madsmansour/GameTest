import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.asset.AssetLoader;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsControl;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.PixelFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.lang.reflect.Type;
import java.util.GregorianCalendar;
import java.util.Map;

public class SoccerSlime extends GameApplication {


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
    private Entity player2;
    private PhysicsComponent ball;
    private PhysicsComponent bottomline;
    private PhysicsComponent top;




    @Override
    protected void initGame() {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);


        PhysicsComponent immovable = new PhysicsComponent();
        immovable.setBodyType(BodyType.STATIC);




        Entities.builder()
                .type(EntityType.BACKGROUND)
                .at(0, 0)
                .viewFromTexture("background1.png")
                .buildAndAttach(getGameWorld());


        player = Entities.builder()
                .at(100, 395)
                .type(EntityType.PLAYER)
                //.viewFromNode(new Rectangle(25, 25, Color.DARKGREEN))
                .viewFromTextureWithBBox("slime1.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        player2 = Entities.builder()
                .at(910, 391)
                .type(EntityType.PLAYER2)
                .viewFromTextureWithBBox("slime2.png")
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .buildAndAttach(getGameWorld());


        Entities.builder()
                .type(EntityType.BALL)
                .at(550,  200)
                .viewFromTextureWithBBox("ball.png")
                .with(new CollidableComponent(true))
                .with(physicsComponent)
                .buildAndAttach(getGameWorld());

        Entities.builder()
                .type(EntityType.BOTTOMLINE)
                .at(0,  482)
                .viewFromTextureWithBBox("bottomline.png")
                .with(new CollidableComponent(true))
                .with(immovable)
                .buildAndAttach(getGameWorld());

    }

    public enum EntityType {
        PLAYER, PLAYER2, BALL , BACKGROUND , BOTTOMLINE, SCREEN
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


        input.addAction(new UserAction("Celebrate") {
            @Override
            protected void onActionBegin() {
                getAudioPlayer().playSound("siii.wav");
            }
        }, KeyCode.F);

        input.addAction(new UserAction("Move Right (Player2)") {
            @Override
            protected void onAction() {
                player2.translateX(5);
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Left (Player2)") {
            @Override
            protected void onAction() {
                player2.translateX(-5); // move left 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Up (Player 2)") {
            @Override
            protected void onAction() {
                player2.translateY(-5); // move up 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.UP);

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
        PhysicsWorld physics = getPhysicsWorld();


        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.BALL) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity ball) {
                ball.removeFromWorld();
            }
        });



    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.BOTTOMLINE) {
        @Override
        protected void onCollisionBegin(Entity player, Entity bottomline) {
            System.out.println("Player1 colliding bottomline");
        }
    });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER2, EntityType.BALL) {
            @Override
            protected void onCollisionBegin(Entity player2, Entity ball) {
                System.out.println("Player2 colliding ball");
            }
        });
}

    public static void main(String[] args) {
        launch(args);
    }
}
