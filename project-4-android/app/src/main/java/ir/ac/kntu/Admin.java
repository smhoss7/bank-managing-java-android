package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.ac.kntu.Request.ReqType;

enum AuthCheck {ACCEPT, REJECT, PENDING}


public class Admin extends Person {
    private Boolean block;
    private String userName;
    private String password;
    private int adminId;
    private static int counterID = 2456;
    private static Map<Integer, User> authenList = new HashMap<>();
    private static Map<Integer, Request> requestList = new HashMap<>();
    private Map<ReqType, Boolean> accessibility = new HashMap<>();

    public Admin(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, "-1");
        this.userName = userName;
        this.password = password;
        this.adminId = counterID++;
        accessibility.put(ReqType.AUTHEN, false);
        accessibility.put(ReqType.TRANSFER, false);
        accessibility.put(ReqType.REPORT, false);
        accessibility.put(ReqType.ABOUT_CONTACT, false);
        accessibility.put(ReqType.SETTING, false);
        this.block = false;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public Map<ReqType, Boolean> getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Map<ReqType, Boolean> accessibility) {
        this.accessibility = accessibility;
    }

    public void changeAccessibility(ReqType reqType, Boolean bool) {
        this.accessibility.put(reqType, bool);
    }

    public boolean checkPass(String password) {
        if (password.matches(".*[a-z].*")) {
            if (password.matches(".*[A-Z].*")) {
                if (password.matches(".*[0-9].*")) {
                    if (password.matches(".*[!@#$%&?].*")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<User> getAuthenList() {
        List<User> list = new ArrayList<>();
        for (User user : Admin.authenList.values()) {
            list.add(user);
        }
        return list;
    }

    public static void setAuthenList(Map<Integer, User> authenList) {
        Admin.authenList = authenList;
    }

    public static Map<Integer, Request> getRequestList() {
        return requestList;
    }

    public static void setRequestList(Map<Integer, Request> requestList) {
        Admin.requestList = requestList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return adminId;
    }

    public void setId(int adminId) {
        this.adminId = adminId;
    }

    public static void removeAuthen(int authId) {
        Admin.authenList.remove(authId);
    }

    public static void removeReq(int requestId) {
        Admin.requestList.remove(requestId);
    }

    public static void addAuthen(User user) {
        Admin.authenList.put(user.getUserId(), user);
    }

    public static void addReq(Request request) {
        Admin.requestList.put(request.getRequestId(), request);
    }

    public static List<User> seeUsers(String firstName, String lastName, String phone) {
        List<User> filteredUsers = new ArrayList<>();
        for (User user : authenList.values()) {
            boolean matches = true;
            if (firstName != null && !firstName.isEmpty() && !user.getFirstName().equalsIgnoreCase(firstName)) {
                matches = false;
            }
            if (lastName != null && !lastName.isEmpty() && !user.getLastName().equalsIgnoreCase(lastName)) {
                matches = false;
            }
            if (phone != null && !phone.isEmpty() && !user.getPhone().equals(phone)) {
                matches = false;
            }
            if (matches) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "block=" + block +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", adminId=" + adminId +
                ", accessibility=" + accessibility +
                '}';
    }
}
