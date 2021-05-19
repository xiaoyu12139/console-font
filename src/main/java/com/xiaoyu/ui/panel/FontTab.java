package com.xiaoyu.ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FontTab extends JPanel{
	private JComboBox<String> cbm;
	private JTextField in;
	private JTextArea out;
	
	public FontTab() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		
		cbm = new JComboBox<>();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		layout.setConstraints(cbm, c);
		add(cbm);
		
		in = new JTextField(20);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.1;
		layout.setConstraints(in, c);
		add(in);
		
		out = new JTextArea();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.weightx = 1;
		c.weighty = 1;
		layout.setConstraints(out, c);
		add(out);
	}
}
