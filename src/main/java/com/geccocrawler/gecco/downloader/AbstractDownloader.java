package com.geccocrawler.gecco.downloader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectTimeoutException;

public abstract class AbstractDownloader implements Downloader {
	
	private static Log log = LogFactory.getLog(AbstractDownloader.class);

	private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

	private String getCharsetFromContentType(String contentType) {
		if (contentType == null)
			return null;

		Matcher m = charsetPattern.matcher(contentType);
		if (m.find()) {
			return m.group(1).trim().toUpperCase();
		}
		return null;
	}
	
	protected String getCharset(String requestCharset, String contentType) {
		//先取contentType的字符集
		String charset = getCharsetFromContentType(contentType);
		if(charset == null) {
			//再取request指定的字符集
			charset = requestCharset;
		}
		if(charset == null) {
			//默认采用utf-8
			charset = "UTF-8";
		}
		return charset;
	}
	
	/**
	 * 将原始的inputStream转换为ByteArrayInputStream使raw可以重复使用
	 * 
	 * @param in 原始的inputStream
	 * @return 可以重复使用的ByteArrayInputStream
	 * @throws IOException
	 */
	protected ByteArrayInputStream toByteInputStream(InputStream in) throws IOException {
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] b = new byte[1024];
			for (int c = 0; (c = in.read(b)) != -1;) {
				bos.write(b, 0, c);
			}
			b = null;
			bis = new ByteArrayInputStream(bos.toByteArray());
		} catch(EOFException eof){
			bis = new ByteArrayInputStream(bos.toByteArray());
			log.warn("inputstream " + in.getClass().getName() + " eof!");
		} 
		// 读取 org.apache.http.client.entity.LazyDecompressingInputStream 流时会抛出超时异常
		catch(ConnectTimeoutException | SocketTimeoutException e) {
			throw e;
		} catch (IOException e) {
			log.warn("inputstream " + in.getClass().getName() + " don't to byte inputstream!");
			return null;
		} finally {
			try {
				in.close();
				bos.close();
			} catch (IOException e) {
				bos = null;
				in = null;
			}
		}
		return bis;
	}

}
