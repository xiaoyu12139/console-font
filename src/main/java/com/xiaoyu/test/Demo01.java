package com.xiaoyu.test;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo01 extends Frame {
	Button b0 = new Button("0");
	Button b1 = new Button("1");
	Button b2 = new Button("2");
	Button b3 = new Button("3");

	Demo01() {
		setSize(400, 400);
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 6;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(b0, gbc);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.gridx = 0;
		gbc_1.gridy = 1;
		gbc_1.gridheight = 4;
		gbc_1.weighty = 1;
		gbc_1.fill = GridBagConstraints.VERTICAL;
		this.add(b1, gbc_1);
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.gridx = 1;
		gbc_2.gridy = 1;
		gbc_2.gridwidth = 5;
		gbc_2.gridheight = 4;
		gbc_2.weightx = 1;
		gbc_2.weighty = 1;
		gbc_2.fill = GridBagConstraints.BOTH;
		this.add(b2, gbc_2);
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.gridx = 0;
		gbc_3.gridy = 5;
		gbc_3.gridwidth = 6;
		gbc_3.weightx = 1;
		gbc_3.fill = GridBagConstraints.HORIZONTAL;
		this.add(b3, gbc_3);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		System.out.println(Demo01.class.getResource("/"));
		InputStream resourceAsStream = Demo01.class.getClassLoader().getResourceAsStream("/X.png");
		System.out.println(resourceAsStream);
	}
}