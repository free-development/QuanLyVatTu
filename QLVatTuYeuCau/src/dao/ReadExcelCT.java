/**
 * 
 */
package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CTVatTu;
import model.ChatLuong;
import model.DonViTinh;
import model.NoiSanXuat;
import model.VatTu;

/**
 * @author quoioln
 *
 */
public class ReadExcelCT {
	public static boolean readXlsx(File file) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			ArrayList<VatTu> vatTuList = new ArrayList<VatTu>();
			ArrayList<NoiSanXuat> nsxList = new ArrayList<NoiSanXuat>();
			ArrayList<ChatLuong> chatLuongList = new ArrayList<ChatLuong>();
			ArrayList<CTVatTu> ctvtList = new ArrayList<CTVatTu>();
			ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>(); 
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				j++;
				if (j == 1)
					continue;
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String vtTen = "";
				String dvt = "";
				String nsxMa = "";
				String clMa = "";
				int soLuong = 0;
				int dinhMuc = 0;
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							vtMa = cell.getStringCellValue();
							break;
						case 1:
							vtTen = cell.getStringCellValue();
							break;
						case 2:
							dvt = cell.getStringCellValue();
							break;	
						case 3:
							nsxMa = cell.getStringCellValue();
							break;
						case 5:
							clMa = cell.getStringCellValue();
							break;
						}
					} 
					count++;
				}
				if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxMa.length() == 0 && clMa.length() == 0 )
					break;
				if (vtMa.length() == 0 || vtTen.length() == 0 || dvt.length() == 0 || nsxMa.length() == 0 || clMa.length() == 0 )
					return false;
				DonViTinh donViTinh = new DonViTinh(dvt, 0);
					dvtList.add(donViTinh);
//					System.out.println("VTMa = "+vtMa+ "\n vtTen = "+ vtTen + "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
				VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
				NoiSanXuat nsx = new NoiSanXuat(nsxMa);
				ChatLuong chatLuong = new ChatLuong(clMa);
				CTVatTu ctvt = new CTVatTu(new VatTu(vtMa), new NoiSanXuat(nsxMa), new ChatLuong(clMa),
						 dinhMuc,  soLuong, 0);
				vatTuList.add(vatTu);
				nsxList.add(nsx);
				chatLuongList.add(chatLuong);
				ctvtList.add(ctvt);
			}
			int lenght = vatTuList.size();
			
			for (int i = 0; i< lenght; i++) {
				
				VatTuDAO vtDAO = new VatTuDAO();
				NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
				ChatLuongDAO clDAO = new ChatLuongDAO();
				CTVatTuDAO ctvtDAO = new CTVatTuDAO();
				DonViTinhDAO dvtDAO = new DonViTinhDAO();
				VatTu vatTu = vatTuList.get(i);
				NoiSanXuat nsx = nsxList.get(i);
				ChatLuong chatLuong = chatLuongList.get(i);
				DonViTinh dvt = dvtList.get(i);
				
				NoiSanXuat noisx = nsxDAO.getNoiSanXuat(nsx.getNsxMa());
				ChatLuong chatluong = clDAO.getChatLuong(chatLuong.getClMa());
				if ((noisx != null) && (chatluong != null))
				{
					DonViTinh temp = dvtDAO.getDonViTinhByTen(dvt.getDvtTen());
					if (temp ==  null) {
						dvtDAO.addDonViTinh(dvt);
						dvt.setDvtId(new DonViTinhDAO().lastInsertId());
					} 
//					else {
//						temp.setDvtTen(dvt.getDvtTen());
//						temp.setDaXoa(0);
//						dvtDAO.updateDonViTinh(temp);
//					}
					VatTu vt = vtDAO.getVatTu(vatTu.getVtMa());
					VatTu vtTemp = vatTuList.get(i);
					if (vt == null)
					{
						vtDAO.addVatTu(vtTemp);
					}
					CTVatTu ctvt = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsx.getNsxMa(), chatLuong.getClMa());
					CTVatTu ctvtTemp = ctvtList.get(i);
					if (ctvt == null) {
						ctvtDAO.addCTVatTu(ctvtTemp);
					}
				}
			
			vtDAO.disconnect();
			nsxDAO.close();
			clDAO.close();
			ctvtDAO.close();
			dvtDAO.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	// read xls
	public static ArrayList<CTVatTu> readXls(File file) {
		ArrayList<CTVatTu> ctvtListError = new ArrayList<CTVatTu>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			ArrayList<VatTu> vatTuList = new ArrayList<VatTu>();
			ArrayList<String> nsxList = new ArrayList<String>();
			ArrayList<String> chatLuongList = new ArrayList<String>();
			ArrayList<CTVatTu> ctvtList = new ArrayList<CTVatTu>();
			ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>(); 
			
			ArrayList<VatTu> vatTuListError = new ArrayList<VatTu>();
			ArrayList<String> nsxListError = new ArrayList<String>();
			ArrayList<String> chatLuongListError = new ArrayList<String>();
		//	ArrayList<CTVatTu> ctvtListError = new ArrayList<CTVatTu>();
			ArrayList<DonViTinh> dvtListError = new ArrayList<DonViTinh>(); 
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				j++;
				if (j == 1)
					continue;
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String vtTen = "";
				String dvt = "";
				String nsxMa = "";
				String clMa = "";
				double soLuong = 0;
				double dinhMuc = 0;
					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							switch (count) {
							case 0:
								vtMa = cell.getStringCellValue();
								break;
							case 1:
								vtTen = cell.getStringCellValue();
								break;
							case 2:
								dvt = cell.getStringCellValue();
								break;	
							case 3:
								nsxMa = cell.getStringCellValue();
								break;
							case 5:
								clMa = cell.getStringCellValue();
								break;
							}
						} 
						count++;
					}
					if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxMa.length() == 0 && clMa.length() == 0 )
						break;
//					System.out.println("VTMa = "+vtMa+ "\n vtTen = "+ vtTen + "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
					if (vtTen.length() == 0 || dvt.length() == 0 || nsxMa.length() == 0 || clMa.length() == 0 )
						{
					//	System.out.println("VTMa = "+vtMa+ "\n vtTen = "+ vtTen + "\n dvt = "+dvt+ "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
							//if (vtMa.length() == 0) vtMa = "";
							if (vtTen.length() == 0) vtTen = "";
							
							if (nsxMa.length() == 0) nsxMa = "";
							if (clMa.length() == 0) clMa = "";
							if (dvt.length() == 0)
								{
									VatTu vatTuError = new VatTu(vtMa, vtTen);
									CTVatTu ctvtError = new CTVatTu(vatTuError, new NoiSanXuat(nsxMa), new ChatLuong(clMa),(int) dinhMuc, (int) soLuong, 0);
									ctvtListError.add(ctvtError);
								}
							else if (dvt.length() != 0)
							{
								DonViTinh donViTinhError = new DonViTinh(dvt, 0);
								VatTu vatTuError = new VatTu(vtMa, vtTen,donViTinhError,0);
								CTVatTu ctvtError = new CTVatTu(vatTuError, new NoiSanXuat(nsxMa), new ChatLuong(clMa),(int) dinhMuc, (int) soLuong, 0);
								ctvtListError.add(ctvtError);
							}
								
							
									System.out.println(ctvtListError.size());
						}
					else
						{
							DonViTinh donViTinh = new DonViTinh(dvt, 0);
							dvtList.add(donViTinh);
						VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
						CTVatTu ctvt = new CTVatTu(vatTu, new NoiSanXuat(nsxMa), new ChatLuong(clMa),
								(int) dinhMuc, (int) soLuong, 0);
						vatTuList.add(vatTu);
						nsxList.add(nsxMa);
						chatLuongList.add(clMa);
						ctvtList.add(ctvt);
						}
				}
				int lenght = vatTuList.size();
		//		ArrayList<CTVatTu> ctvtListError1 = new ArrayList<CTVatTu>();//danh sach ctvt da ton tai
				for (int i = 0; i< lenght; i++) {
					
					VatTuDAO vtDAO = new VatTuDAO();
					CTVatTuDAO ctvtDAO = new CTVatTuDAO();
					DonViTinhDAO dvtDAO = new DonViTinhDAO();
					NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
					ChatLuongDAO clDAO = new ChatLuongDAO();
					VatTu vatTu = vatTuList.get(i);
					String nsxMa = nsxList.get(i);
					String clMa = chatLuongList.get(i);
					DonViTinh dvt = dvtList.get(i);
					//System.out.println("count = " + i + " \n  VTMa = "+vatTu.getVtMa()+ "\n vtTen = "+ vatTu.getVtTen() + "\n dvt = " + dvt.getDvtTen() + "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
					
					NoiSanXuat noisx = nsxDAO.getNoiSanXuat(nsxMa);
					ChatLuong chatluong = clDAO.getChatLuong(clMa);
					if ((noisx != null) && (chatluong != null))
					{
						DonViTinh temp = dvtDAO.getDonViTinhByTen(dvt.getDvtTen());
						if (temp ==  null) {
							dvtDAO.addDonViTinh(dvt);
							dvt.setDvtId(new DonViTinhDAO().lastInsertId());
						}
						else {
							dvt.setDvtId(temp.getDvtId());
						}
						VatTu vt = vtDAO.getVatTu(vatTu.getVtMa());
						VatTu vtTemp = vatTuList.get(i);
						vtTemp.setDvt(dvt);
						if (vt == null)
						{
							//System.out.println("VTMa = "+vtTemp.getVtMa()+ "\n vtTen = "+ vtTemp.getVtTen());
							vtDAO.addVatTu(vtTemp);
							
						}
						CTVatTu ctvt = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsxMa, clMa);
						CTVatTu ctvtTemp = ctvtList.get(i);
						if (ctvt == null) {
							//System.out.println("CTVT them duoc!");
							//System.out.println("VTMa = "+ctvtTemp.getVatTu().getVtMa()+ "\n vtTen = "+ ctvtTemp.getVatTu().getVtTen() + "\n dvt = "+dvt.getDvtTen()+ "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
							ctvtDAO.addCTVatTu(ctvtTemp);
						}
						else {
							//System.out.println("CTVT bi loi!");
							//System.out.println("VTMa = "+ctvtTemp.getVatTu().getVtMa()+ "\n vtTen = "+ ctvtTemp.getVatTu().getVtTen() + "\n dvt = "+dvt.getDvtTen()+ "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
							
							ctvtListError.add(ctvtTemp);
						}
					}
					else 
					{
						CTVatTu ctvt = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsxMa, clMa);
						CTVatTu ctvtTemp = ctvtList.get(i);
						ctvtListError.add(ctvtTemp);
					}
					
				vtDAO.close();
				nsxDAO.close();
				clDAO.close();
				ctvtDAO.close();
				dvtDAO.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(ctvtListError.size());
			return ctvtListError;
		}
}
