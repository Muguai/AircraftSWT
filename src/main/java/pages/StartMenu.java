package pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import components.Player;
import data.DataHandler;
import eventListeners.EscapeKeyListener;
import utils.ImageManager;

public class StartMenu extends Page {

	private Image backgroundImage;
	private Image planeImage;
	private Player player;
	private int offsetX;
	private PaintListener menuPaintListener;
	private PaintListener planePaintListener;
	private boolean menuPaintListenerActive = false;
	private boolean planePaintListenerActive = false;

	/*	[Class Constructor] StartMenu
	 *  Stage 1/3 in the main loop.
	 *  This class runs animation via the update() method, using deltaTime.
	 *  A background is rendered and a label where the user is prompted to press ESC to start a new game.
	 *  On pressing ESC, running is set to false, and start menu exits and the game loop begins in main (stage 2/3).
	 */
	
	public StartMenu(Display display, Shell shell, DataHandler dataHandler, Canvas canvas, Player player) {
		super(display, shell, dataHandler, canvas);
		this.player = player;

		setBackgroundImage("src\\main\\java\\resources\\images\\cloud_background.jpg");
		setPlaneImage("src\\main\\java\\resources\\images\\aircrafts\\aircraft_06.png");

		// Set up a PaintListener to draw the background image and instructions
		drawMenu();
		
		// Draw the image at 270 degrees, in west direction
		drawPlane(270);

		canvas.setFocus();

		canvas.addKeyListener(new EscapeKeyListener(this));
	}

	/*
	 * setBackgroundImage() Get and resize the backgroundimage
	 */
	public void setBackgroundImage(String path) {
		try {
			Image image = new Image(display, path);
			backgroundImage = ImageManager.resizeImage(image, canvas.getBounds().width, canvas.getBounds().height);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * setPlaneImage() Get the planeImage
	 */
	public void setPlaneImage(String path) {
		try {
			planeImage = new Image(display, path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void drawMenu() {
		if(menuPaintListenerActive) {
			return;
		}
		menuPaintListenerActive = true;
		
		canvas.addPaintListener(e -> {
			GC gc = e.gc;
			
			// 1. Draw the background

			// Window sizes and background image width
			Rectangle bounds = canvas.getBounds();
			int imageWidth = backgroundImage.getBounds().width;

			// Clear the canvas
			gc.setBackground(canvas.getBackground());
			gc.fillRectangle(bounds);

			offsetX = canvas.getBounds().width / 2 - backgroundImage.getBounds().width / 2
					- (int) dataHandler.getPlayer().getX();

			// Calculate the number of times the image needs to be drawn to cover the canvas
			int numImages = (bounds.width / imageWidth) + 2;

			// Draw the image
			for (int i = 0; i < numImages; i++) {
				int x = i * imageWidth + offsetX;
				gc.drawImage(backgroundImage, x, 0);
			}


			// 2. Add start game instruction text
			FontData fontData = new FontData("Arial", 18, SWT.NORMAL);

			// Dispose of the existing font to avoid memory leaks
			if (gc.getFont() != null) {
				gc.getFont().dispose();
			}

			Font font = new Font(canvas.getDisplay(), fontData);
			
			// Set the new Font to the GC
			gc.setFont(font);
			
			// Text to be displayed
			String startGameInstruction = "Press ESC key to start the game";

			// Calculate the position for the instruction font
			int textXCoordinate = (canvas.getBounds().width - gc.textExtent(startGameInstruction).x) / 2;
			int textYCoordinate = 3 * canvas.getBounds().height / 4;

			// Draw your health bar
			gc.drawText(startGameInstruction, textXCoordinate, textYCoordinate);

			// Dispose of the Font to free up resources
			font.dispose();

		});
	}
	
	/*	drawPlane()
	 * 	A function that draws a plane image on the canvas.
	 *  The Move method is called to make the airplane fly while the player has not started a new game.
	 */
	
	public void drawPlane(float degrees) {
		if(planePaintListenerActive) {
			return;
		}
		planePaintListenerActive = true;

	    planePaintListener = new PaintListener() {
	        @Override
	        public void paintControl(PaintEvent e) {
			GC gc = e.gc;

			// 1. Calculate positions for middle of the screen
			int x = canvas.getBounds().width / 2;
			int y = canvas.getBounds().height / 2;

			// 2. Get the transform and translate it to (x,y):
			Transform transform = new Transform(gc.getDevice());
			transform.translate(x, y);

			// 3. After this translation, do rotation:
			transform.rotate((degrees));

			// 4. Draw out the object centered in the transform's origo:
			gc.setTransform(transform);
			gc.drawImage(planeImage, -planeImage.getBounds().width / 2, -planeImage.getBounds().height / 2);

			// 5. Set the new transform as the identity transform and dispose the old one:
			gc.setTransform(null);
			transform.dispose();
	        }
		};
		 canvas.addPaintListener(planePaintListener);
	}

	/*	update()
	 * 	Call the Move method on the plane object and redraw the canvas. 
	 */
	
	public void update(float deltaTime) {
		player.moveObject(deltaTime);
		canvas.redraw();
	}
	
	/*	exit()
	 * 	Override page's exit to remove the paint listeners we have used. 
	 */
	@Override
	public void exit() {
		this.isRunning = false;
		removePaintListener(menuPaintListener);
		removePaintListener(planePaintListener);
	}
	

}
