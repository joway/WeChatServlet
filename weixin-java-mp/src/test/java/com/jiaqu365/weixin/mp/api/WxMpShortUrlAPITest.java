package com.jiaqu365.weixin.mp.api;

import com.google.inject.Inject;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * 测试短连接
 * 
 * @author chanjarster
 */
@Test(groups = "shortURLAPI", dependsOnGroups = { "baseAPI" })
@Guice(modules = ApiTestModule.class)
public class WxMpShortUrlAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testShortUrl() throws WxErrorException {
    String shortUrl = wxService.shortUrl("www.baidu.com");
    Assert.assertNotNull(shortUrl);
  }

}
