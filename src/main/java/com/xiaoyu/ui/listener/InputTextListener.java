package com.xiaoyu.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.xiaoyu.model.SelectFont;
import com.xiaoyu.ui.panel.FontTab;

public class InputTextListener implements KeyListener{

	private FontTab fontTab;
	
	public InputTextListener(FontTab fontTab) {
		this.fontTab = fontTab;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10) {
			JTextField in = fontTab.getIn();
			JComboBox<String> cbm = fontTab.getCbm();
			String text = in.getText();
			if(SelectFont.map.size() == 0) return ;
			List<String> list = new ArrayList<>();
			for(char c : text.toCharArray()) {
				String temp = c + "";
				Map<String, String> map = SelectFont.map;
				if(map.containsKey(temp)) {
					list.add(map.get(temp));
				}
			}
			System.out.println(list);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
