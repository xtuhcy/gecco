package com.geccocrawler.gecco.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import com.google.common.io.Files;

/**
 * 下载图片到指定目录
 * 
 * @author huchengyi
 *
 */
public class DownloadImage {
	
	private static Log log = LogFactory.getLog(DownloadImage.class);
	
	/**
	 * 下载图片到指定目录
	 * 
	 * @param parentPath 指定目录
	 * @param imgUrl 图片地址
	 * @return 下载文件地址
	 */
	public static String download(String parentPath, String imgUrl) {
		if(Strings.isNullOrEmpty(imgUrl) || Strings.isNullOrEmpty(parentPath)) {
			return null;
		}
		if(imgUrl.length() > 500) {
			return null;
		}
		Closer closer = Closer.create();
		try {
			File imageDir = new File(parentPath);
			if(!imageDir.exists()) {
				imageDir.mkdirs();
			}
			String fileName =  StringUtils.substringBefore(imgUrl, "?");
			fileName = StringUtils.substringAfterLast(fileName, "/");
			File imageFile = new File(imageDir, fileName);
			InputStream in = closer.register(new URL(imgUrl).openStream());
			Files.write(ByteStreams.toByteArray(in), imageFile);
			return imageFile.getAbsolutePath();
		} catch(Exception ex) {
			ex.printStackTrace();
			log.error("image download error :"+imgUrl);
			return null;
		} finally {
			try {
				closer.close();
			} catch (IOException e) {
				closer = null;
			}
		}
	}
	
	public static String download(String parentPath, String fileName, InputStream in) {
		Closer closer = Closer.create();
		try {
			File imageDir = new File(parentPath);
			if(!imageDir.exists()) {
				imageDir.mkdirs();
			}
			File imageFile = new File(imageDir, fileName);
			Files.write(ByteStreams.toByteArray(in), imageFile);
			return imageFile.getAbsolutePath();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				closer.close();
			} catch (IOException e) {
				closer = null;
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DownloadImage.download("d:\\", "http://git.oschina.net/uploads/78/666978_xtuhcy.jpg?1459474621"));
	}
}
