package ir.ac.kntu;

import java.io.Serializable;

public class Request implements Serializable {
    enum ReqType {ABOUT_CONTACT, REPORT, SETTING, TRANSFER, AUTHEN}

    enum Accept {ACCEPT, REJECT, NOTTING}

    enum Condition {PENDING, CLOSE}

    private Integer requestId;
    private User user;
    private static Integer countId = 123;
    private String userRequest;
    private String adminAnswer;
    private Boolean isAnswer;
    private ReqType reqType;
    private Condition condition;
    private Accept accept;

    public Request(String userRequest, ReqType reqType, User user) {
        this.requestId = countId++;
        this.userRequest = userRequest;
        this.isAnswer = false;
        this.reqType = reqType;
        this.condition = Condition.PENDING;
        this.user = user;
        this.accept = Accept.NOTTING;
    }

    public Request(User user) {
        this.requestId = countId++;
        this.userRequest = user.toString();
        this.isAnswer = false;
        this.reqType = ReqType.AUTHEN;
        this.condition = Condition.PENDING;
        this.user = user;
        this.accept = Accept.NOTTING;

    }

    public Accept getAccept() {
        return accept;
    }

    public void setAccept(Accept accept) {
        this.accept = accept;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReqType getReqType() {
        return reqType;
    }

    public void setReqType(ReqType reqType) {
        this.reqType = reqType;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }

    public String getAdminAnswer() {
        return adminAnswer;
    }

    public void setAdminAnswer(String adminAnswer) {
        this.adminAnswer = adminAnswer;
    }

    public Boolean getAnswer() {
        return isAnswer;
    }

    public void setAnswer(Boolean answer) {
        isAnswer = answer;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }


    @Override
    public String toString() {
        return "Request{" +
                "user=" + user.getFirstName() + " " + user.getLastName() +
                ", userRequest='" + userRequest + '\'' +
                ", adminAnswer='" + adminAnswer + '\'' +
                ", reqType=" + reqType +
                ", condition=" + condition +
                '}';
    }
}
