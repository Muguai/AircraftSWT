package aircraftgame;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import components.Enemy;
import components.EnemySpawner;
import components.GameWorld;
import components.Player;
import data.DataHandler;

public class Main {
    public static void main(String[] args) {

        // 1. Set up the SWT shell:
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("SWT Boilerplate");
        shell.setLayout(new FillLayout());
        shell.setFullScreen(true);
        shell.open();
          
		// 2. Create our Player, DataHandler and GameWorld objects:
        Player player = new Player(display, 0.0f, 0.0f, 180);
		DataHandler dataHandler = new DataHandler(player);
		GameWorld gameWorld = new GameWorld(display, shell, dataHandler);
		
        // 3. Set up of a test scenario with two enemySpawners: Five enemies, each:
		EnemySpawner enemySpawner = new EnemySpawner(display.getBounds().width/2-300, display.getBounds().height/2-300, dataHandler, display);
		EnemySpawner enemySpawner2 = new EnemySpawner(display.getBounds().width/2-1000, display.getBounds().height/2-400, dataHandler, display);
        enemySpawner.vFormationSpawn(5, 45, -35, 55);
        enemySpawner2.vFormationSpawn(5, 45, 135, 65);
        
        // 4. Game Loop, for each frame, update the game world:
        long lastUpdateTime = System.currentTimeMillis();
        while (!shell.isDisposed() && gameWorld.runs()) {
        	
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