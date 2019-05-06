/*
 *   DB Connection을 얻고 관련된 자원을 닫는 중복된 코드를 방지하기 위한 클래스 
 */

package com.diary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.logging.Handler;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class Directory extends JPanel {
	Main main;
	
	JPanel p_root,p_dir, p_bt;
	JButton bt_root,bt_new, bt_remove;
	JTextField t_root;
	JFileChooser chooser;
	
	String rootPath; //root 폴더 경로
	
	DefaultMutableTreeNode root;
	JTree tree;
	TreePath path;//선택한 폴더
	
	public Directory(Main main) {
		p_root=new JPanel();
		p_dir = new JPanel();
		p_bt = new JPanel();
		bt_root=new JButton("경로 선택");
		bt_new = new JButton("새 폴더 만들기");
		bt_remove = new JButton("폴더 삭제");
		t_root=new JTextField();
		
		p_root.setPreferredSize(new Dimension(360,50));
		p_root.setBackground(Color.pink);
		p_dir.setPreferredSize(new Dimension(360,740));
		p_dir.setBackground(Color.pink);
		p_bt.setPreferredSize(new Dimension(360,40));
		p_bt.setBackground(Color.pink);
		bt_root.setPreferredSize(new Dimension(100, 40));
		t_root.setPreferredSize(new Dimension(240, 40));
		t_root.setFont(new Font("MD개성체", Font.BOLD, 20));
		
		root = new DefaultMutableTreeNode("나의 다이어리");
		tree = new JTree(root);
		
		MouseAdapter handler=new com.diary.Handler(tree);
		tree.addMouseListener(handler);
		
		bt_root.addActionListener((e) -> {
			setRoot();
		});
		bt_new.addActionListener((e) -> {
			createFolder();
		});
		bt_remove.addActionListener((e) -> {
			removeFolder(path);
		});
		
		p_root.add(bt_root);
		p_root.add(t_root);
		p_dir.add(tree);
		p_bt.add(bt_new);
		p_bt.add(bt_remove);
		add(p_root);
		add(p_dir);
		add(p_bt);
	}
	//저장할 경로 파일을 설정하는 메서드
	public void setRoot() {
		t_root.setText("");
		chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result=chooser.showOpenDialog(this);
		
       if(result==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			rootPath=file.getAbsolutePath(); //선택한 파일의 경로를 저장해두기
			t_root.setText(rootPath);
       }
		
	}
	//새 폴더를 생성하는 메서드
	public void createFolder() {
		while(true) {
			String folderName=JOptionPane.showInputDialog("폴더명을 입력해주세요");
			if(folderName.equals("")) {
				JOptionPane.showMessageDialog(null, "폴더명을 입력해주세요");
				System.out.println("공백 파일!!");
			}else {
				/*
				DefaultTreeModel model=(DefaultTreeModel)tree.getModel();
				DefaultMutableTreeNode root=(DefaultMutableTreeNode)model.getRoot();
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
				node.add(new DefaultMutableTreeNode(folderName));
				model.reload(node);
				*/
				
				DefaultMutableTreeNode folder = new DefaultMutableTreeNode(folderName);
				root.add(folder);
				tree.updateUI();
				tree.expandPath(new TreePath(root.getPath()));
				return;
			}
		}
	}
	//폴더를 삭제하는 메서드
	public void removeFolder(TreePath path) {
		int result = JOptionPane.showConfirmDialog(null, "폴더를 삭제하시겠습니까?");
		if(result==JOptionPane.OK_OPTION) {
			DefaultTreeModel model=(DefaultTreeModel)tree.getModel();
			DefaultMutableTreeNode root=(DefaultMutableTreeNode)model.getRoot();
			DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
			root.add(node);
			model.reload(root);
		}
	}
}