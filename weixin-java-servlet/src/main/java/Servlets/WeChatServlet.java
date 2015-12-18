package Servlets;

import Config.MyWxConfig;
import Handlers.MessageHandler;
import Handlers.TulingRobotHandler;
import UI.MenuManage;
import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.common.util.StringUtils;
import com.jiaqu365.weixin.mp.api.WxMpMessageRouter;
import com.jiaqu365.weixin.mp.api.WxMpService;
import com.jiaqu365.weixin.mp.api.WxMpServiceImpl;
import com.jiaqu365.weixin.mp.bean.WxMpXmlMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by joway on 15/12/13.
 */
public class WeChatServlet extends HttpServlet {
    //WxMpInMemoryConfigStorage在正式生产环境中，
    //可以提供自己的实现，比如将这些信息存储到数据库里，以便各个节点能够共享access token。
    protected MyWxConfig myWxConfig;
    protected WxMpService wxMpService;
    protected WxMpMessageRouter wxMpMessageRouter;

    private MessageHandler messageHandler;
    private MenuManage menuManage;

    @Override
    public void init() throws ServletException {
        super.init();
        myWxConfig = new MyWxConfig();

        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(myWxConfig);


        // 路由绑定
        wxMpMessageRouter = new WxMpMessageRouter(wxMpService);

        // 创建消息处理,并与路由相映射
        messageHandler = new MessageHandler(wxMpService, wxMpMessageRouter);

        // MenuManage 创建自定义菜单
        try {
            menuManage = new MenuManage(wxMpService);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
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
            inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), myWxConfig,
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
                response.getWriter().write(outMessage.toEncryptedXml(myWxConfig)); // 加密消息返回
            }
        }

    }
}
