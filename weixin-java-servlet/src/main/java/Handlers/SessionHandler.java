package Handlers;

import com.jiaqu365.weixin.common.exception.WxErrorException;
import com.jiaqu365.weixin.common.session.WxSession;
import com.jiaqu365.weixin.common.session.WxSessionManager;
import com.jiaqu365.weixin.mp.api.WxMpMessageHandler;
import com.jiaqu365.weixin.mp.api.WxMpMessageMatcher;
import com.jiaqu365.weixin.mp.api.WxMpService;
import com.jiaqu365.weixin.mp.bean.WxMpCustomMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlMessage;
import com.jiaqu365.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by joway on 15/12/17.
 */
public class SessionHandler implements WxMpMessageHandler, WxMpMessageMatcher {

    private Pattern pattern = Pattern.compile("^bd *");

    private boolean isMatch(WxMpXmlMessage message){
        return pattern.matcher(message.getContent()).matches();
    }

    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        WxSession session = sessionManager.getSession(wxMessage.getFromUserName());
        if(session.getAttribute("last_message")!=null){
            WxMpCustomMessage m = WxMpCustomMessage
                    .TEXT()
                    .toUser(wxMessage.getFromUserName())
                    .content("你上一条消息是:" + session.getAttribute("last_message"))
                    .build();
            wxMpService.customMessageSend(m);
        }
        else{
            WxMpCustomMessage m = WxMpCustomMessage
                    .TEXT()
                    .toUser(wxMessage.getFromUserName())
                    .content("你是第一次发送绑定消息")
                    .build();
            wxMpService.customMessageSend(m);
        }
        session.setAttribute("last_message", wxMessage.getContent());
        return null;
    }

    public boolean match(WxMpXmlMessage message) {
        return isMatch(message);
    }
}
