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


public class JPanel_right extends ContentPanel{         //������ �г� !!
	
   private JTextField[] texts;
   private JLabel[] labelDummy;
   private JPanel textPanel;
   private Button button;
   public JPanel_right(Color color, Dimension size, LayoutManager layout, JPanel_center center,EventListener listener) {
      super(color,size,layout);
      setSize(500,700);
      setLayout(new BorderLayout());
      setBorder(new EmptyBorder(new Insets(0,0,0,0)));
      labelDummy = new JLabel[6];         //���� ���� ����
      texts = new JTextField[6];         //text�� ���� ����
      
      textPanel= new JPanel();                  //BorderLayout���� �߾ӿ� ���� �г� ����
      textPanel.setLayout(new GridLayout(6,2,-25,60));      // �߾ӿ� ���� �г� GridLayout���� ����
      textPanel.setBackground(new Color(0xBB8FCE));
      
      for(int i =0; i<texts.length;i++) {//texts������ ��üȭ
         texts[i]=new JTextField(1);
         texts[i].setHorizontalAlignment(JTextField.CENTER);
         texts[i].setFont(texts[i].getFont().deriveFont(30.0f));
         texts[i].setBorder(BorderFactory.createMatteBorder(1 , 1, 1, 1, Color.BLACK));
      }
      texts[0].setEditable(false);//text�� ���� �Ұ����ϰ�
      ColorListener colorListener = new ColorListener();
      texts[5].addMouseListener(colorListener);
      JLabel text = new JLabel(" Text:");text.setFont(text.getFont().deriveFont(30.0f));labelDummy[0]=text;   //�� �߰� �� ����ũ�⼳��
      JLabel X = new JLabel(" X :"); X.setFont(text.getFont().deriveFont(30.0f));   labelDummy[1]=X;//�� �߰� �� ����ũ�⼳��
      JLabel Y =new JLabel(" Y :");  Y.setFont(text.getFont().deriveFont(30.0f));   labelDummy[2]=Y;//�� �߰� �� ����ũ�⼳��
      JLabel W = new JLabel(" W :"); W.setFont(text.getFont().deriveFont(30.0f));   labelDummy[3]=W;//�� �߰� �� ����ũ�⼳��
      JLabel H = new JLabel(" H :"); H.setFont(text.getFont().deriveFont(30.0f));   labelDummy[4]=H;//�� �߰� �� ����ũ�⼳��
      JLabel Color = new JLabel(" Color:");Color.setFont(text.getFont().deriveFont(30.0f));labelDummy[5]=Color;   //�� �߰� �� ����ũ�⼳��
      
      
      for(int i=0; i<texts.length;i++) {   //�� ->text������ �гο� �ٿ��ش�.
         textPanel.add(labelDummy[i]);
         textPanel.add(texts[i]);
      }
      
      JScrollPane Scpp = new JScrollPane(textPanel);   //���� �ʰ��� ���� ��ũ�� �г�.
      Scpp.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, new Color(0x000000)));
      Scpp.getVerticalScrollBar().setUI(new ScrollBar());
	  Scpp.getHorizontalScrollBar().setUI(new ScrollBar());
      
      //Button(String text,JPanel centerPanel, JPanel_right right)
      JLabel top = new TopicLabel("Text Editor Pane",this,0,2,2); // ������ ���� Label      
      button = new Button("����",center,this,listener);
      add(top,"North");      //���� �г��� ��ܿ� �߰�
      add(Scpp,"Center");      //���� �г��� �߾ӿ� �߰�
      add(button,"South");      //���� �г��� �ϴܿ� ��ư �߰�
   }
   
   public JTextField getTexts(int i) {
	   return this.texts[i];
   }
   
   public JPanel getPanel() {
	   return this.textPanel;
   }
}