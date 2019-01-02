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
		if(data.equals("새로만들기")) {
			return "새로운 마인드 맵을 만듭니다.";
		}else if(data.equals("열기")) {
			return "저장된 마인드 맵을 불러옵니다.";
		}else if(data.equals("저장")) {
			return "마인 맵을 저장합니다.";
		}else if(data.equals("다른 이름으로 저장")) {
			return "마인드 맵을 다른 이름으로 저장합니다.";
		}else if(data.equals("닫기")) {
			return "마인드 맵을 종료합니다.";
		}else if(data.equals("적용")) {
			return "Text Editor Pane에 입력된 내용을 적용해 마인드맵을 만듭니다.";
		}else if(data.equals("변경")) {
			return "마인드 맵의 속성을 변경합니다.";
		}else if(data.substring(0,4).equals("  현재")){
			return " "+LoadData.getRecentUrl()+" ";
		}else {
			return "";
		}
	}
	
}
