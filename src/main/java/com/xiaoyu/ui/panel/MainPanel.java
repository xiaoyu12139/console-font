package com.xiaoyu.ui.panel;

import javax.swing.JTabbedPane;

public class MainPanel extends JTabbedPane{
	
	public MainPanel() {
		add("ASCII��ĸ", new FontTab());
		add("ASCII������/ͼ", new ArtTab());
	}
}
