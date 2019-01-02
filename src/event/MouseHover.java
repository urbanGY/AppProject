package event;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.border.EmptyBorder;

import model.LoadData;

public class MouseHover extends MouseAdapter{
	
	@Override
	public void mouseEntered(MouseEvent e) {	
		if(e.getSource().getClass().getName().equals("javax.swing.JButton")) {
			JButton component = (JButton)e.getSource();
			component.setBackground(new Color(0x8E44AD));
			component.setForeground(new Color(0xffffff));
			component.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
			component.setToolTipText(explain(component.getText()));
		}else if(e.getSource().getClass().getName().equals("javax.swing.JLabel")){
			JLabel component = (JLabel)e.getSource();
			component.setToolTipText(explain(component.getText()));
		}else {
			JMenu component = (JMenu)e.getSource();
			component.setBackground(new Color(0x8E44AD));
			component.setForeground(new Color(0xffffff));
			component.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));			
		}			
		super.mouseEntered(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource().getClass().getName().equals("javax.swing.JButton")) {
			JButton component = (JButton)e.getSource();
			component.setBackground(new Color(0xBB8FCE));
			component.setForeground(new Color(0x000000));
			component.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		}else if(e.getSource().getClass().getName().equals("javax.swing.JLabel")){
			
		}else {		
			JMenu component = (JMenu)e.getSource();
			component.setBackground(new Color(0xBB8FCE));
			component.setForeground(new Color(0x000000));
			component.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		}		
		super.mouseExited(e);
	}
	private String explain(String data) {
		if(data.equals("���θ����")) {
			return "���ο� ���ε� ���� ����ϴ�.";
		}else if(data.equals("����")) {
			return "����� ���ε� ���� �ҷ��ɴϴ�.";
		}else if(data.equals("����")) {
			return "���� ���� �����մϴ�.";
		}else if(data.equals("�ٸ� �̸����� ����")) {
			return "���ε� ���� �ٸ� �̸����� �����մϴ�.";
		}else if(data.equals("�ݱ�")) {
			return "���ε� ���� �����մϴ�.";
		}else if(data.equals("����")) {
			return "Text Editor Pane�� �Էµ� ������ ������ ���ε���� ����ϴ�.";
		}else if(data.equals("����")) {
			return "���ε� ���� �Ӽ��� �����մϴ�.";
		}else if(data.substring(0,4).equals("  ����")){
			return " "+LoadData.getRecentUrl()+" ";
		}else {
			return "";
		}
	}
	
}
