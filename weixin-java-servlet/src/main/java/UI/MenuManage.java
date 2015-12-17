package UI;

import com.jiaqu365.weixin.common.api.WxConsts;
import com.jiaqu365.weixin.common.bean.WxMenu;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.mp.api.WxMpService;

/**
 * Created by joway on 15/12/16.
 */
public class MenuManage {

    private WxMenu menu;
    public static final String BUTTON_BOUND = "BUTTON_BOUND";
    public static final String BUTTON_2 = "BUTTON_2";

    public MenuManage(WxMpService wxMpService) throws WxErrorException {
        // 建立菜单
        menu = new WxMenu();
        WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();
        button1.setName("信息查询");

        WxMenu.WxMenuButton button11 = new WxMenu.WxMenuButton();
        button11.setType(WxConsts.BUTTON_VIEW);
        button11.setName("预约教练");
        button11.setUrl("https://www.baidu.com/");

        WxMenu.WxMenuButton button12 = new WxMenu.WxMenuButton();
        button12.setType(WxConsts.BUTTON_VIEW);
        button12.setName("预约查询");
        button12.setUrl("http://qq.com/");

        WxMenu.WxMenuButton button13 = new WxMenu.WxMenuButton();
        button13.setType(WxConsts.BUTTON_CLICK);
        button13.setName("信息绑定");
        button13.setKey(BUTTON_BOUND);


        button1.getSubButtons().add(button11);
        button1.getSubButtons().add(button12);
        button1.getSubButtons().add(button13);


        WxMenu.WxMenuButton button2 = new WxMenu.WxMenuButton();
        button2.setType(WxConsts.BUTTON_CLICK);
        button2.setName("近期活动");
        button2.setKey(BUTTON_2);

        WxMenu.WxMenuButton button3 = new WxMenu.WxMenuButton();
        button3.setName("大波福利");

        WxMenu.WxMenuButton button31 = new WxMenu.WxMenuButton();
        button31.setType(WxConsts.BUTTON_VIEW);
        button31.setName("转发赢红包");
        button31.setUrl("http://www.soso.com/");

        WxMenu.WxMenuButton button32 = new WxMenu.WxMenuButton();
        button32.setType(WxConsts.BUTTON_VIEW);
        button32.setName("签到送礼");
        button32.setUrl("http://163.com/");

        WxMenu.WxMenuButton button33 = new WxMenu.WxMenuButton();
        button33.setType(WxConsts.BUTTON_VIEW);
        button33.setName("App下载");
        button33.setUrl("http://163.com/");

        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button32);
        button3.getSubButtons().add(button33);


        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        wxMpService.menuCreate(menu);

    }

    public WxMenu getWxMenu(){
        return menu;
    }
}
