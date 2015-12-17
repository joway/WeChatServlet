package com.jiaqu365.weixin.cp.bean.outxmlbuilder;

import com.jiaqu365.weixin.cp.bean.WxCpXmlOutNewsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息builder
 * @author Daniel Qian
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxCpXmlOutNewsMessage> {

  protected final List<WxCpXmlOutNewsMessage.Item> articles = new ArrayList<WxCpXmlOutNewsMessage.Item>();
  
  public NewsBuilder addArticle(WxCpXmlOutNewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }
  
  public WxCpXmlOutNewsMessage build() {
    WxCpXmlOutNewsMessage m = new WxCpXmlOutNewsMessage();
    for(WxCpXmlOutNewsMessage.Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}
