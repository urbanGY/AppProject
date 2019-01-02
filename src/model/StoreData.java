package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import model.Tree.TreeNode;

import org.jdom2.output.Format.TextMode;

public class StoreData {
	private TreeNode root;
	
	private Document doc;
	private Element rootElement;
	private Element parent;
	    
	private Element text;
	private Element xpos;
	private Element ypos;
	private Element width;
	private Element height;
	private Element color;
	private Element level;
    private Element childNum;
	
	private boolean first;
	
	public StoreData(TreeNode root) {
		this.root = root;
		
		this.doc = new Document();
		this.rootElement = null;
		this.parent = null;				
		
		first = true;
	}
	
	public boolean start(boolean defaultOption) {//true �׳� �׳� ����, false �ٸ� �̸����� ����
		try {
			String url = "";
			if(defaultOption) {//�⺻ ����
				if(LoadData.getRecentUrl().length() == 0)
					url = System.getProperty("user.home") + "\\Desktop\\out";
					//url = "C:\\Users\\sfsfk\\Desktop\\out";	
				//loadData recentUrl = ""�ʱ�ȭ�ϱ�
				else 
					url = LoadData.getRecentUrl();//�ҷ����� �� ��� �ҷ��� ������ ��� ����
			}else {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				chooser.setCurrentDirectory(new File("/"));
				
				chooser.setAcceptAllFileFilterUsed(true);
				chooser.setDialogTitle("�����ϱ�");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("xml ����", "*.xml");
				chooser.addChoosableFileFilter(filter);
				
				int store =chooser.showSaveDialog(null);				
				
				if(store == JFileChooser.APPROVE_OPTION) {
					System.out.println("save");
					url = chooser.getSelectedFile().toString();
					LoadData.setRecent(url);
				}
				else if(store == JFileChooser.CANCEL_OPTION) {
					System.out.println("cancel store");
					return false;
				}
			}
			
			String checkUrl = url.substring(url.length()-4);//�о��  url�� ���� 4���� ����
			if(checkUrl.equals(".xml"))//�� 4���ڰ� .xml���� ��
				url = url.substring(0, url.length()-4);//���� .xml�̸� ���� ,�� ������ ���� �� ���� ������ *.xml.xml.xml�� ��� .xml�� �ߺ��ǰԳ���			
			FileOutputStream fos = new FileOutputStream(url+".xml");//���ϸ� .xml�Ƚᵵ xmlȮ���ڷ� ����		
			traversal(root,null);
						
			Format format;
		    format = Format.getPrettyFormat();
		    format.setEncoding("UTF-8");
		    format.setTextMode(TextMode.TRIM);
		    XMLOutputter output = new XMLOutputter(format);
		    output.output(doc,fos);
		    fos.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void traversal(TreeNode node,Element parent) {
		if(node == null) return;
		this.text = new Element("text");
		this.xpos = new Element("xpos");
		this.ypos = new Element("ypos");
		this.width = new Element("width");
		this.height = new Element("height");
		this.color = new Element("color");
		this.level = new Element("level");
		this.childNum = new Element("childNum");
		
		text.setText(node.getText());  
		xpos.setText(node.getX()+"");
		ypos.setText(node.getY()+"");
		width.setText(node.getWidth()+"");
		height.setText(node.getHeight()+"");
		color.setText(node.getColor());
		level.setText(node.getLevel()+"");
		childNum.setText(node.getChildNum()+"");
		
		Element element;
		element = new Element("child");
		if(first) {
			doc.setRootElement(element);
			first = false;
		}else {			
			parent.addContent(element);
		}
	    
		element.addContent(text);
		element.addContent(xpos);
		element.addContent(ypos);
		element.addContent(width);
		element.addContent(height);
		element.addContent(color);
		element.addContent(level);
		element.addContent(childNum);		
		/*if(node.getChild() != null)
			parent = element;*/
		
	    traversal(node.getChild(),element);//�ڽ� ��ȸ
	    traversal(node.getSibling(),parent);//���� ��ȸ
	    //������ ��ġ�� �θ��� �缳�� �ؾ���
	    //�׳� �����ϸ� ���� �ϰ� �����ϰ� ��������
	    //�� ���� �� ���� ����
	}
}
