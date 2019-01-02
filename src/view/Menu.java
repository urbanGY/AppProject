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
		
		setBackground(new Color(0x8E44AD));//�޴��� ��
		setPreferredSize(new Dimension((int)this.getSize().getWidth(),40));
		setBorder(BorderFactory.createMatteBorder(2, 2, 1, 2, Color.BLACK));//�޴��� border		 
		mouseHover = new MouseHover();
		
		JMenu file = new JMenu("����");
		file.addMouseListener(mouseHover);
		file.setFont(new Font("����",Font.BOLD,15));
		file.setPreferredSize(new Dimension(50, 37));
		file.setVerticalAlignment(JMenu.CENTER);
		file.setHorizontalAlignment(JMenu.CENTER);
		
		JMenuItem makeNew = new JMenuItem("���θ����");
		makeNew.addActionListener(listener);  
		file.add(makeNew);
		
		JMenuItem open = new JMenuItem("����");		
		open.addActionListener(listener);		
		file.add(open); //�޴��ٿ� '����'�̶�� �޴��� �߰�
		
		JMenuItem store = new JMenuItem("����");		//TODO:����� ������ �� ������ ������ �װ�η� �����ϱ�, ���� �� leftText�� text���̰� ������ �����ֱ� 
		store.addActionListener(listener);		
		file.add(store); //�޴��ٿ� '����'�̶�� �޴��� �߰�
		
		JMenuItem renameStore = new JMenuItem("�ٸ� �̸����� ����");
		renameStore.addActionListener(listener);
		file.add(renameStore);
		
		JMenuItem exit = new JMenuItem("�ݱ�"); //'����'��� �޴������� ����  
		exit.addActionListener(listener);
		file.add(exit);  //'����' �޴� �ȿ� '����'��� �޴������� �߰� 
		 
		JMenu edit = new JMenu("����");
		edit.setFont(new Font("����",Font.BOLD,15));
		edit.setPreferredSize(new Dimension(48, 37));
		edit.setVerticalAlignment(JMenu.CENTER);
		edit.setHorizontalAlignment(JMenu.CENTER);
		edit.addMouseListener(mouseHover);
		JMenuItem apply = new JMenuItem("����");
		apply.addActionListener(listener);
		edit.add(apply);
		
		JMenuItem change = new JMenuItem("����");
		change.addActionListener(listener);
		edit.add(change);
				
		
		
		add(file);
		add(edit);
		listener.setMenu(this);
	 	}
	
		public void addInfo(String fileName) {
			if(fileName.length() != 0) {//�ҷ����Ⱑ ��ҵ� ��쿣 �������� ����
				curentFile = new JLabel("  ���� ���� : "+fileName);
				curentFile.setFont(new Font("����",Font.BOLD,15));
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