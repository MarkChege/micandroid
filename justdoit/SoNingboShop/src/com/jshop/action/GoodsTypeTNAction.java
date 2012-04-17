package com.jshop.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;
import com.jshop.action.tools.BaseTools;
import com.jshop.action.tools.Serial;
import com.jshop.action.tools.Validate;
import com.jshop.entity.GoodsTypeTN;
import com.jshop.service.impl.GoodsTypeTNServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;

@ParentPackage("jshop")
@InterceptorRefs({  
    @InterceptorRef("goodsmoduleGoodsTypeBrandInterecptor"),  
    @InterceptorRef("defaultStack")  
})

@Controller("goodsTypeTNAction")
public class GoodsTypeTNAction extends ActionSupport {
	@Resource(name = "serial")
	private Serial serial;
	@Resource(name = "goodsTypeTNServiceImpl")
	private GoodsTypeTNServiceImpl goodsTypeTNServiceImpl;
	private String goodsTypeId;
	private String name;
	private Date createtime;
	private String creatorid;
	private String goodsParameter;
	private GoodsTypeTN bean=new GoodsTypeTN();
	private List<GoodsTypeTN> beanlist=new ArrayList<GoodsTypeTN>();;
	private String goodstypetnlist;
	private String query;
	private String qtype;
	private List rows=new ArrayList();
	private int rp;
	private int page = 1;
	private int total = 0;
	private boolean slogin;
	private boolean sucflag;
	private String sortname;
	private String sortorder;

	

	@JSON(serialize = false)
	public Serial getSerial() {
		return serial;
	}

	public void setSerial(Serial serial) {
		this.serial = serial;
	}

	@JSON(serialize = false)
	public GoodsTypeTNServiceImpl getGoodsTypeTNServiceImpl() {
		return goodsTypeTNServiceImpl;
	}

	public void setGoodsTypeTNServiceImpl(GoodsTypeTNServiceImpl goodsTypeTNServiceImpl) {
		this.goodsTypeTNServiceImpl = goodsTypeTNServiceImpl;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	public String getGoodsParameter() {
		return goodsParameter;
	}

	public void setGoodsParameter(String goodsParameter) {
		this.goodsParameter = goodsParameter;
	}

	public GoodsTypeTN getBean() {
		return bean;
	}

	public void setBean(GoodsTypeTN bean) {
		this.bean = bean;
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

	public boolean isSucflag() {
		return sucflag;
	}

	public void setSucflag(boolean sucflag) {
		this.sucflag = sucflag;
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

	public String getGoodstypetnlist() {
		return goodstypetnlist;
	}

	public void setGoodstypetnlist(String goodstypetnlist) {
		this.goodstypetnlist = goodstypetnlist;
	}

	public List<GoodsTypeTN> getBeanlist() {
		return beanlist;
	}

	public void setBeanlist(List<GoodsTypeTN> beanlist) {
		this.beanlist = beanlist;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	/**
	 *清理错误
	 */
	@Override
	public void validate() {
		this.clearErrorsAndMessages();

	}

	/**
	 * 增加商品类型
	 * 
	 * @return
	 */
	@Action(value = "addGoodsTypeTN", results = { @Result(name = "json", type = "json") })
	public String addGoodsTypeTN() {
		GoodsTypeTN gtn = new GoodsTypeTN();
		gtn.setGoodsTypeId(this.getSerial().Serialid(Serial.GOODSTYPE));
		gtn.setName(this.getName());
		gtn.setCreatetime(BaseTools.systemtime());
		gtn.setCreatorid(BaseTools.adminCreateId());
		gtn.setGoodsParameter(this.getGoodsParameter());
		if (this.getGoodsTypeTNServiceImpl().addGoodsTypeTN(gtn) > 0) {
			this.setSucflag(true);
			return "json";
		}
		this.setSucflag(false);
		return "json";
	}

	/**
	 * 异步增加商品类型
	 * 
	 * @return
	 */
	@Action(value = "addGoodsTypeTNajax", results = { @Result(name = "json", type = "json") })
	public String addGoodsTypeTNajax() {
		GoodsTypeTN gtn = new GoodsTypeTN();
		gtn.setGoodsTypeId(this.getSerial().Serialid(Serial.GOODSTYPE));
		gtn.setName(this.getName());
		gtn.setCreatetime(BaseTools.systemtime());
		gtn.setCreatorid(BaseTools.adminCreateId());
		gtn.setGoodsParameter(this.getGoodsParameter());
		if (this.getGoodsTypeTNServiceImpl().addGoodsTypeTN(gtn) > 0) {
			return "json";
		}
		return "json";
	}

	/**
	 * 获取所有商品类型
	 * 
	 * @return
	 */
	@Action(value = "findAllGoodsTypeTN", results = { @Result(name = "json", type = "json") })
	public String findAllGoodsTypeTN() {
		if (this.getQtype().equals("sc")) {
			findDefaultAllGoodsTypeTN();
		} else {
			if (Validate.StrisNull(this.getQuery())) {
				return "json";
			} else {
				return "json";
			}
		}
		return "json";
	}

	public void ProcessGoodsTypeTNList(List<GoodsTypeTN> list) {
		for (Iterator it = list.iterator(); it.hasNext();) {
			GoodsTypeTN gtn = (GoodsTypeTN) it.next();
			Map<String, Object> cellMap = new HashMap<String, Object>();
			cellMap.put("id", gtn.getGoodsTypeId());
			cellMap.put("cell", new Object[] {gtn.getName(), BaseTools.formateDbDate(gtn.getCreatetime()) });
			rows.add(cellMap);
		}
	}

	public void findDefaultAllGoodsTypeTN() {
		int currentPage = page;
		int lineSize = rp;
		total = this.getGoodsTypeTNServiceImpl().countfindAllGoodsTypeTN();
		if (Validate.StrNotNull(sortname) && Validate.StrNotNull(sortorder)) {
			String queryString = "from GoodsTypeTN  order by " + sortname + " " + sortorder + "";
			List<GoodsTypeTN> list = this.getGoodsTypeTNServiceImpl().sortAllGoodsTypeTN(currentPage, lineSize, queryString);
			this.ProcessGoodsTypeTNList(list);
		}
	}

	/**
	 * 根据id获取商品类型
	 * 
	 * @return
	 */
	@Action(value = "findGoodsTypeTNById", results = { @Result(name = "json", type = "json") })
	public String findGoodsTypeTNById() {
		if (Validate.StrNotNull(this.getGoodsTypeId())) {
			List<GoodsTypeTN> list=this.getGoodsTypeTNServiceImpl().findGoodsTypeTNById(this.getGoodsTypeId().trim());
			if(!list.isEmpty()){
				bean=list.get(0);
			}
		}
		return "json";
	}

	/**
	 * 更新商品类型
	 * 
	 * @return
	 */
	@Action(value = "UpdateGoodsTypeTN", results = { @Result(name = "json", type = "json") })
	public String UpdateGoodsTypeTN() {
		GoodsTypeTN gtn = new GoodsTypeTN();
		gtn.setGoodsTypeId(this.getGoodsTypeId().trim());
		gtn.setName(this.getName());
		gtn.setCreatetime(BaseTools.systemtime());
		gtn.setCreatorid(BaseTools.adminCreateId());
		gtn.setGoodsParameter(this.getGoodsParameter());
		@SuppressWarnings("unused")
		int i = this.getGoodsTypeTNServiceImpl().updateGoodsTypeTN(gtn);
		this.setSucflag(true);
		return "json";
	}

	/**
	 * 删除商品类型
	 * 
	 * @return
	 */
	@Action(value = "DelGoodsTypeTN", results = { @Result(name = "json", type = "json") })
	public String DelGoodsTypeTN() {
		if (Validate.StrNotNull(this.getGoodsTypeId())) {
			String[] list = this.getGoodsTypeId().trim().split(",");
			int i = this.getGoodsTypeTNServiceImpl().delGoodsTypeTN(list);
			return "json";
		}
		return "json";
	}

	@Action(value = "findGoodsTypeTNForSelect", results = { @Result(name = "json", type = "json") })
	public String findGoodsTypeTNForSelect() {
		this.setGoodstypetnlist("");
		List<GoodsTypeTN> list = this.getGoodsTypeTNServiceImpl().findAllGoodsTypeTNNopage();
		if (list != null) {
			this.setGoodstypetnlist("<option value='0'>--请选择--</option>");
			for (Iterator it = list.iterator(); it.hasNext();) {
				GoodsTypeTN gtn = (GoodsTypeTN) it.next();
				this.goodstypetnlist += "<option value='" + gtn.getGoodsTypeId() + "'>" + gtn.getName() + "</option>";
			}
			this.setGoodstypetnlist(goodstypetnlist);
			return "json";
		}
		this.setGoodstypetnlist("");
		return "json";

	}

	/**
	 * 获取商品参数
	 * 
	 * @return
	 */
	@Action(value = "findGoodsParameter", results = { @Result(name = "json", type = "json") })
	public String findGoodsParameter() {
		if(Validate.StrNotNull(this.getGoodsTypeId())){
			List<GoodsTypeTN>list=this.getGoodsTypeTNServiceImpl().findGoodsTypeTNById(this.getGoodsTypeId());
			if(!list.isEmpty()){
				bean=list.get(0);
				this.setSucflag(true);
				return "json";
			}
		}
		this.setSucflag(false);
		return "json";

	}
}
