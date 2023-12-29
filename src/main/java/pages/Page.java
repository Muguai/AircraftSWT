package pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import data.DataHandler;

public abstract class Page {
	
	protected Display display;
	protected DataHandler dataHandler;
	protected Canvas canvas;
	protected boolean isRunning;
	protected Shell shell;

	public Page(Display display, Shell shell, DataHandler dataHandler, Canvas canvas) {
		this.display = display;
		this.dataHandler = dataHandler;
		this.shell = shell;
		// Create a new canvas as big as the screen
		this.canvas = canvas;
		
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
		Listener[] listeners = canvas.getListeners(SWT.Paint);
		for(Listener listener : listeners) {
			if(listener instanceof PaintListener) {
				canvas.removePaintListener(((PaintListener) listener));
			}
		}
		canvas.update();
	}
	

}
