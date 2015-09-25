package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.NoiSanXuatDAO;
import dao.ReadExcelCtvt;
import dao.VatTuDAO;
import map.siteMap;
import model.CTVatTu;
import model.ChatLuong;
import model.NoiSanXuat;
import model.VatTu;
import util.FileUtil;

/**
 * Servlet implementation class ReadExcel
 */
@Controller
public class ReadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int BUFFER_SIZE = 4096;
	String filePath = "./";
	String pathTemp = "./";
	private boolean isMultipart;
	private int maxFileSize = 10000 * 1024;
	private int maxMemSize = 5000 * 1024;
	private File file;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping("/readExcel")
	protected ModelAndView readExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(pathTemp));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			String extenstionFile = FileUtil.getExtensionByPath(fileName);
			File file;
			if ("xls".equals(extenstionFile)) {
				file = new File("temp.xls");
				fi.write(file);
				if(!ReadExcelCtvt.readXls(file))
					return new ModelAndView("import-excel", "status", "formatException");
			}
			else if ("xlsx".equals(extenstionFile)) {
				file = new File("temp.xlsx");
				fi.write(file);
				if(!ReadExcelCtvt.readXlsx(file))
					return new ModelAndView("import-excel", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excel", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(siteMap.ctVatu);

	}

}
