package com.jshop.action;

import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;
import com.jshop.action.tools.AllOrderState;
import com.jshop.alipay.fh.config.AlipayConfig;
import com.jshop.entity.OrderT;
import com.jshop.entity.PaymentM;
import com.jshop.service.impl.OrderTServiceImpl;
import com.jshop.service.impl.PaymentMServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("jshop")
@Controller("alipayFhTAction")
public class AlipayFhTAction extends ActionSupport {
	@Resource(name = "paymentMServiceImpl")
	private PaymentMServiceImpl paymentMServiceImpl;
	@Resource(name = "orderTServiceImpl")
	private OrderTServiceImpl orderTServiceImpl;
	private String paymentid;
	private String orderid;
	private String orderstate;
	private String paystate;
	private String shippingstate;
	private boolean spaymentflag;

	@JSON(serialize = false)
	public PaymentMServiceImpl getPaymentMServiceImpl() {
		return paymentMServiceImpl;
	}

	public void setPaymentMServiceImpl(PaymentMServiceImpl paymentMServiceImpl) {
		this.paymentMServiceImpl = paymentMServiceImpl;
	}

	@JSON(serialize = false)
	public OrderTServiceImpl getOrderTServiceImpl() {
		return orderTServiceImpl;
	}

	public void setOrderTServiceImpl(OrderTServiceImpl orderTServiceImpl) {
		this.orderTServiceImpl = orderTServiceImpl;
	}

	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public boolean isSpaymentflag() {
		return spaymentflag;
	}

	public void setSpaymentflag(boolean spaymentflag) {
		this.spaymentflag = spaymentflag;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}

	public String getPaystate() {
		return paystate;
	}

	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}

	public String getShippingstate() {
		return shippingstate;
	}

	public void setShippingstate(String shippingstate) {
		this.shippingstate = shippingstate;
	}

	/**
	 * 清理错误
	 */
	@Override
	public void validate() {
		this.clearErrorsAndMessages();

	}

	/**
	 * 获取支付信息
	 * 
	 * @return
	 */
	public void GetPaymentinfo() {
		PaymentM list = this.getPaymentMServiceImpl().findPaymentbyId(this.getPaymentid().trim());
		if (list != null) {
			AlipayConfig.partnerID = list.getPartnerid();
			AlipayConfig.key = list.getSafecode();
			this.setSpaymentflag(true);
		} else {
			this.setSpaymentflag(false);
		}
	}

	/**
	 * 调用支付信息
	 * 
	 * @return
	 */
	@Action(value = "GetFhPaymentinfo", results = { @Result(name = "json", type = "json") })
	public String GetFhPaymentinfo() {
		this.GetPaymentinfo();
		return "json";
	}

	/**
	 * 更新本地发货状态和支付状态
	 * 
	 * @return
	 */
	@Action(value = "CheckFhLocalThenUpdateOrderToShipping", results = { @Result(name = "json", type = "json") })
	public String CheckFhLocalThenUpdateOrderToShipping() {
		OrderT o = this.getOrderTServiceImpl().findOrderDetailByorderid(this.getOrderid());
		if (AllOrderState.SHIPPINGSTATE_ZERO_NUM.equals(o.getPaystate())) {
			String orderstate = AllOrderState.ORDERSTATE_THREE_NUM;
			String paystate = o.getPaystate();
			String shippingstate = AllOrderState.SHIPPINGSTATE_ONE_NUM;
			@SuppressWarnings("unused")
			int i = this.getOrderTServiceImpl().updateOrderPayShippingState(orderid, orderstate, paystate, shippingstate);
			//InitOrdersDetail();
			return "json";
		}
		return "json";

	}
}
