package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import map.siteMap;
import model.CTVatTu;
import model.ChatLuong;
import model.MucDich;
import model.NoiSanXuat;
import model.VatTu;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.ChatLuongDAO;
import dao.VatTuDAO;
import dao.CTVatTuDAO;

@Controller
public class CtvtController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
   @RequestMapping("/manageCtvt")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VatTuDAO vatTuDAO = new VatTuDAO();
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		HttpSession session = request.getSession(false);
		String action = request.getParameter("action");
//		if("addVatTu".equalsIgnoreCase(action)) {
//			
//			String vtMa = request.getParameter("vtMa");
//			String vtTen = request.getParameter("vtTen");
//			String dvt = request.getParameter("dvt");
//			String nsxMa = request.getParameter("nsxMa");
//			String clMa = request.getParameter("clMa");
//			int dinhMuc = Integer.parseInt(request.getParameter("dinhMuc"));
//			int soLuongTon = Integer.parseInt(request.getParameter("soLuongTon"));
//
//			if(new CTVatTuDAO().getCTVatTu(vtMa, nsxMa, clMa) != null){
//
//				request.setAttribute("error", "Váº­t tÆ° Ä‘Ã£ tá»“n táº¡i");
//				System.out.println("Vat tu da ton tai");
//				return new ModelAndView("danh-muc-vat-tu");
//			}
//			else{
//				vatTuDAO.addVatTu(new VatTu(vtMa,vtTen,dvt));
//				ctVatTuDAO.addCTVatTu(new CTVatTu(new VatTu(vtMa,vtTen,dvt), new NoiSanXuat(nsxMa), new ChatLuong(clMa), dinhMuc, soLuongTon));
//				ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) new VatTuDAO().getAllVatTu();
//				ArrayList<CTVatTu> ctVatTuList =  (ArrayList<CTVatTu>) new CTVatTuDAO().getAllCTVatTu();
//				return new ModelAndView("danh-muc-vat-tu", "ctVatTuList", ctVatTuList);
//			}
//			
//		}
		if("deleteVatTu".equalsIgnoreCase(action)) {
			String[] vtIdList = request.getParameterValues("vtMa");
			for(String s : vtIdList) {
				
					ctVatTuDAO.deleteCTVatTu(s);
			}
			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.getAllVatTu();
			return new ModelAndView("danh-muc-vat-tu", "vatTuList", vatTuList);
		}
		if("manageCtvt".equalsIgnoreCase(action)) {
			long size = ctVatTuDAO.size();
			ArrayList<CTVatTu> ctVatTuList =  (ArrayList<CTVatTu>) ctVatTuDAO.limit(page - 1, 10);
			request.setAttribute("page", size/10);
			session.setAttribute("ctVatTuList", ctVatTuList);
			return new ModelAndView(siteMap.ctVatu);
		}
		return new ModelAndView("login");
	}
   @RequestMapping(value="/showCTVatTu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String showCTVatTu(@RequestParam("vtMa")  String vtMa) {
			
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) ctVatTuDAO.getCTVTu(vtMa);
			if(listCTVatTu.size() == 0) {
				VatTu vatTu = new VatTuDAO().getVatTu(vtMa);
				return JSonUtil.toJson(vatTu);
			}
			System.out.println(listCTVatTu.get(0).getVatTu().getVtTen());
			return JSonUtil.toJson(listCTVatTu);
		}
   @RequestMapping(value="/preEditCTVattu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditCTVattu(@RequestParam("ctvtId") String ctvtId) {
			System.out.println("****" + ctvtId + "****");
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			CTVatTu vt = ctVatTuDAO.getCTVatTuById(Integer.parseInt(ctvtId));
			System.out.println("****" + vt.getVatTu().getVtMa() + "****");
			return JSonUtil.toJson(vt);
		}
   
	@RequestMapping(value="/addCTVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addCTVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("noiSanXuat") String noiSanXuat, @RequestParam("chatLuong") String chatLuong, 
			 @RequestParam("dvt") String dvt, @RequestParam("dinhMuc") String dinhMuc, @RequestParam("soLuongTon") String soLuongTon) {
		//String result = "";
		System.out.println("MA: " + vtMa);
		System.out.println("NSX: " + noiSanXuat);
		System.out.println("CL: " + chatLuong);
		if(new CTVatTuDAO().getCTVatTu(vtMa, noiSanXuat, chatLuong) == null)
		{
			
			CTVatTu ctvt = new CTVatTu(new VatTu(vtMa) , new NoiSanXuat(noiSanXuat), new ChatLuong(chatLuong), Integer.parseInt(dinhMuc), Integer.parseInt(soLuongTon),0);
			new CTVatTuDAO().addOrUpdateCTVatTu(ctvt);
			System.out.println("success");

			int id = new CTVatTuDAO().getLastInsert()-1;
			CTVatTu ctVatTu = new CTVatTuDAO().getCTVatTuById(id);
			return JSonUtil.toJson(ctVatTu);
		
		}
		else
		{
			System.out.println("fail");	
			return JSonUtil.toJson("");
		}
		
	}
	@RequestMapping(value="/updateCTVattu", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateCTVattu(@RequestParam("vtMaUpdate") String vtMaUpdate,  @RequestParam("nsxUpdate") String nsxUpdate, @RequestParam("clUpdate") String clUpdate, @RequestParam("dinhMucUpdate") String dinhMucUpdate, @RequestParam("soLuongTonUpdate") String soLuongTonUpdate) {
//		System.out.println(vtMaUpdate + "&" + nsxUpdate + "&" + clUpdate + "&" + dinhMucUpdate + "&" + soLuongTonUpdate);
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		System.out.println(vtMaUpdate + "&" + nsxUpdate + "&" + clUpdate + "&" + dinhMucUpdate + "&" + soLuongTonUpdate);
		CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMaUpdate, nsxUpdate, clUpdate);
		System.out.println(vtMaUpdate + "&" + nsxUpdate + "&" + clUpdate + "&" + dinhMucUpdate + "&" + soLuongTonUpdate);
		// CTVatTu ctvt = new CTVatTu(new VatTu(vtMaUpdate) , new
		// NoiSanXuat(nsxUpdate), new ChatLuong(clUpdate),
		// Integer.parseInt(dinhMucUpdate), Integer.parseInt(soLuongTonUpdate));
//		ctvt.setDinhMuc(Integer.parseInt(dinhMucUpdate));
//		ctvt.setNoiSanXuat(new NoiSanXuat(nsxUpdate));
//		ctvt.setChatLuong(new ChatLuong(clUpdate));
//		ctvt.setSoLuongTon(Integer.parseInt(soLuongTonUpdate));
//		System.out.println(ctvt.getCtvtId() + ctvt.getSoLuongTon());
//		ctvtDAO.updateCTVatTu(ctvt);
		// new CTVatTuDAO().updateCTVatTu(ctvt);
		if (ctvt == null)
			System.out.println("Result  = null");
		else 
			System.out.println(ctvt.getCtvtId());
		return JSonUtil.toJson(ctvt);
	}

	@RequestMapping(value = "/deleteCTVattu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteVattu(@RequestParam("ctvtList") String ctvtList) {
		String[] str = ctvtList.split("\\, ");
		
		CTVatTuDAO ctvtDAO =  new CTVatTuDAO();
		for(String ctvtId : str) {
			ctvtDAO.deleteCTVatTu(ctvtId);
		}
		return JSonUtil.toJson(ctvtList);
	}
	
	@RequestMapping(value="/loadPageCtvt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCtvt(@RequestParam("pageNumber") String pageNumber) {
		String result = "";
		System.out.println("MA: " + pageNumber);
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<CTVatTu> ctvtList = (ArrayList<CTVatTu>) ctvtDAO.limit((page -1 ) * 10, 10);
		
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
			return JSonUtil.toJson(ctvtList);
	}
	
}
