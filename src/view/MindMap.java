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
		super("JSplitPane Sample");			//������ ������
		setBounds(400,100,1200,900);
		setMinimumSize(new Dimension(1200, 900));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//�ݱ��ư������ ���α׷� ����
	    
	    setLayout(new BorderLayout());	//�����¿��߾ӿ� ������Ʈ ���� �� �ִ�.
	    EventListener listener = new EventListener();
	    Spanel = new SplitPane(listener);	    	    
	    listener.setLeftText(Spanel.getLeftPanel().getLeftText());
	    	 	    
	    setJMenuBar(new Menu(listener));	//�޴��� �߰�
	    add(new ToolBar(listener),"North");	//���� North�� �߰�
	    add(Spanel,"Center");// Panel �߰�
	    //this.setResizable(false);
	    addComponentListener(new AdjustLocalListener(this, Spanel.getCenterPanel()));
	    setVisible(true);			//�������� ���̰� ���ش�.
	   	}
	
  public static void main(String args[]) {
	  	new MindMap();
  }
}
