package event;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class ColorListener extends MouseAdapter{
	JColorChooser chooser = new JColorChooser();
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			JTextField colorText = (JTextField)e.getSource();
			if(colorText.getText().length() == 0)
				return;			
			Color color = chooser.showDialog(null, "Color Picker", Color.decode("0x"+colorText.getText()));
			colorText.setText(Integer.toHexString(color.getRGB()).toUpperCase().substring(2));
		}else {
			System.out.println("duble click no!");
		}
		
	}
}
