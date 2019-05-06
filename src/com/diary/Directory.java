/*
 *   DB Connection�� ��� ���õ� �ڿ��� �ݴ� �ߺ��� �ڵ带 �����ϱ� ���� Ŭ���� 
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
	
	String rootPath; //root ���� ���
	
	DefaultMutableTreeNode root;
	JTree tree;
	TreePath path;//������ ����
	
	public Directory(Main main) {
		p_root=new JPanel();
		p_dir = new JPanel();
		p_bt = new JPanel();
		bt_root=new JButton("��� ����");
		bt_new = new JButton("�� ���� �����");
		bt_remove = new JButton("���� ����");
		t_root=new JTextField();
		
		p_root.setPreferredSize(new Dimension(360,50));
		p_root.setBackground(Color.pink);
		p_dir.setPreferredSize(new Dimension(360,740));
		p_dir.setBackground(Color.pink);
		p_bt.setPreferredSize(new Dimension(360,40));
		p_bt.setBackground(Color.pink);
		bt_root.setPreferredSize(new Dimension(100, 40));
		t_root.setPreferredSize(new Dimension(240, 40));
		t_root.setFont(new Font("MD����ü", Font.BOLD, 20));
		
		root = new DefaultMutableTreeNode("���� ���̾");
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
	//������ ��� ������ �����ϴ� �޼���
	public void setRoot() {
		t_root.setText("");
		chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result=chooser.showOpenDialog(this);
		
       if(result==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			rootPath=file.getAbsolutePath(); //������ ������ ��θ� �����صα�
			t_root.setText(rootPath);
       }
		
	}
	//�� ������ �����ϴ� �޼���
	public void createFolder() {
		while(true) {
			String folderName=JOptionPane.showInputDialog("�������� �Է����ּ���");
			if(folderName.equals("")) {
				JOptionPane.showMessageDialog(null, "�������� �Է����ּ���");
				System.out.println("���� ����!!");
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
	//������ �����ϴ� �޼���
	public void removeFolder(TreePath path) {
		int result = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?");
		if(result==JOptionPane.OK_OPTION) {
			DefaultTreeModel model=(DefaultTreeModel)tree.getModel();
			DefaultMutableTreeNode root=(DefaultMutableTreeNode)model.getRoot();
			DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
			root.add(node);
			model.reload(root);
		}
	}
}