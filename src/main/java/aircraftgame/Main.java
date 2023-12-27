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
import components.Player;
import components.Radar;
import data.DataHandler;
import pages.GameWorld;
import pages.StartMenu;
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
        
        Player player = new Player(display, 500.0f, 500.0f, 180);
		DataHandler dataHandler = new DataHandler(player);
        StartMenu startMenu = new StartMenu(display, shell, dataHandler);
        
        // 4. Game Loop, for each frame, update the game world:
        long lastUpdateTime = System.currentTimeMillis();
        while (!shell.isDisposed() && (startMenu.runs())) {

        	// 5. Game lags if this is not here:
            if (!display.readAndDispatch()) {
                display.sleep();
            }
            
            
//            while(startMenu.runs()) {
//            	System.out.println("Hello");
////            	long currentTime = System.currentTimeMillis();
////                float deltaTime = (currentTime - lastUpdateTime) / 1000.0f; 
////                lastUpdateTime = currentTime;
////                startMenu.update(deltaTime);
//            }
            
            // 2. Create our Player, DataHandler and GameWorld objects:
            
//    		GameWorld gameWorld = new GameWorld(display, shell, dataHandler);
//    		Radar playerRadar = new Radar(2000.0f, dataHandler);
//    		
//            // 3. Set up of a test scenario with two enemySpawners: Five enemies, each:
//    		EnemySpawner enemySpawner = new EnemySpawner(display.getBounds().width/2-300, display.getBounds().height/2-300, dataHandler, display);
//    		EnemySpawner enemySpawner2 = new EnemySpawner(display.getBounds().width/2-1000, display.getBounds().height/2-400, dataHandler, display);
//            enemySpawner.vFormationSpawn(5, 45, -35, 55);
//            enemySpawner2.vFormationSpawn(5, 45, 135, 65);
//            SoundManager soundManager = new SoundManager();
//            soundManager.playBackgroundOnRepeat();
//            
//            while (gameWorld.runs() && player.getHealth() > 0) {
//            	// 6. Calculate a deltaTime (time difference from last frame) and pass it to GameWorld:
//                long currentTime = System.currentTimeMillis();
//                float deltaTime = (currentTime - lastUpdateTime) / 1000.0f; 
//                lastUpdateTime = currentTime;
//                gameWorld.update(deltaTime);
//            }
            
        }

        // 7. Dispose of the display when done
        display.dispose();
    }
    
    
}