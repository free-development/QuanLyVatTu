package dao;

import java.util.ArrayList;
import java.util.List;

import model.CTNguoiDung;
import model.NguoiDung;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class CTNguoiDungDAO {
	
	private SessionFactory template;  
	private Session session;
	
	public CTNguoiDungDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	
	public CTNguoiDung getCTNguoiDung(final String msnv) {
		session.beginTransaction();
		CTNguoiDung ctNguoiDung = (CTNguoiDung) session.get(CTNguoiDung.class, msnv);
		session.getTransaction().commit();
		return ctNguoiDung;
	}
	public int login(final String msnv, final String matkhau) {
		CTNguoiDung ctNguoiDung = getCTNguoiDung(msnv);
		if (ctNguoiDung == null)
			return -1;
		else if (!matkhau.equals(ctNguoiDung.getMatKhau()))
			return 0;
		return 1;
	}
	public List<CTNguoiDung> getAllCTNguoiDung() {
		session.beginTransaction();
		List<CTNguoiDung> ctNguoiDungList = (List<CTNguoiDung>) session.createCriteria(CTNguoiDung.class).list();
		session.getTransaction().commit();
		return ctNguoiDungList;
	}
	public void addCTNguoiDung(CTNguoiDung ctNguoiDung){
		session.beginTransaction();
		session.save(ctNguoiDung);
		session.getTransaction().commit();
	}
	public void updateCTNguoiDung(CTNguoiDung ctNguoiDung){
		session.beginTransaction();
		session.update(ctNguoiDung);
		session.getTransaction().commit();
	}
	public void deleteCTNguoiDung(CTNguoiDung ctNguoiDung){
		session.beginTransaction();
		session.delete(ctNguoiDung);
		session.getTransaction().commit();
	}
	public int lockNguoiDung(String msnv){
		session.beginTransaction();
		String sql = "update CTNguoiDung set khoa = 1 where msnv = '" + msnv +"'";		
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		session.getTransaction().commit();
		return count;
	}
	public List<CTNguoiDung> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTNguoiDung.class);
		Criterion khoa = Restrictions.eq("khoa", 0);
//		Criterion limitRow = Restrictions.
		cr.add(khoa);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CTNguoiDung> ctnguoiDungList = (ArrayList<CTNguoiDung>) cr.list(); 
		session.getTransaction().commit();
		return ctnguoiDungList;
	}
	
	public long size() {
		session.beginTransaction();
		String sql = "select count(msnv) from CTNguoiDung where khoa = 0";
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

	
}
