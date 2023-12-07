package aircraftgame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;

public class ArrowKeyListener implements KeyListener {
	
	private final GameWorld gameWorld;
	
	public ArrowKeyListener(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int steps = 10;
		
		switch (e.keyCode) {
			case SWT.ARROW_UP:
				gameWorld.setOffsetY(gameWorld.getOffsetY() + steps);
                break;
            case SWT.ARROW_DOWN:
            	gameWorld.setOffsetY(gameWorld.getOffsetY() - steps);
                break;
            case SWT.ARROW_LEFT:
            	gameWorld.setOffsetX(gameWorld.getOffsetX() + steps);
                break;
            case SWT.ARROW_RIGHT:
            	gameWorld.setOffsetX(gameWorld.getOffsetX() - steps);
                break;
		}
		
		gameWorld.getCanvas().redraw();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// Not to use but must be implemented
	}

}
