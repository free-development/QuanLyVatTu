/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.NguoiDung;
import model.VTCongVan;
import model.VaiTro;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;



/**
 * @author quoioln
 *
 */
public class VTCongVanDAO {
	private SessionFactory template;  
	private Session session;
	public VTCongVanDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
//	public VTCongVan getVTCongVan(final int vtId) {
//		session.beginTransaction();
//		VTCongVan VTCongVan = (VTCongVan) session.get(VTCongVan.class, vtId);
//		session.getTransaction().commit();
//		return VTCongVan;
//	}
	public List<VTCongVan> getAllVTCongVan() {
		session.beginTransaction();
		List<VTCongVan> vtCongVanList = (List<VTCongVan>) session.createCriteria(VTCongVan.class).list();
		session.getTransaction().commit();
		return vtCongVanList;
	}
	public void addVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.save(vtCongVan);
		session.getTransaction().commit();
	}
	public void addOrUpdateVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.saveOrUpdate(vtCongVan);
		session.getTransaction().commit();
	}
	public void updateVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.update(vtCongVan);
		session.getTransaction().commit();
	}
	public void deleteVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.delete(vtCongVan);
		session.getTransaction().commit();
	}
	public ArrayList<VTCongVan> getVTCongVan(int cvId, String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		Criterion expCv = Restrictions.eq("cvId", cvId);
		Criterion expNd = Restrictions.eq("msnv", msnv);
		cr.add(expCv);
		cr.add(expNd);
		ArrayList<VTCongVan> vtCongVanList = (ArrayList<VTCongVan>) cr.list();
		session.getTransaction().commit();
		return vtCongVanList;
	}
	public int deleteByCvId(int cvId) {
		session.beginTransaction();
		String sql = "delete from VTCongVan where cvId = :cvId";
		Query query = session.createQuery(sql);
		query.setParameter("cvId", cvId);
		int result = query.executeUpdate();
		session.getTransaction().commit();
		return result;
	}
	public int delete(int cvId, String msnv) {
		session.beginTransaction();
		String sql = "delete from VTCongVan where cvId = :cvId and msnv = :msnv";
		Query query = session.createQuery(sql);
		query.setParameter("cvId", cvId);
		query.setParameter("msnv", msnv);
		int result = query.executeUpdate();
		session.getTransaction().commit();
		return result;
	}
	public String getVaiTro(int vtId) {
		session.beginTransaction();
		String sql = "select vtTen from VaiTro where vtId = :vtId";
		Query query = session.createQuery(sql);
		query.setParameter("vtId", vtId);
		String vaiTro = (String) query.list().get(0);
		session.getTransaction().commit();
		return vaiTro;
	}
	public HashMap<String, NguoiDung> getNguoiXuLy(int cvId) {
		session.beginTransaction();
//		Criteria cr = session.createCriteria(VTCongVan.class);
		String sql = "SELECT distinct E.msnv FROM VTCongVan E where E.cvId = " + cvId;
		Query query = session.createQuery(sql);
		ArrayList<String> msnvList = (ArrayList<String>) query.list();
		
		HashMap<String, NguoiDung> nguoiDungHash = new HashMap<String, NguoiDung>();
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		if (msnvList.size() > 0) {
			for (String msnv : msnvList) {
				NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(msnv);
				nguoiDungHash.put(msnv ,nguoiDung);
			}
		}
		session.getTransaction().commit();
		return nguoiDungHash;
	}
//	public ArrayList<VaiTro> toVaiTro(ArrayList<VTCongVan> vtcvList) {
//		ArrayList<VaiTro> vaiTroList = new ArrayList<VaiTro>();
//		for (VTCongVan vtCongVan : vtcvList) {
//			
//			VaiTro vaiTro = new VaiTroDAO().getVaiTro(vtCongVan.getVtId());
//			int vtId = vaiTro.getVtId();
//			vaiTroList.add(vaiTro);
//		}
//		return vaiTroList;
//	}
	public HashMap<Integer, VaiTro> toVaiTro(ArrayList<VTCongVan> vtcvList) {
		HashMap<Integer, VaiTro>  vaiTroList = new HashMap<Integer, VaiTro> ();
		for (VTCongVan vtCongVan : vtcvList) {
			
			VaiTro vaiTro = new VaiTroDAO().getVaiTro(vtCongVan.getVtId());
			int vtId = vaiTro.getVtId();
			vaiTroList.put(vtId, vaiTro);
		}
		return vaiTroList;
	}
	public ArrayList<Integer> getVtIdByCvId(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		cr.add(Restrictions.eq("cvId", cvId));
		cr.setProjection(Projections.property("vtId"));
		ArrayList<Integer> vtIdList = (ArrayList<Integer>) cr.list();
		session.getTransaction().commit();
		return vtIdList;
	}
	public ArrayList<VaiTro> getVaiTroByCvId(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		
//		ArrayList<Integer> vtIdList = getVtIdByCvId(cvId);
		Criteria cr1 = session.createCriteria(VTCongVan.class);
		cr1.add(Restrictions.eq("cvId", cvId));
		cr1.setProjection(Projections.property("vtId"));
		ArrayList<Integer> vtIdList = (ArrayList<Integer>) cr1.list();
		if (vtIdList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<VaiTro>();
		}
		cr.add(Restrictions.in("vtId", vtIdList));
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) cr.list();
		
		session.getTransaction().commit();
		return vaiTroList;
	}
	public ArrayList<VaiTro> getVaiTro(final int cvId, String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		
//		ArrayList<Integer> vtIdList = getVtIdByCvId(cvId);
		Criteria cr1 = session.createCriteria(VTCongVan.class);
		cr1.add(Restrictions.eq("cvId", cvId));
		cr1.setProjection(Projections.property("vtId"));
		cr1.add(Restrictions.eq("msnv", msnv));
		ArrayList<Integer> vtIdList = (ArrayList<Integer>) cr1.list();
		
		if (vtIdList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<VaiTro>();
		}
		cr.add(Restrictions.in("vtId", vtIdList));
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) cr.list();
		
		session.getTransaction().commit();
		return vaiTroList;
	}
	public ArrayList<String> getNguoiXl(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class);
		
//		ArrayList<Integer> vtIdList = getVtIdByCvId(cvId);
		Criteria cr1 = session.createCriteria(VTCongVan.class);
		cr1.add(Restrictions.eq("cvId", cvId));
		cr1.setProjection(Projections.property("msnv"));
		ArrayList<String> msnvList = (ArrayList<String>) cr1.list();
		
		if (msnvList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<String>();
		}
		cr.add(Restrictions.in("msnv", msnvList));
		cr.setProjection(Projections.property("hoTen"));
		ArrayList<String> hoTenList = (ArrayList<String>) cr.list();
		
		session.getTransaction().commit();
		return hoTenList;
	}
	public ArrayList<VTCongVan> getByMsnv(String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		cr.add(Restrictions.eq("msnv", msnv));
		ArrayList<VTCongVan> vtCongVanList = (ArrayList<VTCongVan>) cr.list();
		session.getTransaction().commit();
		return vtCongVanList;
	}
	public void close() {
		session.close();
	}
	public void disconnect() {
		session.disconnect();
	}
	public static void main(String[] args) {
		VTCongVanDAO l = new VTCongVanDAO();
		System.out.println(l.getVaiTroByCvId(44));
	}
}
