package components;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import data.DataHandler;

import java.util.ArrayList;
import java.util.List;

public class Radar{
	private float detectionRadius;
	private int levels;
	private Player player;
	private int drawRadius;
	private boolean listener;
	private float totalTime;
	private float period;
	private float deltaTime;
	private ArrayList<int[]> aircraftData;
	private DataHandler dataHandler;
	private float prevPlayerX;
	private float prevPlayerY;
	private final String TRIANGLE_MARKER_PATH =  "src\\main\\java\\resources\\images\\triangle.png";
	private Image triangleImage;
	
	
	/*	[class constructor]	Radar()
	 * 	The radar object scans for enemies around the player and paints them onto the visuals of the radar as red dots.
	 * 	'drawRadius' defines how big the radius of the visual circle, comprising the radar, is.
	 *  'detectionRadius' is the radius at which the radar can detect enemies in the game world.
	 *  'period' is the time it takes to do a radar scan (and update positions).
	 *  'levels' is how many equidistant circles, i.e levels, that the radar has, eminating from the center.
	 *  In the very center of the radar there is a triangle image that represents the player and what direction they face.    
	 */
	
	public Radar(float detectionRadius, DataHandler dataHandler) {
		this.detectionRadius = detectionRadius;
		this.player = dataHandler.getPlayer();
		this.drawRadius = 300;
		this.listener = false;
		this.totalTime = 0;
		this.period = 2.0f;
		this.levels = 5;
		this.dataHandler = dataHandler;
    	deepCopyAircrafts(dataHandler.getAircrafts());
		this.dataHandler.setRadar(this);
		this.prevPlayerX = dataHandler.getPlayer().getX();
		this.prevPlayerY = dataHandler.getPlayer().getY();
		
		try {
			triangleImage = new Image(player.getDisplay(), TRIANGLE_MARKER_PATH); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/*	drawRadar()
	 * 	This is the workhorse of the radar class, we can divide the draw methods in 3 steps:
	 
	 *  Prework:
	 *  1. Make sure to set up only one paintListener.
	 *  2. Check if radar is toggled.
	 *  3. Calculate where to place the centerpoint of the radar on the screen.
	 *  4. Draw out equidistant circles to make it look like a radar.
	 *  5. Draw a horizontal and vertical line, again, to make it look better.
	 
	 *  Scanning Line: 
	 *  1. Sin(x) and Cos(x) can be used to create a point on the perifery of the circle.
	 *  2. We can then just draw a line from the center to the point: [Cos(angle), Sin(angle)]
	 
	 *  Load in enemies:
	 *  1. We can use the euclidean formula to calculate a distance to every aircraft.
	 *  2. We can then filter out aircrafts that are too far, that is to say, distance > detectionRadius.
	 *  3. We can calculate a quotient (distance/detectionRadius), where distance is the distance to the aircraft object.
	 *	4. Using this quotient we can then use it as a virtual distance by scaling with the radar radius.
	 *  5. Again we can use ArcTan(x) to compute an angle on the radar.
	 *  6. Use the virtual distance to make enemies closer more bright in colour. 
	 *  7. If aircraft.friendly == true, we draw the circle in green, else we draw it in red.		
	 */
	
	public void drawRadar(Canvas canvas) {
		
		// Prework:
		// 1. Set up only one listener:
		if(listener)
			return;
		listener = true;
		
		Color black = new Color(0, 0, 0);
		Color green = new Color(0, 85, 0);
		canvas.addPaintListener(e -> {
            
			// 2. Check if radar is toggled on:
			if (!player.radarActive())
            	return;
            
			// 3. Calculate coordinate points, such as the center of the visual radar:
			GC gc = e.gc;
			float margin = 20.0f;
			int x = (int)(player.getDisplay().getBounds().width  - drawRadius - margin);
			int y = (int)(player.getDisplay().getBounds().height - drawRadius - margin);
			int centerX = (int)(x + drawRadius/2);
			int centerY = (int)(y + drawRadius/2);
			
			// Set color to black and draw the radar background:
			gc.setBackground(black);
			gc.fillOval(x, y, drawRadius, drawRadius);
			
			// 4. Draw the equidistant levels of the radar object:
			gc.setForeground(green);
			for(int i = 0; i < levels; i++) {
				int r = (int)(drawRadius/levels)*(i+1);
				gc.drawOval(centerX-r/2, centerY-r/2, r, r);
			}
			
			// 5. Draw a horisontal and a vertical green line for the visuals:
			gc.drawLine(x, centerY, x+drawRadius, centerY);
			gc.drawLine(centerX, y, centerX, y + drawRadius);
			
			// Scanning Line:
			// 1. Calculate the angle:
			float timeFraction = totalTime / period;
			float angle = timeFraction*360-90;
			int a = (int)(centerX + Math.cos(Math.toRadians(angle)) * drawRadius/2);
			int b = (int)(centerY + Math.sin(Math.toRadians(angle)) * drawRadius/2);
			
			// 2. Draw a line from the center to the periphery:
			gc.drawLine(centerX, centerY, a, b);
			gc.setBackground(green);
			gc.fillRectangle(a-5, b-5, 10, 10);
			
			
			// Load in enemies:
			// Get the centered gameWorld coordinates of the player:
			int playerX = (int)prevPlayerX + canvas.getBounds().width/2;
			int playerY = (int)prevPlayerY + canvas.getBounds().height/2;
			
			// Iterate over each position of the last update of enemy aircrafts:
			for(int i = 0; i < this.aircraftData.size(); i++) {
				
				// 1. Extract target coordinates and calculate a distance (euclidean distance):
				int targetX = (int)this.aircraftData.get(i)[0];
				int targetY = (int)this.aircraftData.get(i)[1];
				int friendly = (int)this.aircraftData.get(i)[2];
				float resX = (float)Math.pow(playerX - targetX, 2);
				float resY = (float)Math.pow(playerY - targetY, 2);
				float distance = (float)Math.sqrt(resX + resY);
				
				// 2. If distance is less than the detection radius, write out the enemies (filtering):
				if(distance <= this.detectionRadius) {
					
					// 3. Calculate a distance quotient:
					float quotient = (distance/detectionRadius);
					
					// 4. Scale the quotient to create a virtual distance:
					int virtualDist = (int)(drawRadius*quotient);
					
					// 5. Use ArcTan to obtain the degree and use the virtual distance as a distance from the center point:
					float enemyDegree = (float)Math.PI +(float)(Math.atan2(playerY - targetY, playerX - targetX));
					int enemyX = centerX + (int)(((float)Math.cos((enemyDegree))) * virtualDist/2);
					int enemyY = centerY + (int)(((float)Math.sin((enemyDegree))) * virtualDist/2);
					
					// 6. Use the "virtual distance" to write enemies as closer as more bright:
					int gradialHue = (int)((detectionRadius-virtualDist)*255/this.detectionRadius);
					Color redPlane = new Color(gradialHue, 0, 0);
					Color greenPlane = new Color(0, gradialHue, 0);
					
					// 7. If friendly -> Green, else red:
					if(friendly == 0)
						gc.setBackground(redPlane);
					else
						gc.setBackground(greenPlane);
					gc.fillOval(enemyX, enemyY, 10, 10);
					
				}
			}
			
			// 14. Write out a rotatable triangle image to symbolize the player at the center of the radar.
			Transform transform = new Transform(gc.getDevice());
			transform.translate(centerX, centerY);
			transform.scale(0.15f, 0.15f);
			transform.rotate(dataHandler.getPlayer().getDegree()+90);
			gc.setTransform(transform);
			gc.drawImage(triangleImage, -triangleImage.getBounds().width/2, -triangleImage.getBounds().height/2);
			gc.setTransform(null);
			
		});
	}
	
	/*	deepCopyAircrafts()
	 * 	A method that iterates over the aircrafts in play and saves their current values as data.
	 *  For each radar update this method is called.
	 */
	
	public void deepCopyAircrafts(List<Aircraft> aircrafts) {
		aircraftData = new ArrayList<int[]>();
		for(Aircraft aircraft : aircrafts) {
			if(!(aircraft instanceof Player)) {
				int isFriend = 0;
				if(aircraft.friendly)
					isFriend = 1;
				
				int[] positionTuple = {(int)aircraft.getX(), (int)aircraft.getY(), isFriend};
				this.aircraftData.add(positionTuple);
			}
		}
	}
	
	
	/*	getDeltaTime()
	 * 	A getter for the delta time.
	 */
	
	public float getDeltaTime() {
		return this.deltaTime;
	}
	
	
	/*	setDeltaTime()
	 * 	A setter method for the delta time.
	 */
	public void setDeltaTime(float deltaTime) {
		this.deltaTime = deltaTime;
        if (totalTime >= period) {
        	totalTime = 0;
        	deepCopyAircrafts(dataHandler.getAircrafts());
        	prevPlayerX = dataHandler.getPlayer().getX();
        	prevPlayerY = dataHandler.getPlayer().getY();
        }
        totalTime += getDeltaTime();
	}
}
