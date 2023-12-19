package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Radar{
	private float detectionRadius;
	private int levels;
	private Player player;
	private float drawRadius;
	private boolean listener;
	private float totalTime;
	private float period;
	
	public Radar(float detectionRadius,  Player player) {
		this.detectionRadius = detectionRadius;
		this.player = player;
		this.drawRadius = 300.0f;
		this.listener = false;
		this.totalTime = 0;
		this.period = 1.0f;
	}
	
	public void drawRadar(Canvas canvas, float deltaTime) {
		if(listener)
			return;
		listener = true;
		
		canvas.addPaintListener(e -> {
            if (!player.radarActive())
            	return;
            
            
			GC gc = e.gc;            
			float margin = 20.0f;
			int x = (int)(player.getDisplay().getBounds().width  - drawRadius - margin);
			int y = (int)(player.getDisplay().getBounds().height - drawRadius - margin);
		
			gc.fillOval(x, y, 200, 200);
        });
	}
	
	
}
