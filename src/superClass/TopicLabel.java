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
	      setFont(new Font("돋움",Font.BOLD,29));
	      setForeground(Color.black);//글자색 검정
	      setHorizontalAlignment(JLabel.CENTER);//글자 가운데 정렬
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