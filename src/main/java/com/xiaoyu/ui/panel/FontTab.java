package com.xiaoyu.ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.xiaoyu.ui.listener.CbmSelectListener;
import com.xiaoyu.ui.listener.InputTextListener;
import com.xiaoyu.utils.StrUtil;

import lombok.Data;

@Data
public class FontTab extends JPanel{
	private JComboBox<String> cbm;
	private JTextField in;
	private JTextArea out;
	
	public FontTab() {
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {0};
		layout.rowHeights = new int[] {0, 0, 0};
		layout.columnWeights = new double[] {1.0};
		layout.rowWeights = new double[] {0, 0, 1};
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		
		cbm = new JComboBox<>();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(cbm, c);
		add(cbm);
		
		in = new JTextField(20);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		layout.setConstraints(in, c);
		add(in);
		
		out = new JTextArea();
		JScrollPane sp = new JScrollPane(out);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(sp, c);
		add(sp);
		
		in.addKeyListener(new InputTextListener(this));
		cbm.addItemListener(new CbmSelectListener());
		initFontBox();
	}
	
	public void initFontBox() {
		File file = new File(StrUtil.FONTS_DIR);
		cbm.addItem("------Ñ¡Ôñ×ÖÌå------");
		for(File temp : file.listFiles()) {
			cbm.addItem(temp.getName());
		}
	}
}
