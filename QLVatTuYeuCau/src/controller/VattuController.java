package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CTVatTu;
import model.ChatLuong;
import model.NoiSanXuat;
import model.VaiTro;
import model.VatTu;
import model.DonViTinh;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.ChatLuongDAO;
import dao.DonViDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;
import dao.CTVatTuDAO;
import dao.DonViTinhDAO;

@Controller
public class VattuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;

   @RequestMapping("/manageVattu")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VatTuDAO vatTuDAO = new VatTuDAO();
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
		String action = request.getParameter("action");
//		if("addVatTu".equalsIgnoreCase(action)) {
//			
//			String vtMa = request.getParameter("vtMa");
//			String vtTen = request.getParameter("vtTen");
//			String dvt = request.getParameter("dvt");
//
//			if(new VatTuDAO().getVatTu(vtMa) != null){
//				request.setAttribute("error", "Vật tư đã tồn tại");
//				System.out.println("Vat tu da ton tai");
//				return new ModelAndView("danh-muc-vat-tu");
//			}
//			else{
//				vatTuDAO.addVatTu(new VatTu(vtMa,vtTen,dvt,0));
//				ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) new VatTuDAO().getAllVatTu();
//				return new ModelAndView("danh-muc-vat-tu", "vatTuList", vatTuList);
//			}
//			
//		}
//		if("deleteVatTu".equalsIgnoreCase(action)) {
//			String[] vtMaList = request.getParameterValues("vtMa");
//			for(String s : vtMaList) {
//				
//					vatTuDAO.deleteVatTu(s);
//			}
//			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.getAllVatTu();
//			return new ModelAndView("danh-muc-vat-tu", "vatTuList", vatTuList);
//
//		}
		if("manageVattu".equalsIgnoreCase(action)) {
			long size = vatTuDAO.size();
			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.limit(page-1, 10);
			request.setAttribute("size", size);
			ArrayList<NoiSanXuat> noiSanXuatList =  (ArrayList<NoiSanXuat>) noiSanXuatDAO.getAllNoiSanXuat();
			ArrayList<ChatLuong> chatLuongList =  (ArrayList<ChatLuong>) chatLuongDAO.getAllChatLuong();
			ArrayList<DonViTinh> donViTinhList =  (ArrayList<DonViTinh>) donViTinhDAO.getAllDonViTinh();
			request.setAttribute("noiSanXuatList", noiSanXuatList);
			request.setAttribute("chatLuongList", chatLuongList);
			request.setAttribute("donViTinhList", donViTinhList);
			request.setAttribute("vatTuList", vatTuList);
			return new ModelAndView("danh-muc-vat-tu");
		}
		return new ModelAndView("login");
	}
   @RequestMapping(value="/preEditVattu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditVattu(@RequestParam("vtMa") String vtMa) {
			System.out.println("****" + vtMa + "****");
			VatTuDAO VatTuDAO = new VatTuDAO();
			VatTu vt = VatTuDAO.getVatTu(vtMa);
			return JSonUtil.toJson(vt);
		}
	@RequestMapping(value="/addVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("dvt") String dvt) {
		String result = "";
		System.out.println("MA: "+vtMa);
		int id = Integer.parseInt(dvt);
		if(new VatTuDAO().getVatTu(vtMa) == null)
		{
			new VatTuDAO().addOrUpdateVatTu(new VatTu(vtMa, vtTen, new DonViTinh(id),0));
			System.out.println("success");
			result = "success";
			
			
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
			return JSonUtil.toJson(result);
	}
	@RequestMapping(value="/timKiemVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String timKiemVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen) {
		VatTuDAO vtDAO = new VatTuDAO();
		System.out.println("Ma goi qua " + vtMa);
		System.out.println("Ten goi qua " + vtTen);
		if(vtMa != ""){
			ArrayList<VatTu> vtList = (ArrayList<VatTu>) vtDAO.searchVtMa(vtMa);
			System.out.println("MA: "+vtMa);
			return JSonUtil.toJson(vtList);
		}
		else
		{
			ArrayList<VatTu> vtList = (ArrayList<VatTu>) vtDAO.searchVtTen(vtTen);
			System.out.println("Ten: "+vtTen);
			return JSonUtil.toJson(vtList);
		}
	}
	@RequestMapping(value="/updateVattu", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateVattu(@RequestParam("vtMaUpdate") String vtMaUpdate, @RequestParam("vtTenUpdate") String vtTenUpdate, @RequestParam("dvtUpdate") String dvtUpdate) {
		int id = Integer.parseInt(dvtUpdate);
		VatTu vt = new VatTu(vtMaUpdate, vtTenUpdate, new DonViTinh(id),0);
		new VatTuDAO().updateVatTu(vt);
		return JSonUtil.toJson(vt);
	}
	@RequestMapping(value="/deleteVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteVattu(@RequestParam("vtList") String vtList) {
		String[] str = vtList.split("\\, ");
		
		VatTuDAO vtDAO =  new VatTuDAO();
		for(String vtMa : str) {
			vtDAO.deleteVatTu(vtMa);
		}
		return JSonUtil.toJson(vtList);
	}
	
	@RequestMapping(value="/loadPageVatTu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVt(@RequestParam("pageNumber") String pageNumber) {
		String result = "";
		System.out.println("MA: " + pageNumber);
		VatTuDAO vtDAO = new VatTuDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<VatTu> vtList = (ArrayList<VatTu>) vtDAO.limit((page -1 ) * 10, 10);
		
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
			return JSonUtil.toJson(vtList);
	}
}
