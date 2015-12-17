package com.jiaqu365.weixin.common.api;

import com.jiaqu365.weixin.common.exception.WxErrorException;

/**
 * WxErrorException处理器
 */
public interface WxErrorExceptionHandler {

  public void handle(WxErrorException e);

}
