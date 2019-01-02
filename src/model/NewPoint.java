package model;

import java.awt.Point;

public class NewPoint extends Point{        ///point equal 비교를 위한 오버라이드

public NewPoint(int x, int y) { this.x = x; this.y = y; }

   public boolean equals(Object obj) {  Point p = (Point)obj; ///point equal 비교를 위한 오버라이드
      if(x == p.x && y == p.y) return true;  else return false;
   }
}