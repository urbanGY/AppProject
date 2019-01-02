package model;


import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import model.Tree.TreeNode;
import view.JPanel_center;
import view.JPanel_right;

public class LoadData {
	private Tree tree;
	private JPanel_center centerPanel;
	private JPanel_right rightPanel;
	private JTextArea leftText;
	
	private Vector<Point> start;
	private Vector<Point> end;
	
	private static String recentUrl = "";
	public LoadData(JPanel_center centerPanel, JPanel_right rightPanel, JTextArea leftText, Vector<Point> start, Vector<Point> end) {
		this.centerPanel = centerPanel;
		this.rightPanel = rightPanel;
		this.leftText = leftText;
		this.tree = null;
		this.start = start;
		this.end = end;
		this.recentUrl = "";
	}
	
	public static String getRecentUrl() {
		System.out.println(recentUrl.length());
		return recentUrl;
	}
	
	public static void setRecentNew() {
		recentUrl = "";
	}
	
	public static void setRecent(String url) {
		recentUrl = url;
	}
	public TreeNode start() {
		
		try {
			String url = "";
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			chooser.setCurrentDirectory(new File("/"));
			chooser.setAcceptAllFileFilterUsed(true);
			chooser.setDialogTitle("불러오기");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xml 파일", "xml");
			chooser.addChoosableFileFilter(filter);
			
			int open = chooser.showOpenDialog(null);
			
			if(open == JFileChooser.APPROVE_OPTION)
				url = chooser.getSelectedFile().toString();
			else if(open == JFileChooser.CANCEL_OPTION) {
				recentUrl = "";
				System.out.println("cancel");
				return null;
			}
			this.recentUrl = url;
			//File xmlFile = new File("C:\\Users\\sfsfk\\Desktop\\out.xml");		
			String str = readFile(url,StandardCharsets.UTF_8);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();			
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();			
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(str));			
			Document doc = dbBuilder.parse(is);			
			doc.getDocumentElement().normalize();
			tree = new Tree(rightPanel,centerPanel,doc,leftText,start,end);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tree.getRoot();
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
}
