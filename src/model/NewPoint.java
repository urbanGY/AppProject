package model;

import java.awt.Point;

public class NewPoint extends Point{        ///point equal �񱳸� ���� �������̵�

public NewPoint(int x, int y) { this.x = x; this.y = y; }

   public boolean equals(Object obj) {  Point p = (Point)obj; ///point equal �񱳸� ���� �������̵�
      if(x == p.x && y == p.y) return true;  else return false;
   }
}