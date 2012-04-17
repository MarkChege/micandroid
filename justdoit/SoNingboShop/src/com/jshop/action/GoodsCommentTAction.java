package com.jshop.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;
import com.jshop.action.tools.BaseTools;
import com.jshop.action.tools.Serial;
import com.jshop.action.tools.Validate;
import com.jshop.entity.GoodsCommentT;
import com.jshop.entity.GoodsT;
import com.jshop.service.impl.GoodsCommentTServiceImpl;
import com.jshop.service.impl.GoodsTServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("jshop")
@Controller("goodsCommentTAction")
public class GoodsCommentTAction extends ActionSupport {
	@Resource(name = "goodsCommentTServiceImpl")
	private GoodsCommentTServiceImpl goodsCommentTServiceImpl;
	@Resource(name = "goodsTServiceImpl")
	private GoodsTServiceImpl goodsTServiceImpl;
	@Resource(name = "serial")
	private Serial serial;
	private String commentid;
	private String goodsid;
	private String goodsname;
	private String replyorcommentusername;
	private Date posttime;
	private String commentcontent;
	private Integer score;
	private String state;
	private String replyorcomment;
	private String replyid;
	private String replyorcommentuserid;
	private String emailable;
	private String userid;
	private GoodsT g = new GoodsT();
	private String query;//text
	private String qtype;//select
	private GoodsCommentT beanlist = new GoodsCommentT();
	private List rows = new ArrayList();
	private int rp;
	private int page = 1;
	private int total = 0;
	private boolean slogin;
	private boolean sucflag;

	@JSON(serialize = false)
	public GoodsCommentTServiceImpl getGoodsCommentTServiceImpl() {
		return goodsCommentTServiceImpl;
	}

	public void setGoodsCommentTServiceImpl(GoodsCommentTServiceImpl goodsCommentTServiceImpl) {
		this.goodsCommentTServiceImpl = goodsCommentTServiceImpl;
	}

	@JSON(serialize = false)
	public GoodsTServiceImpl getGoodsTServiceImpl() {
		return goodsTServiceImpl;
	}

	public void setGoodsTServiceImpl(GoodsTServiceImpl goodsTServiceImpl) {
		this.goodsTServiceImpl = goodsTServiceImpl;
	}

	@JSON(serialize = false)
	public Serial getSerial() {
		return serial;
	}

	public void setSerial(Serial serial) {
		this.serial = serial;
	}

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getReplyorcommentusername() {
		return replyorcommentusername;
	}

	public void setReplyorcommentusername(String replyorcommentusername) {
		this.replyorcommentusername = replyorcommentusername;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReplyorcomment() {
		return replyorcomment;
	}

	public void setReplyorcomment(String replyorcomment) {
		this.replyorcomment = replyorcomment;
	}

	public String getReplyid() {
		return replyid;
	}

	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}

	public String getReplyorcommentuserid() {
		return replyorcommentuserid;
	}

	public void setReplyorcommentuserid(String replyorcommentuserid) {
		this.replyorcommentuserid = replyorcommentuserid;
	}

	public String getEmailable() {
		return emailable;
	}

	public void setEmailable(String emailable) {
		this.emailable = emailable;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public GoodsCommentT getBeanlist() {
		return beanlist;
	}

	public void setBeanlist(GoodsCommentT beanlist) {
		this.beanlist = beanlist;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isSlogin() {
		return slogin;
	}

	public void setSlogin(boolean slogin) {
		this.slogin = slogin;
	}

	public GoodsT getG() {
		return g;
	}

	public void setG(GoodsT g) {
		this.g = g;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isSucflag() {
		return sucflag;
	}

	public void setSucflag(boolean sucflag) {
		this.sucflag = sucflag;
	}

	/**
	 * 去除查询所有商品类别的错误
	 */
	@Override
	public void validate() {
		this.clearErrorsAndMessages();

	}

	/**
	 * 增加虚拟评论
	 * 
	 * @return
	 */
	@Action(value = "addvirtualGoodsComment", results = { @Result(name = "json", type = "json") })
	public String addvirtualGoodsComment() {
		if (Validate.StrNotNull(this.getGoodsid())) {
			g = this.getGoodsTServiceImpl().findGoodsById(this.getGoodsid());
			if (g != null) {
				GoodsCommentT gct = new GoodsCommentT();
				gct.setCommentid(this.getSerial().Serialid(Serial.GOODSCOMMENT));
				gct.setGoodsid(this.getGoodsid().trim());
				gct.setGoodsname(g.getGoodsname());
				gct.setReplyorcommentusername(this.getReplyorcommentusername());
				gct.setReplyorcommentuserid(this.getUserid());
				gct.setPosttime(BaseTools.systemtime());
				gct.setCommentcontent(this.getCommentcontent());
				gct.setScore(0);
				gct.setState("1");
				gct.setReplyorcomment("1");
				gct.setReplyid("0");
				gct.setEmailable("0");
				gct.setVirtualadd("1");
				if (this.getGoodsCommentTServiceImpl().addGoodsComment(gct) > 0) {
					this.setSucflag(true);
					return "json";
				} else {
					this.setSucflag(false);
					return "json";
				}
			}
		}
		this.setSucflag(false);
		return "json";
	}

	/**
	 * 处理商品评论迭代
	 * 
	 * @param gct
	 */
	@SuppressWarnings("unchecked")
	public void ProcessGoodsCommentList(List<GoodsCommentT> gct) {
		total = this.getGoodsCommentTServiceImpl().countfindAllGoodsComment();
		for (Iterator it = gct.iterator(); it.hasNext();) {
			GoodsCommentT gctt = (GoodsCommentT) it.next();
			if (gctt.getState().equals("1")) {
				gctt.setState("显示");
			} else {
				gctt.setState("禁止");
			}
			if (gctt.getReplyorcomment().equals("1")) {
				gctt.setReplyorcomment("商品评论");
			} else {
				gctt.setReplyorcomment("回复评论");
			}
			if (gctt.getEmailable().equals("1")) {
				gctt.setEmailable("邮件提醒");
			} else if (gctt.getEmailable().equals("2")) {
				gctt.setEmailable("站内信提醒");
			} else {
				gctt.setEmailable("缺省行为");
			}
			if (gctt.getVirtualadd().equals("1")) {
				gctt.setVirtualadd("虚拟");
			} else {
				gctt.setVirtualadd("正常");
			}
			Map cellMap = new HashMap();
			cellMap.put("id", gctt.getCommentid());
			cellMap.put("cell", new Object[] { gctt.getGoodsname(), gctt.getCommentcontent(), gctt.getReplyorcommentusername(), BaseTools.formateDbDate(gctt.getPosttime()), gctt.getState(), gctt.getReplyorcomment(), gctt.getEmailable(), gctt.getVirtualadd() });
			rows.add(cellMap);
		}
	}

	/**
	 * 查询所有商品评论数据没有搜索条件
	 */
	public void finddefaultAllGoodsComment() {
		int currentPage = page;
		int lineSize = rp;
		List<GoodsCommentT> gct = this.getGoodsCommentTServiceImpl().findAllGoodsComment(currentPage, lineSize);
		if (gct != null) {
			ProcessGoodsCommentList(gct);
		}
	}

	/**
	 * 查询所有商品评论数据
	 * 
	 * @return
	 */
	@Action(value = "findAllGoodsComment", results = { @Result(name = "json", type = "json") })
	public String findAllGoodsComment() {
		if ("sc".equals(this.getQtype())) {
			//获取默认的所有商品评论
			this.setTotal(0);
			rows.clear();
			finddefaultAllGoodsComment();
		} else {
			return "json";
		}
		return "json";
	}

	/**
	 * 删除商品评论
	 * 
	 * @return
	 */
	@Action(value = "DelGoodsComment", results = { @Result(name = "json", type = "json") })
	public String DelGoodsComment() {
		if (Validate.StrNotNull(this.getCommentid())) {
			String[] strs = this.getCommentid().trim().split(",");
			if (this.getGoodsCommentTServiceImpl().delGoodsComment(strs) > 0) {
				return "json";
			}
			return "json";
		}
		return "json";
	}
}
