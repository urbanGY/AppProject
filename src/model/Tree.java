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
   private JPanel_center centerPanel;//��� ���ε�� �г� ����
   private JPanel_right rightPanel;//���� �Ӽ� �г� ����
   private JTextArea leftText;//���� TextArea ����
   private Controller controller;//Ʈ����� ���� 
   private Component[] save;
   private TreeNode changeNode;
   private Point startPoint;
   private Vector<Point> start;
   private Vector<Point> end;
   private String[] colorString = {"0xEB984E","0xF4D03F","0x58D68D","0x5DADE2","0x5499C7","0xA569BD"};   //����� �� �����ϱ����� ��Ʈ���迭 "0xEC7063",
      
   public Tree(JPanel_right rightPanel, JPanel_center centerPanel,String data,JTextArea leftText,Vector<Point> Start, Vector<Point> end) {//�����ư�� ������
      this.rightPanel=rightPanel;//���� �г� ������ �Ҵ�
      this.centerPanel=centerPanel;//��� ���ε�� ����� �г� ������ �Ҵ�
      this.leftText = leftText;//���� textArea������ �Ҵ�
      controller = new Controller(Start,end);//Ʈ����带 ������ ��Ʈ�ѷ� ����      
      controller.run(data);//�Ѿ�� �����͸� ������� �⺻ Ʈ�� ��������
   }
      
   public Tree(JPanel_right rightPanel, JPanel_center centerPanel,Document doc,JTextArea leftText, Vector<Point> Start, Vector<Point> end) {//���� ���� ������
      this.centerPanel = centerPanel;
      this.rightPanel = rightPanel;
      this.leftText = leftText;
      controller = new Controller(Start,end);
      controller.run(doc);//xml���Ͽ��� �Ľ��� document��ü�� �Ű������� �ѱ��.
   }
   
   public class TreeNode extends JLabel {//���� ��ӹ��� Ʈ�����         
         private TreeNode sibling;//����� ���� ����  
         private TreeNode child;//����� �ڽ� ����         
         private int childNum;//��尡 ���� �ڽ��� ��
         private int childCount;
         private String firstColor;//����� ��      
         int siblingNum;//����� ������ ��
         private int level;//����� ����
         public Point[] point;//����� ��ǥ
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
            
         
         public TreeNode(String data, int level, int childNum) {//��� ������ ���ÿ� �ؽ�Ʈ,����,�ڽ��� ���� �ʱ�ȭ�Ѵ�.
            this.setText(data);
            this.sibling = null;
            this.child = null;
            this.level = level;            
            this.childNum = childNum;
         }
         
         public void setSibling(TreeNode sibling) {//���� ����� ���� ����
               this.sibling = sibling;
         }         
         public TreeNode getSibling() {//���� ����� ���� ��ȯ
               return this.sibling;
         }
         
         public void setChild(TreeNode child) {//�ڽ� ����
               this.child = child;
         }         
         public TreeNode getChild() {//���� ����� �ڽ� ��ȯ
               return this.child;
         }
         
         public int getChildNum() {//���� ����� �ڽ��� �� ��ȯ
            return this.childNum;
         }         
         
         public int getSbling() {//���� ����� ������ �� ��ȯ
            return this.siblingNum;
         }               
               
                  
         public void setRootXY(int i, int j) {//����� ��ǥ ����
            this.setLocation(i, j);
         }
         
         public void setColor(String color) {//����� �� string���� �ʵ�� ����
            this.firstColor = color;
         }
         public int getx() {return this.getX();}//����� x��ǥ ��ȯ
         public int gety() {return this.getY();}//����� y��ǥ ��ȯ
         public int getw() {return this.getWidth();}//����� width��ȯ
         public int geth() {return this.getHeight();}//����� height��ȯ
         public String getColor() { return this.firstColor; }//����� �� string���·� ��ȯ
         public int getLevel() {return this.level; }//����� ����(����)��ȯ
                                  
      }

   private class Controller {//����� Ʈ�������� ������� ���� Ŭ����
         private TreeNode root;//Ʈ���� ��Ʈ
         private TreeNode last;//���� �ֱٿ� �߰��� ��� ����Ŵ
         private TreeNode parent;//���� ����Ű�� ����� �θ��� ����Ŵ
         private int lastHeight;//���� �ֱٿ� �߰��� ����� ����
         private Scanner in;
         private LabelListener labelListener=new LabelListener(rightPanel,centerPanel,start,end);//���ε�ʿ� ����� �󺧿� �ɾ��� ������ ����
         Dimension rememberSize = new Dimension();
         
         public Controller(Vector<Point> tmpStart,Vector<Point> tmpEnd) {//���� ������ �ʿ��� ������ ���� ���͸� �Ű������� �޾ƿ�
            this.root = null;
            this.last = null;
            this.parent = null;
            this.lastHeight = -1;
            start = tmpStart;
            end = tmpEnd;//�ܼ� �ʱ�ȭ
         }
         
         public void run(String data) {//���� ��ư�� �������� ����
           
           centerPanel.getEnd().clear();
           centerPanel.getStart().clear();//���� ���� �� �ʿ��� ��ǥ�� ������ ���� ���� �ʱ�ȭ���ش�.
           rememberSize = centerPanel.getPreferredSize();
           centerPanel.removeAll();//������ �������� �𸣴� centerPanel�� ��ü�� ��� �����س��´�.
           centerPanel.setPreferredSize(rememberSize);
           centerPanel.add(centerPanel.getTop());//���� �������� ������� minMapPane�� �ʿ������� �ٽ� �����Ͽ� ���δ�.
           if(data.length() == 0) {//�ƿ� �Է��� ���� ��쿡�� Ʈ���� ���� �ʿ䵵 �������� �������� �ʴ´�              
              return;
           }
              
           in = new Scanner(data);         
           Vector<Integer> childNum = setChildNumber(data);//���� �����͸� ������� Ʈ���� ����� ���� �� �ٿ� ��� �ĺ����� ������ �ڽ��� ���� ������� ���Ϳ� �����Ѵ�. ���� Ʈ�� ������ �Ű������� �Ѱܼ� ��带 �����Ѵ�.
            if(setRoot(in.nextLine(),childNum.remove(0))) {//ù ��ǲ�� ���Ⱑ ���ִ� ���                
               leftText.setText("");
               leftText.updateUI();//���� �Է��� ���� textArea�� �ʱ�ȭ���ش�.
               return;//�߸��� �Է������� �������� �ʴ´�.
            }
               
            String s;//���پ� ���� �� �� ��Ʈ��
            while(true) {
               try{
                  s=in.nextLine();//���� �� �а�                  
                  setTreeNode(s,null,childNum.remove(0));//Ʈ����带 ����� �Ű������� ������ ���� �Ѿ��. �Է� ��Ʈ��,(�ι�°�� xml������ ���� �� �Ѿ���� document��ü��. �̹� ��쿣 �������� null),�Ʊ� ����� ����� �ڽ��� ���� ��ȯ�ϰ� �����.                   
            }catch(Exception e){//���̻� ���� ��Ʈ���� ���ų�, ��Ʈ ���Ŀ� tab�� ���� �Էµ� ��� ���ܸ� �߻���Ű�� ����������Ѵ�.               
               System.err.println(e.getMessage());//���� ����
               if(e.getMessage().equals("second input error")) {//��Ʈ ���Ŀ� tab���� �Է��� ���� ��� 
                  JOptionPane.showMessageDialog(null, "�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���!");//�����޼����� ����ش�
                  centerPanel.getPreferredSize();
                  centerPanel.removeAll();
                  centerPanel.setTopicLabel();
                  centerPanel.updateUI();//centerPanel�� �ʱ�ȭ���ش�.
                  start.clear();
                  end.clear();//���� ���� �� �ʿ��� ���͵��� �ʱ�ȭ���ش�.
                  leftText.setText("");
                  leftText.updateUI();//textArea �ʱ�ȭ
               }               
               break;
               }
            }
            showAll();//Ʈ���� ����� ����������� ��ȸ�� Ȯ���Ѵ�.
            centerPanel.updateUI();//������� Ʈ����带 ��� �гο� �ø� ���� �ݿ��Ѵ�.            
         }
         
         private Vector<Integer> setChildNumber(String data){//Ʈ����� ���� ������ �� ����ĺ����� ������ �ڽ��� ���� ������ ���Ϳ� ������� ������ ���� �Ű������� �Ѱ��� �� ť ó�� ����Ѵ�.
            Scanner in = new Scanner(data);
            Vector<Integer> vec = new Vector<>();//��� �ĺ��� level�� ������� ����
            Vector<Integer> childNum = new Vector<>();//��� �ĺ��� �ڽ��� ���� ����
            int index = 0;//�Է� ��Ʈ���� �� ���� ����            
            while(true) {
               try {
                  String s = in.nextLine();               
                  int count = 0;//tab�� ��� ������ ����
                  childNum.add(index,0);//index��° ���͸� �߰��ϸ� �⺻ ���� 0�̴�.(�ڽ��� ���� �ϴ���...)
                  
                  for(int i = 0 ; i < s.length() ; i++)//tab���� level��� �� count�� ����
                     if(s.charAt(i) == '	')
                        count++;
                     else
                        break;
                  
                  vec.add(index,count);//index��° �������� ����� ���͸� �߰�
                  if(count > 0) {//��Ʈ�ĺ��� �ƴ� ��
                    for(int j = index-1 ; j >= 0 ; j--) {//���� �ٺ��� ù��° �� ���� ��������
                       if(vec.get(j)<count) {//���� ����� level���� �� ����(�θ���)���� ����� ��带 ã�´�
                          int num = childNum.get(j)+1;//
                          childNum.set(j, num);//ã�� �ش� �θ� ����� �ڽ��� ���� 1 �����ش�
                          break;
                       }                       
                    }
                  }
                  index++;
            } catch (Exception e) {//�� �̻� ���� ���� ������ �����.
               break;
            }               
            }
            return childNum;//������� ������� �ڽ��� ���� ���� ���͸� ��ȯ�Ѵ�.
         }
         
         public void run (Document doc) {//xml���Ͽ��� documnet��ü�� �ҷ��� �̸� ������� Ʈ���� ���� ���
            centerPanel.getEnd().clear();
            centerPanel.getStart().clear();
            centerPanel.removeAll();
            centerPanel.add(centerPanel.getTop());//������ �������� �𸣴� ��� �г��� �ʱ�ȭ����
            String forLeftText = "";//document�� �Ľ��� �� text���� ��Ƽ� ���� textArea�� ����� ���� 
            try {               
               NodeList list = doc.getElementsByTagName("child");//xml�ױ׳����� child�� ��� �Ӽ��� ��帮��Ʈ�� ������               
               for(int i = 0 ; i < list.getLength() ; i++) {
                  String[] dummy = new String[8];//Ʈ���� ������ �� �ʿ��� �� �Ӽ���
                  for(Node node = list.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {                     
                                        
                     if(node.getNodeName().equals("text"))//text            
                        dummy[0] = node.getTextContent();                                             
                     else if (node.getNodeName().equals("xpos"))//x��ǥ
                        dummy[1] = node.getTextContent();
                     else if (node.getNodeName().equals("ypos"))//y��ǥ
                        dummy[2] = node.getTextContent();
                     else if (node.getNodeName().equals("width"))//�ʺ�
                        dummy[3] = node.getTextContent();
                     else if (node.getNodeName().equals("height"))//����
                        dummy[4] = node.getTextContent();
                     else if (node.getNodeName().equals("color"))//��
                        dummy[5] = node.getTextContent();
                     else if (node.getNodeName().equals("level"))//����
                        dummy[6] = node.getTextContent();
                     else if (node.getNodeName().equals("childNum"))//�ڽ��� �� ���� �ҷ��� ��Ʈ�� �迭�� ���� �������ش�.
                        dummy[7] = node.getTextContent();
                  }   
                  if(i == 0) {
                        forLeftText += dummy[0]+"\n";//���� ���� textArea�� ����� text���� ���౸���� �Ͽ� �������ش�.
                     setRoot(dummy);//��Ʈ ����
                  }else {                     
                     String blank = "";
                     for(int q = 0 ; q < Integer.parseInt(dummy[6]); q++)//������ŭ ��Ʈ���� tab�� �߰����ش�
                        blank += '	';
                     dummy[0] = blank+dummy[0];
                     forLeftText += dummy[0]+"\n";// ���� textArea�� ����� text���� ���౸���� �Ͽ� �������ش�.
                     setTreeNode(dummy[0],dummy,Integer.parseInt(dummy[7]));//Ʈ����带 ����� �Ӽ����� �� dummy�� �Ѱ��ش�.(��Ʈ��,���� ��ü,level)
                  }                                    
               }
         } catch (Exception e) {
            System.err.println("xml������ �ҷ����µ� �����߽��ϴ�... �Ф�");
         }
            centerPanel.updateUI();//������� Ʈ���� �ö� ��� �г��� �ݿ��Ѵ�            
            leftText.setText(forLeftText);//���� textArea�� �Է� ���� �������ش�.
            leftText.updateUI();//�ش� ���� �ݿ�
         }
         
         private boolean setRoot(String data,int childNum) {//���� ��ư�� ���Ǵ� �Լ�            
           if(data.charAt(0) == '	') {//�ƿ� ó������ tab�� �� ��� �������� �ʴ´�.
              JOptionPane.showMessageDialog(null, "�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���!");
              return true;
           }
           root = new TreeNode(data,0,childNum);//��Ʈ ��带 �����Ѵ�.
            root.setRootXY(240, 200);//��Ʈ����� ��ǥ
            MakeTreeNode(root,root,1);
            last = root;
            lastHeight = 0;
            return false;
         }
         
         private void setRoot(String[] dummy) {//���� �ҷ��� �� ���Ǵ� �Լ�            
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
                 
            }else if(lastHeight < curHeight) {//�ڽ� ��� ������ ���
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
               
            }else if(lastHeight > curHeight) {//�ٸ� �θ� ����� ���
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
            traversal(TreeNode.getSibling());//���� ��ȸ
            traversal(TreeNode.getChild());//�ڽ� ��ȸ      
         }
         
            void MakeTreeNode(TreeNode node,TreeNode Parent,int height) {     
               if(Parent.childNum == 0)
                  Parent.childNum = 1;
               double angleSize=0; 
               if(Parent.childNum >3)
                    angleSize = 360/Parent.childNum;   //�ڽ��� ���� ������� 360���� ������.
                else
                   angleSize =90;
                double XYpie = 240-angleSize*(Parent.childCount-1)-angleSize*(Parent.siblingNum) + 25*(height-1);   //������ ������ �����Ѵ�.
                node.point = new Point[node.childNum];   //��尡 �������ִ� ����Ʈ�� �� �ڽ��� �� ��ŭ �����ش�.
         
                node.setFont(node.getFont().deriveFont(13.0f-(height-1)));      //�۾� ũ�� ����
                if(node.getFont().getSize()<6)
                   node.setFont(node.getFont().deriveFont(5.0f));
                node.setBorder(BorderFactory.createLineBorder(Color.BLACK));    //�׵θ� ����
                node.addMouseListener(labelListener);   //��尡 ����Ǵ°��� �ݿ��ϱ� ���� ���콺 �����ʸ� �޾��ش�
                node.addMouseMotionListener(labelListener);   //��尡 �巡�׽ÿ� �����ϴ� ���� �ݿ��ϱ����� ��Ǹ�����
                node.setHorizontalAlignment(JLabel.CENTER);   //�۾��� ��� ����
                node.setOpaque(true);   //����� �������� ���� ����
                node.setSize(35-3*(height-1),35-3*(height-1));      //������ ����
                if(node.getSize().getWidth()<11)
                   node.setSize(10, 10);
                int distance = 220-50*height;
                   if(distance < 150)
                      distance = 100;
                   double cal = (Math.cos(Math.toRadians(XYpie))*(distance));
                   double cul = (Math.sin(Math.toRadians(XYpie))*(distance));
                if(node!=Parent) {            //root�� �ƴ� �� �� ������ ��ǥ����
                   node.setLocation(Parent.getX()+(int)(Math.cos(Math.toRadians(XYpie))*(distance)),Parent.getY()+(int)(Math.sin(Math.toRadians(XYpie))*(distance)));                                   
                   node.setBackground(Color.decode(colorString[height%6]));   //colorString�� �ִ� ������ ���� ���� ����
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());      //����� ���� ���� ���� ���
                   node.firstColor = node.firstColor.substring(2);      //RGB������ �޾��� �� alpha�� �����ϱ����� ���
                   node.firstColor = "0X"+node.firstColor;      //alpha������ ���� �ٲ� �� ����� �� �ֵ��� 0x�� �ٿ��ش�.
                 }
                else {   ///Root�� �� ������ ����
                   node.setLocation(270,350);
                   node.setBackground(Color.decode("0xEC7063"));
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());
                   node.firstColor = node.firstColor.substring(2);
                   node.firstColor = "0X"+node.firstColor;
                }
                node.pointPie = new double[node.point.length];
                for(int i =0; i<node.point.length;i++) {            //childNum��ŭ ������ point �迭�� ����Ʈ�� ����
                   node.pointPie[i] = 240-angleSize*i + 25*(height-1);   //ù ��ġ +������ ���� �θ�� ��ġ�� ���� �������� �߰��� ����
                   node.point[i] = new Point((node.getX()+node.getWidth()/2)+(int)(Math.cos(Math.toRadians(node.pointPie[i]))*(node.getWidth()/2)),(node.getY()+node.getHeight()/2)+(int)(Math.sin(Math.toRadians(node.pointPie[i]))*(node.getHeight()/2)));
                }   //����� �߽��� �������� ���� �׷� ������ ��ǥ�� ���ؼ� ������ �ڽĵ��� �����ϱ����� ���.
                
                centerPanel.add(node);   //��带 �гο� ����ش�.
                
                if(node!=Parent) {      //��Ʈ�� �ƴ� �� ���� �׸��� ���� �� ���� �Է��Ѵ�.
                   startPoint = new Point(node.getX()+node.getWidth()/2, node.getY()+node.getHeight()/2);
                   start.add(startPoint);   //������ 
                   end.add(parent.point[parent.childCount-1]);   //��������
                }
                
                if(node.getX()<0) {
                   int xSaver= node.getX();
                   int ySaver = node.getY();
                   save = centerPanel.getComponents();         //�гο� �ִ� ��带 save�� ����
                   centerPanel.setPreferredSize(new Dimension((int)centerPanel.getPreferredSize().getWidth()+80, (int)centerPanel.getPreferredSize().getHeight()+80));
                   for(int i= 0; i<save.length;i++) {      //�гο� �ִ� �����߿� ������ ��带 Ž���ϱ� ����.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane�� �ִ� ���� ���� Node�� ��ȯ�� �� ���� ������ try catch�� �����.
                         changeNode.setLocation(changeNode.getX()+(-2)*xSaver,changeNode.getY());
                         for(int j =0; j<changeNode.point.length;j++) {
                              changeNode.point[j] = new Point((changeNode.getX()+changeNode.getWidth()/2)+(int)(Math.cos(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getWidth()/2)),(changeNode.getY()+changeNode.getHeight()/2)+(int)(Math.sin(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getHeight()/2)));
                         }
                      }catch(Exception E) {}
                   }
                   for(int i =0; i<start.size();i++) {
                      System.out.println("i�� ��� ? "+i);
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
                   save = centerPanel.getComponents();         //�гο� �ִ� ��带 save�� ����
                   centerPanel.setPreferredSize(new Dimension((int)centerPanel.getPreferredSize().getWidth()+80, (int)centerPanel.getPreferredSize().getHeight()+80));
                   centerPanel.updateUI();
                   for(int i= 0; i<save.length;i++) {      //�гο� �ִ� �����߿� ������ ��带 Ž���ϱ� ����.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane�� �ִ� ���� ���� Node�� ��ȯ�� �� ���� ������ try catch�� �����.
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
                double angleSize = 360/Parent.childNum;   //�ڽ��� ���� ������� 360���� ������.
                double XYpie = 240-angleSize*(Parent.childCount-1) + 25*(height-1);   //������ ������ �����Ѵ�.
                double pointPie;
                node.point = new Point[node.childNum];   //��尡 �������ִ� ����Ʈ�� �� �ڽ��� �� ��ŭ �����ش�.
                node.setFont(node.getFont().deriveFont(13.0f-(height-1)));      //�۾� ũ�� ����
                if(node.getFont().getSize()<6)
                   node.setFont(node.getFont().deriveFont(5.0f));
                node.setBorder(BorderFactory.createLineBorder(Color.BLACK));    //�׵θ� ����
                node.addMouseListener(labelListener);   //��尡 ����Ǵ°��� �ݿ��ϱ� ���� ���콺 �����ʸ� �޾��ش�
                node.addMouseMotionListener(labelListener);   //��尡 �巡�׽ÿ� �����ϴ� ���� �ݿ��ϱ����� ��Ǹ�����
                node.setHorizontalAlignment(JLabel.CENTER);   //�۾��� ��� ����
                node.setOpaque(true);   //����� �������� ���� ����
                node.setSize(Integer.parseInt(dummy[3]),Integer.parseInt(dummy[4]));      //������ ����
                /*if(height>2)
                height = height*2/3;*/
                
                if(node!=Parent) {            //root�� �ƴ� �� �� ������ ��ǥ����
                   node.setLocation(Integer.parseInt(dummy[1]),Integer.parseInt(dummy[2]));                   
                   node.setBackground(Color.decode(colorString[height%6]));   //colorString�� �ִ� ������ ���� ���� ����
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());      //����� ���� ���� ���� ���
                   node.firstColor = node.firstColor.substring(2);      //RGB������ �޾��� �� alpha�� �����ϱ����� ���
                   node.firstColor = "0X"+node.firstColor;      //alpha������ ���� �ٲ� �� ����� �� �ֵ��� 0x�� �ٿ��ش�.
                }
                else {   ///Root�� �� ������ ����
                   node.setLocation(Integer.parseInt(dummy[1]),Integer.parseInt(dummy[2]));
                   node.setBackground(Color.decode("0xEC7063"));
                   node.firstColor = String.valueOf(Integer.toHexString(node.getBackground().getRGB()).toUpperCase());
                   node.firstColor = node.firstColor.substring(2);
                   node.firstColor = "0X"+node.firstColor;
                }
                
                node.pointPie = new double[node.point.length];
                for(int i =0; i<node.point.length;i++) {            //childNum��ŭ ������ point �迭�� ����Ʈ�� ����
                   node.pointPie[i] = 240-angleSize*i + 25*(height-1);   //ù ��ġ +������ ���� �θ�� ��ġ�� ���� �������� �߰��� ����
                   node.point[i] = new Point((node.getX()+node.getWidth()/2)+(int)(Math.cos(Math.toRadians(node.pointPie[i]))*(node.getWidth()/2)),(node.getY()+node.getHeight()/2)+(int)(Math.sin(Math.toRadians(node.pointPie[i]))*(node.getHeight()/2)));
                }   //����� �߽��� �������� ���� �׷� ������ ��ǥ�� ���ؼ� ������ �ڽĵ��� �����ϱ����� ���.
                
                centerPanel.add(node);   //��带 �гο� ����ش�.
                
                if(node!=Parent) {      //��Ʈ�� �ƴ� �� ���� �׸��� ���� �� ���� �Է��Ѵ�.
                   startPoint = new Point(node.getX()+node.getWidth()/2, node.getY()+node.getHeight()/2);
                   start.add(startPoint);   //������ 
                   end.add(parent.point[parent.childCount-1]);   //��������
                }
                
                if(node.getX()<0) {
                   int xSaver= node.getX();
                   int ySaver = node.getY();
                   save = centerPanel.getComponents();         //�гο� �ִ� ��带 save�� ����
                   centerPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width+80, centerPanel.getPreferredSize().height+80));
                   for(int i= 0; i<save.length;i++) {      //�гο� �ִ� �����߿� ������ ��带 Ž���ϱ� ����.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane�� �ִ� ���� ���� Node�� ��ȯ�� �� ���� ������ try catch�� �����.
                         changeNode.setLocation(changeNode.getX()+(-2)*xSaver,changeNode.getY());
                         for(int j =0; j<changeNode.point.length;j++) {
                              changeNode.point[j] = new Point((changeNode.getX()+changeNode.getWidth()/2)+(int)(Math.cos(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getWidth()/2)),(changeNode.getY()+changeNode.getHeight()/2)+(int)(Math.sin(Math.toRadians(changeNode.pointPie[j]))*(changeNode.getHeight()/2)));
                         }
                      }catch(Exception E) {}
                   }
                   for(int i =0; i<start.size();i++) {
                      System.out.println("i�� ��� ? "+i);
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
                   save = centerPanel.getComponents();         //�гο� �ִ� ��带 save�� ����
                   centerPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width+80, centerPanel.getPreferredSize().height+80));
                   for(int i= 0; i<save.length;i++) {      //�гο� �ִ� �����߿� ������ ��带 Ž���ϱ� ����.
                      try{
                         changeNode = (TreeNode)save[i];         //MindMapPane�� �ִ� ���� ���� Node�� ��ȯ�� �� ���� ������ try catch�� �����.
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