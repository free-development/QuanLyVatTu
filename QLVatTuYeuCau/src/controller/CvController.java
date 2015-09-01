package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import dao.CongVanDAO;
import dao.DonViDAO;
import dao.FileDAO;
import dao.MucDichDAO;
import dao.TrangThaiDAO;
import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.TrangThai;
import util.DateUtil;
import util.FileUtil;
import util.JSonUtil;


@Controller
public class CvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	private HttpSession session;
	private final String tempPath = "/File/Temp"; 
    private final String pathFile = "/File/File";
    private final int maxSize = 52428800;
    
    private int year = 0;
    private int month = 0;
    private int date = 0;
    private String ttMa = "";
    private String column = "";
    private String columnValue = "";
    
    public ModelAndView getCongvan( HttpServletRequest request) {
//    	session = request.getSession(false);
    	
    	MucDichDAO mucDichDAO =  new MucDichDAO();
    	DonViDAO donViDAO =  new DonViDAO();
    	TrangThaiDAO trangThaiDAO =  new TrangThaiDAO();
    	CongVanDAO congVanDAO =  new CongVanDAO();
    	FileDAO fileDAO =  new FileDAO();
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, null, 0, 3);
		HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
		ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
		ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
		ArrayList<Integer> yearList = congVanDAO.groupByYearLimit(5);
		long size = congVanDAO.size();
		for(CongVan congVan : congVanList) {
			int cvId = congVan.getCvId();
			fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
		}
		request.setAttribute("congVanList", congVanList);
		request.setAttribute("fileHash", fileHash);
		request.setAttribute("mucDichList", mucDichList);
		request.setAttribute("donViList", donViList);
		request.setAttribute("trangThaiList", trangThaiList);
		request.setAttribute("yearList", yearList);
		request.setAttribute("size", size);
//		congVanDAO.close();
//		donViDAO.close();
//		trangThaiDAO.close();
//		mucDichDAO.close();
//		fileDAO.close();
		congVanDAO.disconnect();
		donViDAO.disconnect();
		trangThaiDAO.disconnect();
		fileDAO.disconnect();
		mucDichDAO.disconnect();
		return new ModelAndView(siteMap.congVan);
    }
    
    @RequestMapping("/cvManage")
	public ModelAndView manageCV(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
    	
		String action = request.getParameter("action");
		if("manageCv".equalsIgnoreCase(action)) {
			FileDAO fileDAO = new FileDAO();
	    	CongVanDAO congVanDAO = new CongVanDAO();
	    	
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, null, 0, 3);
			HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
			for(CongVan congVan : congVanList) {
				int cvId = congVan.getCvId();
				File file = fileDAO.getByCongVanId(cvId);
				fileHash.put(cvId, file);
			}
			congVanDAO.disconnect();
			fileDAO.disconnect();
			return getCongvan(request);
		}
	
		if("download".equals(action)) {
			try {
				FileDAO fileDAO = new FileDAO();
				int cvId = Integer.parseInt(request.getParameter("file"));
				model.File f = fileDAO.getByCongVanId(cvId);
				java.io.File file = new java.io.File(f.getDiaChi());
				fileDAO.close();
				return new ModelAndView(siteMap.fileDownload, "file", file);
				
			} catch (NumberFormatException e){
				System.out.println("Cannot convert to int");
			}
		}
		return new ModelAndView("login");
	}
   
    @RequestMapping("addCongVan")
    public ModelAndView addCV(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	request.ge
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
    	CongVanDAO congVanDAO = new CongVanDAO();
    	FileDAO fileDAO = new FileDAO();
    	int cvId = congVanDAO.getLastInsert();
    	int fileId = fileDAO.getLastInsert();
    	MultipartRequest multipartRequest = new  MultipartRequest(request, tempPath, maxSize);
		String action = multipartRequest.getParameter("action");
		String cvSo = multipartRequest.getParameter("cvSo");
		CongVan congVanCheck = congVanDAO.getByCvSo(cvSo);
		if (congVanCheck != null && congVanCheck.getDaXoa() == 0) {
			request.setAttribute("error", "error");
			return getCongvan(request);
		}
		Date cvNgayGoi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoi"));
		Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhan"));
		String mdMa = multipartRequest.getParameter("mucDich");
		String dvMa = multipartRequest.getParameter("donVi");
		String trichYeu = multipartRequest.getParameter("trichYeu");
		String butPhe = multipartRequest.getParameter("butPhe");
		String moTa = multipartRequest.getParameter("moTa");
		int soDen = congVanDAO.getSoDenMax();
		if (cvNgayNhan.getDate() == 1 && soDen != 1)
			soDen = 1;
		// upload file
		String fileFullName = "";
		Enumeration<String> listFileName = multipartRequest.getFileNames();		
		String fileName = "";
		while(listFileName.hasMoreElements()) {
			fileFullName = multipartRequest.getFilesystemName(listFileName.nextElement().toString());
			//int i = fileName.lastIndexOf("."); 
			java.io.File file = new java.io.File(tempPath + fileFullName);
			fileName = FileUtil.getNameFile(file);
			String fileExtension = FileUtil.getExtension(file);
			if(fileExtension.length() > 0) {
				 fileName = fileName + "-" + soDen + "." + fileExtension;
			 } else {
				 fileName = fileName + "-" + soDen;
			 }
				
			file.renameTo(new java.io.File(pathFile + fileName));
			
		}
		
		if (congVanCheck != null) {
			congVanCheck.setSoDen(soDen);
			congVanCheck.setCvSo(cvSo);
			congVanCheck.setButPhe(butPhe);
			congVanCheck.setCvNgayDi(cvNgayGoi);
			congVanCheck.setCvNgayNhan(cvNgayNhan);
			congVanCheck.setDonVi(new DonVi(dvMa));
			congVanCheck.setMucDich(new MucDich(mdMa));
			congVanCheck.setTrangThai(new TrangThai("CGQ",""));
			congVanCheck.setTrichYeu(trichYeu);
			congVanCheck.setDaXoa(0);
			congVanDAO.updateCongVan(congVanCheck);
			File file = fileDAO.getByCongVanId(congVanCheck.getCvId());
			file.setDiaChi(pathFile + fileName);
			file.setMoTa(moTa);
			fileDAO.updateFile(file);
		} else {
			congVanDAO.addCongVan(new CongVan (soDen, cvSo, cvNgayNhan, cvNgayGoi, trichYeu, butPhe, new MucDich(mdMa), new TrangThai("CGQ",""), new DonVi(dvMa),0));
			fileDAO.addFile(new File(pathFile + fileName, moTa, cvId));
    	}
		congVanDAO.close();
		fileDAO.close();
		return getCongvan(request);
    }
    
    @RequestMapping(value="/preEditCongVan", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("cvId") int cvId) {
    	CongVanDAO congVanDAO = new CongVanDAO();
		System.out.println("OK");
		CongVan congVan = congVanDAO.getCongVan(cvId);
		return JSonUtil.toJson(congVan);
	}
    @RequestMapping("updateCongVan")
    public ModelAndView updateCongVan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	request.ge
    	request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
    	CongVanDAO congVanDAO = new CongVanDAO();
    	FileDAO fileDAO = new FileDAO();
    	// 
    	MultipartRequest multipartRequest = new MultipartRequest(request, tempPath, maxSize);
		String action = multipartRequest.getParameter("action");
//		int soDen = Integer.parseInt(multipartRequest.getParameter("soDen"));
		String soDen = multipartRequest.getParameter("soDen");
		String cvSo = multipartRequest.getParameter("cvSo");
		
		Date cvNgayGoi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoiUpdate"));
		Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhanUpdate"));
		String mdMa = multipartRequest.getParameter("mucDichUpdate");
		String dvMa = multipartRequest.getParameter("donViUpdate");
		String trichYeu = multipartRequest.getParameter("trichYeuUpdate");
		String butPhe = multipartRequest.getParameter("butPheUpdate");
		String ttMa = multipartRequest.getParameter("ttMaUpdate");
		String fileFullName = "";
		Enumeration<String> listFileName = multipartRequest.getFileNames();		
		String fileName = "";
		while(listFileName.hasMoreElements()) {
			fileFullName = multipartRequest.getFilesystemName(listFileName.nextElement().toString());
			//int i = fileName.lastIndexOf("."); 
			java.io.File file = new java.io.File(tempPath + fileFullName);
			fileName = FileUtil.getNameFile(file);
			String fileExtension = FileUtil.getExtension(file);
			if(fileExtension.length() > 0) {
				 fileName = fileName + "-" + soDen + "." + fileExtension;
			 } else {
				 fileName = fileName + "-" + soDen;
			 }
				
			file.renameTo(new java.io.File(pathFile + fileName));
			
		}
		CongVan congVan = congVanDAO.getByCvSo(cvSo);
		int cvId = congVan.getCvId();
		congVan.setButPhe(butPhe);
		congVan.setCvNgayDi(cvNgayGoi);
		congVan.setCvNgayNhan(cvNgayNhan);
		congVan.setDonVi(new DonVi(dvMa));
		congVan.setMucDich(new MucDich(mdMa));
		congVan.setTrangThai(new TrangThai(ttMa));
		congVan.setTrichYeu(trichYeu);
		congVanDAO.updateCongVan(congVan);
//    	File
		if (fileName.length() !=0) {
			File file = fileDAO.getByCongVanId(cvId);
			file.setDiaChi(pathFile + fileName);
			fileDAO.updateFile(file);
			fileDAO.close();
		}
		congVanDAO.close();
		
		return getCongvan(request);
    }
	@RequestMapping(value="/deleteCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteNsx(@RequestParam("cvId") String cvId) {
		String[] congVanList = cvId.split("\\, ");
		for (String s : congVanList) {
			int id = Integer.parseInt(s);
			new CongVanDAO().deleteCongVan(id);
		}
		
		return JSonUtil.toJson(cvId);
	}
	
	@RequestMapping(value="/loadPageCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCv(@RequestParam("pageNumber") String pageNumber) {
		String result = "";
		System.out.println("MA: " + pageNumber);
		CongVanDAO cvDAO = new CongVanDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<CongVan> cvList = (ArrayList<CongVan>) cvDAO.limit((page -1 ) * 10, 10);
		
		return JSonUtil.toJson(cvList);
	}
	@RequestMapping(value="/preUpdateCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateCv(@RequestParam("congVan") String congVan) {
		CongVanDAO congVanDAO = new CongVanDAO();
		int id = Integer.parseInt(congVan);
		CongVan cv = congVanDAO.getCongVan(id);
		congVanDAO.close();
		return JSonUtil.toJson(cv);
	}
	@RequestMapping(value="/loadByYear", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByYear(@RequestParam("year") String yearRequest) {
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		year = Integer.parseInt(yearRequest);
		ArrayList<Integer> monthList = congVanDAO.groupByMonth(year);
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		month = 0;
		date = 0;
		if (year != 0)
			conditions.put("year", year);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
		orderBy.put("cvNgayNhan", true);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		for (CongVan congVan : congVanList) {
			File file = fileDAO.getByCongVanId(congVan.getCvId());
			fileList.add(file);
		}
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		objectList.add(monthList);
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByMonth", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByMonth(@RequestParam("month") String monthRequest) {
		month = Integer.parseInt(monthRequest);
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		ArrayList<Integer> dateList = congVanDAO.groupByDate(year, month);
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
		orderBy.put("cvNgayNhan", true);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		for (CongVan congVan : congVanList) {
			File file = fileDAO.getByCongVanId(congVan.getCvId());
			fileList.add(file);
		}
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		objectList.add(dateList);
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadByDate", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadByDate(@RequestParam("date") String dateRequest) {
		date = Integer.parseInt(dateRequest);
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (date != 0)
			conditions.put("day", date);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0) {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
		orderBy.put("cvNgayNhan", true);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		for (CongVan congVan : congVanList) {
			File file = fileDAO.getByCongVanId(congVan.getCvId());
			fileList.add(file);
		}
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/searchByTrangThai", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String searchByTrangThai(@RequestParam("trangThai") String trangThai) {
		ttMa = trangThai;
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (date != 0)
			conditions.put("day", date);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0)  {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
			
		orderBy.put("cvNgayNhan", true);
		orderBy.put("soDen", true);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		for (CongVan congVan : congVanList) {
			File file = fileDAO.getByCongVanId(congVan.getCvId());
			fileList.add(file);
		}
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/filter", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String filter(@RequestParam("filter") String filter, @RequestParam("filterValue") String filterValue) {
		column = filter;
		columnValue = filterValue;
		CongVanDAO congVanDAO = new CongVanDAO();
		FileDAO fileDAO = new FileDAO();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (year != 0)
			conditions.put("year", year);
		if (month != 0)
			conditions.put("month", month);
		if (date != 0)
			conditions.put("day", date);
		if (ttMa.length() > 0)
			conditions.put("trangThai.ttMa", ttMa);
		if (column.length() > 0 && columnValue.length() > 0)  {
			if (column.equals("soDen"))
				conditions.put(column, Integer.parseInt(columnValue));
			else
				conditions.put(column, columnValue);
		}
			
		orderBy.put("cvNgayNhan", true);
		ArrayList<CongVan> congVanList = congVanDAO.searchLimit(conditions, orderBy, 0, 3);
		ArrayList<File> fileList = new ArrayList<File>();
		for (CongVan congVan : congVanList) {
			File file = fileDAO.getByCongVanId(congVan.getCvId());
			fileList.add(file);
		}
		
		congVanDAO.disconnect();
		fileDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>(); 
		objectList.add(congVanList);
		objectList.add(fileList);
		
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadPageCongVan", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCongVan(@RequestParam("pageNumber") String pageNumber) {
		try { 
			page = Integer.parseInt(pageNumber);
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			if (year != 0)
				conditions.put("year", year);
			if (month != 0)
				conditions.put("month", month);
			if (date != 0)
				conditions.put("day", date);
			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && columnValue.length() > 0)  {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt(columnValue));
				else
					conditions.put(column, columnValue);
			}
				
			orderBy.put("cvNgayNhan", true);
			orderBy.put("soDen", true);
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(conditions, orderBy, (page) *3, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = congVanDAO.size();
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>(); 
			objectList.add(congVanList);
			objectList.add(fileList);
			long page = (size % 3 == 0 ? size/3 : (size/3) +1 ); 
			objectList.add(page);
			return JSonUtil.toJson(objectList);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
