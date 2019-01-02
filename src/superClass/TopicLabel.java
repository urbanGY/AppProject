package superClass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopicLabel extends JLabel{
	   public TopicLabel(String text,JPanel center,int top, int left, int right) {
	      setText(text);
	      setFont(new Font("����",Font.BOLD,29));
	      setForeground(Color.black);//���ڻ� ����
	      setHorizontalAlignment(JLabel.CENTER);//���� ��� ����
	      setBorder(BorderFactory.createMatteBorder(top , left, 2, right, Color.BLACK));
	      setBackground(new Color(0xA569BD));
          setOpaque(true);
	      if(text.equals(" Mind Map Pane ")) {
	            setSize(500,60);
	            System.out.println(center.getWidth());
	            setLocation(50,5);	                    
	         }
	         else
	         setPreferredSize(new Dimension(300, 60));
	   	 }	   
}