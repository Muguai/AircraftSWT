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
	private ArrayList<int[]> aircraftPositions;
	private DataHandler dataHandler;
	private float prevPlayerX;
	private float prevPlayerY;
	
	private final String TRIANGLE_MARKER_PATH =  "src\\main\\java\\resources\\images\\triangle.png";
	private Image triangleImage;
	
	
	public Radar(float detectionRadius, DataHandler dataHandler) {
		this.detectionRadius = detectionRadius;
		this.player = dataHandler.getPlayer();
		this.drawRadius = 400;
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
	
	public void drawRadar(Canvas canvas) {
		if(listener)
			return;
		listener = true;
		
		Color black = new Color(0, 0, 0);
		Color green = new Color(0, 125, 0);
		canvas.addPaintListener(e -> {
            if (!player.radarActive())
            	return;
            
			GC gc = e.gc;
			
			float margin = 20.0f;
			int x = (int)(player.getDisplay().getBounds().width  - drawRadius - margin);
			int y = (int)(player.getDisplay().getBounds().height - drawRadius - margin);
			int centerX = (int)(x + drawRadius/2);
			int centerY = (int)(y + drawRadius/2);
			
			gc.setBackground(black);
			gc.fillOval(x, y, drawRadius, drawRadius);
			
			gc.setForeground(green);
			for(int i = 0; i < levels; i++) {
				int r = (int)(drawRadius/levels)*(i+1);
				gc.drawOval(centerX-r/2, centerY-r/2, r, r);
			}
			
			float timeFraction = totalTime / period;
			float angle = timeFraction*360-90;
			int a = (int)(centerX + Math.cos(Math.toRadians(angle)) * drawRadius/2);
			int b = (int)(centerY + Math.sin(Math.toRadians(angle)) * drawRadius/2);
			gc.drawLine(centerX, centerY, a, b);
			gc.setBackground(green);
			gc.fillRectangle(a-5, b-5, 10, 10);
			
			int playerX = (int)prevPlayerX + canvas.getBounds().width/2;
			int playerY = (int)prevPlayerY + canvas.getBounds().height/2;
			for(int i = 0; i < this.aircraftPositions.size(); i++) {
				int targetX = (int)this.aircraftPositions.get(i)[0];
				int targetY = (int)this.aircraftPositions.get(i)[1];
				float resX = (float)Math.pow(playerX - targetX, 2);
				float resY = (float)Math.pow(playerY - targetY, 2);
				float distance = (float)Math.sqrt(resX + resY);
				
				if(distance <= this.detectionRadius) {
					int enemyDist = (int)(drawRadius*(distance/detectionRadius));
					float enemyDegree = (float)Math.PI +(float)(Math.atan2(playerY - targetY, playerX - targetX));
					int enemyX = centerX + (int)(((float)Math.cos((enemyDegree))) * enemyDist/2);
					int enemyY = centerY + (int)(((float)Math.sin((enemyDegree))) * enemyDist/2);
					int gradialRed = (int)((detectionRadius-enemyDist)*255/this.detectionRadius);
					Color red = new Color(gradialRed, 0, 0);
					gc.setBackground(red);
					gc.fillOval(enemyX, enemyY, 10, 10);
					
				}
			}
			
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
		aircraftPositions = new ArrayList<int[]>();
		for(Aircraft aircraft : aircrafts) {
			if(!aircraft.friendly) {
				int[] positionTuple = {(int)aircraft.getX(), (int)aircraft.getY()};
				this.aircraftPositions.add(positionTuple);
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
