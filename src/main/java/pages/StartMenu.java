package pages;

import org.eclipse.swt.SWT;
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

	public StartMenu(Display display, Shell shell, DataHandler dataHandler, Canvas canvas, Player player) {
		super(display, shell, dataHandler, canvas);
		this.player = player;

		// We want the player to go in an east direction
		player.setDegree(180);

		setBackgroundImage("src\\main\\java\\resources\\images\\cloud_background.jpg");
		setPlaneImage("src\\main\\java\\resources\\images\\aircrafts\\aircraft_06.png");

		// Set up a PaintListener to draw the background image
		canvas.addPaintListener(e -> {
			GC gc = e.gc;

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
				System.out.println("i " + i + " imagewidth " + imageWidth + " offx " + offsetX + " x " + x);
				gc.drawImage(backgroundImage, x, 0);
			}

			// Draw the image at 0 degrees, in east direction
			//drawPlane(90);

//			Button startGameButton = new Button(shell, SWT.PUSH);
//			startGameButton.setText("Start Game");
//			int buttonXCoordinate = canvas.getBounds().width/2;
//			int buttonYCoordinate = 3 * canvas.getBounds().height/4;
//			startGameButton.setBounds(buttonXCoordinate, buttonYCoordinate, startGameButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, startGameButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//
//			
//			startGameButton.addSelectionListener(new SelectionAdapter() {
//	            @Override
//	            public void widgetSelected(SelectionEvent e) {
//	                // Notify the main game application to start the game
//	                //startGame();
//	            }
//
//	        });

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

		canvas.setFocus();

		canvas.addKeyListener(new EscapeKeyListener(this));
	}

	/*
	 * setBackgroundImage() Get and resize the backgroundimage
	 */
	public void setBackgroundImage(String path) {
		try {
			Image image = new Image(display, path);
			// Calculating image sizes so that it is customized to the canvas height
//			int originalHeight = image.getBounds().height;
//			int originalWidth = image.getBounds().width;
//			
//			int newHeight = canvas.getBounds().height;
//			float scaleFactor = newHeight / originalHeight;
//			int newWidth = (int)(scaleFactor * originalWidth);

			// backgroundImage = ImageManager.resizeImage(image, newWidth, newHeight);
			backgroundImage = ImageManager.resizeImage(image, canvas.getBounds().width, canvas.getBounds().height);
			System.out.println("hello");
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

	public void drawPlane(float degrees) {
		canvas.addPaintListener(e -> {
			GC gc = e.gc;

			int x = canvas.getBounds().width / 2;
			int y = canvas.getBounds().height / 2;

			// 1. Get the transform and translate it to (x,y):
			Transform transform = new Transform(gc.getDevice());
			transform.translate(x, y);

			// 2. After this translation, do rotation:
			transform.rotate((degrees));

			// 3. Draw out the object centered in the transform's origo:
			gc.setTransform(transform);
			gc.drawImage(planeImage, -planeImage.getBounds().width / 2, -planeImage.getBounds().height / 2);

			// 4. Set the new transform as the identity transform and dispose the old one:
			gc.setTransform(null);
			transform.dispose();
		});
	}

	public void update(float deltaTime) {
		// Repaint the canvas
		player.moveObject(deltaTime);
		player.setOffsets(dataHandler.getPlayer().getXOffset(), dataHandler.getPlayer().getYOffset());
		canvas.redraw();
	}

}
