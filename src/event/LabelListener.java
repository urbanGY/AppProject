package event;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import model.NewPoint;
import model.Tree.TreeNode;
import view.JPanel_center;
import view.JPanel_right;


public class LabelListener extends MouseAdapter{
   JPanel_right rightPanel;
   JPanel_center centerPanel;
   TreeNode label,savelabel; 
   String str;
   String saveColor;
   NewPoint point,newpoint;
   Vector<Point> start;
   Vector<Point> end;
   int count;
   public LabelListener(JPanel_right tmp,JPanel_center panel,Vector<Point> start,Vector<Point> end)
   {
      rightPanel = tmp;
      centerPanel = panel;
      this.start = start;
      this.end = end;
   }
   
   public void mouseDragged(MouseEvent e) {
      label = (TreeNode)e.getSource();
      point = new NewPoint(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2); //�̵� �� ��ġ ����
                                            //////////////////////////�� ������ ����
      if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR))             //���ʸ𼭸�
         if(e.getX()<3&&e.getY()<3)
            label.setBounds(label.getX()-1,label.getY()-1, label.getWidth()+1,label.getHeight()+1);
         else
            label.setBounds(label.getX()+1,label.getY()+1, label.getWidth()-1,label.getHeight()-1);
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR))          //����
         if(e.getY()<3)   //����
            label.setBounds(label.getX(),label.getY()-1, label.getWidth(),label.getHeight()+1);
         else         //����
            label.setBounds(label.getX(),label.getY()+1, label.getWidth(),label.getHeight()-1);
      
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR))         //�����ʸ𼭸� 
         if(e.getX()>label.getWidth()-3&&e.getY()<3)
            label.setBounds(label.getX(),label.getY()-1,label.getWidth()+1,label.getHeight()+1);
         else
            label.setBounds(label.getX(),label.getY()+1,label.getWidth()-1,label.getHeight()-1);
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR))          //������
         if(e.getX()>label.getWidth()-3)    //����
            label.setBounds(label.getX(),label.getY(), label.getWidth()+1,label.getHeight());
         else   //����
            label.setBounds(label.getX(),label.getY(), label.getWidth()-1,label.getHeight());
      
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR))       //�����ʾƷ��𼭸�
         if(e.getX()>label.getWidth()-3&&e.getY()>label.getHeight()-3)
            label.setBounds(label.getX(),label.getY(), label.getWidth()+1,label.getHeight()+1);
         else
            label.setBounds(label.getX(),label.getY(), label.getWidth()-1,label.getHeight()-1);
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR))          //�Ʒ���
         if(e.getY()>label.getHeight()-3)   //����
            label.setBounds(label.getX(),label.getY(),label.getWidth(),label.getHeight()+1);
         else   //����
            label.setBounds(label.getX(),label.getY(),label.getWidth(),label.getHeight()-1);
      
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR))       //���ʾƷ��𼭸�
         if(e.getX()<3&&e.getY()>label.getHeight()-3)
            label.setBounds(label.getX()-1,label.getY(), label.getWidth()+1,label.getHeight()+1);
         else
            label.setBounds(label.getX()+1,label.getY(), label.getWidth()-1,label.getHeight()-1);
      else if(label.getCursor()==Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR))          //����
         if(e.getX()<3)      //����
            label.setBounds(label.getX()-1,label.getY(), label.getWidth()+1,label.getHeight());
         else      //����
            label.setBounds(label.getX()+1,label.getY(), label.getWidth()-1,label.getHeight());
      
         ///////////////////////////////////////////�� �̵� & �׷��� �ٽ� �׸��� !!!!!!!!!!!!
      else if(label.getCursor()==Cursor.getDefaultCursor()) {      
         //point = new Point2(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2); //�̵� �� ��ġ ����
         
         label.setLocation(label.getLocation().x + e.getX()-label.getWidth()/2,label.getLocation().y + e.getY()-label.getHeight()/2); //�� �巡��
         label.repaint();
         
         }
      newpoint = new NewPoint(label.getX()+label.getWidth()/2,label.getY()+label.getHeight()/2);   //�̵� �� ��ġ ����
      int count =0;
      int changeX = (int)(newpoint.getX()-point.getX());
      int changeY = (int)(newpoint.getY()-point.getY());
      Point newpo;
      for(int i=0; i<centerPanel.getStart().size();i++) {      //����� ����
         Point tmp= new Point(centerPanel.getStart().get(i)); 
         if(point.equals(tmp)) {
            centerPanel.getStart().remove(i);
            centerPanel.getStart().add(i,newpoint);
         }
      }
      for(int i =0; i<centerPanel.getEnd().size(); i++)
         for(int j = 0; j<label.point.length;j++)
            if(centerPanel.getEnd().get(i).getX()==label.point[j].getX()&&centerPanel.getEnd().get(i).getY()==label.point[j].getY()) {
               centerPanel.getEnd().remove(i);
               label.point[j].setLocation(label.point[j].getX()+changeX, label.point[j].getY()+changeY);
               centerPanel.getEnd().add(i,label.point[j]);
            }
        if(label.getX()+label.getWidth()>600||label.getY()+label.getHeight()>760) {
           System.out.println("�� �̵����� ���� �� ������ "+centerPanel.getPreferredSize().getWidth()+","+centerPanel.getPreferredSize().getHeight());
           centerPanel.setPreferredSize(new Dimension(label.getX()+label.getWidth()+100, label.getY()+label.getHeight()+100));
           System.out.println("�� �̵����� ���� �� ������ "+centerPanel.getPreferredSize().getWidth()+","+centerPanel.getPreferredSize().getHeight());
        }else
           ;//centerPanel.setPreferredSize(new Dimension(600,760));
        centerPanel.updateUI();
      }
   

   public void mousePressed(MouseEvent e) {
      label = (TreeNode)e.getSource();
      String color = String.valueOf(Integer.toHexString(label.getBackground().getRGB()).toUpperCase());
      if(count>0&&savelabel.getText()==label.getText()&&color.equals("FFFFFFF0")) {
         label.setBackground(Color.decode(label.getColor()));
         System.out.println(1);
      }
      else {
         if(count>0)
            savelabel.setBackground(Color.decode(savelabel.getColor()));
         label.setBackground(Color.decode("0XFFFFF0"));
         System.out.println(2);
      }
      color = color.substring(2);
      String showcolor = label.getColor();
      showcolor= showcolor.substring(2);
      rightPanel.getTexts(0).setText(label.getText());
      rightPanel.getTexts(1).setText(String.valueOf(label.getX()));
      rightPanel.getTexts(2).setText(String.valueOf(label.getY()));
      rightPanel.getTexts(3).setText(String.valueOf(label.getWidth()));
      rightPanel.getTexts(4).setText(String.valueOf(label.getHeight()));
      rightPanel.getTexts(5).setText(showcolor);
      str=label.getText();
      rightPanel.getPanel().updateUI();
      savelabel = label;
      count++;
      }
   
   public void mouseReleased(MouseEvent e) {
      label = (TreeNode)e.getSource();
      rightPanel.getTexts(0).setText(label.getText());
      rightPanel.getTexts(1).setText(String.valueOf(label.getX()));
      rightPanel.getTexts(2).setText(String.valueOf(label.getY()));
      rightPanel.getTexts(3).setText(String.valueOf(label.getWidth()));
      rightPanel.getTexts(4).setText(String.valueOf(label.getHeight()));
      str=label.getText();
      rightPanel.getPanel().updateUI();
      }

   public void mouseMoved(MouseEvent e) {
      //System.out.println(e.getX());
      label = (TreeNode)e.getSource();
      if(e.getX()<2 && e.getY()<2)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));   //������ �𼭸�
      else if(e.getX()!=0&&e.getY()<2)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));      //����
      else if(e.getX()>label.getWidth()-3&&e.getY()<3)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));   //�������� �𼭸�
      else if(e.getX()>label.getWidth()-3&&e.getY()>3&&e.getY()<label.getHeight()-3)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));      //������
      else if(e.getX()>label.getWidth()-3&&e.getY()>label.getHeight()-3)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));   //�����ʾƷ� �𼭸�
      else if(e.getX()!=0 &&e.getY()>label.getHeight()-3)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));      //�Ʒ�
      else if(e.getX()<3 &&e.getY()>label.getHeight()-3)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));   //���ʾƷ� �𼭸�
      else if(e.getX()<3 &&e.getY()!=0)
         label.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));      //����
      else
         label.setCursor(Cursor.getDefaultCursor());
        }
  
   public String ReturnString() {return str;}
}