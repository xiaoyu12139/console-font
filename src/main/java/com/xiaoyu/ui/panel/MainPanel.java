package com.xiaoyu.ui.panel;

import javax.swing.JTabbedPane;

public class MainPanel extends JTabbedPane{
	
	public MainPanel() {
		add("ASCII×ÖÄ¸", new FontTab());
		add("ASCIIÒÕÊõ×Ö/Í¼", new ArtTab());
	}
}
