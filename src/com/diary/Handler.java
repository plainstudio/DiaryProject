package com.diary;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class Handler extends MouseAdapter {
	JTree tree;

	public Handler(JTree tree) {
		this.tree = tree;
	}

	public void mousePressed(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
	}

}
