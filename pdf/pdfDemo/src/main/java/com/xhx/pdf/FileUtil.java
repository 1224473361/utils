package com.xhx.pdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	/**
	 * 获取file
	 * 
	 * @param filePath
	 * @return
	 */
	public static File getFileByAbsolute(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				log.error("文件创建失败:" + e.getMessage(), e);
				throw new RuntimeException("文件创建失败:" + e.getMessage(), e);
			}
		}
		return f;
	}

	/**
	 * 获取file
	 * 
	 * @param filePath
	 * @return
	 */
	public static InputStream getFileByResource(String filePath) {
		return FileUtil.class.getClassLoader().getResourceAsStream(filePath);
	}
}