package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import event.EventListener;
import event.MouseHover;

public class Menu extends JMenuBar{ 
	private JLabel curentFile;
	private MouseHover mouseHover;
	public Menu(EventListener listener) { 
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		setBackground(new Color(0x8E44AD));//메뉴바 색
		setPreferredSize(new Dimension((int)this.getSize().getWidth(),40));
		setBorder(BorderFactory.createMatteBorder(2, 2, 1, 2, Color.BLACK));//메뉴바 border		 
		mouseHover = new MouseHover();
		
		JMenu file = new JMenu("파일");
		file.addMouseListener(mouseHover);
		file.setFont(new Font("돋움",Font.BOLD,15));
		file.setPreferredSize(new Dimension(50, 37));
		file.setVerticalAlignment(JMenu.CENTER);
		file.setHorizontalAlignment(JMenu.CENTER);
		
		JMenuItem makeNew = new JMenuItem("새로만들기");
		makeNew.addActionListener(listener);  
		file.add(makeNew);
		
		JMenuItem open = new JMenuItem("열기");		
		open.addActionListener(listener);		
		file.add(open); //메뉴바에 '파일'이라는 메뉴를 추가
		
		JMenuItem store = new JMenuItem("저장");		//TODO:저장시 기존에 연 파일이 있으면 그경로로 저장하기, 저장 후 leftText에 text보이게 역으로 보여주기 
		store.addActionListener(listener);		
		file.add(store); //메뉴바에 '파일'이라는 메뉴를 추가
		
		JMenuItem renameStore = new JMenuItem("다른 이름으로 저장");
		renameStore.addActionListener(listener);
		file.add(renameStore);
		
		JMenuItem exit = new JMenuItem("닫기"); //'종료'라는 메뉴아이템 생성  
		exit.addActionListener(listener);
		file.add(exit);  //'파일' 메뉴 안에 '종료'라는 메뉴아이템 추가 
		 
		JMenu edit = new JMenu("편집");
		edit.setFont(new Font("돋움",Font.BOLD,15));
		edit.setPreferredSize(new Dimension(48, 37));
		edit.setVerticalAlignment(JMenu.CENTER);
		edit.setHorizontalAlignment(JMenu.CENTER);
		edit.addMouseListener(mouseHover);
		JMenuItem apply = new JMenuItem("적용");
		apply.addActionListener(listener);
		edit.add(apply);
		
		JMenuItem change = new JMenuItem("변경");
		change.addActionListener(listener);
		edit.add(change);
				
		
		
		add(file);
		add(edit);
		listener.setMenu(this);
	 	}
	
		public void addInfo(String fileName) {
			if(fileName.length() != 0) {//불러오기가 취소된 경우엔 실행하지 않음
				curentFile = new JLabel("  현재 파일 : "+fileName);
				curentFile.setFont(new Font("돋움",Font.BOLD,15));
				curentFile.setForeground(new Color(0xffffff));
				curentFile.addMouseListener(mouseHover);
				this.add(curentFile);
			}			
		}
		
		public void removeInfo() {
			if(curentFile != null) {
				this.remove(curentFile);
				curentFile = null;
			}				
		}
		
		public boolean hasCurFile() {
			if(curentFile == null)
				return false;
			else 
				return true;
		}
	}