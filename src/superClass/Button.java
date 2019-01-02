package superClass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import event.EventListener;
import view.JPanel_center;
import view.JPanel_right;

public class Button extends JButton{
	   public Button(String text) {
	      setText(text);
	      setFont(new Font("돋움",Font.BOLD,30));
	      setPreferredSize(new Dimension(300,40));//최초 생성 크기 지정
	      setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
	      setBackground(new Color(0x6C3483));
	      setForeground(Color.white);
	      setFocusable(false);
	   }
	   
	   public Button(String text,JPanel_center centerPanel, JTextArea leftText,JPanel_right right, EventListener listener) {//left 적용 버튼 생성자
	      this(text);
	      listener.setCenterPanel(centerPanel);
	      listener.setLeftText(leftText);
	      listener.setRightPanel(right);
	      this.addActionListener(listener);
	   }
	   
	   public Button(String text,JPanel_center centerPanel, JPanel_right right, EventListener listener) {//right
		      this(text);
		      listener.setCenterPanel(centerPanel);
		      listener.setRightPanel(right);
		      this.addActionListener(listener);
		   }
	}