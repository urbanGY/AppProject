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
   Vector<Point> start; // 그래픽을 그리기위한 시작점들의 집합
   Vector<Point> end; // 그래픽을 그리기위한 끝점들의 집합
   JPanel_left left;
   JLabel top;
   
   public JPanel_center(Color color, Dimension size, LayoutManager layout) {
      super(color,size,layout);
      start = new Vector<Point>();
      end = new Vector<Point>();   
      top = new TopicLabel(" Mind Map Pane ",this,2,2,2);   //상단 라벨
      setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
      setPreferredSize(new Dimension(500, 700));
      add(top);   //상단 라벨
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g); // 부모 페인트호출
      
      if(start.size() == 0) // 벡터가 비어 있다면 리턴.
         return;
      for(int i=0;i<start.size();i++){ // 벡터값을 꺼내서 그린다.
         Point startPoint = start.get(i); // 시작점을 꺼낸다.
         Point endPoint = end.get(i);   //   끝점을 꺼낸다.
         g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);// 시작점과 끝점을 이어준다.
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