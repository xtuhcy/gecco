package com.geccocrawler.gecco.demo.tb;

import java.util.List;

import com.geccocrawler.gecco.demo.tb.json.I2iTagsBean;
import com.geccocrawler.gecco.demo.tb.json.IconBean;
import com.geccocrawler.gecco.demo.tb.json.ShopcardBean;
import com.geccocrawler.gecco.demo.tb.json.SkuBean;
import com.geccocrawler.gecco.spider.HtmlBean;

public class ProductBrief implements HtmlBean {

	private static final long serialVersionUID = 1L;

	private String raw_title;
	private String view_fee;
	private String item_loc;
	private String comment_count;
	private String detail_url;
	private String nid;
	private String comment_url;
	private String pid;
	private String title;
	private String view_price;
	private String nick;
	private String view_sales;
	private String user_id;

	private I2iTagsBean i2iTags;

	private ShopcardBean shopcard;
	private String shopLink;
	private String category;
	private String pic_url;
	private String reserve_price;
	private List<?> p4pTags;

	private List<IconBean> icon;

	private List<SkuBean> sku;

	public String getRaw_title() {
		return raw_title;
	}

	public void setRaw_title(String raw_title) {
		this.raw_title = raw_title;
	}

	public String getView_fee() {
		return view_fee;
	}

	public void setView_fee(String view_fee) {
		this.view_fee = view_fee;
	}

	public String getItem_loc() {
		return item_loc;
	}

	public void setItem_loc(String item_loc) {
		this.item_loc = item_loc;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getComment_url() {
		return comment_url;
	}

	public void setComment_url(String comment_url) {
		this.comment_url = comment_url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getView_price() {
		return view_price;
	}

	public void setView_price(String view_price) {
		this.view_price = view_price;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getView_sales() {
		return view_sales;
	}

	public void setView_sales(String view_sales) {
		this.view_sales = view_sales;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public I2iTagsBean getI2iTags() {
		return i2iTags;
	}

	public void setI2iTags(I2iTagsBean i2iTags) {
		this.i2iTags = i2iTags;
	}

	public ShopcardBean getShopcard() {
		return shopcard;
	}

	public void setShopcard(ShopcardBean shopcard) {
		this.shopcard = shopcard;
	}

	public String getShopLink() {
		return shopLink;
	}

	public void setShopLink(String shopLink) {
		this.shopLink = shopLink;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getReserve_price() {
		return reserve_price;
	}

	public void setReserve_price(String reserve_price) {
		this.reserve_price = reserve_price;
	}

	public List<?> getP4pTags() {
		return p4pTags;
	}

	public void setP4pTags(List<?> p4pTags) {
		this.p4pTags = p4pTags;
	}

	public List<IconBean> getIcon() {
		return icon;
	}

	public void setIcon(List<IconBean> icon) {
		this.icon = icon;
	}

	public List<SkuBean> getSku() {
		return sku;
	}

	public void setSku(List<SkuBean> sku) {
		this.sku = sku;
	}

}
