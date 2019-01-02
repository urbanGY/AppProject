package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import event.LabelListener;
import model.Tree.TreeNode;
import view.JPanel_center;
import view.JPanel_right;


public class Tree {
   private JPanel_center centerPanel;//가운데 마인드맵 패널 참조
   private JPanel_right rightPanel;//우측 속성 패널 참조
   private JTextArea leftText;//좌측 TextArea 참조
   private Controller controller;//트리노드 제어 
   private Component[] save;
   private TreeNode changeNode;
   private Point startPoint;
   private Vector<Point> start;
   private Vector<Point> end;
   private String[] colorString = {"0xEB984E","0xF4D03F","0x58D68D","0x5DADE2","0x5499C7","0xA569BD"};   //노드의 색 지정하기위한 스트링배열 "0xEC7063",
      
   public Tree(JPanel_right rightPanel, JPanel_center centerPanel,String data,JTextArea leftText,Vector<Point> Start, Vector<Point> end) {//적용버튼시 생성자
      this.rightPanel=rightPanel;//우측 패널 참조값 할당
      this.centerPanel=centerPanel;//가운데 마인드맵 띄어줄 패널 참조값 할당
      this.leftText = leftText;//좌측 textArea참조값 할당
      controller = new Controller(Start,end);//트리노드를 제어할 컨트롤러 생성      
      controller.run(data);//넘어온 데이터를 기반으로 기본 트리 생성시작
   }
      
   public Tree(JPanel_right rightPanel, JPanel_center centerPanel,Document doc,JTextArea leftText, Vector<Point> Start, Vector<Point> end) {//파일 열기 생성자
      this.centerPanel = centerPanel;
      this.rightPanel = rightPanel;
      this.leftText = leftText;
      controller = new Controller(Start,end);
      controller.run(doc);//xml파일에서 파싱한 document객체를 매개변수로 넘긴다.
   }
   
   public class TreeNode extends JLabel {//라벨을 상속받은 트리노드         
         private TreeNode sibling;//노드의 형제 참조  
         private TreeNode child;//노드의 자식 참조         
         private int childNum;//노드가 가진 자식의 수
         private int childCount;
         private String firstColor;//노드의 색      
         int siblingNum;//노드의 형제의 수
         private int level;//노드의 레벨
         public Point[] point;//노드의 좌표
         private double[] pointPie;
         
         /*
          * red 0xEC7063
          * orange 0xEB984E
          * yellow 0xF4D03F
          * green 58D68D
          * blue 5DADE2
          * indigo 5499C7
          * purple A569BD
          */
            
         
         public TreeNode(String data, int level, int childNum) {//노드 생성과 동시에 텍스트,레벨,자식의 수를 초기화한다.
            this.setText(data);
            this.sibling = null;
            this.child = null;
            this.level = level;            
            this.childNum = childNum;
         }
         
         public void setSibling(TreeNode sibling) {//현제 노드의 형제 설정
               this.sibling = sibling;
         }         
         public TreeNode getSibling() {//현재 노드의 형제 반환
               return this.sibling;
         }
         
         public void setChild(TreeNode child) {//자식 설정
               this.child = child;
         }         
         public TreeNode getChild() {//현재 노드의 자식 반환
               return this.child;
         }
         
         public int getChildNum() {//현재 노드의 자식의 수 반환
            return this.childNum;
         }         
         
         public int getSbling() {//현재 노드의 형제의 수 반환
            return this.siblingNum;
         }               
               
                  
         public void setRootXY(int i, int j) {//노드의 좌표 설정
            this.setLocation(i, j);
         }
         
         public void setColor(String color) {//노드의 색 string형태 필드로 보관
            this.firstColor = color;
         }
         public int getx() {return this.getX();}//노드의 x좌표 반환
         public int gety() {return this.getY();}//노드의 y좌표 반환
         public int getw() {return this.getWidth();}//노드의 width반환
         public int geth() {return this.getHeight();}//노드의 height반환
         public String getColor() { return this.firstColor; }//노드의 색 string형태로 반환
         public int getLevel() {return this.level; }//노드의 계층(레벨)반환
                                  
      }

   private class Controller {//노드의 트리구조를 만들어줄 제어 클래스
         private TreeNode root;//트리의 루트
         private TreeNode last;//가장 최근에 추가된 노드 가리킴
         private TreeNode parent;//현재 가리키는 노드의 부모노드 가리킴
         private int lastHeight;//가장 최근에 추가된 노드의 레벨
         private Scanner in;
         private LabelListener labelListener=new LabelListener(rightPanel,centerPanel,start,end);//마인드맵에 띄워질 라벨에 걸어줄 리스너 생성
         Dimension rememberSize = new Dimension();
         
         public Controller(Vector<Point> tmpStart,Vector<Point> tmpEnd) {//선을 이을때 필요한 시작점 끝점 벡터를 매개변수로 받아옴
            this.root = null;
            this.last = null;
            this.parent = null;
            this.lastHeight = -1;
            start = tmpStart;
            end = tmpEnd;//단순 초기화
         }
         
         public void run(String data) {//적용 버튼을 눌렀을때 실행
           
           centerPanel.getEnd().clear();
           centerPanel.getStart().clear();//선을 이을 때 필요한 좌표를 가지는 벡터 둘을 초기화해준다.
           rememberSize = centerPanel.getPreferredSize();
           centerPanel.removeAll();//이전에 있을지도 모르는 centerPanel의 객체를 모두 제거해놓는다.
           centerPanel.setPreferredSize(rememberSize);
           centerPanel.add(centerPanel.getTop());//위의 과정에서 사라지는 minMapPane은 필요함으로 다시 생성하여 붙인다.
           if(data.length() == 0) {//아예 입력이 없는 경우에는 트리를 만들 필요도 없음으로 실행하지 않는다              
              return;
           }
              
           in = new Scanner(data);         
           Vector<Integer> childNum = setChildNumber(data);//들어온 데이터를 기반으로 트리를 만들기 전에 각 줄에 노드 후보들이 가지는 자식의 수를 순서대로 벡터에 저장한다. 이후 트리 생성시 매개변수로 넘겨서 노드를 생성한다.
            if(setRoot(in.nextLine(),childNum.remove(0))) {//첫 인풋이 띄어쓰기가 들어가있는 경우                
               leftText.setText("");
               leftText.updateUI();//다음 입력을 위해 textArea를 초기화해준다.
               return;//잘못된 입력임으로 실행하지 않는다.
            }
               
            String s;//한줄씩 읽을 때 쓸 스트링
            while(true) {
               try{
                  s=in.nextLine();//한줄 씩 읽고                  
                  setTreeNode(s,null,childNum.remove(0));//트리노드를 만들며 매개변수로 다음과 같이 넘어간다. 입력 스트링,(두번째는 xml파일을 읽을 때 넘어오는 document객체다. 이번 경우엔 없음으로 null),아까 계산한 노드의 자식의 수를 반환하고 지운다.                   
            }catch(Exception e){//더이상 읽을 스트링이 없거나, 루트 이후에 tab이 없이 입력된 경우 예외를 발생시키고 실행을취소한다.               
               System.err.println(e.getMessage());//에러 내용
               if(e.getMessage().equals("second input error")) {//루트 이후에 tab없는 입력이 들어온 경우 
                  JOptionPane.showMessageDialog(null, "잘못된 입력입니다. 다시 입력해주세요!");//에러메세지를 띄워준다
                  centerPanel.getPreferredSize();
                  centerPanel.removeAll();
                  centerPanel.setTopicLabel();
                  centerPanel.updateUI();//centerPanel을 초기화해준다.
                  start.clear();
                  end.clear();//선을 이을 때 필요한 벡터들을 초기화해준다.
                  leftText.setText("");
                  leftText.updateUI();//textArea 초기화
               }               
               break;
               }
            }
            showAll();//트리가 제대로 만들어졌는지 순회로 확인한다.
            centerPanel.updateUI();//만들어진 트리노드를 가운데 패널에 올린 것을 반영한다.            
         }
         
         private Vector<Integer> setChildNumber(String data){//트리노드 생성 이전에 각 노드후보들이 가지는 자식의 수를 사전에 벡터에 순서대로 저장해 위의 매개변수로 넘겨줄 때 큐 처럼 사용한다.
            Scanner in = new Scanner(data);
            Vector<Integer> vec = new Vector<>();//노드 후보의 level을 순서대로 저장
            Vector<Integer> childNum = new Vector<>();//노드 후보의 자식의 수를 저장
            int index = 0;//입력 스트링의 줄 순서 제어            
            while(true) {
               try {
                  String s = in.nextLine();               
                  int count = 0;//tab이 몇번 들어가는지 저장
                  childNum.add(index,0);//index번째 벡터를 추가하며 기본 값은 0이다.(자식이 없다 일단은...)
                  
                  for(int i = 0 ; i < s.length() ; i++)//tab으로 level계산 후 count에 저장
                     if(s.charAt(i) == '	')
                        count++;
                     else
                        break;
                  
                  vec.add(index,count);//index번째 계층구조 저장용 벡터를 추가
                  if(count > 0) {//루트후보가 아닐 때
                    for(int j = index-1 ; j >= 0 ; j--) {//현재 줄부터 첫번째 줄 까지 역순으로
                       if(vec.get(j)<count) {//현재 노드의 level보다 더 작은(부모인)가장 가까운 노드를 찾는다
                          int num = childNum.get(j)+1;//
                          childNum.set(j, num);//찾은 해당 부모 노드의 자식의 수를 1 더해준다
                          break;
                       }                       
                    }
                  }
                  index++;
            } catch (Exception e) {//더 이상 읽을 줄이 없으면 멈춘다.
               break;
            }               
            }
            return childNum;//순서대로 만들어진 자식의 수를 담은 벡터를 반환한다.
         }
         
         public void run (Document doc) {//xml파일에서 documnet객체를 불러와 이를 기반으로 트리를 만들 경우
            centerPanel.getEnd().clear();
            centerPanel.getStart().clear();
            centerPanel.removeAll();
            centerPanel.add(centerPanel.getTop());//기존에 있을지도 모르는 가운데 패널을 초기화해줌
            String forLeftText = "";//document를 파싱한 후 text들을 모아서 좌측 textArea에 띄워줄 예정 
            try {               
               NodeList list = doc.getElementsByTagName("child");//xml테그네임이 child인 모든 속성을 노드리스트에 저장함               
               for(int i = 0 ; i < list.getLength() ; i++) {
                  String[] dummy = new String[8];//트리를 생성할 때 필요한 각 속성들
                  for(Node node = list.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {                     
                                        
                     if(node.getNodeName().equals("text"))//text            
                        dummy[0] = node.getTextContent();                                             
                     else if (node.getNodeName().equals("xpos"))//x좌표
                        dummy[1] = node.getTextContent();
                     else if (node.getNodeName().equals("ypos"))//y좌표
                        dummy[2] = node.getTextContent();
                     else if (node.getNodeName().equals("width"))//너비
                        dummy[3] = node.getTextContent();
                     else if (node.getNodeName().equals("height"))//높이
                        dummy[4] = node.getTextContent();
                     else if (node.getNodeName().equals("color"))//색
                        dummy[5] = node.getTextContent();
                     else if (node.getNodeName().equals("level"))//계층
                        dummy[6] = node.getTextContent();
                     else if (node.getNodeName().equals("childNum"))//자식의 수 등을 불러와 스트링 배열에 각각 저장해준다.
                        dummy[7] = node.getTextContent();
                  }   
                  if(i == 0) {
                        forLeftText += dummy[0]+"\n";//이중 좌측 textArea에 띄워줄 text들을 개행구분을 하여 저장해준다.
                     setRoot(dummy);//루트 생성
                  }else {                     
                     String blank = "";
                     for(int q = 0 ; q < Integer.parseInt(dummy[6]); q++)//계층만큼 스트링에 tab을 추가해준다
                        blank += '	';
                     dummy[0] = blank+dummy[0];
                     forLeftText += dummy[0]+"\n";// 좌측 textArea에 띄워줄 text들을 개행구분을 하여 저장해준다.
                     setTreeNode(dummy[0],dummy,Integer.parseInt(dummy[7]));//트리노드를 만들며 속성값이 될 dummy를 넘겨준다.(스트링,더미 전체,level)
                  }                                    
               }
         } catch (Exception e) {
            System.err.println("xml파일을 불러오는데 실패했습니다... ㅠㅠ");
         }
            centerPanel.updateUI();//만들어진 트리가 올라간 가운데 패널을 반영한다            
            leftText.setText(forLeftText);//좌측 textArea에 입력 폼을 복원해준다.
            leftText.updateUI();//해당 내용 반영
         }
         
         private boolean setRoot(String data,int childNum) {//적용 버튼시 사용되는 함수            
           if(data.charAt(0) == '	') {//아예 처음부터 tab이 들어간 경우 실행하지 않는다.
              JOptionPane.showMessageDialog(null, "잘못된 입력입니다. 다시 입력해주세요!");
              return true;
           }
           root = new TreeNode(data,0,childNum);//루트 노드를 생성한다.
            root.setRootXY(240, 200);//루트노드의 좌표
            MakeTreeNode(root,root,1);
            last = root;
            lastHeight = 0;
            return false;
         }
         
         private void setRoot(String[] dummy) {//파일 불러올 때 사용되는 함수            
            root = new TreeNode(dummy[0],0,Integer.parseInt(dummy[7])); 
            MakeTreeNode(root,root,1,dummy);            
            last = root;            
            lastHeight = 0;            
         }
         
         private void setTreeNode(String data, String[] dummy, int childNum) throws Exception {      
           
            int curHeight = 0;
            for(int i = 0 ; i < data.length() ; i++)
               if(data.charAt(i) == '	')
                  curHeight++;
               else 
                  break;
            if(curHeight == 0) {
               throw new Exception("second input error");
            }
            data = data.substring(curHeight);   
            TreeNode temp = new TreeNode(data,curHeight,childNum);
            if(lastHeight == curHeight) {
              parent.childCount++;
              last.setSibling(temp);
               last = last.getSibling();
               last.siblingNum=parent.childCount;                                      
              
              if(dummy == null)
                 MakeTreeNode(last,parent,curHeight);
              else 
                 MakeTreeNode(last,parent,curHeight,dummy);//dummy
                 
            }else if(lastHeight < curHeight) {//자식 노드 생성일 경우
               last.setChild(temp);
               parent = last;
               last = last.getChild();
               lastHeight = curHeight;
               parent.childCount++;
               last.siblingNum=parent.childCount;                              
               
               if(dummy == null)
                  MakeTreeNode(last,parent,curHeight);
               else
                  MakeTreeNode(last,parent,curHeight,dummy);//dummy
               
            }else if(lastHeight > curHeight) {//다른 부모 노드일 경우
               last = root;
               for(int i = 0 ; i < curHeight ; i++) {
                  parent = last;
                  last = last.getChild();
                  while(last.getSibling() != null) {
                     last = last.getSibling();
                  }
               }
               last.setSibling(temp);
               last = last.getSibling();
               lastHeight = curHeight;
               
               parent.childCount++;
               last.siblingNum=parent.childCount;                 
               
               if(dummy == null)
                  MakeTreeNode(last,parent,curHeight);
               else
                  MakeTreeNode(last,parent,curHeight,dummy);//dummy
            }   
            
         }
         
         public void showAll() {
            traversal(root);
         }
         
         private void traversal(TreeNode TreeNode) {
            if(TreeNode == null) return;
            traversal(TreeNode.getSibling());//형제 순회
            traversal(TreeNode.getChild());//자식 순회      
         }
         
            void MakeTreeNode(TreeNode node,TreeNode Parent,int height) {     
               if(Parent.childNum == 0)
                  Parent.childNum = 1;
               double angleSize=0; 
               if(Parent.childNum >3)
                    angleSize = 360/Parent.childNum;   //자식의 수를 기반으로 360도를 나눈다.
                else
                   angleSize =90;
                double XYpie = 240-angleSize*(Parent.childCount-1)-angleSize*(Parent.siblingNum) + 25*(height-1);   //시작의 각도를 설정한다.
                node.point = new Point[node.childNum];   //노드가 가지고있는 포인트의 수 자식의 수 만큼 정해준다.
         
                node.setFont(node.getFont().deriveFont(13.0f-(height-1)));      //글씨 크기 결정
                if(node.getFont().getSize()<6)
                   node.setFont(node.getFont().deriveFont(5.0f));
                node.setBorder(BorderFactory.createLineBorder(Color.BLACK));    //테두리 결정
                node.addMouseListener(labelListener);   //노드가 변경되는것을 반영하기 위한 마우스 리스너를 달아준다
                node.addMouseMotionListener(labelListener);   //노드가 드래그시에 변경하는 것을 반영하기위한 모션리스너
                node.setHorizontalAlignment(JLabel.CENTER);   //글씨를 가운데 정렬
                node.setOpaque(true);   //노드의 색입히는 것을 찬성
                node.setSize(35-3*(height-1),35-3*(height-1));      //사이즈 설정
                if(node.getSize().getWidth()<11)
                   node.setSize(10, 10);
                int distance = 220-50*height;
                   if(distance < 150)
                      distance = 100;
                   double cal = (Math.cos(Math.toRadians(XYpie))*(distance));
                   double cul = (Math.sin(Math.toRadians(XYpie))*(distance));
                if(node!=Parent) {            //root가 아닐 때 색 지정과 좌표설정
                   node.setLocation(Parent.getX()+(int)(Math.cos(Math.toRadians(XYpie))*(distance)),Parent.getY()+(int)(Math.sin(Math.toRadians(XYpie))*(distance)));                                   
                   node.setBackground(Color.decode(colorString[height%6]));   //colorString에 있는 색으로 라벨의 색을 설정
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());      //노드의 변경 이전 색을 기억
                   node.firstColor = node.firstColor.substring(2);      //RGB값으로 받았을 떄 alpha를 제거하기위한 방법
                   node.firstColor = "0X"+node.firstColor;      //alpha제거후 색을 바꿀 때 사용할 수 있도록 0x를 붙여준다.
                 }
                else {   ///Root일 떄 별도록 설정
                   node.setLocation(270,350);
                   node.setBackground(Color.decode("0xEC7063"));
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());
                   node.firstColor = node.firstColor.substring(2);
                   node.firstColor = "0X"+node.firstColor;
                }
                node.pointPie = new double[node.point.length];
                for(int i =0; i<node.point.length;i++) {            //childNum만큼 설정한 point 배열에 포인트점 저장
                   node.pointPie[i] = 240-angleSize*i + 25*(height-1);   //첫 위치 +이후의 식은 부모와 겹치는 것을 막기위해 추가의 설정
                   node.point[i] = new Point((node.getX()+node.getWidth()/2)+(int)(Math.cos(Math.toRadians(node.pointPie[i]))*(node.getWidth()/2)),(node.getY()+node.getHeight()/2)+(int)(Math.sin(Math.toRadians(node.pointPie[i]))*(node.getHeight()/2)));
                }   //노드의 중심을 기준으로 원을 그려 원위에 좌표를 구해서 수많은 자식들을 설정하기위한 방식.
                
                centerPanel.add(node);   //노드를 패널에 띄워준다.
                
                if(node!=Parent) {      //루트가 아닐 때 선을 그리기 위한 두 점을 입력한다.
                   startPoint = new Point(node.getX()+node.getWidth()/2, node.getY()+node.getHeight()/2);
                   start.add(startPoint);   //시작점 
                   end.add(parent.point[parent.childCount-1]);   //끝나는점
                }
                
                if(node.getX()<0) {
                   int xSaver= node.getX();
                   int ySaver = node.getY();
                   save = centerPanel.getComponents();         //패널에 있는 노드를 save에 저장
                   centerPanel.setPreferredSize(new Dimension((int)centerPanel.getPreferredSize().getWidth()+80, (int)centerPanel.getPreferredSize().getHeight()+80));
                   for(int i= 0; i<save.length;i++) {      //패널에 있는 노드들중에 변경할 노드를 탐색하기 위함.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane에 있는 위에 라벨이 Node로 변환될 수 없기 때문에 try catch로 잡아줌.
                         changeNode.setLocation(changeNode.getX()+(-2)*xSaver,changeNode.getY());
                         for(int j =0; j<changeNode.point.length;j++) {
                              changeNode.point[j] = new Point((changeNode.getX()+changeNode.getWidth()/2)+(int)(Math.cos(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getWidth()/2)),(changeNode.getY()+changeNode.getHeight()/2)+(int)(Math.sin(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getHeight()/2)));
                         }
                      }catch(Exception E) {}
                   }
                   for(int i =0; i<start.size();i++) {
                      System.out.println("i가 몇번 ? "+i);
                      startPoint = start.remove(i);
                     startPoint.setLocation(startPoint.getX()+(-2)*xSaver, startPoint.getY());
                     start.add(i, startPoint);
               
                     startPoint = end.remove(i);
                     startPoint.setLocation(startPoint.getX()+(-2)*xSaver, startPoint.getY());
                     end.add(i, startPoint);
                
                   }
                }if(node.getY()<50) {
                   int xSaver= node.getX();
                   int ySaver = 130;
                   save = centerPanel.getComponents();         //패널에 있는 노드를 save에 저장
                   centerPanel.setPreferredSize(new Dimension((int)centerPanel.getPreferredSize().getWidth()+80, (int)centerPanel.getPreferredSize().getHeight()+80));
                   centerPanel.updateUI();
                   for(int i= 0; i<save.length;i++) {      //패널에 있는 노드들중에 변경할 노드를 탐색하기 위함.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane에 있는 위에 라벨이 Node로 변환될 수 없기 때문에 try catch로 잡아줌.
                         changeNode.setLocation(changeNode.getX(),changeNode.getY()+ySaver);
                         for(int j =0; j<changeNode.point.length;j++) {
                              changeNode.point[j] = new Point((changeNode.getX()+changeNode.getWidth()/2)+(int)(Math.cos(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getWidth()/2)),(changeNode.getY()+changeNode.getHeight()/2)+(int)(Math.sin(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getHeight()/2)));
                         }
                      }catch(Exception E) {}
                   }
                   for(int i =0; i<start.size();i++) {
                      startPoint = start.remove(i);
                     startPoint.setLocation(startPoint.getX(), startPoint.getY()+ySaver);
                     start.add(i, startPoint);
               
                     startPoint = end.remove(i);
                     startPoint.setLocation(startPoint.getX(), startPoint.getY()+ySaver);
                     end.add(i, startPoint);
                
                   }
                centerPanel.updateUI();
                }
                else if(node.getX()+node.getWidth()>centerPanel.getPreferredSize().getWidth()||node.getY()+node.getHeight()>centerPanel.getPreferredSize().getHeight()) {
                   centerPanel.setPreferredSize(new Dimension((int)centerPanel.getPreferredSize().getWidth()+80, (int)centerPanel.getPreferredSize().getHeight()+80));
                }
             }
            
            void MakeTreeNode(TreeNode node,TreeNode Parent,int height,String[] dummy) {   
               if(Parent.childNum == 0)
                  Parent.childNum = 1;
                double angleSize = 360/Parent.childNum;   //자식의 수를 기반으로 360도를 나눈다.
                double XYpie = 240-angleSize*(Parent.childCount-1) + 25*(height-1);   //시작의 각도를 설정한다.
                double pointPie;
                node.point = new Point[node.childNum];   //노드가 가지고있는 포인트의 수 자식의 수 만큼 정해준다.
                node.setFont(node.getFont().deriveFont(13.0f-(height-1)));      //글씨 크기 결정
                if(node.getFont().getSize()<6)
                   node.setFont(node.getFont().deriveFont(5.0f));
                node.setBorder(BorderFactory.createLineBorder(Color.BLACK));    //테두리 결정
                node.addMouseListener(labelListener);   //노드가 변경되는것을 반영하기 위한 마우스 리스너를 달아준다
                node.addMouseMotionListener(labelListener);   //노드가 드래그시에 변경하는 것을 반영하기위한 모션리스너
                node.setHorizontalAlignment(JLabel.CENTER);   //글씨를 가운데 정렬
                node.setOpaque(true);   //노드의 색입히는 것을 찬성
                node.setSize(Integer.parseInt(dummy[3]),Integer.parseInt(dummy[4]));      //사이즈 설정
                /*if(height>2)
                height = height*2/3;*/
                
                if(node!=Parent) {            //root가 아닐 때 색 지정과 좌표설정
                   node.setLocation(Integer.parseInt(dummy[1]),Integer.parseInt(dummy[2]));                   
                   node.setBackground(Color.decode(colorString[height%6]));   //colorString에 있는 색으로 라벨의 색을 설정
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());      //노드의 변경 이전 색을 기억
                   node.firstColor = node.firstColor.substring(2);      //RGB값으로 받았을 떄 alpha를 제거하기위한 방법
                   node.firstColor = "0X"+node.firstColor;      //alpha제거후 색을 바꿀 때 사용할 수 있도록 0x를 붙여준다.
                }
                else {   ///Root일 떄 별도록 설정
                   node.setLocation(Integer.parseInt(dummy[1]),Integer.parseInt(dummy[2]));
                   node.setBackground(Color.decode("0xEC7063"));
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());
                   node.firstColor = node.firstColor.substring(2);
                   node.firstColor = "0X"+node.firstColor;
                }
                
                node.pointPie = new double[node.point.length];
                for(int i =0; i<node.point.length;i++) {            //childNum만큼 설정한 point 배열에 포인트점 저장
                   node.pointPie[i] = 240-angleSize*i + 25*(height-1);   //첫 위치 +이후의 식은 부모와 겹치는 것을 막기위해 추가의 설정
                   node.point[i] = new Point((node.getX()+node.getWidth()/2)+(int)(Math.cos(Math.toRadians(node.pointPie[i]))*(node.getWidth()/2)),(node.getY()+node.getHeight()/2)+(int)(Math.sin(Math.toRadians(node.pointPie[i]))*(node.getHeight()/2)));
                }   //노드의 중심을 기준으로 원을 그려 원위에 좌표를 구해서 수많은 자식들을 설정하기위한 방식.
                
                centerPanel.add(node);   //노드를 패널에 띄워준다.
                
                if(node!=Parent) {      //루트가 아닐 때 선을 그리기 위한 두 점을 입력한다.
                   startPoint = new Point(node.getX()+node.getWidth()/2, node.getY()+node.getHeight()/2);
                   start.add(startPoint);   //시작점 
                   end.add(parent.point[parent.childCount-1]);   //끝나는점
                }
                
                if(node.getX()<0) {
                   int xSaver= node.getX();
                   int ySaver = node.getY();
                   save = centerPanel.getComponents();         //패널에 있는 노드를 save에 저장
                   centerPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width+80, centerPanel.getPreferredSize().height+80));
                   for(int i= 0; i<save.length;i++) {      //패널에 있는 노드들중에 변경할 노드를 탐색하기 위함.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane에 있는 위에 라벨이 Node로 변환될 수 없기 때문에 try catch로 잡아줌.
                         changeNode.setLocation(changeNode.getX()+(-2)*xSaver,changeNode.getY());
                         for(int j =0; j<changeNode.point.length;j++) {
                              changeNode.point[j] = new Point((changeNode.getX()+changeNode.getWidth()/2)+(int)(Math.cos(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getWidth()/2)),(changeNode.getY()+changeNode.getHeight()/2)+(int)(Math.sin(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getHeight()/2)));
                         }
                      }catch(Exception E) {}
                   }
                   for(int i =0; i<start.size();i++) {
                      System.out.println("i가 몇번 ? "+i);
                      startPoint = start.remove(i);
                     startPoint.setLocation(startPoint.getX()+(-2)*xSaver, startPoint.getY());
                     start.add(i, startPoint);
               
                     startPoint = end.remove(i);
                     startPoint.setLocation(startPoint.getX()+(-2)*xSaver, startPoint.getY());
                     end.add(i, startPoint);
                
                   }
                }if(node.getY()<50) {
                   int xSaver= node.getX();
                   int ySaver = 130;
                   save = centerPanel.getComponents();         //패널에 있는 노드를 save에 저장
                   centerPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width+80, centerPanel.getPreferredSize().height+80));
                   for(int i= 0; i<save.length;i++) {      //패널에 있는 노드들중에 변경할 노드를 탐색하기 위함.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane에 있는 위에 라벨이 Node로 변환될 수 없기 때문에 try catch로 잡아줌.
                         changeNode.setLocation(changeNode.getX(),changeNode.getY()+ySaver);
                         for(int j =0; j<changeNode.point.length;j++) {
                              changeNode.point[j] = new Point((changeNode.getX()+changeNode.getWidth()/2)+(int)(Math.cos(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getWidth()/2)),(changeNode.getY()+changeNode.getHeight()/2)+(int)(Math.sin(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getHeight()/2)));
                         }
                      }catch(Exception E) {}
                   }
                   for(int i =0; i<start.size();i++) {
                      startPoint = start.remove(i);
                     startPoint.setLocation(startPoint.getX(), startPoint.getY()+ySaver);
                     start.add(i, startPoint);
               
                     startPoint = end.remove(i);
                     startPoint.setLocation(startPoint.getX(), startPoint.getY()+ySaver);
                     end.add(i, startPoint);
                
                   }
                centerPanel.updateUI();
                }
                else if(node.getX()+node.getWidth()>centerPanel.getPreferredSize().getWidth()||node.getY()+node.getHeight()>centerPanel.getPreferredSize().getHeight()) {
                   centerPanel.setPreferredSize(new Dimension((int)centerPanel.getPreferredSize().getWidth()+80, (int)centerPanel.getPreferredSize().getHeight()+80));
                }
             }
         public TreeNode getRoot() {
            return this.root;
         }
         
      }
   
      public TreeNode getRoot() {
         return controller.getRoot();
      }
}