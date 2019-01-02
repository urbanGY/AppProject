package view;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import superClass.ContentPanel;
import superClass.TopicLabel;
import view.JPanel_left;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.Vector;

public class JPanel_center extends ContentPanel{
   Vector<Point> start; // �׷����� �׸������� ���������� ����
   Vector<Point> end; // �׷����� �׸������� �������� ����
   JPanel_left left;
   JLabel top;
   
   public JPanel_center(Color color, Dimension size, LayoutManager layout) {
      super(color,size,layout);
      start = new Vector<Point>();
      end = new Vector<Point>();   
      top = new TopicLabel(" Mind Map Pane ",this,2,2,2);   //��� ��
      setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
      setPreferredSize(new Dimension(500, 700));
      add(top);   //��� ��
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g); // �θ� ����Ʈȣ��
      
      if(start.size() == 0) // ���Ͱ� ��� �ִٸ� ����.
         return;
      for(int i=0;i<start.size();i++){ // ���Ͱ��� ������ �׸���.
         Point startPoint = start.get(i); // �������� ������.
         Point endPoint = end.get(i);   //   ������ ������.
         g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);// �������� ������ �̾��ش�.
      }
      
   }
   public void setTopicLabel() {
	   top = new TopicLabel(" Mind Map Pane ",this,2,2,2);
	   this.add(top);
   }
   public Vector<Point> getStart(){
	   return this.start;
   
   }
   
   public Vector<Point> getEnd(){
	   return this.end;
   }
   
   public JLabel getTop() {
	   return this.top;
   }
}