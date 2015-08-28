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
import javax.swing.JOptionPane;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import dao.CTNguoiDungDAO;
import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.CongVanDAO;
import dao.DonViDAO;
import dao.FileDAO;
import dao.MucDichDAO;
import dao.NoiSanXuatDAO;
import dao.TrangThaiDAO;
import map.siteMap;
import model.CTNguoiDung;
import model.CTVatTu;
import model.ChatLuong;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.TrangThai;
import util.DateUtil;
import util.FileUtil;
import util.JSonUtil;
import util.StringUtil;


@Controller
public class CvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	private final String tempPath = "./"; 
    private final String pathFile = "./";
    public ModelAndView getCongvan( HttpServletRequest request) {
    	MucDichDAO mucDichDAO =  new MucDichDAO();
    	DonViDAO donViDAO =  new DonViDAO();
    	TrangThaiDAO trangThaiDAO =  new TrangThaiDAO();
    	CongVanDAO congVanDAO =  new CongVanDAO();
    	FileDAO fileDAO =  new FileDAO();
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.getAllCongVan();
		HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
		ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
		ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
		for(CongVan congVan : congVanList) {
			int cvId = congVan.getCvId();
			fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
		}
		request.setAttribute("congVanList", congVanList);
		request.setAttribute("fileHash", fileHash);
		request.setAttribute("mucDichList", mucDichList);
		request.setAttribute("donViList", donViList);
		request.setAttribute("trangThaiList", trangThaiList);
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
//	    	MucDichDAO mucDichDAO =  new MucDichDAO();
//	    	DonViDAO donViDAO =  new DonViDAO();
//	    	TrangThaiDAO trangThaiDAO =  new TrangThaiDAO();
	    	
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.getAllCongVan();
			HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
//			ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
//			ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
			for(CongVan congVan : congVanList) {
				int cvId = congVan.getCvId();
				File file = fileDAO.getByCongVanId(cvId);
				fileHash.put(cvId, file);
			}
			System.out.println("OK2");
//			request.setAttribute("congVanList", congVanList);
//			request.setAttribute("fileHash", fileHash);
//			request.setAttribute("mucDichList", mucDichList);
//			request.setAttribute("donViList", donViList);
//			return new ModelAndView(siteMap.congVan);
//			congVanDAO.close();
//			fileDAO.close();
//			mucDichDAO.close();
//			donViDAO.close();
//			trangThaiDAO.close();
			congVanDAO.disconnect();
			fileDAO.disconnect();
			return getCongvan(request);
		}
		/*
		if("manageCv".equalsIgnoreCase(action)) {
			long size = congVanDAO.size();
			ArrayList<CongVan> congVanList =  (ArrayList<CongVan>) congVanDAO.limit(page, 10);
			request.setAttribute("size", size);
			return new ModelAndView("cong-van", "congVanList", congVanList);
		}
		*/
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
//		if("addCv".equalsIgnoreCase(action))
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
    	MultipartRequest multipartRequest = new MultipartRequest(request, tempPath);
		String action = multipartRequest.getParameter("action");
//		int soDen = Integer.parseInt(multipartRequest.getParameter("soDen"));
		String cvSo = multipartRequest.getParameter("cvSo");
		if (congVanDAO.getByCvSo(cvSo) != null) {
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
		if (cvNgayNhan.getDate() == 1)
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
//		new CongVan(cvId, soDen, cvNgayNhan, cvSo, cvNgayDi, trichYeu, butPhe, mucDich, trangThai, donVi)
		System.out.println(dvMa);
//		java.util.Date.
		congVanDAO.addCongVan(new CongVan (cvId, soDen, cvSo, cvNgayNhan, cvNgayGoi, trichYeu, butPhe, new MucDich(mdMa), new TrangThai("CGQ",""), new DonVi(dvMa),0));
		fileDAO.addFile(new File(pathFile + fileName, moTa, cvId));
//    	
		congVanDAO.close();
		fileDAO.close();
		return getCongvan(request);
//    	return new ModelAndView("login");
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
    	MultipartRequest multipartRequest = new MultipartRequest(request, tempPath);
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
//		String moTa = multipartRequest.getParameter("moTa");
//		int soDen = congVanDAO.getSoDenMax();
//		if (cvNgayNhan.getDate() == 1)
//			soDen = 1;
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
//		new CongVan(cvId, soDen, cvNgayNhan, cvSo, cvNgayDi, trichYeu, butPhe, mucDich, trangThai, donVi)
		System.out.println(dvMa);
//		java.util.Date.
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
//		congVanDAO.updateCongVan(new CongVan (cvId, Integer.parseInt(soDen), cvSo, cvNgayNhan, cvNgayGoi, trichYeu, butPhe, new MucDich(mdMa), new TrangThai("CGQ",""), new DonVi(dvMa),0));
		//fileDAO.addFile(new File(pathFile + fileName, moTa, cvId));
//    	File
		if (fileName.length() !=0) {
			File file = fileDAO.getByCongVanId(cvId);
			file.setDiaChi(pathFile + fileName);
			fileDAO.updateFile(file);
			fileDAO.close();
		}
		congVanDAO.close();
		
		return getCongvan(request);
//    	return new ModelAndView("login");
    }
	@RequestMapping(value="/deleteCv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteNsx(@RequestParam("cvId") String cvId) {
		String[] congVanList = cvId.split("\\, ");
		for (String s : congVanList) {
			int id = Integer.parseInt(s);
			new CongVanDAO().deleteCongVan(id);
		}
		
	//	return toJson(nsxList);
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
		
		/*
		if(new NoiSanXuatDAO().getNoiSanXuat(nsxMa)==null)
		{
			new NoiSanXuatDAO().addNoiSanXuat(new NoiSanXuat(nsxMa, nsxTen,0));
			System.out.println("success");
			result = "success";	
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
		*/
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
}
