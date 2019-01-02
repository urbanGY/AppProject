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

public class JPanel_left extends ContentPanel{			//���� ������
	private JTextArea text;
	private Button button; 
	private JLabel top;
	
	public JPanel_left(Color color, Dimension size, LayoutManager layout, JPanel_center center,JPanel_right right,EventListener listener) {
		super(color,size,layout);
		
			//������ Ư������ ���� TextArea
		text = new JTextArea();
		text.setLineWrap(true);//textArea���� �� ���� �����ϰ� ����
		text.setBackground(new Color(0xBB8FCE));
		text.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		text.setFont(new Font("����",Font.BOLD,24));
		text.setTabSize(1);
		
		button = new Button("����",center,this.text, right,listener);	//��ư�� ���� �Է�
		top = new TopicLabel("Text Editor Pane",this,0,2,2);	//������ ǥ���� ��
		JScrollPane sc_text = new JScrollPane(text);	//textArea�� ��ũ�����ο� �־ �ٻ���
		sc_text.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
		UIManager uiManager = new UIManager();
		sc_text.getVerticalScrollBar().setUI(new ScrollBar());
		
		add(sc_text,"Center");		//�ؽ�Ʈ			
		add(top,"North");			//��ܶ�
		add(button,"South");			//�ϴܹ�ư
			
	
	}
	public JTextArea getLeftText() {
		return this.text;
	}
	
}
