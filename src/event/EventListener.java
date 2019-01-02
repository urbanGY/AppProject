package event;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


import model.LoadData;
import model.NewPoint;
import model.StoreData;
import model.Tree;
import model.Tree.TreeNode;
import view.JPanel_center;
import view.JPanel_right;
import view.Menu;

public class EventListener implements ActionListener{
	  private JPanel_center centerPanel;
	  private JTextArea leftText;
	  private JPanel_right rightPanel;
	  private Menu menu;
	  private Tree tree;
	  
	  private TreeNode label;
	  private Component[] save = new JLabel[50];
	  private NewPoint point,newPoint;
	  
	  private static TreeNode root;
	  
	  private LoadData loadData;
	  private StoreData store;
	  
	  public EventListener() {
		System.out.println("generate event Listener!");  
	  }
	  
	  public void setLeftText(JTextArea leftText) {
		  this.leftText = leftText;
	  }
	  
	  public void setCenterPanel(JPanel_center centerPanel) {
		  this.centerPanel = centerPanel;
	  }
	   
	  public void setRightPanel(JPanel_right rightPanel) {
		  this.rightPanel = rightPanel;
	  }
	  
	  public void setMenu(Menu menu) {
		  this.menu = menu;
	  }
	  /*public EventListener(JPanel_center centerPanel) {	   
	      this.centerPanel = centerPanel;
	      System.out.println("how many?");
	  }
	   
	  public EventListener(JPanel_center centerPanel, JTextArea leftText,JPanel_right rightPanel) {//���� ��ư ������		  
		   this(centerPanel);		   
		   this.rightPanel=rightPanel;		   
		   this.leftText = leftText;
		   this.loadData = new LoadData(centerPanel,rightPanel,leftText,centerPanel.getStart(),centerPanel.getEnd());		   
	  }
	   
	  public EventListener(JPanel_center centerPanel,JPanel_right rightPanel) {
		   this(centerPanel);
		   this.rightPanel=rightPanel;
	  }*/

		
	  @Override
	  public void actionPerformed(ActionEvent e) {		 
		  if(e.getActionCommand().equals("����")) 
			  apply();			
		  
		  if(e.getActionCommand().equals("����")) 		
			change();
		  
		  if(e.getActionCommand().equals("���θ����"))
			  setNew();
		  
		   if(e.getActionCommand().equals("����"))
		      store(true);
		   
		   if(e.getActionCommand().equals("�ٸ� �̸����� ����"))
			  store(false);
		   
		   if(e.getActionCommand().equals("����"))
			  load();
		   
		   if(e.getActionCommand().equals("�ݱ�")) {
			   System.out.println("Exit!");
			   System.exit(0); 
		   }
		   
	   }
      private void change() {
          save = centerPanel.getComponents();         //�гο� �ִ� ��带 save�� ����
          for(int i= 0; i<save.length;i++) {      //�гο� �ִ� �����߿� ������ ��带 Ž���ϱ� ����.
             try{label = (TreeNode)save[i];         //MindMapPane�� �ִ� ���� ���� Node�� ��ȯ�� �� ���� ������ try catch�� �����.
             if(label.getText().equals(rightPanel.getTexts(0).getText())) 
                break;   //���� ���� ã���� ���´�.
             }catch(Exception E) {}
          }
          ////////////////////////�� ��ġ ������ ����
          point = new NewPoint(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2); //�ؽ�Ʈ�� ���� ���� �󺧿� �����ϱ� �� ���� ����.
          this.label.setLocation(Integer.parseInt(rightPanel.getTexts(1).getText()),Integer.parseInt(rightPanel.getTexts(2).getText()));   //�ؽ�Ʈ�� xy��ǥ ����
          this.label.setSize(Integer.parseInt(rightPanel.getTexts(3).getText()), Integer.parseInt(rightPanel.getTexts(4).getText()));      //�ؽ�Ʈ�� wh�� ����
          
          String setColor = rightPanel.getTexts(5).getText();   //�ؽ�Ʈ ���� Label�� Color�� �����ϱ����ؼ��� 0X�� �ʿ��ϱ� ������ �����ϱ� ���� ����.
          setColor="0X"+setColor;   //�տ� 0X�� �ٿ��ش�.
          this.label.setBackground(Color.decode(setColor));   //Label�� ���� ����
          this.label.setColor(setColor);      //ó������ ������ ������ �ٲ��ش�.
          
          ///////////////////////����� �� ����
          newPoint = new NewPoint(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2);   //�̵� �� ��ġ ����
          int changeX = (int)(newPoint.getX()-point.getX());      //�̵� ���� �̵� ���� x��ǥ ���� ����
          int changeY = (int)(newPoint.getY()-point.getY());      //�̵� ���� �̵� ���� y��ǥ ���� ����
          
          for(int i=0; i<centerPanel.getStart().size();i++) {      //����� ����
             Point tmp= new Point(centerPanel.getStart().get(i)); 
             if(point.equals(tmp)) {      //���� ���� �ִٸ� 
                centerPanel.getStart().remove(i);   //������ ��ġ�� ����
                centerPanel.getStart().add(i,newPoint);   //�̵� ���� ��ġ�� ����
             }
          }
          for(int i =0; i<centerPanel.getEnd().size(); i++)      //������(�� ����� ��ġ��)�� ����
             for(int j = 0; j<label.point.length;j++)
                if(centerPanel.getEnd().get(i).getX()==label.point[j].getX()&&centerPanel.getEnd().get(i).getY()==label.point[j].getY()) {   //point���������� Ž��
                   centerPanel.getEnd().remove(i);   //�ִٸ� ����
                   label.point[j].setLocation(label.point[j].getX()+changeX, label.point[j].getY()+changeY);  //����ȸ�ŭ ũ�⸦ ����
                   centerPanel.getEnd().add(i,label.point[j]);   //�����Ŀ� �ٽ� �Ȱ��� ��ġ�� �߰�.
                }
          
          centerPanel.updateUI();   //centerPanel�� �ҷ��� �ٽ� ���� �׸����Ѵ�.
          rightPanel.getPanel().updateUI();   //������ ���� ������ �ؽ�Ʈ���� �ٽ� ��Ÿ���� �Ѵ�.
          JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�!");
       }
	   
	   private void setNew() {
		 System.out.println("new !!");
		 centerPanel.removeAll();
		 centerPanel.setPreferredSize(new Dimension(600, 750));
		 leftText.setText("");
		 for(int i = 0 ; i < 6 ; i++)
			 rightPanel.getTexts(i).setText("");
		 root = null;
		 centerPanel.getStart().clear();
		 centerPanel.getEnd().clear();
		 centerPanel.setTopicLabel();
		 centerPanel.updateUI();
		 leftText.updateUI();
		 rightPanel.updateUI();
		 
		 if(menu.hasCurFile()) {
			 menu.removeInfo();
			 menu.updateUI();
		 }
			 
		 LoadData.setRecentNew();
	   }
	   
	   private void apply() {		 
		   
	     tree = new Tree(rightPanel,centerPanel,leftText.getText(),leftText,centerPanel.getStart(),centerPanel.getEnd());
	     root = tree.getRoot();
	     for(int i = 0 ; i < 6 ; i++)
			 rightPanel.getTexts(i).setText("");
		 rightPanel.updateUI();
	   }
	   
	   /**
	    * �׳� ���� 
	    * 
	    * 
	    * 
	    * 
	    * 
	    */
	   private void store(boolean defaultOption) {
		if(root == null) {
			JOptionPane.showMessageDialog(null, "�����ư�� �����ּ���");
			return;
		}				
		store = new StoreData(root);
	    if(store.start(defaultOption))
	    	JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�!");
	   }
	   
	   private void load() {
		setNew();
		loadData = new LoadData(centerPanel,rightPanel,leftText,centerPanel.getStart(),centerPanel.getEnd());
		root = loadData.start();
		String s = loadData.getRecentUrl();//�ҷ��� ���
		int count = 0;
		for(int i = s.length()-1 ; i > 0 ; i--) {//���� �̸� ����			
			if(s.charAt(i) == '\\') {
				count = i+1;
				break;
			}				
		}
		s = s.substring(count);
		menu.addInfo(s);
	   }


	}