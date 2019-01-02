package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import event.EventListener;
import superClass.ScrollBar;
import view.JPanel_center;
import view.JPanel_left;
import view.JPanel_right;

public class SplitPane extends JSplitPane{
	private SplitPane fSplitPane;
	private JPanel_left left;
	private JPanel_center center;
	private JPanel_right right;
	
	public SplitPane(EventListener listener) {
		this(0.75,900);
		fSplitPane = new SplitPane(0.15,300);
	    
	    center = new JPanel_center(new Color(0xD2B4DE),new Dimension(500,500),/*new FlowLayout()*/null);
	    JScrollPane ScrollcenterPane = new JScrollPane(center);   //패널을 스크롤패인에 부착
	    ScrollcenterPane.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
	    ScrollcenterPane.getVerticalScrollBar().setUI(new ScrollBar());
	    ScrollcenterPane.getHorizontalScrollBar().setUI(new ScrollBar());
	    right = new JPanel_right(new Color(0xA569BD), new Dimension(250,500), new BorderLayout(), center,listener);
	    left = new JPanel_left(new Color(0xA569BD),new Dimension(250,500),new BorderLayout(), center,right,listener);
	    
	    setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		setDividerSize(0);
		
		fSplitPane.setRightComponent(ScrollcenterPane);
		fSplitPane.setLeftComponent(left);	    
	    
	    setLeftComponent(fSplitPane);
	    setRightComponent(right);
	}
	
	public SplitPane(double weight, int dividerLocation) {
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setContinuousLayout(true);
		setResizeWeight(weight);
		setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		setDividerLocation(dividerLocation);
		setDividerSize(0);
	}
	
	public JPanel_left getLeftPanel() {
		return this.left;
	}
	
	public JPanel_center getCenterPanel() {
		return this.center;
	}
}
