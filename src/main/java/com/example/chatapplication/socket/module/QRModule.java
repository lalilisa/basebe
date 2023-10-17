package com.example.chatapplication.socket.module;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.chatapplication.common.Category;
import com.example.chatapplication.common.Constant;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.dto.response.LoginSocketResponse;
import com.example.chatapplication.repo.UserRepository;
import com.example.chatapplication.socket.datalistner.QRRawText;
import com.example.chatapplication.socket.datalistner.QrDataListener;
import com.example.chatapplication.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component

public class QRModule {
    private final SocketIONamespace namespace;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String backendSecret;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(QRModule.class);
    @Autowired
    public QRModule(SocketIOServer socketIOServer){
        this.namespace=socketIOServer.addNamespace(Category.SocketService.qr.name);
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        onListeningVerifiQr();
    }
    private ConnectListener onConnected(){
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.info("Client[{}] - Connected to QR module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }
    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from QR module.", client.getSessionId().toString());
        };
    }

    public void onListeningVerifiQr(){
        log.info("Verifi Qr");
        this.namespace.addEventListener(Category.EventLoginQr.verifiQR.name, QRRawText.class, (client1, data, ackSender) -> {
            System.out.println(data.getHashCode());
            String raw= Utils.decodeBase64(data.getHashCode());
            System.out.println(raw);
            String [] result = raw.split("%");
            String socketKey=result[2];
            String expire=result[1];
            String usernameQr=result[0];
            if(Long.parseLong(expire) < new Date().getTime()){
                LoginSocketResponse loginSocketResponse=LoginSocketResponse.builder()
                        .status(0)
                        .accessToken(null)
                        .userId(null)
                        .role(null)
                        .refreshToken(null)
                        .expireAccressToken(null)
                        .build();
                client1.sendEvent(Category.EventLoginQr.responseLoginQr.name,loginSocketResponse);
                return;
            }
            SocketIOClient clientWebAuthen=findSocketClientInNamespace(socketKey);
            System.out.println(clientWebAuthen.getHandshakeData().getSingleUrlParam("socketKey"));
             clientWebAuthen.sendEvent(Category.EventLoginQr.checkIsUser.name);
            namespace.addEventListener(Category.EventLoginQr.clientConfirm.name, QrDataListener.class, (client, data1, ackSender1) -> {
                if(data1.getStatus()==0)
                    return;
                String username=jwtTokenUtil.getUsernameFromToken(data1.getAccessToken(),backendSecret);
                User user=userRepository.findByUsername(username);
                if(user==null)
                    return;
                Date expireAccess=new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 1000);
                Date expireRefresh=new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 10000);
                String accessToken=jwtTokenUtil.generateAccountToken(user,expireAccess);
                String refreshToken=jwtTokenUtil.generateAccountToken(user,expireRefresh);
                client1.sendEvent(Category.EventLoginQr.responseLoginQr.name,
                        LoginSocketResponse.builder()
                                .status(1)
                                .accessToken(accessToken)
                                .userId(user.getId())
                                .role(user.getRole())
                                .refreshToken(refreshToken)
                                .expireAccressToken(expireAccess)
                                .build());
            });
        });
    }

    private SocketIOClient findSocketClientInNamespace(String socketKey){
      return  this.namespace.getAllClients().stream().filter(client -> socketKey.equals(client.getHandshakeData().getSingleUrlParam("socketKey"))).findFirst().orElse(null);
    }
}
