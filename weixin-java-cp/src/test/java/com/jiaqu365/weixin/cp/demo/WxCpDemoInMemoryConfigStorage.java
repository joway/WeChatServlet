package com.jiaqu365.weixin.cp.demo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.jiaqu365.weixin.common.util.xml.XStreamInitializer;
import com.jiaqu365.weixin.cp.api.WxCpInMemoryConfigStorage;

import java.io.InputStream;

/**
 * @author Daniel Qian
 */
@XStreamAlias("xml")
class WxCpDemoInMemoryConfigStorage extends WxCpInMemoryConfigStorage {

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appidOrCorpid=" + corpId + ", corpSecret=" + corpSecret + ", accessToken=" + accessToken
        + ", expiresTime=" + expiresTime + ", token=" + token + ", aesKey=" + aesKey + "]";
  }


  public static WxCpDemoInMemoryConfigStorage fromXml(InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxCpDemoInMemoryConfigStorage.class);
    return (WxCpDemoInMemoryConfigStorage) xstream.fromXML(is);
  }

}
