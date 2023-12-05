package aircraftgame;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Boilerplate code from chat GPT:
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("SWT Boilerplate");

        // Set layout for the shell
        shell.setLayout(new FillLayout());

        // Create a label widget
        Label label = new Label(shell, SWT.CENTER);
        label.setText("Hello, SWT!");

        // Set the size of the shell
        shell.setSize(300, 200);

        // Open the shell
        shell.open();

        // Event loop
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        // Dispose of the display when done
        display.dispose();
    }
}