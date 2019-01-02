package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import event.EventListener;
import superClass.Button;
import superClass.ContentPanel;
import superClass.ScrollBar;
import superClass.TopicLabel;

public class JPanel_left extends ContentPanel{			//왼쪽 프레임
	private JTextArea text;
	private Button button; 
	private JLabel top;
	
	public JPanel_left(Color color, Dimension size, LayoutManager layout, JPanel_center center,JPanel_right right,EventListener listener) {
		super(color,size,layout);
		
			//계절과 특성들을 넣을 TextArea
		text = new JTextArea();
		text.setLineWrap(true);//textArea에서 꽉 차면 개행하게 만듬
		text.setBackground(new Color(0xBB8FCE));
		text.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		text.setFont(new Font("돋움",Font.BOLD,24));
		text.setTabSize(1);
		
		button = new Button("적용",center,this.text, right,listener);	//버튼에 적용 입력
		top = new TopicLabel("Text Editor Pane",this,0,2,2);	//맨위에 표시할 라벨
		JScrollPane sc_text = new JScrollPane(text);	//textArea를 스크롤패인에 넣어서 휠생성
		sc_text.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
		UIManager uiManager = new UIManager();
		sc_text.getVerticalScrollBar().setUI(new ScrollBar());
		
		add(sc_text,"Center");		//텍스트			
		add(top,"North");			//상단라벨
		add(button,"South");			//하단버튼
			
	
	}
	public JTextArea getLeftText() {
		return this.text;
	}
	
}
