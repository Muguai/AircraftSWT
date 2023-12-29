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
	public GameOver(Display display, Shell shell, DataHandler dataHandler, Canvas canvas) {
		super(display, shell, dataHandler, canvas);
		drawGameOver();
		canvas.setFocus();
		canvas.addKeyListener(new EscapeKeyListener(this));
	}
	
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
            gc.fillRectangle(fontX, fontY, 300, 100);
            gc.setAlpha(255);
            gc.drawText("Game Over! \n(Press ESC to exit)", fontX, fontY, true);
		});
	}
	
	public void update(float deltaTime) {
		canvas.redraw();
	}
}
