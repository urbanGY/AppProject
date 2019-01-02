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
	   
	  public EventListener(JPanel_center centerPanel, JTextArea leftText,JPanel_right rightPanel) {//적용 버튼 생성자		  
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
		  if(e.getActionCommand().equals("적용")) 
			  apply();			
		  
		  if(e.getActionCommand().equals("변경")) 		
			change();
		  
		  if(e.getActionCommand().equals("새로만들기"))
			  setNew();
		  
		   if(e.getActionCommand().equals("저장"))
		      store(true);
		   
		   if(e.getActionCommand().equals("다른 이름으로 저장"))
			  store(false);
		   
		   if(e.getActionCommand().equals("열기"))
			  load();
		   
		   if(e.getActionCommand().equals("닫기")) {
			   System.out.println("Exit!");
			   System.exit(0); 
		   }
		   
	   }
      private void change() {
          save = centerPanel.getComponents();         //패널에 있는 노드를 save에 저장
          for(int i= 0; i<save.length;i++) {      //패널에 있는 노드들중에 변경할 노드를 탐색하기 위함.
             try{label = (TreeNode)save[i];         //MindMapPane에 있는 위에 라벨이 Node로 변환될 수 없기 때문에 try catch로 잡아줌.
             if(label.getText().equals(rightPanel.getTexts(0).getText())) 
                break;   //같은 라벨을 찾으면 나온다.
             }catch(Exception E) {}
          }
          ////////////////////////라벨 위치 사이즈 변경
          point = new NewPoint(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2); //텍스트에 적힌 값을 라벨에 적용하기 전 값을 저장.
          this.label.setLocation(Integer.parseInt(rightPanel.getTexts(1).getText()),Integer.parseInt(rightPanel.getTexts(2).getText()));   //텍스트의 xy좌표 저장
          this.label.setSize(Integer.parseInt(rightPanel.getTexts(3).getText()), Integer.parseInt(rightPanel.getTexts(4).getText()));      //텍스트의 wh값 저장
          
          String setColor = rightPanel.getTexts(5).getText();   //텍스트 값을 Label의 Color로 지정하기위해서는 0X가 필요하기 때문에 설정하기 위한 변수.
          setColor="0X"+setColor;   //앞에 0X를 붙여준다.
          this.label.setBackground(Color.decode(setColor));   //Label의 색을 지정
          this.label.setColor(setColor);      //처음색을 변경한 색으로 바꿔준다.
          
          ///////////////////////노드의 선 변경
          newPoint = new NewPoint(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2);   //이동 후 위치 저장
          int changeX = (int)(newPoint.getX()-point.getX());      //이동 전과 이동 후의 x좌표 차이 저장
          int changeY = (int)(newPoint.getY()-point.getY());      //이동 전과 이동 후의 y좌표 차이 저장
          
          for(int i=0; i<centerPanel.getStart().size();i++) {      //출발점 수정
             Point tmp= new Point(centerPanel.getStart().get(i)); 
             if(point.equals(tmp)) {      //같은 점이 있다면 
                centerPanel.getStart().remove(i);   //이전의 위치를 삭제
                centerPanel.getStart().add(i,newPoint);   //이동 후의 위치를 삽입
             }
          }
          for(int i =0; i<centerPanel.getEnd().size(); i++)      //도착점(각 노드의 위치점)을 수정
             for(int j = 0; j<label.point.length;j++)
                if(centerPanel.getEnd().get(i).getX()==label.point[j].getX()&&centerPanel.getEnd().get(i).getY()==label.point[j].getY()) {   //point점과같은걸 탐색
                   centerPanel.getEnd().remove(i);   //있다면 삭제
                   label.point[j].setLocation(label.point[j].getX()+changeX, label.point[j].getY()+changeY);  //변경된만큼 크기를 수정
                   centerPanel.getEnd().add(i,label.point[j]);   //수정후에 다시 똑같은 위치에 추가.
                }
          
          centerPanel.updateUI();   //centerPanel을 불러서 다시 선을 그리게한다.
          rightPanel.getPanel().updateUI();   //수정된 값을 오른쪽 텍스트들이 다시 나타내게 한다.
          JOptionPane.showMessageDialog(null, "변경되었습니다!");
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
	    * 그냥 저장 
	    * 
	    * 
	    * 
	    * 
	    * 
	    */
	   private void store(boolean defaultOption) {
		if(root == null) {
			JOptionPane.showMessageDialog(null, "적용버튼을 눌러주세요");
			return;
		}				
		store = new StoreData(root);
	    if(store.start(defaultOption))
	    	JOptionPane.showMessageDialog(null, "저장이 완료되었습니다!");
	   }
	   
	   private void load() {
		setNew();
		loadData = new LoadData(centerPanel,rightPanel,leftText,centerPanel.getStart(),centerPanel.getEnd());
		root = loadData.start();
		String s = loadData.getRecentUrl();//불러온 경로
		int count = 0;
		for(int i = s.length()-1 ; i > 0 ; i--) {//파일 이름 추출			
			if(s.charAt(i) == '\\') {
				count = i+1;
				break;
			}				
		}
		s = s.substring(count);
		menu.addInfo(s);
	   }


	}