package pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import data.DataHandler;

public abstract class Page {
	
	protected Display display;
	protected DataHandler dataHandler;
	protected Canvas canvas;
	private boolean isRunning;

	public Page(Display display, Shell shell, DataHandler dataHandler) {
		this.display = display;
		this.dataHandler = dataHandler;
		
		// Create a new canvas as big as the screen
		this.canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
		canvas.setSize(shell.getSize().x, shell.getSize().y);
		
		isRunning = true;
	}
	
	public Display getDisplay() { return display; }
	public DataHandler getDataHandler() { return dataHandler; }
	public Canvas getCanvas() { return canvas; }
	
	/*	runs()
	 * 	Returns the state of the game.
	 */
	
	public boolean runs() {
		return isRunning;
	}
	
	/* exit()
	 * Sets the state of the game to be false.
	 */
	public void exit() {
		isRunning = false;
	}
	
	
}
