package Config;

import com.jiaqu365.weixin.mp.api.WxMpInMemoryConfigStorage;

/**
 * Created by joway on 15/12/13.
 */

public class MyWxConfig extends WxMpInMemoryConfigStorage {

    public static final String TOKEN = "test";
    public static final String APPID = "wxe388ab08637aa809";
    public static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
    public static final String ENCODING_AESKEY = "pwIRoLymSEzMX7YOmezy4zt8t55k0DHXwP5OZNbwlLh";

    public MyWxConfig(){
        appId = APPID; // 设置微信公众号的appid
        secret =  APPSECRET; // 设置微信公众号的app corpSecret
        token = TOKEN; // 设置微信公众号的token
        aesKey = ENCODING_AESKEY; // 设置微信公众号的EncodingAESKey
    }


}
