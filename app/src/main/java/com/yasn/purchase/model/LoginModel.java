package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/8/7.
 */

public class LoginModel implements Serializable{

    /**
     * code : 200
     * message : 登录成功
     * data : {"yasn_shop_token":"{\"access_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxNTAxMDQ4NzU2NSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTUzNjIwNzQzMCwiYXV0aG9yaXRpZXMiOlsiWUFTTl9TSE9QX0NMSUVOVCIsIllBU05fU0hPUF9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiI1YWE5YzAxMy1lNDc4LTQ4MzUtYmRlZi1mZGNjMTY4NWU4YjgiLCJjbGllbnRfaWQiOiJ5YXNuLXNob3AiLCJtZW1iZXJJZCI6MjMyNjd9.G54hP2uXwkLkoIG8210bE6rAli8o4Xd0Ba-vVpPxG7g\",\"token_type\":\"bearer\",\"refresh_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxNTAxMDQ4NzU2NSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjVhYTljMDEzLWU0NzgtNDgzNS1iZGVmLWZkY2MxNjg1ZThiOCIsImV4cCI6MTUzNjIwNzQzMCwiYXV0aG9yaXRpZXMiOlsiWUFTTl9TSE9QX0NMSUVOVCIsIllBU05fU0hPUF9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiIzMWIwMTQxOC1kYTE5LTQwY2QtODZjMS0wNWM0OTgzMTMyMTIiLCJjbGllbnRfaWQiOiJ5YXNuLXNob3AiLCJtZW1iZXJJZCI6MjMyNjd9.1-YGNtt-X1JnncNHmd5o9fiIxK0dTvN5Y-_oxBI8kQo\",\"expires_in\":2591999,\"scope\":\"read write trust\",\"memberId\":23267,\"jti\":\"5aa9c013-e478-4835-bdef-fdcc1685e8b8\"}"}
     * totalCount : 0
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private int totalCount;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * yasn_shop_token : {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxNTAxMDQ4NzU2NSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTUzNjIwNzQzMCwiYXV0aG9yaXRpZXMiOlsiWUFTTl9TSE9QX0NMSUVOVCIsIllBU05fU0hPUF9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiI1YWE5YzAxMy1lNDc4LTQ4MzUtYmRlZi1mZGNjMTY4NWU4YjgiLCJjbGllbnRfaWQiOiJ5YXNuLXNob3AiLCJtZW1iZXJJZCI6MjMyNjd9.G54hP2uXwkLkoIG8210bE6rAli8o4Xd0Ba-vVpPxG7g","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxNTAxMDQ4NzU2NSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjVhYTljMDEzLWU0NzgtNDgzNS1iZGVmLWZkY2MxNjg1ZThiOCIsImV4cCI6MTUzNjIwNzQzMCwiYXV0aG9yaXRpZXMiOlsiWUFTTl9TSE9QX0NMSUVOVCIsIllBU05fU0hPUF9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiIzMWIwMTQxOC1kYTE5LTQwY2QtODZjMS0wNWM0OTgzMTMyMTIiLCJjbGllbnRfaWQiOiJ5YXNuLXNob3AiLCJtZW1iZXJJZCI6MjMyNjd9.1-YGNtt-X1JnncNHmd5o9fiIxK0dTvN5Y-_oxBI8kQo","expires_in":2591999,"scope":"read write trust","memberId":23267,"jti":"5aa9c013-e478-4835-bdef-fdcc1685e8b8"}
         */

        private String yasn_shop_token;

        public String getYasn_shop_token() {
            return yasn_shop_token;
        }

        public void setYasn_shop_token(String yasn_shop_token) {
            this.yasn_shop_token = yasn_shop_token;
        }
    }
}
