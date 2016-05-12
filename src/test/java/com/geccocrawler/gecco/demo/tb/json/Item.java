package com.geccocrawler.gecco.demo.tb.json;

import java.util.List;

/**
 * 说明 : 作者 : WeiHui.jackson 日期 : 2016/5/10 17:10 版本 : 1.0.0
 */
public class Item {

	/**
	 * raw_title : 小姿家 2016春新欧美气质街头圆领垫肩纯色包臀显瘦中长款连衣裙 view_fee : 450.00 item_loc :
	 * 北京 comment_count : 57 detail_url :
	 * //ju.taobao.com/tg/home.htm?item_id=528744267263
	 * &tracelog=fromsearch&abbucket=0 p4pTags : [] nid : 528744267263 icon :
	 * [{"icon_key"
	 * :"icon-fest-xinshilizhou2","show_type":"0","trace":"srpservice"
	 * ,"icon_category"
	 * :"baobei","outer_text":"0","innerText":"新势力周","html":"","position"
	 * :"1","title"
	 * :"新势力周","dom_class":"icon-fest-xinshilizhou2","traceIdx":0},{"icon_key"
	 * :"icon-fest-xiashangxin"
	 * ,"show_type":"0","trace":"srpservice","icon_category"
	 * :"baobei","outer_text"
	 * :"0","innerText":"夏上新","html":"","position":"1","title"
	 * :"夏上新","dom_class":
	 * "icon-fest-xiashangxin","traceIdx":1},{"outer_text":"0"
	 * ,"innerText":"金牌卖家"
	 * ,"title":"金牌卖家从千万卖家中脱颖而出，会为您的购物体验带来更多信任和安心","iconPopupNormal"
	 * :{"dom_class":"icon-service-jinpaimaijia-l"},"traceIdx":2,"url":
	 * "//www.taobao.com/go/act/jpmj.php"
	 * ,"icon_key":"icon-service-jinpaimaijia",
	 * "show_type":"0","trace":"srpservice"
	 * ,"icon_category":"shop","html":"","position"
	 * :"1","dom_class":"icon-service-jinpaimaijia"}] comment_url :
	 * //ju.taobao.com
	 * /tg/home.htm?item_id=528744267263&tracelog=fromsearch&abbucket
	 * =0&on_comment=1 pid : -1340852390 title : 小姿家
	 * 2016春新欧美气质街头圆领垫肩纯色包臀显瘦中长款连衣裙 view_price : 99.00 nick : baobe1201
	 * view_sales : 293人付款 user_id : 16438668 i2iTags :
	 * {"samestyle":{"url":""},"similar":{"url":
	 * "/list?type=similar&app=i2i&rec_type=1&uniqpid=-1340852390&nid=528744267263"
	 * }} shopcard :
	 * {"isTmall":false,"delivery":[478,0,0],"sellerCredit":17,"totalRate"
	 * :9952,"service"
	 * :[478,0,0],"description":[474,0,0],"levelClasses":[{"levelClass"
	 * :"icon-supple-level-jinguan"
	 * },{"levelClass":"icon-supple-level-jinguan"}],
	 * "encryptedUserId":"UvFx0vGgLMCgT"} shopLink :
	 * //store.taobao.com/shop/view_shop.htm?user_number_id=16438668 sku :
	 * [{"picUrl":
	 * "//g-search2.alicdn.com/img/bao/uploaded/i4/i2/16438668/TB2463TmXXXXXblXXXXXXXXXXXX_!!16438668.jpg"
	 * ,"skuParam":"sku=1627207:20412615#detail"},{"picUrl":
	 * "//g-search2.alicdn.com/img/bao/uploaded/i4/i1/16438668/TB2rcUDmXXXXXXuXpXXXXXXXXXX_!!16438668.jpg"
	 * ,"skuParam":"sku=1627207:3594022#detail"}] category : 50010850 pic_url :
	 * //g-search1.alicdn.com/img/bao/uploaded/i4/i4/16438668/
	 * TB2wqalmXXXXXapXpXXXXXXXXXX_!!16438668.jpg reserve_price : 172.00
	 */

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
	/**
	 * samestyle : {"url":""} similar : {"url":
	 * "/list?type=similar&app=i2i&rec_type=1&uniqpid=-1340852390&nid=528744267263"
	 * }
	 */

	private I2iTagsBean i2iTags;
	/**
	 * isTmall : false delivery : [478,0,0] sellerCredit : 17 totalRate : 9952
	 * service : [478,0,0] description : [474,0,0] levelClasses :
	 * [{"levelClass":
	 * "icon-supple-level-jinguan"},{"levelClass":"icon-supple-level-jinguan"}]
	 * encryptedUserId : UvFx0vGgLMCgT
	 */

	private ShopcardBean shopcard;
	private String shopLink;
	private String category;
	private String pic_url;
	private String reserve_price;
	private List<?> p4pTags;
	/**
	 * icon_key : icon-fest-xinshilizhou2 show_type : 0 trace : srpservice
	 * icon_category : baobei outer_text : 0 innerText : 新势力周 html : position :
	 * 1 title : 新势力周 dom_class : icon-fest-xinshilizhou2 traceIdx : 0
	 */

	private List<IconBean> icon;
	/**
	 * picUrl : //g-search2.alicdn.com/img/bao/uploaded/i4/i2/16438668/
	 * TB2463TmXXXXXblXXXXXXXXXXXX_!!16438668.jpg skuParam :
	 * sku=1627207:20412615#detail
	 */

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

	@Override
	public String toString() {
		return "Item [raw_title=" + raw_title + ", view_fee=" + view_fee
				+ ", item_loc=" + item_loc + ", comment_count=" + comment_count
				+ ", detail_url=" + detail_url + ", nid=" + nid
				+ ", comment_url=" + comment_url + ", pid=" + pid + ", title="
				+ title + ", view_price=" + view_price + ", nick=" + nick
				+ ", view_sales=" + view_sales + ", user_id=" + user_id
				+ ", i2iTags=" + i2iTags + ", shopcard=" + shopcard
				+ ", shopLink=" + shopLink + ", category=" + category
				+ ", pic_url=" + pic_url + ", reserve_price=" + reserve_price
				+ ", p4pTags=" + p4pTags + ", icon=" + icon + ", sku=" + sku
				+ "]";
	}

}
