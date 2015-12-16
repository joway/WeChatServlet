package Handlers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

import java.util.Map;

/**
 * Created by joway on 15/12/16.
 */
public class MessageHandler {
    public MessageHandler(WxMpService wxMpService, WxMpMessageRouter wxMpMessageRouter){

        /* 消息路由器:
            WxMessageRouter默认使用异步的方式处理消息
            支持对多种消息类型进行路由.
            在整个应用程序中，WxMessageRouter应该是单例的。
            配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
            规则的结束必须用Rule.end()或者Rule.next()，否则不会生效
            具体使用可以看源代码中的WxMpMessageRouterTest单元测试，或者查看Javadoc
         */
        wxMpMessageRouter
                .rule() // 建立规则
                .async(false) //这个不加发现会无法处理到消息
                .handler(new WxMpMessageHandler() {
                    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

                        // 主动发送消息
                        WxMpCustomMessage message = WxMpCustomMessage.TEXT().toUser(wxMessage.getFromUserName())
                                .content("主动").build();
                        wxMpService.customMessageSend(message);

                        WxMpXmlOutTextMessage m
                                = WxMpXmlOutMessage.TEXT().content(wxMessage.getContent()).fromUser(wxMessage.getToUserName())
                                .toUser(wxMessage.getFromUserName()).build();
                        return m;
                    }
                }) // 把消息全部转发给handler
                .end();
    }
}
