package aircraftgame;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {
    public static void main(String[] args) {
    	
    	
    	

        // Boilerplate code from chat GPT:
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("SWT Boilerplate");

        // Set layout for the shell
        shell.setLayout(new FillLayout());
        
        DataHandler dataHandler = new DataHandler();
    	GameWorld gameWorld = new GameWorld(display, shell, dataHandler);
  

        // Set the size of the shell
        shell.setSize(300, 200);

        // Open the shell
        shell.open();

        // Test scenario, one enemy, one player:
        Player player = new Player(200.0f, 200.0f, 1.0f, 0.0f);
        Enemy enemy = new Enemy(300.0f, 200.0f, 0.0f, 1.0f);
        dataHandler.addGameObject(player);
        dataHandler.addGameObject(enemy);
        
        long lastUpdateTime = System.currentTimeMillis();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                //display.sleep();
            }
            
            long currentTime = System.currentTimeMillis();
            float deltaTime = (currentTime - lastUpdateTime) / 1000.0f; // Convert to seconds
            lastUpdateTime = currentTime;
            gameWorld.update(deltaTime);
        }

        // Dispose of the display when done
        display.dispose();
    }
}