package dao;

import java.util.ArrayList;
import java.util.List;

import model.ChucDanh;
import model.NoiSanXuat;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class NoiSanXuatDAO {
	
	private SessionFactory template;  
	private Session session;
	public NoiSanXuatDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public NoiSanXuatDAO(Session session) {
		this.session = session;
	}
	public NoiSanXuat getNoiSanXuat(final String nsxMa) {
		session.beginTransaction();
		
		NoiSanXuat noiSanXuat = (NoiSanXuat) session.get(NoiSanXuat.class, nsxMa);
//		session.
		
		session.getTransaction().commit();
		return noiSanXuat;
	}
	public List<NoiSanXuat> getAllNoiSanXuat() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NoiSanXuat.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		cr.add(xoaCd);
		ArrayList<NoiSanXuat> noiSanXuatList = (ArrayList<NoiSanXuat>) cr.list(); 
		session.getTransaction().commit();
		return noiSanXuatList;
	}
	public List<NoiSanXuat> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NoiSanXuat.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
//		Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<NoiSanXuat> noiSanXuatList = (ArrayList<NoiSanXuat>) cr.list(); 
		session.getTransaction().commit();
		return noiSanXuatList;
	}
	
	public void addNoiSanXuat(NoiSanXuat noiSanXuat){
		session.beginTransaction();
		session.save(noiSanXuat);
		session.getTransaction().commit();
	}
	public void addOrUpdateNoiSanXuat(NoiSanXuat noiSanXuat){
		session.beginTransaction();
		session.saveOrUpdate(noiSanXuat);
		session.getTransaction().commit();
	}
	public void updateNoiSanXuat(NoiSanXuat noiSanXuat){
		session.beginTransaction();
		session.update(noiSanXuat);
		session.getTransaction().commit();
	}
	public void deleteNoiSanXuat(String nsxMa){
		session.beginTransaction();
		String sql = "update NoiSanXuat set daXoa = 1 where nsxMa = '" + nsxMa + "'";		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public long size() {
		session.beginTransaction();
		String sql = "select count(nsxMa) from NoiSanXuat where daXoa = 0";
		Query query =  session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
		
	}
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

	public static void main(String[] args) {
//		new NoiSanXuatDAO().deleteNoiSanXuat("Vn5");
//		System.out.println(new NoiSanXuatDAO().size());
		ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) new NoiSanXuatDAO().limit(0, 10);
		int count = 0;
		for (NoiSanXuat nsx : nsxList) {
			count++;
			System.out.println(count + nsx.getNsxTen()); }
	}
	
	
}
