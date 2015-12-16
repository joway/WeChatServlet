package UI;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * Created by joway on 15/12/16.
 */
public class MenuManage {

    private WxMenu menu;
    public static final String BUTTON_1 = "1";
    public static final String BUTTON_2 = "2";
    public static final String BUTTON_3_SUB = "3_SUB";

    public MenuManage(WxMpService wxMpService) throws WxErrorException {
        // 建立菜单
        menu = new WxMenu();
        WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();
        button1.setType(WxConsts.BUTTON_CLICK);
        button1.setName("Btn1");
        button1.setKey(BUTTON_1);

        WxMenu.WxMenuButton button2 = new WxMenu.WxMenuButton();
        button2.setType(WxConsts.BUTTON_CLICK);
        button2.setName("Btn2");
        button2.setKey(BUTTON_2);

        WxMenu.WxMenuButton button3 = new WxMenu.WxMenuButton();
        button3.setName("Btn3");

        WxMenu.WxMenuButton button31 = new WxMenu.WxMenuButton();
        button31.setType(WxConsts.BUTTON_VIEW);
        button31.setName("搜索");
        button31.setUrl("http://www.soso.com/");

        WxMenu.WxMenuButton button32 = new WxMenu.WxMenuButton();
        button32.setType(WxConsts.BUTTON_VIEW);
        button32.setName("视频");
        button32.setUrl("http://v.qq.com/");

        WxMenu.WxMenuButton button33 = new WxMenu.WxMenuButton();
        button33.setType(WxConsts.BUTTON_CLICK);
        button33.setName("赞一下我们");
        button33.setKey(BUTTON_3_SUB);

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
