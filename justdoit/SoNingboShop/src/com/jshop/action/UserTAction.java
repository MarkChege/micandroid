package com.jshop.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.jshop.action.tools.BaseTools;
import com.jshop.action.tools.MD5Code;
import com.jshop.action.tools.Serial;
import com.jshop.action.tools.Validate;
import com.jshop.entity.UserT;
import com.jshop.service.impl.UsertServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import freemarker.template.TemplateException;
@ParentPackage("jshop")

//@ExceptionMappings( {  
//     @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
     
@Controller("userTAction")
public class UserTAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware, CookiesAware {
	@Resource(name="usertServiceImpl")
	private UsertServiceImpl usertServiceImpl;
	@Resource(name="sendSystemEmail")
	private SendSystemEmail sendSystemEmail;
	@Resource(name="serial")
	private Serial serial;
	@Resource(name="initTAction")
	private InitTAction initTAction;
	private UserT beanlist = new UserT();
	private String param;
	private List<UserT> user = new ArrayList<UserT>();
	private List rows = new ArrayList();
	private int rp;
	private int page = 1;
	private int total = 0;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map session;
	private Map cookies;
	private String goingToURL;
	private String remember;
	private String userid;
	private String username;
	private String realname;
	private String email;
	private String telno;
	private String mobile;
	private String question;
	private String answer;
	private String password;
	private String newpassword;
	private String userstate;
	private String points;
	private String postingcount;
	private String sex;
	private Date registtime;
	private Date disablebegin;
	private Date disableend;
	private String section;
	private String position;
	private String groupid;
	private String parttime1;
	private String parttime2;
	private String parttime3;
	private String parttime4;
	private String parttime5;
	private String parttime6;
	private String hobby;
	private String qq;
	private String msn;
	private String othercontract;
	private String address;
	private String postcode;
	private String birthday;
	private String grade;
	private String gradetime;
	private String state;
	private String creatorid;
	private String msg;
	private boolean slogin = false;
	private boolean sucflag;
	private String usession;
	private String sortname;
	private String sortorder;

	@JSON(serialize = false)	
	public SendSystemEmail getSendSystemEmail() {
		return sendSystemEmail;
	}
	public void setSendSystemEmail(SendSystemEmail sendSystemEmail) {
		this.sendSystemEmail = sendSystemEmail;
	}	

	@JSON(serialize = false)
	public InitTAction getInitTAction() {
		return initTAction;
	}
	public void setInitTAction(InitTAction initTAction) {
		this.initTAction = initTAction;
	}
	@JSON(serialize = false)
	public Serial getSerial() {
		return serial;
	}
	public void setSerial(Serial serial) {
		this.serial = serial;
	}
	@JSON(serialize = false)
	public UsertServiceImpl getUsertServiceImpl() {
		return usertServiceImpl;
	}
	public void setUsertServiceImpl(UsertServiceImpl usertServiceImpl) {
		this.usertServiceImpl = usertServiceImpl;
	}
	@JSON(serialize = false)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@JSON(serialize = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JSON(serialize = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JSON(serialize = false)
	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	@JSON(serialize = false)
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@JSON(serialize = false)
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@JSON(serialize = false)
	public Map getCookies() {
		return cookies;
	}

	public void setCookies(Map cookies) {
		this.cookies = cookies;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getUserstate() {
		return userstate;
	}

	public void setUserstate(String userstate) {
		this.userstate = userstate;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getPostingcount() {
		return postingcount;
	}

	public void setPostingcount(String postingcount) {
		this.postingcount = postingcount;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Date getRegisttime() {
		return registtime;
	}

	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	public Date getDisablebegin() {
		return disablebegin;
	}

	public void setDisablebegin(Date disablebegin) {
		this.disablebegin = disablebegin;
	}

	public Date getDisableend() {
		return disableend;
	}

	public void setDisableend(Date disableend) {
		this.disableend = disableend;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getParttime1() {
		return parttime1;
	}

	public void setParttime1(String parttime1) {
		this.parttime1 = parttime1;
	}

	public String getParttime2() {
		return parttime2;
	}

	public void setParttime2(String parttime2) {
		this.parttime2 = parttime2;
	}

	public String getParttime3() {
		return parttime3;
	}

	public void setParttime3(String parttime3) {
		this.parttime3 = parttime3;
	}

	public String getParttime4() {
		return parttime4;
	}

	public void setParttime4(String parttime4) {
		this.parttime4 = parttime4;
	}

	public String getParttime5() {
		return parttime5;
	}

	public void setParttime5(String parttime5) {
		this.parttime5 = parttime5;
	}

	public String getParttime6() {
		return parttime6;
	}

	public void setParttime6(String parttime6) {
		this.parttime6 = parttime6;
	}

	@JSON(serialize = false)
	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@JSON(serialize = false)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@JSON(serialize = false)
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@JSON(serialize = false)
	public String getOthercontract() {
		return othercontract;
	}

	public void setOthercontract(String othercontract) {
		this.othercontract = othercontract;
	}

	@JSON(serialize = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JSON(serialize = false)
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@JSON(serialize = false)
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@JSON(serialize = false)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	
	@JSON(serialize = false)
	public String getGradetime() {
		return gradetime;
	}
	public void setGradetime(String gradetime) {
		this.gradetime = gradetime;
	}
	@JSON(serialize = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JSON(serialize = false)
	public Map getSession() {
		return session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public void setCookiesMap(Map cookies) {
		this.cookies = cookies;
	}

	public String getGoingToURL() {
		return goingToURL;
	}

	public void setGoingToURL(String goingToURL) {
		this.goingToURL = goingToURL;
	}

	public UserT getBeanlist() {
		return beanlist;
	}

	public void setBeanlist(UserT beanlist) {
		this.beanlist = beanlist;
	}

	public List<UserT> getUser() {
		return user;
	}

	public void setUser(List<UserT> user) {
		this.user = user;
	}

	@JSON(name = "rows")
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

	public String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSucflag() {
		return sucflag;
	}

	public void setSucflag(boolean sucflag) {
		this.sucflag = sucflag;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	
	public String getUsession() {
		return usession;
	}

	public void setUsession(String usession) {
		this.usession = usession;
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
	 * 清理错误
	 */
	@Override
	public void validate() {
		this.clearErrorsAndMessages();

	}

	/**
	 * 验证登陆
	 */
	@Action(value="CheckLogin",results={
			@Result(name = "json", type="json",params={"includeProperties","slogin"})
	})
	public String CheckLogin() {
		String adminid = (String) ActionContext.getContext().getSession().get(BaseTools.BACK_USER_SESSION_KEY);
		if (!adminid.isEmpty()) {
			this.setCreatorid(adminid);
			this.setSlogin(false);
			return "json";
		} else {
			this.setSlogin(true);
			return "json";
		}
	}

	
	
	/**
	 * 管理员登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "adminlogin", results = { 
			@Result(name = "success", type="redirect",location = "/jshop/admin/adminindex.jsp?session=${param}"),
			@Result(name = "input", type="redirect",location = "/jshop/admin/adminlogin.jsp?msg=${param}")
	})
	public String adminlogin() throws Exception {
		MD5Code md5 = new MD5Code();
		UserT user = new UserT();
		user.setUsername(this.getUsername().trim());
		user.setPassword(md5.getMD5ofStr(this.getPassword().trim()));
		user.setState("3");//超级管理员
		user = this.getUsertServiceImpl().login(user);
		if (user != null) {
			ActionContext.getContext().getSession().put(BaseTools.BACK_USER_SESSION_KEY, user.getUserid());
			ActionContext.getContext().getSession().put(BaseTools.BACK_USER_NAME_SESSION_KEY, user.getUsername());
			this.setParam(md5.getMD5ofStr(user.getUserid()));
			ActionContext.getContext().getSession().put(BaseTools.BACK_SESSION_KEY, param);
			//获取默认主题
			this.getInitTAction().InitDefaultThemeT();
			return SUCCESS;
		}
		this.setParam("1");
		return INPUT;
	}


	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "findAllUsert", results = { 
			@Result(name = "json",type="json")
	})
	public String findAllUsert() {
		int currentPage = page;
		int lineSize = rp;
		String queryString="from UserT order by "+sortname+" "+sortorder+" ";
		if(Validate.StrNotNull(sortname)&&Validate.StrNotNull(sortorder)){
		List<UserT> list = this.getUsertServiceImpl().sortAllUsert(currentPage, lineSize, queryString);
		if (list != null) {
			total = this.getUsertServiceImpl().countfindAllUsert();
			rows.clear();
			for (Iterator it = list.iterator(); it.hasNext();) {
				UserT u = (UserT) it.next();
				if ("0".equals(u.getSex())) {
					u.setSex("女");
				}
				if ("1".equals(u.getSex())) {
					u.setSex("男");
				}
				if ("1".equals(u.getGrade())) {
					u.setGrade("普通会员");
				}
				if ("2".equals(u.getGrade())) {
					u.setGrade("银卡会员");
				}
				if ("3".equals(u.getGrade())) {
					u.setGrade("金卡会员");
				}
				if ("4".equals(u.getGrade())) {
					u.setGrade("钻石会员");
				}
				if ("5".equals(u.getGrade())) {
					u.setGrade("至尊会员");
				}
				if ("1".equals(u.getState())) {
					u.setState("普通用户");
				}
				if ("2".equals(u.getState())) {
					u.setState("管理员");
				}
				if ("3".equals(u.getState())) {
					u.setState("超级管理员");
				}
				if ("4".equals(u.getState())) {
					u.setState("手机用户");
				}
				if ("0".equals(u.getUserstate())) {
					u.setUserstate("未激活");
				}
				if ("1".equals(u.getUserstate())) {
					u.setUserstate("激活");
				}
				if ("2".equals(u.getUserstate())) {
					u.setUserstate("禁止访问");
				}
				if ("3".equals(u.getUserstate())) {
					u.setUserstate("禁止发帖");
				}
				if(u.getGradetime()!=null){
					this.setGradetime(BaseTools.formateDbDate(u.getGradetime()));
				}else{
					this.setGradetime("");
				}
				Map cellMap = new HashMap();
				cellMap.put("id", u.getUserid());
				cellMap.put("cell", new Object[] {u.getUsername(), u.getRealname(), u.getEmail(), u.getSex(), u.getPoints(), u.getQq(), u.getMsn(), u.getGrade(), u.getState(), u.getUserstate(), u.getGradetime(), BaseTools.formateDbDate(u.getRegisttime()) });
				rows.add(cellMap);
			}
			return "json";
		}
	}
		this.setTotal(0);
		rows.clear();
		return "json";
	}

	/**
	 * 管理员增加用户
	 * 
	 * @return
	 */
	@Action(value = "adminregister", results = { 
			@Result(name = "success", type="redirect",location = "/jshop/admin/member/membermanagement.jsp?session=${param}"),
			@Result(name = "input", type="redirect",location = "/jshop/admin/member/addmember.jsp?msg=${msg}")
	})
	public String adminregister() {
		MD5Code md5 = new MD5Code();
		UserT u = new UserT();
		u.setUsername(this.getUsername().trim());
		u.setEmail(this.getEmail().trim());
		u = this.getUsertServiceImpl().checkUserByUsername(u);
		if (u != null) {
			//ActionContext.getContext().put("registermsg", "用户已经存在");
			this.setMsg("4");
			return INPUT;
		} else {
			u = new UserT();
			u.setUsername(this.getUsername().trim());
			u.setEmail(this.getEmail().trim());
			u = this.getUsertServiceImpl().checkUserByEmail(u);
			if (u != null) {
				this.setMsg("4");
				return INPUT;
			}
			UserT user = new UserT();
			user.setUserid(this.getSerial().Serialid(Serial.USER));
			user.setUid(md5.getMD5ofStr(user.getUserid()));
			user.setUsername(this.getUsername().trim());
			user.setRealname(null);
			user.setEmail(this.getEmail().trim());
			user.setTelno(null);
			user.setMobile(null);
			user.setQuestion(null);
			user.setAnswer(null);
			user.setPassword(md5.getMD5ofStr("111111"));
			user.setUserstate(this.getUserstate());
			user.setPoints(Double.parseDouble(this.getPoints().trim()));
			user.setPostingcount(0);
			user.setSex(null);
			user.setRegisttime(BaseTools.systemtime());
			user.setDisablebegin(null);
			user.setDisableend(null);
			user.setSection(null);
			user.setPosition(null);
			user.setGroupid(null);
			user.setParttime1(null);
			user.setParttime2(null);
			user.setParttime3(null);
			user.setParttime4(null);
			user.setParttime5(null);
			user.setParttime6(null);
			user.setHobby(null);
			user.setQq(null);
			user.setMsn(null);
			user.setOthercontract(null);
			user.setAddress(null);
			user.setPostcode(null);
			user.setBirthday(null);
			user.setGrade(this.getGrade());
			if (this.getGrade().equals("0")) {
				user.setGradetime(null);
			} else {
				user.setGradetime(BaseTools.systemtime());
			}
			user.setState(this.getState());
			if (this.getUsertServiceImpl().save(user) > 0) {
				//重新获取后台登录时保存的加密session key
				this.setParam(ActionContext.getContext().getSession().get(BaseTools.BACK_SESSION_KEY).toString());
				return SUCCESS;
			}
			return INPUT;
		}
	}

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @return
	 */
	@Action(value = "findUserById", results = { 
			@Result(name = "json",type="json")
	})
	public String findUserById() {
		if (Validate.StrNotNull(this.getUserid())) {
			beanlist = this.getUsertServiceImpl().findById(this.getUserid());
			if (beanlist != null) {
				return "json";
			}
		}
		return "json";
	}

	/**
	 *管理员更新用户信息，不包含密码等安全信息
	 * 
	 * @return
	 */
	@Action(value = "UpdateUserTunpwd", results = { 
			@Result(name = "json",type="json")
	})
	public String UpdateUserTunpwd() {
		this.CheckLogin();
		if (!this.isSlogin()) {
			UserT user = new UserT();
			user.setUserid(this.getUserid());
			user.setUsername(this.getUsername().trim());
			user.setRealname(this.getRealname().trim());
			user.setEmail(this.getEmail().trim());
			user.setTelno(this.getTelno().trim());
			user.setMobile(this.getMobile().trim());
			user.setPoints(Double.parseDouble(this.getPoints().trim()));
			user.setUserstate(this.getUserstate());
			user.setSex(this.getSex());
			user.setQq(this.getQq().trim());
			user.setMsn(this.getMsn().trim());
			user.setGrade(this.getGrade());
			user.setState(this.getState());
			if (this.getGrade().equals("0")) {
				user.setGradetime(null);
			} else {
				user.setGradetime(BaseTools.systemtime());
			}
			this.getUsertServiceImpl().updateUserTunpwd(user);
			this.setSucflag(true);
			return "json";
		}
		this.setSucflag(false);
		return "json";
	}

	/**
	 * 管理员批量删除用户
	 * 
	 * @return
	 */
	@Action(value = "DelUsert", results = { 
			@Result(name = "json",type="json")
	})
	public String DelUsert() {
		if (Validate.StrNotNull(this.getUserid())) {
			String[] list = this.getUserid().trim().split(",");
			if (this.getUsertServiceImpl().delUser(list) > 0) {
				return "json";
			}
			return "json";
		}
		return "json";
	}

	/**
	 * 更改管理员密码
	 * 
	 * @return
	 */
	@Action(value = "UpdateUserMember", results = { 
			@Result(name = "json",type="json")
	})
	public String UpdateUserMember() {
		this.CheckLogin();
		if (!this.isSlogin()) {
			if (this.CheckUser()) {
				MD5Code md5 = new MD5Code();
				UserT user = new UserT();
				user.setUserid(this.getUserid().trim());
				user.setUsername(this.getUsername().trim());
				user.setPassword(md5.getMD5ofStr(this.getNewpassword().trim()));
				this.getUsertServiceImpl().updateUserMember(user);
				return "json";
			}
		}
		return "json";
	}

	/**
	 * 修改管理员密码，判断数据库是否存在
	 * 
	 * @return
	 */
	public boolean CheckUser() {
		this.CheckLogin();
		if (!this.isSlogin()) {
			MD5Code md5 = new MD5Code();
			UserT user = new UserT();
			user.setUsername(this.getUsername().trim());
			user.setPassword(md5.getMD5ofStr(this.getPassword().trim()));
			UserT userlist = this.getUsertServiceImpl().usert(user);
			if (userlist != null) {
				return true;
			} else {
				return false;
			}

		}
		return true;
	}
	/**
	 * 发送激活邮件
	 * 
	 * @param user
	 * @throws MessagingException
	 * @throws IOException
	 * @throws TemplateException
	 * @throws javax.mail.MessagingException 
	 */
	@Action(value = "sendeMail", results = { 
			@Result(name = "json",type="json")
	})
	public String  sendeMail() throws MessagingException, IOException, TemplateException, javax.mail.MessagingException {
		this.findUserById();
		if(beanlist.getUserstate().equals("1")){
			this.setSlogin(false);
			return "json";
		}else{	   
	    sendSystemEmail.sendTextMail(beanlist);
		this.setSlogin(true);
		return "json";
		}
	}

	@Action(value = "updateUserbyuserstate", results = { 
			@Result(name = "json",type="json")
	})
	public String updateUserbyuserstate(){
		if(Validate.StrNotNull(this.getUserid())){
			UserT user=new UserT();
			user=this.getUsertServiceImpl().findById(this.getUserid());
			if(user!=null){
				if(user.getUserstate().equals("0")){
					this.setSucflag(false);
					return "json";		
				}else{
				user.setUserstate(this.getUserstate());
				this.getUsertServiceImpl().updateUserstate(user);
				
				this.setSucflag(true);
				return "json";				
			}
			}
			this.setSucflag(false);
			return "json";
		}
		this.setSucflag(false);
		return "json";
	}
	
}
