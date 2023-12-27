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
	 * 	This is the workhorse of the radar class.
	 *  
	 */
	
	public void drawRadar(Canvas canvas) {
		
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
			
			// 4. Set color to black and draw the radar background:
			gc.setBackground(black);
			gc.fillOval(x, y, drawRadius, drawRadius);
			
			// 5. Draw the equidistant levels of the radar object:
			gc.setForeground(green);
			for(int i = 0; i < levels; i++) {
				int r = (int)(drawRadius/levels)*(i+1);
				gc.drawOval(centerX-r/2, centerY-r/2, r, r);
			}
			
			// 6. Draw a horisontal and a vertical green line for the visuals:
			gc.drawLine(x, centerY, x+drawRadius, centerY);
			gc.drawLine(centerX, y, centerX, y + drawRadius);
			
			// 7. Draw the "scanning line" on the radar:
			float timeFraction = totalTime / period;
			float angle = timeFraction*360-90;
			int a = (int)(centerX + Math.cos(Math.toRadians(angle)) * drawRadius/2);
			int b = (int)(centerY + Math.sin(Math.toRadians(angle)) * drawRadius/2);
			gc.drawLine(centerX, centerY, a, b);
			gc.setBackground(green);
			gc.fillRectangle(a-5, b-5, 10, 10);
			
			// 8. Get the centered gameWorld coordinates of the player:
			int playerX = (int)prevPlayerX + canvas.getBounds().width/2;
			int playerY = (int)prevPlayerY + canvas.getBounds().height/2;
			
			// 9. Iterate over each position of the last update of enemy aircrafts:
			for(int i = 0; i < this.aircraftData.size(); i++) {
				
				// 10. Extract target coordinates and calculate a distance (euclidean distance):
				int targetX = (int)this.aircraftData.get(i)[0];
				int targetY = (int)this.aircraftData.get(i)[1];
				int friendly = (int)this.aircraftData.get(i)[2];
				float resX = (float)Math.pow(playerX - targetX, 2);
				float resY = (float)Math.pow(playerY - targetY, 2);
				float distance = (float)Math.sqrt(resX + resY);
				
				// 11. If distance is less than the detection radius, write out the enemies:
				if(distance <= this.detectionRadius) {
					
					// 12. Convert their ingame position into a degree and distance on the radar:
					int enemyDist = (int)(drawRadius*(distance/detectionRadius));
					float enemyDegree = (float)Math.PI +(float)(Math.atan2(playerY - targetY, playerX - targetX));
					int enemyX = centerX + (int)(((float)Math.cos((enemyDegree))) * enemyDist/2);
					int enemyY = centerY + (int)(((float)Math.sin((enemyDegree))) * enemyDist/2);
					
					// 13. Use the "virtual distance" to write enemies as closer as more red.
					int gradialHue = (int)((detectionRadius-enemyDist)*255/this.detectionRadius);
					Color redPlane = new Color(gradialHue, 0, 0);
					Color greenPlane = new Color(0, gradialHue, 0);
					
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
	
	public float getDeltaTime() {
		return this.deltaTime;
	}
	
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
