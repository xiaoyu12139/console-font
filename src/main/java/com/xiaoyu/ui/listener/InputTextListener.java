package com.xiaoyu.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.xiaoyu.model.SelectFont;
import com.xiaoyu.ui.panel.FontTab;

public class InputTextListener implements KeyListener, ActionListener{

	private FontTab fontTab;

	public InputTextListener(FontTab fontTab) {
		this.fontTab = fontTab;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			action();
		}
	}
	
	public void action() {
		JTextField in = fontTab.getIn();
		JTextArea out = fontTab.getOut();
		JComboBox<String> cbm = fontTab.getCbm();
		String text = in.getText();
		if (SelectFont.map.size() == 0)
			return;
		List<Map<Integer, String>> list = new ArrayList<>();
		for (char c : text.toCharArray()) {
			String temp = c + "";
			Map<String, Map<Integer, String>> map = SelectFont.map;
			if (map.containsKey(temp)) {
				list.add(map.get(temp));
			} else {
				list.add(null);
			}
		}
		String res = rankList(list);
		out.setText(res);
	}

	private String rankList(List<Map<Integer, String>> list) {
		// 对齐基准点为，每个字最后一行的第一个字母和最后一个字母。
		// 整体高度为该list中字体最高的高度为构建的数组中最高的高度
		// 结果用一个map表示，key为行。
		int h = getHight(list);
		fillList(list, h);
		Map<Integer, String> resMap = new HashMap<>();
		for (int i = 0; i < list.get(0).size(); i++) {
			String temp = "";
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) == null) {
					temp += " ";
					continue;
				}
				String str = list.get(j).get(i);
				temp += str;
			}
			resMap.put(i, temp);
		}
		String res = "";
		for (Integer i : resMap.keySet()) {
			res += resMap.get(i) + "\n";
		}
		return res;
	}

	private void fillList(List<Map<Integer, String>> list, int h) {
		for (Map<Integer, String> temp : list) {
			if (temp != null && temp.size() < h) {
				// 移动map
				int m = h - temp.size();
				for (int i = temp.size() - 1; i >= 0; i--) {
					String v = temp.get(i);
					temp.put(i + m, v);
				}
				String nv = "";
				for (int i = 0; i < temp.get(temp.size() - 1).length(); i++) {
					nv += " ";
				}
				for (int i = 0; i < m; i++) {
					temp.put(i, nv);
				}
			}
		}
	}

	private int getHight(List<Map<Integer, String>> list) {
		int max = 0;
		for (Map<Integer, String> temp : list) {
			if (temp != null && temp.size() > max)
				max = temp.size();
		}
		return max;
	}



}
