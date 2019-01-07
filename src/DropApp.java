import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class DropApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("DropApp");
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
    }
}
