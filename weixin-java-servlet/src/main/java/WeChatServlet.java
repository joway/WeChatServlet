import Config.Config;
import Config.WxMpDemoInMemoryConfigStorage;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by joway on 15/12/13.
 */
public class WeChatServlet extends HttpServlet {
    //WxMpInMemoryConfigStorage在正式生产环境中，
    //可以提供自己的实现，比如将这些信息存储到数据库里，以便各个节点能够共享access token。
    protected WxMpDemoInMemoryConfigStorage wxMpConfigStorage;
    protected WxMpService wxMpService;
    protected WxMpMessageRouter wxMpMessageRouter;

    @Override public void init() throws ServletException {
        super.init();

        wxMpConfigStorage = new WxMpDemoInMemoryConfigStorage();

        wxMpConfigStorage.setAppId(Config.APPID); // 设置微信公众号的appid
        wxMpConfigStorage.setSecret(Config.APPSECRET); // 设置微信公众号的app corpSecret
        wxMpConfigStorage.setToken(Config.TOKEN); // 设置微信公众号的token
        wxMpConfigStorage.setAesKey(Config.ENCODING_AESKEY); // 设置微信公众号的EncodingAESKey

        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);

        WxMpMessageHandler handler = new WxMpMessageHandler() {
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
                WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName()).build();
                return m;
            }
        };


        /* 消息路由器:
            支持对多种消息类型进行路由.
            在整个应用程序中，WxMessageRouter应该是单例的。
            配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
            规则的结束必须用Rule.end()或者Rule.next()，否则不会生效
            具体使用可以看源代码中的WxMpMessageRouterTest单元测试，或者查看Javadoc
         */
        wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
        wxMpMessageRouter
                .rule() // 建立规则
                .async(false) //
                .content("*") // 拦截内容为“哈哈”的消息
                .handler(handler)
                .end();


        WxMenu wxMenu = new WxMenu();
        WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();
        button1.setType(WxConsts.BUTTON_CLICK);
        button1.setName("今日歌曲");
        button1.setKey("V1001_TODAY_MUSIC");
        wxMenu.getButtons().add(button1);

        // 设置菜单
        try {
            wxMpService.menuCreate(wxMenu);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }

    @Override protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }

        // 此段专用于验证url
        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(echostr);
            return;
        }

        /*
        如果微信传过来的是加密信息，那么返回给微信的也得是加密信息。
         */

        // 判断加密类型
        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ?
                "raw" :
                request.getParameter("encrypt_type");

        WxMpXmlMessage inMessage = null;

        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        } else if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            String msgSignature = request.getParameter("msg_signature");
            inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage,
                    timestamp, nonce, msgSignature);
        } else {
            response.getWriter().println("不可识别的加密类型");
            return;
        }

        WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);

        if (outMessage != null) {
            if ("raw".equals(encryptType)) {
                response.getWriter().write(outMessage.toXml());// 非加密消息返回
            } else if ("aes".equals(encryptType)) {
                response.getWriter().write(outMessage.toEncryptedXml(wxMpConfigStorage)); // 加密消息返回
            }
        }


    }
}
