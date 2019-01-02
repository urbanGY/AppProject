package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import event.ColorListener;
import event.EventListener;
import superClass.Button;
import superClass.ContentPanel;
import superClass.ScrollBar;
import superClass.TopicLabel;


public class JPanel_right extends ContentPanel{         //오른쪽 패널 !!
	
   private JTextField[] texts;
   private JLabel[] labelDummy;
   private JPanel textPanel;
   private Button button;
   public JPanel_right(Color color, Dimension size, LayoutManager layout, JPanel_center center,EventListener listener) {
      super(color,size,layout);
      setSize(500,700);
      setLayout(new BorderLayout());
      setBorder(new EmptyBorder(new Insets(0,0,0,0)));
      labelDummy = new JLabel[6];         //라벨의 갯수 선언
      texts = new JTextField[6];         //text의 갯수 선언
      
      textPanel= new JPanel();                  //BorderLayout으로 중앙에 넣을 패널 생성
      textPanel.setLayout(new GridLayout(6,2,-25,60));      // 중앙에 넣을 패널 GridLayout으로 설정
      textPanel.setBackground(new Color(0xBB8FCE));
      
      for(int i =0; i<texts.length;i++) {//texts각각을 객체화
         texts[i]=new JTextField(1);
         texts[i].setHorizontalAlignment(JTextField.CENTER);
         texts[i].setFont(texts[i].getFont().deriveFont(30.0f));
         texts[i].setBorder(BorderFactory.createMatteBorder(1 , 1, 1, 1, Color.BLACK));
      }
      texts[0].setEditable(false);//text는 수정 불가능하게
      ColorListener colorListener = new ColorListener();
      texts[5].addMouseListener(colorListener);
      JLabel text = new JLabel(" Text:");text.setFont(text.getFont().deriveFont(30.0f));labelDummy[0]=text;   //라벨 추가 및 글자크기설정
      JLabel X = new JLabel(" X :"); X.setFont(text.getFont().deriveFont(30.0f));   labelDummy[1]=X;//라벨 추가 및 글자크기설정
      JLabel Y =new JLabel(" Y :");  Y.setFont(text.getFont().deriveFont(30.0f));   labelDummy[2]=Y;//라벨 추가 및 글자크기설정
      JLabel W = new JLabel(" W :"); W.setFont(text.getFont().deriveFont(30.0f));   labelDummy[3]=W;//라벨 추가 및 글자크기설정
      JLabel H = new JLabel(" H :"); H.setFont(text.getFont().deriveFont(30.0f));   labelDummy[4]=H;//라벨 추가 및 글자크기설정
      JLabel Color = new JLabel(" Color:");Color.setFont(text.getFont().deriveFont(30.0f));labelDummy[5]=Color;   //라벨 추가 및 글자크기설정
      
      
      for(int i=0; i<texts.length;i++) {   //라벨 ->text순으로 패널에 붙여준다.
         textPanel.add(labelDummy[i]);
         textPanel.add(texts[i]);
      }
      
      JScrollPane Scpp = new JScrollPane(textPanel);   //범위 초과를 위한 스크롤 패널.
      Scpp.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, new Color(0x000000)));
      Scpp.getVerticalScrollBar().setUI(new ScrollBar());
	  Scpp.getHorizontalScrollBar().setUI(new ScrollBar());
      
      //Button(String text,JPanel centerPanel, JPanel_right right)
      JLabel top = new TopicLabel("Text Editor Pane",this,0,2,2); // 맨위에 붙일 Label      
      button = new Button("변경",center,this,listener);
      add(top,"North");      //메인 패널의 상단에 추가
      add(Scpp,"Center");      //메인 패널의 중앙에 추가
      add(button,"South");      //메인 패널의 하단에 버튼 추가
   }
   
   public JTextField getTexts(int i) {
	   return this.texts[i];
   }
   
   public JPanel getPanel() {
	   return this.textPanel;
   }
}