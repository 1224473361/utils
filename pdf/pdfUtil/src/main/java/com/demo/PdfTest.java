package com.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.demo.entity.PdfField;
import com.demo.util.ManualPdfUtil;
import com.itextpdf.io.font.FontProgramFactory;

public class PdfTest {

	public static void main(String[] args) throws IOException {
		// 查看所有注册字体
		// FontProgramFactory.registerSystemFontDirectories();
		Set<String> set = FontProgramFactory.getRegisteredFonts();
		for (String string : set) {
			System.out.println(string);
		}
		// FontProgramFactory.createRegisteredFont(fontName)

		String userDir = System.getProperty("user.dir");
		// 表单域
		List<PdfField> flist = new ArrayList<>();
		PdfField field = new PdfField();
		field.setPage(1);
		field.setFieldKey("k1");
		field.setFieldValue("大师傅大师傅但是");
		field.setPositionX(150f);
		field.setPositionY(500F);
		flist.add(field);

		field = new PdfField();
		field.setPage(1);
		field.setFieldKey("k2");
		field.setFieldValue("sdfdsfsd");
		field.setPositionX(250f);
		field.setPositionY(700F);
		flist.add(field);
		// 源文件
		File pdf = new File(userDir + "\\src\\main\\resources\\template\\template.pdf");
		// 保存路径
		String savePath = userDir + "\\src\\main\\resources\\pdfs\\generatePdf.pdf";
		ManualPdfUtil.generatePdfByTemplateAndDatas(pdf, savePath, userDir + "\\src\\main\\resources\\font\\simsun.ttf",
				flist);
		System.out.println("创建结束》》》》");
	}
}
