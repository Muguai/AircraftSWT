package aircraftgame;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;

import components.FighterPlane;
import components.PlaneSpawner;
import components.Explosion;
import components.GameWorld;
import components.Player;
import components.Radar;
import data.DataHandler;
import utils.SoundManager;

public class Main {
    public static void main(String[] args) {

        // 1. Set up the SWT shell:
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("SWT Boilerplate");
        shell.setLayout(new FillLayout());
        shell.setFullScreen(true);
        shell.open();
        
        StartMenu startMenu = new StartMenu(display, shell);
          
		// 2. Create our Player, DataHandler and GameWorld objects:
        Player player = new Player(display, 500.0f, 500.0f, 180);
		DataHandler dataHandler = new DataHandler(player);
		GameWorld gameWorld = new GameWorld(display, shell, dataHandler);
		Radar playerRadar = new Radar(2000.0f, dataHandler);
		
        // 3. Set up of a test scenario with two enemySpawners: Five enemies, each:
		PlaneSpawner enemySpawner = new PlaneSpawner(0, 0, dataHandler, display);
		PlaneSpawner enemySpawner2 = new PlaneSpawner(1200, 0, dataHandler, display);
        enemySpawner.vFormationSpawn(10, 45, 0, 55, true);
        enemySpawner2.vFormationSpawn(10, 45, 180, 65, false);
        SoundManager soundManager = new SoundManager();
        soundManager.playBackgroundOnRepeat();
        
        
        // 4. Game Loop, for each frame, update the game world:
        long lastUpdateTime = System.currentTimeMillis();
        boolean test = true;
        while (!shell.isDisposed() && gameWorld.runs() && player.getHealth() > 0) {
        	
        	// 5. Game lags if this is not here:
            if (!display.readAndDispatch()) {
                display.sleep();
            }
            
            // 6. Calculate a deltaTime (time difference from last frame) and pass it to GameWorld:
            long currentTime = System.currentTimeMillis();
            float deltaTime = (currentTime - lastUpdateTime) / 1000.0f; 
            lastUpdateTime = currentTime;
            gameWorld.update(deltaTime);
        }

        // 7. Dispose of the display when done
        display.dispose();
    }
    
    
}