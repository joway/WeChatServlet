package com.jiaqu365.weixin.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaqu365.weixin.common.bean.result.WxError;
import com.jiaqu365.weixin.common.util.json.WxErrorAdapter;
import com.jiaqu365.weixin.cp.bean.WxCpMessage;
import com.jiaqu365.weixin.cp.bean.WxCpTag;
import com.jiaqu365.weixin.cp.bean.WxCpUser;
import com.jiaqu365.weixin.cp.bean.WxCpDepart;

public class WxCpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCpMessage.class, new WxCpMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
