package com.xhx.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneratePdfUtil {
	public static final String TEMPLATE_FILE_PATH = "template/template.pdf";

	/**
	 * 简单生成pdf
	 * 
	 * @param filePath
	 */
	public static void ezGeneratePdf(String filePath, List<String> strs) {
		File f = FileUtil.getFileByAbsolute(filePath);
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(f));
			document.open();
			for (String string : strs) {
				document.add(new Paragraph(string));
			}
			document.add(new Paragraph("123"));
			document.close();
			log.info("end");
		} catch (Exception e) {
			log.error("失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 根据模板生成pdf
	 * 
	 * @param templateFilePath
	 * @param newFilePath
	 * @param params
	 */
	public static void generatePDFByTemplateAndAbsolute(String templateFilePath, String newFilePath,
			Map<String, String> params) {
		File f = FileUtil.getFileByAbsolute(templateFilePath);
		try (FileInputStream inputStream = new FileInputStream(f);
				FileOutputStream fo = new FileOutputStream(newFilePath);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
			PdfReader reader = new PdfReader(inputStream, null);
			int pageNum = reader.getNumberOfPages();
			PdfStamper stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			params.forEach((k, v) -> {
				try {
					form.setField(k, v);
				} catch (Exception e) {
					log.error("【{}】字段填充失败：【】", k, e.getMessage(), e);
				}
			});

			// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
			stamper.setFormFlattening(true);
			stamper.close();
			// 复制pdf
			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, fo);
			doc.open();
			PdfImportedPage importPage;
			for (int i = 1; i <= pageNum; i++) {
				importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
				copy.addPage(importPage);
			}
			doc.close();
			reader.close();
			log.info("end");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据模板生成pdf
	 * 
	 * @param templateFilePath
	 * @param newFilePath
	 * @param params
	 */
	public static void generatePDFByTemplate(String templateFilePath, String newFilePath, Map<String, String> params) {
		try (InputStream inputStream = FileUtil.getFileByResource(templateFilePath);
				FileOutputStream fo = new FileOutputStream(newFilePath);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
			PdfReader reader = new PdfReader(inputStream, null);
			int pageNum = reader.getNumberOfPages();
			PdfStamper stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			params.forEach((k, v) -> {
				try {
					form.setField(k, v);
				} catch (Exception e) {
					log.error("【{}】字段填充失败：【】", k, e.getMessage(), e);
				}
			});

			// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
			stamper.setFormFlattening(true);
			stamper.close();
			// 复制pdf
			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, fo);
			doc.open();
			PdfImportedPage importPage;
			for (int i = 1; i <= pageNum; i++) {
				importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
				copy.addPage(importPage);
			}
			doc.close();
			reader.close();
			log.info("end");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("partyA", "偶是甲方");
		params.put("partyAAddress", "一大师傅大师傅似的开发了好发大水发射点吧");
		params.put("partyB", "偶是乙方");
		params.put("partyBAddress", "一大师傅大师傅似的开发了的撒发大水发射点吧");
		generatePDFByTemplate(TEMPLATE_FILE_PATH, "D:/tmp/" + UUID.randomUUID().toString() + ".pdf", params);
	}
}
