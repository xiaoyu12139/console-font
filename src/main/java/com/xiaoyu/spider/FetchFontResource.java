package com.xiaoyu.spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FetchFontResource {
	
	public CloseableHttpClient client = HttpClientBuilder.create().build();
	public String leter = "q w e r t y u i o p a s d f g h j k l z x c v b n m";
	public String symbol = "? ! ; = + - @ % $ # ^ & * ( ) { } [ ] | \\ / . ~ ` : _";
	public String number = "0 1 2 3 4 5 6 7 8 9";
	public String baseUrl =  "https://www.bootschool.net/ascii";
	public String baseArt = "https://www.bootschool.net/ascii-art";
	
	public static void main(String[] args) {
		double start = System.currentTimeMillis();
		FetchFontResource main = new FetchFontResource();
		main.run();
		double end = System.currentTimeMillis();
		System.out.println((end - start) / (1000 * 60));
	}
	
	public void run() {
//		getFont();
//		getStyle();
	}
	
	//当前目录下是否存在fonts目录，没有就创建
	//每个字体建个文件，每个文件中的每个字符一个txt文本存储
	public void getFont() {
		try {
			//创建fonts目录，如果没有的话
			String path = "D:\\My Documents\\桌面\\console-font\\target\\conf\\" + "fonts";
			File file = new File(path);
			if(!file.exists()) file.mkdir();
			log.info("开始拉取console字体，创建目录：" + path);
			String kinds = leter + " " + symbol + " " + number;
			HttpGet get = new HttpGet(baseUrl);
			CloseableHttpResponse get2Form = client.execute(get);
			List<String> fonts = getFontForm(EntityUtils.toString(get2Form.getEntity(), "utf-8"));
			log.info("当前所有的字体：" + fonts);
			for(String font : fonts) {
				String[] split = kinds.split(" ");
				//遍历每种字符
				for(String str : split) {
					String html = getAscll(font, str);
					String tempPath = path + "\\" + font;
					new File(tempPath).mkdir();
					File file2 = new File(tempPath + "\\" + (int)str.charAt(0));
					file2.createNewFile();
					log.info("创建文件:" + file2.getAbsolutePath() + ".并写入……");
					parseAndStore(html, file2);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseAndStore(String html, File file) throws IOException {
		Document doc = Jsoup.parse(html);
		Element res = doc.select("pre#asciiResult").first();
		String target = res.wholeText();
		//写入到fonts/{font}/{symbol的ascll码}.txt
		Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		w.write(target);
		w.close();
	}

	public void getStyle() {
		try {
			//创建fonts目录，如果没有的话
			String path = "D:\\My Documents\\桌面\\console-font\\target\\conf\\" + "arts";
			File file = new File(path);
			if(!file.exists()) file.mkdir();
			log.info("开始拉取非字体类型的console图形，创建文件夹：" + path);
			List<String> kinds = getArtKinds();
			log.info("console图形分类：" + kinds);
			for(String kind : kinds) {
				int pageSize = getPageSize(kind);
				log.info(kind + "共有" + pageSize + "页");
				for(int i = 1; i <= pageSize; i++) {
					String url = kind + "?pageNO=" + i;
					Pattern p = Pattern.compile("ascii-art/(.*)\\?");
					Matcher m = p.matcher(url);
					String str = "";
					if(m.find()) {
						str = path + "\\" + m.group(1) + "\\page" + i;
						new File(str).mkdirs();
					}
					log.info("创建分类" + kind + "的第" + i + "页文件夹：" + str);
					getAndStoreStyle(url, str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getAndStoreStyle(String url, String path) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse res = client.execute(get);
		Document html = Jsoup.parse(EntityUtils.toString(res.getEntity(), "utf-8"));
		Elements selects = html.select("div.banner-wrapper div.banner-item");
		Iterator<Element> i = selects.iterator();
		int index = 1;
		while(i.hasNext()) {
			Element next = i.next();
			Element select = next.select("pre.darcala").first();
			String text = select.wholeText();
			String tempPath = path + "\\" + index;
			File file = new File(tempPath);
			file.createNewFile();
			Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			w.write(text);
			w.close();
			log.info("写入console图形" + file.getAbsolutePath());
			index ++;
		}
	}

	private int getPageSize(String url) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse page = client.execute(get);
		Document doc = Jsoup.parse(EntityUtils.toString(page.getEntity(), "utf-8"));
		Element size = doc.select("ul.pagination > li:last-child").first();
		Pattern p = Pattern.compile("([0-9][0-9]?)");
		Matcher m = p.matcher(size.wholeText());
		if(m.find()) return Integer.valueOf(m.group(1));
		return 0;
	}

	private List<String> getArtKinds() throws ClientProtocolException, IOException {
		List<String> list = new ArrayList<>();
		HttpGet get = new HttpGet(baseArt);
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/89.0.4389.128 Safari/537.36");
		CloseableHttpResponse res = client.execute(get);
		String html = EntityUtils.toString(res.getEntity(), "utf-8");
		Document doc = Jsoup.parse(html);
		Elements select = doc.select("div.category-list ul li");
		String base = "https://www.bootschool.net";
		Iterator<Element> i = select.iterator();
		while(i.hasNext()) {
			Element next = i.next();
			Element a = next.select("a").first();
			String temp = a.attr("href");
			list.add(base + temp);
		}
		return list;
	}

	public String getAscll(String font, String letter) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(baseUrl);
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/89.0.4389.128 Safari/537.36");
		// 设置2个post参数，一个是scope、一个是q
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
		parameters.add(new BasicNameValuePair("asciiWord", letter));
		parameters.add(new BasicNameValuePair("asciiFont", font));
		// 构造一个form表单式的实体
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
		// 将请求实体设置到httpPost对象中
		post.setEntity(formEntity);
		CloseableHttpResponse res = client.execute(post);
		return EntityUtils.toString(res.getEntity(), "utf-8");
	}

	public List<String> getFontForm(String html) {
		List<String> list = new ArrayList<>(1);
		Document doc = Jsoup.parse(html);
		Elements select = doc.select("form#asciiForm select option");
		Iterator<Element> i = select.iterator();
		while (i.hasNext()) {
			Element next = i.next();
			String attr = next.attr("value");
			log.info(attr);
			attr.trim();
			if(attr.equals("")) continue;
			list.add(attr);
		}
		return list;
	}
	
	
//	public void test() throws ClientProtocolException, IOException {
//		CloseableHttpClient client = HttpClientBuilder.create().build();
//		String url = "https://www.bootschool.net/ascii";
//		HttpGet get = new HttpGet(url);
//		RequestConfig config = RequestConfig.custom()
//				// 设置连接超时时间(单位毫秒)
//				.setConnectTimeout(5000)
//				// 设置请求超时时间(单位毫秒)
//				.setConnectionRequestTimeout(5000)
//				// socket读写超时时间(单位毫秒)
//				.setSocketTimeout(5000)
//				// 设置是否允许重定向(默认为true)
//				.setRedirectsEnabled(true).build();
//		get.setConfig(config);
//		CloseableHttpResponse res = client.execute(get);
//		System.out.println(EntityUtils.toString(res.getEntity()));
//	}
}
