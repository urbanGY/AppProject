package superClass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBar extends BasicScrollBarUI{
	protected void configureScrollBarColors() {
        thumbRect.width = 5;
        trackRect.width = 5;
        
        thumbColor = new Color(0x8E44AD);
        thumbDarkShadowColor = new Color(0x000000);
        thumbHighlightColor = new Color(0x000000);
        thumbLightShadowColor = new Color(0x000000);
        
        trackColor = new Color(0xBB8FCE);
        trackHighlightColor = new Color(0x000000);
        
        
   }
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new BasicArrowButton(orientation);
		button.setBackground(new Color(0xBB8FCE));
		button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0xBB8FCE)));
		return button;
	}
	
	@Override	
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new BasicArrowButton(orientation);
		button.setBackground(new Color(0xBB8FCE));
		button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0xBB8FCE)));
		return button;
	}
	
}
