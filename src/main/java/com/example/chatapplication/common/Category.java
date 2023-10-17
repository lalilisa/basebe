package com.example.chatapplication.common;

public enum Category {
    ;

    public enum SocketService{
        chat("/chat"),
        qr("/qr");
        public final String name;
        SocketService(String name){
            this.name=name;
        }

    };

    public enum EventLoginQr{

        verifiQR("verifi-qr"),
        checkIsUser("authen"),
        responseLoginQr("login-response"),
        clientConfirm("confirm");
        public final String name;
        EventLoginQr(String name){
            this.name=name;
        }
    }

    public enum Role{
        USER,
        ADMIN
    };
    public enum ErrorCodeEnum {
        INTERNAL_SERVER_ERROR,
        URI_NOT_FOUND,
        INVALID_PARAMETER,
        INVALID_FORMAT;

        private ErrorCodeEnum() {
        }
    };
}
