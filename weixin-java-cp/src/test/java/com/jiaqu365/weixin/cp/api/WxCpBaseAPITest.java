package com.jiaqu365.weixin.cp.api;

import com.google.inject.Inject;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.common.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * 基础API测试
 * @author Daniel Qian
 *
 */
@Test(groups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxCpBaseAPITest {

  @Inject
  protected WxCpServiceImpl wxService;

  public void testRefreshAccessToken() throws WxErrorException {
    WxCpConfigStorage configStorage = wxService.wxCpConfigStorage;
    String before = configStorage.getAccessToken();
    wxService.getAccessToken(false);

    String after = configStorage.getAccessToken();

    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }

}
