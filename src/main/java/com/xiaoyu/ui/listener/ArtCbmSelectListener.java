package com.xiaoyu.ui.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.xiaoyu.ui.panel.ArtTab;
import com.xiaoyu.utils.StrUtil;

public class ArtCbmSelectListener implements ItemListener, ActionListener {

	private ArtTab tab;
	private File target;
	private static int page = 1;
	private Box box;
	private JPanel panel;
	private int total;

	public ArtCbmSelectListener(ArtTab tab) {
		this.tab = tab;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() != ItemEvent.DESELECTED) {
			String select = (String) e.getItem();
			Stream.of(new File(StrUtil.ARTS_DIR).listFiles()).forEach(temp -> {
				if (select.equals(temp.getName())) {
					target = temp;
					return;
				}
			});
			if (target == null)
				return;
			page = 1;
			getTotal();
			initPage();
		}
	}

	private void getTotal() {
		total = target.listFiles().length;
	}

	private boolean initPage() {
		panel = tab.listPanel;
		panel.removeAll();
		JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setLayout(new BorderLayout());
		panel.add(sp);
		box = Box.createVerticalBox();
//		box.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
		sp.setViewportView(box);
		boolean flag = showList(box);
		if (!flag)
			return false;
		tab.pageLabel.setText("共" + total + "页，当前第" + page + "页");
		panel.revalidate();
		return true;
	}

	private boolean showList(Box box) {
		box.removeAll();
		box.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				panel.setFocusable(true);
				panel.requestFocus();
				System.out.println(1);
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});
		String path = target.getAbsolutePath() + "\\page" + page;
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("page not exists");
			return false;
		}
		for (File temp : file.listFiles()) {
			try {
				BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(temp), "utf-8"));
				String s = null;
				String res = "";
				while ((s = r.readLine()) != null) {
					res += s + "\n";
				}
				JTextArea t = new JTextArea(res);
				JScrollPane sp = new JScrollPane(t);
				sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				box.add(sp);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		box.add(Box.createVerticalGlue());
		box.revalidate();
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source.getText().equals("上一页")) {
			page--;
			boolean flag = initPage();
			if(!flag) page++;
			tab.pageLabel.setText("共" + total + "页，当前第" + page + "页");
		}
		if (source.getText().equals("下一页")) {
			page++;
			boolean flag = initPage();
			if(!flag) page--;
			tab.pageLabel.setText("共" + total + "页，当前第" + page + "页");
		}
	}

}
