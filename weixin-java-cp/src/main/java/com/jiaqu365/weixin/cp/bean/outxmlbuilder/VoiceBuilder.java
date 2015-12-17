package com.jiaqu365.weixin.cp.bean.outxmlbuilder;

import com.jiaqu365.weixin.cp.bean.WxCpXmlOutVoiceMessage;

/**
 * 语音消息builder
 * @author Daniel Qian
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxCpXmlOutVoiceMessage> {

  private String mediaId;

  public VoiceBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }
  
  public WxCpXmlOutVoiceMessage build() {
    WxCpXmlOutVoiceMessage m = new WxCpXmlOutVoiceMessage();
    setCommon(m);
    m.setMediaId(mediaId);
    return m;
  }
  
}
