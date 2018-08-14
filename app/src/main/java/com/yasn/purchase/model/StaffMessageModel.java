package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/27.
 */

public class StaffMessageModel implements Serializable{

    /**
     * code : 0
     * message : null
     * data : [{"page":null,"pageSize":null,"id":24688,"memberAdmin":null,"phone":"15012345673","inviteStatus":2,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24710,"memberAdmin":null,"phone":"15229995999","inviteStatus":1,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24711,"memberAdmin":null,"phone":"15229995899","inviteStatus":1,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24713,"memberAdmin":null,"phone":"15226665899","inviteStatus":1,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24717,"memberAdmin":null,"phone":"15012345614","inviteStatus":1,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24718,"memberAdmin":null,"phone":"18200000000","inviteStatus":1,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24806,"memberAdmin":null,"phone":"15010002002","inviteStatus":2,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":24807,"memberAdmin":null,"phone":"15010002003","inviteStatus":2,"employeeAuth":"1","createTime":null},{"page":null,"pageSize":null,"id":25055,"memberAdmin":null,"phone":"15010487565","inviteStatus":2,"employeeAuth":"2","createTime":null},{"page":null,"pageSize":null,"id":25056,"memberAdmin":null,"phone":"15345678901","inviteStatus":1,"employeeAuth":"1","createTime":null}]
     * totalCount : 0
     * success : false
     */

    private int code;
    private Object message;
    private int totalCount;
    private boolean success;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : null
         * pageSize : null
         * id : 24688
         * memberAdmin : null
         * phone : 15012345673
         * inviteStatus : 2
         * employeeAuth : 1
         * createTime : null
         */

        private Object page;
        private Object pageSize;
        private int id;
        private Object memberAdmin;
        private String phone;
        private int inviteStatus;
        private String employeeAuth;
        private Object createTime;

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getMemberAdmin() {
            return memberAdmin;
        }

        public void setMemberAdmin(Object memberAdmin) {
            this.memberAdmin = memberAdmin;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getInviteStatus() {
            return inviteStatus;
        }

        public void setInviteStatus(int inviteStatus) {
            this.inviteStatus = inviteStatus;
        }

        public String getEmployeeAuth() {
            return employeeAuth;
        }

        public void setEmployeeAuth(String employeeAuth) {
            this.employeeAuth = employeeAuth;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }
}
