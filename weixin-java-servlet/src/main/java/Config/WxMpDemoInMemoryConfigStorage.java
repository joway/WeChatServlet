package Config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

import java.io.InputStream;

/**
 * Created by joway on 15/12/13.
 * 根据需求自定义配置类
 */

//@XStreamAlias("xml")
public class WxMpDemoInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

    @Override
    public String toString() {
        return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret + ", accessToken=" + accessToken
                + ", expiresTime=" + expiresTime + ", token=" + token + ", aesKey=" + aesKey + "]";
    }


//    public static WxMpDemoInMemoryConfigStorage fromXml(InputStream is) {
//        XStream xstream = XStreamInitializer.getInstance();
//        xstream.processAnnotations(WxMpDemoInMemoryConfigStorage.class);
//        return (WxMpDemoInMemoryConfigStorage) xstream.fromXML(is);
//    }

}