package com.xiaoyu.ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xiaoyu.ui.listener.ArtCbmSelectListener;
import com.xiaoyu.utils.StrUtil;

public class ArtTab extends JPanel{
	
	public JComboBox<String> cbm;
	public JPanel listPanel;
	public JLabel pageLabel;
	public JButton up;
	public JButton down;
	
	public ArtTab() {
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {0, 0, 0};
		layout.rowHeights = new int[] {0, 0, 0};
		layout.columnWeights = new double[] {1.0, 0.0, 0.0};
		layout.rowWeights = new double[] {0, 1, 0};
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		cbm = new JComboBox<>();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.weightx = 1;
		layout.setConstraints(cbm, c);
		add(cbm);
		
		
		listPanel = new JPanel();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		layout.setConstraints(listPanel, c);
		add(listPanel);
		
		ArtCbmSelectListener l = new ArtCbmSelectListener(this);
		up = new JButton("上一页");
		up.addActionListener(l);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.3;
		layout.setConstraints(up, c);
		add(up);
		
		pageLabel = new JLabel("共3页，当前第1页");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 4d / 7;
		layout.setConstraints(pageLabel, c);
		add(pageLabel);
		
		down = new JButton("下一页");
		down.addActionListener(l);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		layout.setConstraints(down, c);
		add(down);
		
		createBox();
		cbm.addItemListener(l);
	}

	private void createBox() {
		File file = new File(StrUtil.ARTS_DIR);
		cbm.addItem("------选择样式------");
		for(File temp : file.listFiles()) {
			cbm.addItem(temp.getName());
		}
	}
	
}
