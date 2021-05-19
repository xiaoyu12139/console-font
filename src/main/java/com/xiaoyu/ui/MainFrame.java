package com.xiaoyu.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.xiaoyu.ui.panel.MainPanel;

public class MainFrame extends JFrame{
	
	private MainPanel mainPanel;
	
	public MainFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPanel = new MainPanel();
		setLayout(new BorderLayout());
		getContentPane().add(mainPanel);
		
		setSize(500, 400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}
