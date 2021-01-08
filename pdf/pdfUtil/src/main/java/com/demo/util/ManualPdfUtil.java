package com.demo.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.demo.entity.PdfField;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

/**
 * 根据模板pdf和字段域列表生成pdf
 */
public class ManualPdfUtil {

	/**
	 * 
	 * @param pdf            模板文件file
	 * @param newPdfFilePath 生成pdf的路径
	 * @param fieldList      字段域列表
	 * @throws IOException
	 */
	public static void generatePdfByTemplateAndDatas(File pdf, String newPdfFilePath, String fontFilePath,
			List<PdfField> fieldList) throws IOException {
		System.out.println("生成电子合同开始》》》》》》》》》》");
		// 编辑后的文件
		PdfWriter pdfWriter = new PdfWriter(newPdfFilePath);
		PdfDocument pdfDocument = new PdfDocument(new PdfReader(pdf), pdfWriter);
		PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(pdfDocument, true);
		PdfTextFormField pdfTextFormField;
		Rectangle rectangle;

		// 默认使用"黑体"
		FontProgram fontProgram = FontProgramFactory.createRegisteredFont("宋体");
		PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, true);

		for (PdfField pdfField : fieldList) {
			rectangle = new Rectangle(pdfField.getPositionX(), pdfField.getPositionY(), 400f, 20f);
			pdfTextFormField = PdfTextFormField.createText(pdfDocument, rectangle);
			// 设置字体 样式和大小
			pdfTextFormField.setFontAndSize(font, 14);
			// 设置字体颜色
			pdfTextFormField.setColor(ColorConstants.BLACK);
			pdfTextFormField.setFieldName(pdfField.getFieldKey());
			pdfTextFormField.setMappingName(pdfField.getFieldKey());
			// 设置文本
			pdfTextFormField.setValue(pdfField.getFieldValue());
			/** 将表单域加入pdf的指定页中 */
			pdfAcroForm.addField(pdfTextFormField, pdfDocument.getPage(pdfField.getPage()));
		}

		// 将表单域中的value嵌入到pdf文件中
		pdfAcroForm.flattenFields();
		pdfDocument.close();
		pdfWriter.close();
		System.out.println("生成电子合同结束【生成路径：" + newPdfFilePath + "】》》》》》》》》》》");
	}

}
