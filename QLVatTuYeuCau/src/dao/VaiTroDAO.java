package dao;


import java.util.ArrayList;
import java.util.List;

import model.CTVatTu;
import model.ChucDanh;
import model.DonViTinh;
import model.VTCongVan;
import model.VaiTro;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class VaiTroDAO {
	
	private SessionFactory template;  
	private Session session;
	public VaiTroDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	// trong day phai la kieu int
	public VaiTro getVaiTro(final int vtId) {
		session.beginTransaction();
		
		VaiTro vaiTro = (VaiTro) session.get(VaiTro.class, vtId);
//		session.
		
		session.getTransaction().commit();
		return vaiTro;
	}
	public VaiTro getVaiTroByTen(final String vtTen) {
		session.beginTransaction();
		String sql = "from VaiTro where LOWER(vtTen) = :vtTen";
		Query query = session.createQuery(sql);
		query.setParameter("vtTen", vtTen.toLowerCase());
		ArrayList<VaiTro> list = (ArrayList<VaiTro>) query.list();
		VaiTro vt = null;
		if(list.size() != 0)
			vt = list.get(0);
		session.getTransaction().commit();
		return vt;
	}
	public List<VaiTro> getAllVaiTro() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		cr.add(xoaCd);
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) cr.list(); 
		session.getTransaction().commit();
		return vaiTroList;
	}
	
	public List<VaiTro> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) cr.list(); 
		session.getTransaction().commit();
		return vaiTroList;
	}
	public long size() {
		session.beginTransaction();
		String sql = "select count(vtId) from VaiTro where daXoa = 0";
		Query query =  session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
		
	}
	
	public void addVaiTro(VaiTro vaiTro){
		session.beginTransaction();
		session.save(vaiTro);
		session.getTransaction().commit();
	}
	public void updateVaiTro(VaiTro vaiTro){
		session.beginTransaction();
		session.update(vaiTro);
		session.getTransaction().commit();
	}
	public void addOrUpdateVaiTro(VaiTro vt){
		session.beginTransaction();
		session.saveOrUpdate(vt);
		session.getTransaction().commit();
	}
	public void deleteVaiTro(int vtId){
		session.beginTransaction();
		String sql = "update VaiTro set daXoa = 1 where vtId = " + vtId;		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public void deleteVaiTroTen(String vtTen){
		session.beginTransaction();
		String sql = "update VaiTro set daXoa = 1 where vtTen = '" + vtTen+ "'";		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public int getVaiTroDAO(final int vtId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		Criterion expVtId = Restrictions.eq("vtId", vtId);
		cr.add(expVtId);
		int l =  cr.list().size();
		session.getTransaction().commit();
		return l;
	}
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}
	public ArrayList<VaiTro> getVaiTro(ArrayList<VTCongVan> vtcvList) {
		ArrayList<VaiTro> vaiTroList = new ArrayList<VaiTro>();
		for  (VTCongVan vtCongVan : vtcvList) {
			VaiTro vaiTro = getVaiTro(vtCongVan.getVtId());
			vaiTroList.add(vaiTro);
		}
		return vaiTroList;
	}
}
