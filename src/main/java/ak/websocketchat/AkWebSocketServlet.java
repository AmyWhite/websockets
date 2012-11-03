/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ak.websocketchat;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author Tiamat
 */
public class AkWebSocketServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;
    private static final String GUEST_PREFIX = "Guest";
    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private final Set<ChatMessageInbound> connections = new CopyOnWriteArraySet<>();
    
    private static final String PARTICIPANTS_PREFIX = "Participants: ";
    private static final String PARTICIPANTS_DELIMITER = ";";
    
    @Override
    protected StreamInbound createWebSocketInbound(String string, HttpServletRequest request) {
        String nickname = (request.getParameter("nickname") == null || "".equals(request.getParameter("nickname"))) 
                ? GUEST_PREFIX : (String)request.getParameter("nickname");
        try {
            nickname = URLDecoder.decode(nickname, "UTF-16");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AkWebSocketServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ChatMessageInbound(nickname, connectionIds.incrementAndGet());
    }
    
    private void sendMessage(GenericMessage theMessage) {
        for (ChatMessageInbound conn : connections) {
            try {
                if (theMessage.getRecipient() != null 
                        && !"".contains(theMessage.getRecipient())
                        && !conn.getNickname().equals(theMessage.getRecipient())) {
                    continue;
                }
                CharBuffer buf = CharBuffer.wrap(theMessage.generateJSON());
                conn.getWsOutbound().writeTextMessage(buf);
            } catch (IOException ex) {
                Logger.getLogger(AkWebSocketServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void sendBinaryData(ByteBuffer bb) {
        for (ChatMessageInbound conn : connections) {
            try {
                conn.getWsOutbound().writeBinaryMessage(bb);
            } catch (IOException ex) {
                Logger.getLogger(AkWebSocketServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String getParticipantsString() {
        StringBuffer buf = new StringBuffer(PARTICIPANTS_PREFIX);
        for (ChatMessageInbound conn : connections) {
            buf.append(conn.getNickname());
            buf.append(PARTICIPANTS_DELIMITER);
        }
        if (buf.lastIndexOf(PARTICIPANTS_DELIMITER) != -1) {
            buf.delete(buf.lastIndexOf(PARTICIPANTS_DELIMITER), buf.length());
        }
        
        return buf.toString();
    }

    
    private final class ChatMessageInbound extends MessageInbound {

        private String nickname;
        
        public String getNickname() {
            return nickname;
        }
        
        public ChatMessageInbound(String userNickname, int uniqueId) {
            this.nickname = userNickname + "(" + uniqueId + ")";
        }

        @Override
        protected void onClose(int status) {
            connections.remove(this);
            String text = " left the chat.";
            GenericMessage message = new GenericMessage(false, nickname, "", text);
            sendMessage(message);
            GenericMessage parts = new GenericMessage(true, nickname, "", getParticipantsString());
            sendMessage(parts);
        }

        @Override
        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
            String text = " has just connected.";
            GenericMessage message = new GenericMessage(false, nickname, "", text);
            sendMessage(message);
            GenericMessage parts = new GenericMessage(true, nickname, "", getParticipantsString());
            sendMessage(parts);
        }
        
        @Override
        protected void onBinaryMessage(ByteBuffer bb) throws IOException {
            sendBinaryData(bb);
        }

        @Override
        protected void onTextMessage(CharBuffer cb) throws IOException {
            // we need to filter text from client in case it contains javascript
        	
        	if (cb.length() > 0) {
                GenericMessage incomingMessage = new GenericMessage(cb.toString());
                incomingMessage.setParticipants(false);
                incomingMessage.setSender(nickname);
                sendMessage(incomingMessage);
            }
        }
        
        
    }
    
    private class GenericMessage {
        private boolean participants;
        private String sender;
        private String recipient;
        private String text;

        public GenericMessage(boolean participants, String sender, String recipient, String text) {
            this.participants = participants;
            this.sender = sender;
            this.recipient = recipient;
            this.text = text;
        }
        
        public void copy(GenericMessage temp) {
            this.participants = temp.isParticipants();
            this.sender = temp.getSender();
            this.recipient = temp.getRecipient();
            this.text = temp.getText();
        }
        
        public GenericMessage(String jsonString) {
            
            super();
            GenericMessage temp = (new Gson()).fromJson(jsonString, GenericMessage.class);
            this.copy(temp);
        }

        public String generateJSON() {
            return (new Gson()).toJson(this);
        }
        
        public String getRecipient() {
            return recipient;
        }

        public String getSender() {
            return sender;
        }

        public String getText() {
            return text;
        }

        public boolean isParticipants() {
            return participants;
        }

        public void setParticipants(boolean participants) {
            this.participants = participants;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
        
    }
}
