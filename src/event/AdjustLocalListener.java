package event;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import model.Tree.TreeNode;
import view.JPanel_center; 
import view.MindMap;

public class AdjustLocalListener extends ComponentAdapter{
   JPanel_center center;
   MindMap mindmap;
   Vector<Point> moveStart;
   Vector<Point> moveEnd;
   int width=1200;
   int height = 900;
   Component[] save;
   TreeNode label;
   Point point1,point2;
   public AdjustLocalListener(MindMap mindmap,JPanel_center center) {
      this.center = center;
      this.mindmap = mindmap;
      moveStart = center.getStart();
      moveEnd = center.getEnd();
   }
   public void componentResized(ComponentEvent e) {
      int moveRight=(mindmap.getWidth()-width)/3;
      int moveLow = (mindmap.getHeight()-height)/2;
      center.getTop().setSize(center.getTop().getWidth()+moveRight*2,center.getTop().getHeight());
      
      save = new TreeNode[center.getComponentCount()];
      save = center.getComponents();         //패널에 있는 노드를 save에 저장
        for(int i= 0; i<save.length;i++) {      //패널에 있는 노드들중에 변경할 노드를 탐색하기 위함.
           try{label = (TreeNode)save[i];         //MindMapPane에 있는 위에 라벨이 Node로 변환될 수 없기 때문에 try catch로 잡아줌.
           label.setLocation(label.getX()+moveRight, label.getY()+moveLow);
           }catch(Exception E) {}
        }
        for(int i=0; i<center.getStart().size();i++) {
           point1 =  moveStart.remove(i);
           point1.setLocation(point1.getX()+moveRight, point1.getY()+moveLow);
           moveStart.add(i,point1);
           
           point2 = moveEnd.remove(i);
           point2.setLocation(point2.getX()+moveRight, point2.getY()+moveLow);
           moveEnd.add(i,point2);
        }
      
      width = mindmap.getWidth();
      height = mindmap.getHeight();
   }
}