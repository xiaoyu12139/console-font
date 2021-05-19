package com.xiaoyu.ui.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.stream.Stream;

import com.xiaoyu.model.SelectFont;
import com.xiaoyu.utils.StrUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CbmSelectListener implements ItemListener{
	
	private  File target = null;;

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() != ItemEvent.DESELECTED) {
			File file = new File(StrUtil.FONTS_DIR);
			Stream.of(file.listFiles()).forEach(temp -> {
				if(temp.getName().equals(e.getItem())) {
					target = temp;
					return ;
				}
			});
			if(target == null || !target.exists()) return ;
			log.info(target.getAbsolutePath());
			try {
				for(File temp : target.listFiles()) {
					BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(temp), "UTF-8"));
					String s = null;
					String res = "";
					while((s = r.readLine()) != null) {
						res += s + "\n";
					}
					r.close();
					int valueOf = Integer.valueOf(temp.getName());
					SelectFont.map.put((char)valueOf + "", res);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
