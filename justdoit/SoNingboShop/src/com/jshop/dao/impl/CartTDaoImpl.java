package com.jshop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.jshop.dao.CartTDao;
import com.jshop.entity.CartT;

/**
 * A data access object (DAO) providing persistence and search support for CartT
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.jshop.entity.CartT
 * @author MyEclipse Persistence Tools
 */
@Repository("cartTDaoImpl")
public class CartTDaoImpl extends HibernateDaoSupport implements CartTDao {
	
	private static final Log log = LogFactory.getLog(CartTDaoImpl.class);
	
	public int addCart(CartT c) {
		log.debug("save CartT");
		try {
			this.getHibernateTemplate().save(c);
			log.debug("save successful");
			return 1;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public int countfindAllCart() {
		log.debug("count all brand");
		try {
			String queryString = "select count(*) from CartT";
			List list = this.getHibernateTemplate().find(queryString);
			if (list.size() > 0) {
				Object o = list.get(0);
				long l = (Long) o;
				return (int) l;
			}
			return 0;
		} catch (RuntimeException re) {
			log.error("count all CartT error", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CartT> findAllCart(final int currentPage, final int lineSize) {
		log.debug("find all CartT");
		try {
			List<CartT> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {

				String queryString = "from CartT  order by addtime desc";

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString);
					query.setFirstResult((currentPage - 1) * lineSize);
					query.setMaxResults(lineSize);
					List list = query.list();
					return list;
				}
			});
			if (list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			log.error("find all CartT error", re);
			throw re;
		}
	}

	public List<CartT> findAllCartByUserId(String userid) {
		log.debug("find all CartT ");
		try {
			String queryString = "from CartT as c where c.userid=:userid and c.state='1' order by addtime desc";
			List<CartT> list = this.getHibernateTemplate().findByNamedParam(queryString, "userid", userid);
			if (list != null) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			log.error("find all CartT  error", re);
			throw re;
		}
	}

	public CartT findGoodsInCartOrNot(String userid, String goodsid, String state) {
		log.debug("find good or in CartT ");
		try {
			String queryString = "from CartT as c where c.userid=:userid and c.goodsid=:goodsid and c.state=:state order by addtime desc";
			List<CartT> list = this.getHibernateTemplate().findByNamedParam(queryString, new String[] { "userid", "goodsid", "state" }, new Object[] { userid, goodsid, state });
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("find good or in CartT error", re);
			throw re;
		}
	}

	public int updateCartNeedquantityByGoodsid(final String userid, final String goodsid, final int needquantity, final String state) {
		log.debug("update cart needquantity");
		try {
			final String queryString = "update CartT as c set c.needquantity=:needquantity+c.needquantity where c.userid=:userid and c.goodsid=:goodsid and c.state=:state";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					int i = 0;
					Query query = session.createQuery(queryString);
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					query.setParameter("needquantity", needquantity);
					query.setParameter("state", state);
					i = query.executeUpdate();
					++i;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("update  cart needquantity", re);
			throw re;
		}
		return 0;
	}

	public int reduceCartNeddquantityByGoodsid(final String userid, final String goodsid, final int needquantity) {
		log.debug("update cart needquantity");
		try {
			final String queryString = "update CartT as c set c.needquantity=c.needquantity-:needquantity where c.userid=:userid and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					int i = 0;
					Query query = session.createQuery(queryString);
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					query.setParameter("needquantity", needquantity);
					i = query.executeUpdate();
					++i;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("update  cart needquantity", re);
			throw re;
		}
		return 0;
	}

	public int delCartByGoodsId(final String userid, final String goodsid) {
		log.debug("del cart goods");
		try {

			final String queryString = "delete from CartT as c where c.userid=:userid and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString);
					int i = 0;
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					i = query.executeUpdate();

					if (i > 0) {
						++i;
						return i;
					} else {
						return 0;
					}
				}
			});
		} catch (RuntimeException re) {
			log.error("del brandt failed", re);
			throw re;
		}
		return 0;
	}

	public int updateCartNeedquantity(final String userid, final String goodsid, final int needquantity) {
		log.debug("update cart needquantity");
		try {
			final String queryString = "update CartT as c set c.needquantity=:needquantity where c.userid=:userid and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					int i = 0;
					Query query = session.createQuery(queryString);
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					query.setParameter("needquantity", needquantity);
					i = query.executeUpdate();
					++i;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("update  cart needquantity", re);
			throw re;
		}
		return 0;
	}

	public int updateCartSubtotal(final String userid, final String goodsid, final double subtotal) {
		log.debug("update cart subtotal");
		try {
			final String queryString = "update CartT as c set c.subtotal=:subtotal where c.userid=:userid and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					int i = 0;
					Query query = session.createQuery(queryString);
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					query.setParameter("subtotal", subtotal);
					i = query.executeUpdate();
					++i;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("update  cart subtotal", re);
			throw re;
		}
		return 0;
	}

	public int updateCartState(final String userid, final String goodsid, final String state) {
		log.debug("update cart subtotal");
		try {
			final String queryString = "update CartT as c set c.state=:state where c.userid=:userid and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					int i = 0;
					Query query = session.createQuery(queryString);
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					query.setParameter("state", state);
					i = query.executeUpdate();
					++i;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("update  cart subtotal", re);
			throw re;
		}
		return 0;
	}

	public int updateCartStateandOrderidByGoodsidList(final String cartid, final String orderid, final String userid, final String state) {
		log.debug("UpdateCartStateandOrderidByGoodsidList cart");
		try {

			final String queryString = "update CartT as c set c.state=:state,c.orderid=:orderid where c.userid=:userid  and c.cartid=:cartid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString);
					int i = 0;
					query.setParameter("orderid", orderid);
					query.setParameter("userid", userid);
					query.setParameter("cartid", cartid);
					query.setParameter("state", state);
					i = query.executeUpdate();
					i++;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("UpdateCartStateandOrderidByGoodsidList cart failed", re);
			throw re;
		}
		return 0;
	}

	public int updateCartStateByGoodsidList(final String userid, final String[] goodsid, final String state) {
		log.debug("del cart");
		try {

			final String queryString = "update CartT as c set c.state=:state where c.userid=:userid and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString);
					int i = 0;
					for (String s : goodsid) {
						query.setParameter("userid", userid);
						query.setParameter("goodsid", s);
						query.setParameter("state", state);
						i = query.executeUpdate();
						i++;
					}
					if (goodsid.length == i) {
						return i;
					} else {
						return 0;
					}
				}
			});
		} catch (RuntimeException re) {
			log.error("del cart failed", re);
			throw re;
		}
		return 0;
	}

	public List<CartT> findCartGoodsByOrderid(String orderid) {
		log.debug("find all findCartGoodsByOrderid ");
		try {
			String queryString = "from CartT as c where c.orderid=:orderid and c.state='3' order by addtime desc";
			List<CartT> list = this.getHibernateTemplate().findByNamedParam(queryString, "orderid", orderid);
			if (list != null && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			log.error("find all findCartGoodsByOrderid  error", re);
			throw re;
		}
	}

	public int updateCartId(final String cartid, final String userid, final String goodsid, final String state) {
		log.debug("update cart UpdateCartId");
		try {
			final String queryString = "update CartT as c set c.cartid=:cartid where c.userid=:userid and  c.state=:state and c.goodsid=:goodsid";
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					int i = 0;
					Query query = session.createQuery(queryString);
					query.setParameter("userid", userid);
					query.setParameter("goodsid", goodsid);
					query.setParameter("cartid", cartid);
					query.setParameter("state", state);
					i = query.executeUpdate();
					++i;
					return i;
				}
			});
		} catch (RuntimeException re) {
			log.error("update  cart UpdateCartId", re);
			throw re;
		}
		return 0;
	}
}