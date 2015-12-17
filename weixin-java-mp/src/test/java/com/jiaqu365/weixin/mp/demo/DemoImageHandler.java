package com.jiaqu365.weixin.mp.demo;

import com.jiaqu365.weixin.common.api.WxConsts;
import com.jiaqu365.weixin.common.bean.result.WxMediaUploadResult;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.common.session.WxSessionManager;
import com.jiaqu365.weixin.mp.api.WxMpService;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutImageMessage;
import com.jiaqu365.weixin.mp.api.WxMpMessageHandler;
import com.jiaqu365.weixin.mp.bean.WxMpXmlMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutMessage;

import java.io.IOException;
import java.util.Map;

public class DemoImageHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                  WxMpService wxMpService, WxSessionManager sessionManager) {
    try {
      WxMediaUploadResult wxMediaUploadResult = wxMpService
          .mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, ClassLoader.getSystemResourceAsStream("mm.jpeg"));
      WxMpXmlOutImageMessage m
          = WxMpXmlOutMessage
          .IMAGE()
          .mediaId(wxMediaUploadResult.getMediaId())
          .fromUser(wxMessage.getToUserName())
          .toUser(wxMessage.getFromUserName())
          .build();
      return m;
    } catch (WxErrorException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
