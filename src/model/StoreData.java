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
	
	public boolean start(boolean defaultOption) {//true 그냥 그냥 저장, false 다른 이름으로 저장
		try {
			String url = "";
			if(defaultOption) {//기본 저장
				if(LoadData.getRecentUrl().length() == 0)
					url = System.getProperty("user.home") + "\\Desktop\\out";
					//url = "C:\\Users\\sfsfk\\Desktop\\out";	
				//loadData recentUrl = ""초기화하기
				else 
					url = LoadData.getRecentUrl();//불러오기 할 경우 불러온 파일의 경로 저장
			}else {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				chooser.setCurrentDirectory(new File("/"));
				
				chooser.setAcceptAllFileFilterUsed(true);
				chooser.setDialogTitle("저장하기");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("xml 파일", "*.xml");
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
			
			String checkUrl = url.substring(url.length()-4);//읽어온  url의 뒤의 4문자 저장
			if(checkUrl.equals(".xml"))//그 4문자가 .xml인지 비교
				url = url.substring(0, url.length()-4);//만약 .xml이면 제거 ,이 과정이 없을 시 저장 파일이 *.xml.xml.xml등 계속 .xml이 중복되게나옴			
			FileOutputStream fos = new FileOutputStream(url+".xml");//파일명에 .xml안써도 xml확장자로 저장		
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
		
	    traversal(node.getChild(),element);//자식 순회
	    traversal(node.getSibling(),parent);//형제 순회
	    //적절한 위치에 부모노드 재설정 해야함
	    //그냥 저장하면 적용 하고 저장하게 설정하자
	    //판 색깔 및 길이 설정
	}
}
