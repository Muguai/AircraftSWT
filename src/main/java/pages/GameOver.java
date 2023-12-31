package pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import data.DataHandler;
import eventListeners.EscapeKeyListener;

public class GameOver extends Page {
	
	/*	[Class Constructor] GameOver
	 *  A gameOver object is used as stage 3/3 in the main loop.
	 *  The gameOver page will freeze the game and display a game over section, where the player
	 *  is greeted with how many enemies he / she has shot down.
	 *  The player can then press ESC to exit the game. 
	 */
	
	public GameOver(Display display, Shell shell, DataHandler dataHandler, Canvas canvas) {
		super(display, shell, dataHandler, canvas);
		drawGameOver();
		canvas.setFocus();
		canvas.addKeyListener(new EscapeKeyListener(this));
	}
	
	/*	drawGameOver()
	 * 	A method that is called to draw the game over screen.
	 *  The method makes use of the dataHandler to get data of how many enemies have been killed.
	 */
	
	private void drawGameOver() {
		canvas.addPaintListener(e -> {
            GC gc = e.gc;
            
     
            FontData fontData = new FontData("Arial", 18, SWT.NORMAL);
 
            // Dispose of the existing font to avoid memory leaks
            if (gc.getFont() != null) {
                gc.getFont().dispose();
            }
 
            Font font = new Font(canvas.getDisplay(), fontData);
 
            // Set the new Font to the GC
            gc.setFont(font);
            
            // Calculate the position for the bottom right with an offset
            int fontX = canvas.getBounds().width/2-100; 
            int fontY = canvas.getBounds().height/2 - 50; 
 
            // Draw your health bar
            gc.setBackground(new Color(255,255,255));
            gc.setAlpha(140);
            gc.fillRectangle(fontX, fontY-50, 300, 200);
            gc.setAlpha(255);
            gc.drawText("Game Over! "
            		+ "\n(Press ESC to exit)"
            		+ "\n\n Enemies killed: " + this.dataHandler.getKills(), fontX, fontY-50, true);
		});
	}
	
	public void update(float deltaTime) {
		canvas.redraw();
	}
}
