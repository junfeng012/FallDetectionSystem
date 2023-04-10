package com.example.becarefall;

public class c_FallRequest {
    private String request;
    private String userid;
    private String caretakername;
    private String caretakerphone;

    public c_FallRequest() {
        // Default constructor required for calls to DataSnapshot.getValue(c_FallRequest.class)
    }

    public c_FallRequest(String request, String userid, String caretakername, String caretakerphone) {
        this.request = request;
        this.userid = userid;
        this.caretakername = caretakername;
        this.caretakerphone = caretakerphone;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCaretakername() {
        return caretakername;
    }

    public void setCaretakername(String caretakername) {
        this.caretakername = caretakername;
    }

    public String getCaretakerphone() {
        return caretakerphone;
    }

    public void setCaretakerphone(String caretakerphone) {
        this.caretakerphone = caretakerphone;
    }
}
