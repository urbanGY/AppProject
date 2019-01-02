package superClass;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;

public class ContentPanel extends JPanel{
	   public ContentPanel(Color color, Dimension size, LayoutManager layout) {
	      setBackground(color);
	      setMinimumSize(size);
	      setLayout(layout);
	   }
	}