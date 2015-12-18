package Handlers;

import UI.MenuManage;
import com.jiaqu365.weixin.common.api.WxConsts;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.common.session.WxSessionManager;
import com.jiaqu365.weixin.mp.api.WxMpMessageHandler;
import com.jiaqu365.weixin.mp.api.WxMpMessageRouter;
import com.jiaqu365.weixin.mp.api.WxMpService;
import com.jiaqu365.weixin.mp.bean.WxMpCustomMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutTextMessage;

import java.util.Map;

/**
 * Created by joway on 15/12/16.
 */
public class MessageHandler {


    public MessageHandler(WxMpService wxMpService, WxMpMessageRouter wxMpMessageRouter) {


        SessionHandler sessionHandler = new SessionHandler();


        /* 消息路由器:
            WxMessageRouter默认使用异步的方式处理消息
            支持对多种消息类型进行路由.
            在整个应用程序中，WxMessageRouter应该是单例的。
            配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
            规则的结束必须用Rule.end()或者Rule.next()，否则不会生效
            具体使用可以看源代码中的WxMpMessageRouterTest单元测试，或者查看Javadoc
         */
        wxMpMessageRouter
                .rule()
                .msgType(WxConsts.XML_MSG_TEXT)
                .matcher(sessionHandler)
                .handler(sessionHandler)
                .end()
                .rule() // 建立规则
                .msgType(WxConsts.CUSTOM_MSG_TEXT)
                .async(false)
                .handler(new WxMpMessageHandler() {
                    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

//                        System.out.println("async");

//                        System.out.println(wxMessage.getContent()+"\n"+TulingRobotHandler.getReplyFromTulingRobot(wxMessage.getContent()));

                        WxMpXmlOutTextMessage m
                                = WxMpXmlOutTextMessage.TEXT().content(wxMessage.getContent())
                                .fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
                        return m;
                    }
                }) // 把消息全部转发给handler
                .end()
                .rule()
                .msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.BUTTON_CLICK)
                .eventKey(MenuManage.BUTTON_BOUND)
                .async(false)
                .handler(new WxMpMessageHandler() {
                    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
                        WxMpXmlOutTextMessage m
                                = WxMpXmlOutMessage.TEXT().content("回复:\"bd[空格]用户名或邮箱[空格]密码\"即可绑定." +
                                "例如:bd zhangsan wodemima").fromUser(wxMessage.getToUserName())
                                .toUser(wxMessage.getFromUserName()).build();
                        return m;
                    }
                })
                .end()
                .rule()
                .msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.BUTTON_CLICK)
                .eventKey(MenuManage.BUTTON_2)
                .async(false)
                .handler(new WxMpMessageHandler() {
                    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
                        WxMpXmlOutTextMessage m
                                = WxMpXmlOutMessage.TEXT().content("近期活动点击事件").fromUser(wxMessage.getToUserName())
                                .toUser(wxMessage.getFromUserName()).build();
                        return m;
                    }
                })
                .end();


    }
}

