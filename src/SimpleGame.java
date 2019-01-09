import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class SimpleGame extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("SimpleGame");
        gameSettings.setWidth(600);
        gameSettings.setHeight(600);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
