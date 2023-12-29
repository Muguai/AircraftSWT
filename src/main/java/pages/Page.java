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

	/*	[Class Constructor] Page
	 * 	Page is an abstract class that different stages of the game can extend, for example:
	 *  Start Menu, Game World, and Game Over Screen.
	 *  With the abstract page class we can use functionalities that they all should have in common
	 *  such as the isRunning() and exit() methods.
	 */
	
	public Page(Display display, Shell shell, DataHandler dataHandler, Canvas canvas) {
		this.display = display;
		this.dataHandler = dataHandler;
		this.shell = shell;
		this.canvas = canvas;		
		this.isRunning = true;
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
	
	/*	removePaintListener()
	 * 	A method that reads in a paint listener and removes it from the canvas.
	 */
	
	public void removePaintListener(PaintListener paintListener) {
		if(paintListener != null)
			canvas.removePaintListener(paintListener);
		paintListener = null;
	}
}
