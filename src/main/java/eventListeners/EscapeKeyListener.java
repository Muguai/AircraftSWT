package eventListeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;

import pages.Page;

public class EscapeKeyListener implements KeyListener {
	private final Page page;

	public EscapeKeyListener(Page page) {
		this.page = page;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.keyCode == SWT.ESC) {
			page.exit();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// Not to use but must be implemented
	}
}
