package pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import data.DataHandler;
import eventListeners.EscapeKeyListener;

public class StartMenu extends Page {

	private Image backgroundImage;

	public StartMenu(Display display, Shell shell, DataHandler dataHandler) {
		super(display, shell, dataHandler);

//		try {
//			backgroundImage = new Image(display, "src\\main\\java\\resources\\images\\cloud_background.jpg");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		 

		// Set up a PaintListener to draw the background image
//		canvas.addPaintListener(e -> {
//			GC gc = e.gc;
//			Rectangle bounds = canvas.getBounds();
//
//			// Repeat the background image horizontally and vertically
//			for (int x = 0; x < bounds.width; x += backgroundImage.getBounds().width) {
//				for (int y = 0; y < bounds.height; y += backgroundImage.getBounds().height) {
//					e.gc.drawImage(backgroundImage, x, y);
//				}
//			}
//
//		});
		
//		Button startGameButton = new Button(shell, SWT.PUSH);
//		startGameButton.setText("Start Game");
//		
//		startGameButton.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                // Notify the main game application to start the game
//                //startGame();
//            }
//
//        });
		
		canvas.addKeyListener(new EscapeKeyListener(this));
	}

	
	public void update() {
		
	}
	
	
	
	
}
