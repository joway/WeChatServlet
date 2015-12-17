package com.jiaqu365.weixin.mp.demo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.jiaqu365.weixin.common.util.xml.XStreamInitializer;
import com.jiaqu365.weixin.mp.api.WxMpInMemoryConfigStorage;

import java.io.InputStream;

/**
 * @author Daniel Qian
 */
@XStreamAlias("xml")
class WxMpDemoInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken
        + ", expiresTime=" + expiresTime + ", token=" + token + ", aesKey=" + aesKey + "]";
  }


  public static WxMpDemoInMemoryConfigStorage fromXml(InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxMpDemoInMemoryConfigStorage.class);
    return (WxMpDemoInMemoryConfigStorage) xstream.fromXML(is);
  }

}
