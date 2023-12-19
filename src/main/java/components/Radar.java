package components;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import java.util.ArrayList;

public class Radar{
	private float detectionRadius;
	private int levels;
	private Player player;
	private int drawRadius;
	private boolean listener;
	private float totalTime;
	private float period;
	private float deltaTime;
	private ArrayList<Aircraft> aircrafts;
	
	public Radar(float detectionRadius,  Player player) {
		this.detectionRadius = detectionRadius;
		this.player = player;
		this.drawRadius = 300;
		this.listener = false;
		this.totalTime = 0;
		this.period = 4.0f;
		this.levels = 5;
	}
	
	public void drawRadar(Canvas canvas) {
		if(listener)
			return;
		listener = true;
		
		Color black = new Color(0, 0, 0);
		Color green = new Color(0, 155, 0);
		canvas.addPaintListener(e -> {
            if (!player.radarActive())
            	return;
            
            if (totalTime >= period) {
            	totalTime = 0;
            }
            totalTime += getDeltaTime();
            System.out.println(totalTime);
            
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
			float angle = timeFraction*360;
			int a = (int)(centerX + Math.cos(Math.toRadians(angle)) * drawRadius/2);
			int b = (int)(centerY + Math.sin(Math.toRadians(angle)) * drawRadius/2);
			gc.drawLine(centerX, centerY, a, b);
			
			gc.setBackground(green);
			gc.fillRectangle(a-5, b-5, 10, 10);
		});
	}
	
	public float getDeltaTime() {
		return this.deltaTime;
	}
	
	public void setDeltaTime(float deltaTime) {
		this.deltaTime = deltaTime;
	}
}
