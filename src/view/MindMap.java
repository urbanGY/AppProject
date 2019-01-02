package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import event.AdjustLocalListener;
import event.EventListener;
import view.Menu;
import view.SplitPane;

public class MindMap extends JFrame{
	private SplitPane Spanel;
	public MindMap() {
		super("JSplitPane Sample");			//프레임 제목설정
		setBounds(400,100,1200,900);
		setMinimumSize(new Dimension(1200, 900));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//닫기버튼누르면 프로그램 종료
	    
	    setLayout(new BorderLayout());	//상하좌우중앙에 컴포넌트 붙일 수 있다.
	    EventListener listener = new EventListener();
	    Spanel = new SplitPane(listener);	    	    
	    listener.setLeftText(Spanel.getLeftPanel().getLeftText());
	    	 	    
	    setJMenuBar(new Menu(listener));	//메뉴바 추가
	    add(new ToolBar(listener),"North");	//툴바 North에 추가
	    add(Spanel,"Center");// Panel 추가
	    //this.setResizable(false);
	    addComponentListener(new AdjustLocalListener(this, Spanel.getCenterPanel()));
	    setVisible(true);			//프레임이 보이게 해준다.
	   	}
	
  public static void main(String args[]) {
	  	new MindMap();
  }
}
