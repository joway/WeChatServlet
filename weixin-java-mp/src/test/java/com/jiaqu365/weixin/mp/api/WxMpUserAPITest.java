package com.jiaqu365.weixin.mp.api;

import com.google.inject.Inject;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.mp.bean.result.WxMpUser;
import com.jiaqu365.weixin.mp.bean.result.WxMpUserList;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * 测试用户相关的接口
 * @author chanjarster
 *
 */
@Test(groups = "userAPI", dependsOnGroups = { "baseAPI", "groupAPI" })
@Guice(modules = ApiTestModule.class)
public class WxMpUserAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testUserUpdateRemark() throws WxErrorException {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configProvider = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.wxMpConfigStorage;
    wxService.userUpdateRemark(configProvider.getOpenId(), "测试备注名");
  }

  public void testUserInfo() throws WxErrorException  {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configProvider = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.wxMpConfigStorage;
    WxMpUser user = wxService.userInfo(configProvider.getOpenId(), null);
    Assert.assertNotNull(user);
  }
  
  public void testUserList() throws WxErrorException  {
      WxMpUserList wxMpUserList = wxService.userList(null);
      Assert.assertNotNull(wxMpUserList);
      Assert.assertFalse(wxMpUserList.getCount() == -1);
      Assert.assertFalse(wxMpUserList.getTotal() == -1);
      Assert.assertFalse(wxMpUserList.getOpenIds().size() == -1);
  }
    
  public void testGroupQueryUserGroup() throws WxErrorException {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configStorage = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.wxMpConfigStorage;
    long groupid = wxService.userGetGroup(configStorage.getOpenId());
    Assert.assertTrue(groupid != -1l);
  }
  
  public void getGroupMoveUser() throws WxErrorException {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configStorage = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.wxMpConfigStorage;
    wxService.userUpdateGroup(configStorage.getOpenId(), wxService.groupGet().get(3).getId());
  }
  
}
