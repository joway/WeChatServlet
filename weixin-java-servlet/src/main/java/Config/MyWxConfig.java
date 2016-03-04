package Config;

import com.jiaqu365.weixin.mp.api.WxMpInMemoryConfigStorage;

/**
 * Created by joway on 15/12/13.
 */

public class MyWxConfig extends WxMpInMemoryConfigStorage {

    public static final String TOKEN = "test";
    public static final String APPID = "xxxxxxxxxxxxxxxxx";
    public static final String APPSECRET = "xxxxxxxxxxxxxxxxxx";
    public static final String ENCODING_AESKEY = "xxxxxxxxxxxxxxxxxxxxx";

    public MyWxConfig(){
        appId = APPID; // 设置微信公众号的appid
        secret =  APPSECRET; // 设置微信公众号的app corpSecret
        token = TOKEN; // 设置微信公众号的token
        aesKey = ENCODING_AESKEY; // 设置微信公众号的EncodingAESKey
    }


}
