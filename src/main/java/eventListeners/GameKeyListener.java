package eventListeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import components.Bullet;
import components.HeatseekingMissile;
import components.Player;
import data.DataHandler;
import pages.GameWorld;

public class GameKeyListener implements KeyListener {

	private final GameWorld gameWorld;

	public GameKeyListener(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Display display = gameWorld.getDisplay();
		Player player = gameWorld.getDataHandler().getPlayer();
		float offsetX = gameWorld.getCanvas().getBounds().width / 2;
		float offsetY = gameWorld.getCanvas().getBounds().height / 2;

		switch (e.keyCode) {
		// Arrow cases
		case SWT.ARROW_UP:
			gameWorld.getDataHandler().getPlayer().increaseSpeed();
			break;
		case SWT.ARROW_DOWN:
			gameWorld.getDataHandler().getPlayer().decreaseSpeed();
			break;
		case SWT.ARROW_LEFT:
			gameWorld.getDataHandler().getPlayer().turnLeft();
			break;
		case SWT.ARROW_RIGHT:
			gameWorld.getDataHandler().getPlayer().turnRight();
			break;
		
		// Toggle radar
		case SWT.TAB:
			gameWorld.getDataHandler().getPlayer().toggleRadar();
			break;

		// Shooting bullet
		case SWT.SPACE:
			gameWorld.getDataHandler().addGameObject(new Bullet(display, player, offsetX, offsetY, true));
			break;
			
		case SWT.ALT:
        	DataHandler dataHandler = gameWorld.getDataHandler();
        	gameWorld.getDataHandler().addGameObject(
        			new HeatseekingMissile(player.getDisplay(), player, offsetX, offsetY, true, dataHandler));

		}

		//gameWorld.getCanvas().redraw();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not to use but must be implemented
	}

}
