package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.CongVanDAO;
import dao.YeuCauDAO;
import map.siteMap;
import model.CTVatTu;
import model.CongVan;
import model.YeuCau;
import util.DateUtil;
import util.DateUtil;

@Controller
public class BcvttController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @RequestMapping("/manageBcvtt")
	public ModelAndView manageBcvtt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	YeuCauDAO yeuCauDAO = new YeuCauDAO();
    	
    	CongVanDAO congVanDAO = new CongVanDAO();
    	HttpSession session = request.getSession(false);
    	
    	String action = request.getParameter("action");
    	if ("manageBcvtt".equalsIgnoreCase(action)) {
    		return new ModelAndView(siteMap.baoCaoVatTuThieu);
		}
    	if("chitiet".equalsIgnoreCase(action)){
//    		String loaiBc = new String(action);
    		String ngaybd = request.getParameter("ngaybd");
    		String ngaykt = request.getParameter("ngaykt");
    			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.getTrangThai(DateUtil.parseDate(ngaybd), DateUtil.parseDate(ngaykt));
        		HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = new HashMap<Integer, ArrayList<YeuCau>>();
        			for(CongVan congVan: congVanList){
        				int cvId = congVan.getCvId();
        				ArrayList<YeuCau> yeuCau = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
        				yeuCauHash.put(cvId,yeuCau);
        			}
        			if ((Integer)congVanList.get(0).getCvId() != null)
        				System.out.println(yeuCauHash.size() + "*" + congVanList.get(0).getCvId());
        			session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
        			session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
        			session.setAttribute("action", action);
        			session.setAttribute("congVanList", congVanList);
        			session.setAttribute("yeuCau", yeuCauHash);
        			
        			congVanDAO.disconnect();
        			yeuCauDAO.disconnect();
        			return new ModelAndView(siteMap.baoCaoVatTuThieu);
    		}
    	else if ("tonghop".equalsIgnoreCase(action)){
    		String ngaybd = request.getParameter("ngaybd");
    		String ngaykt = request.getParameter("ngaykt");
//    		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) new CongVanDAO().getTrangThai(DateUtil.parseDate(ngaybd), DateUtil.parseDate(ngaykt));

//    		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) new CongVanDAO().getTrangThai(DateUtil.parseDate(ngaybd), DateUtil.parseDate(ngaykt));

    		String cvSo = request.getParameter("cvSo");
    		System.out.println(ngaybd);
    		System.out.println(ngaykt);
    		CongVanDAO congVan = new CongVanDAO();
//    		ArrayList<CongVan> congVanList = (ArrayList<CongVan>)congVanDAO.getByCvSo(cvSo);
    		HashMap<Integer, Integer> yeuCauHash = new HashMap<Integer, Integer>();
    		
//    			for(CongVan congVan: congVanList){
    		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) yeuCauDAO.getVTThieu();
    		HashMap<Integer, CTVatTu> ctvtHash = new CTVatTuDAO().getHashMap();
    		
    		HashMap<Integer, ArrayList<Integer>> soDenHash = new HashMap<Integer, ArrayList<Integer>>();
    		HashMap<Integer, ArrayList<Integer>> cvIdHash = new HashMap<Integer, ArrayList<Integer>>();
    		
//    		ArrayList<Integer> soDenList = new ArrayList<Integer>();
//    		ArrayList<Integer> cvIdList = new ArrayList<Integer>();
    		for(YeuCau yeuCau: yeuCauList){
    				int ctVtId = yeuCau.getCtVatTu().getCtvtId();
//    				soDenList.add(yeuCau.getCvId());
    				int cvId = yeuCau.getCvId();
//    				cvIdList.add(cvId);
//    				soDenList.
//    				add(congVanDAO.getSoDen(cvId));
    				Integer slCu = yeuCauHash.get(ctVtId);
    				Integer soluong = yeuCau.getYcSoLuong();
    				if (slCu != null)
    					soluong += slCu;
<<<<<<< HEAD
=======

    				
//    				ArrayList<Integer> soDenList = soDenHash.get(ctVtId);
//    				ArrayList<Integer> soDenListCu = soDenHash.get(ctVtId);
>>>>>>> 2b6e24594d4aeb6187cb4c9d98e38920c555c95d

    				
    				ArrayList<Integer> cvList = new ArrayList<Integer>();
    				ArrayList<Integer> cvListCu = cvIdHash.get(ctVtId);
    				
    				ArrayList<Integer> soDenList = new ArrayList<Integer>();
    				ArrayList<Integer> soDenListCu = soDenHash.get(ctVtId);
    				int soDen = congVanDAO.getSoDen(cvId);
    				System.out.println(cvId);
    				System.out.println(soDen);
    				if (cvListCu != null) {
    					soDenList = soDenListCu;
    					cvList = cvListCu;
    				}
    				soDenList.add(soDen);
    				soDenHash.put(ctVtId, soDenList);
    				//
    				cvList.add(cvId);
    				cvIdHash.put(ctVtId, cvList);
    				
    				
    				cvIdHash.put(ctVtId, cvList);
    				yeuCauHash.put(ctVtId,soluong);
    			}
		    		session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
					session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
        			session.setAttribute("ctvtHash", ctvtHash);
        			session.setAttribute("action", action);
        			session.setAttribute("yeuCau", yeuCauHash);
        			session.setAttribute("cvIdHash", cvIdHash);
        			session.setAttribute("soDenHash", soDenHash);
        			//session.setAttribute("congVan", congVan);
        			session.setAttribute("yeuCau", yeuCauHash);
        			session.setAttribute("cvIdHash", cvIdHash);
        			session.setAttribute("soDenHash", soDenHash);
        			congVanDAO.disconnect();
        			yeuCauDAO.disconnect();
        			return new ModelAndView(siteMap.baoCaoVatTuThieu);
    	}
    	else
    	  return new ModelAndView("login");
	}

}
