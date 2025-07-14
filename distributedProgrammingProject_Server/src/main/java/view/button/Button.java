package view.button;

import javax.swing.JButton;
import java.awt.*;

public class Button extends JButton{

	public Button(String text) {
		setText(text);
		setBorderPainted(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
