package view;

import event.EventListener;
import event.MouseHover;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class ToolBar extends JToolBar{
	public ToolBar(EventListener listener) {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));	
		setBackground(new Color(0xBB8FCE));//���� ��
		setPreferredSize(new Dimension((int)this.getSize().getWidth(),40));//���� ũ�� ����
		setBorder(BorderFactory.createMatteBorder(1, 2, 2, 2, Color.BLACK));//���� border
		String[] menu = {"���θ����", "����", "����", "�ٸ� �̸����� ����", "�ݱ�", "����", "����"};
		MouseHover mouseHover = new MouseHover();
		
		for(int i = 0 ; i < menu.length ; i++) {
			JButton button = new JButton(menu[i]);			
			button.setBorder(new EmptyBorder(new Insets(0,0,0,0)));			
			button.setFont(new Font("����",Font.BOLD,15));			
			button.setPreferredSize(new Dimension((menu[i].length()+2)*12, 37));
			button.setVerticalAlignment(JButton.CENTER);
			button.setHorizontalAlignment(JButton.CENTER);
			button.setBackground(new Color(0xBB8FCE));
			button.addActionListener(listener);
			button.addMouseListener(mouseHover);
			button.setFocusable(false);
			add(button);
		}
		
	}
}
