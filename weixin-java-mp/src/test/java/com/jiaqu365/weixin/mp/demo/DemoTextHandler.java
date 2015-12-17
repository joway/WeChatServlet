package com.jiaqu365.weixin.mp.demo;

import com.jiaqu365.weixin.common.session.WxSessionManager;
import com.jiaqu365.weixin.mp.api.WxMpMessageHandler;
import com.jiaqu365.weixin.mp.api.WxMpService;
import com.jiaqu365.weixin.mp.bean.WxMpXmlMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutTextMessage;

import java.util.Map;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoTextHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                  WxMpService wxMpService, WxSessionManager sessionManager) {
    WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
    return m;
  }

}
