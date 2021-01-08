package com.demo.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * html转换为pdf<br>
 * https://ld246.com/article/1528278305572
 */
public class ItextUtil {

	public static final String FONT_PATH = "D:\\simsun.ttf";

	/**
	 * html转换为pdf
	 * 
	 * @param htmlPath
	 * @param pdfPath
	 */
	public static void convertHtmlToPdf(String htmlPath, String pdfPath) {
		try (FileInputStream is = new FileInputStream(htmlPath); FileOutputStream os = new FileOutputStream(pdfPath);) {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, os);
			document.open();
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(writer, document, is, StandardCharsets.UTF_8, new AsianFontProvider());
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		convertHtmlToPdf("D:\\test.html", "D:\\test1.pdf");
	}

	/**
	 * 字体处理
	 */
	static class AsianFontProvider extends XMLWorkerFontProvider {
		@Override
		public Font getFont(final String fontname, String encoding, float size, final int style) {
			try {
				BaseFont bfChinese = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				return new Font(bfChinese, size, style);
			} catch (Exception e) {
			}
			return super.getFont(fontname, encoding, size, style);
		}

	}

}
