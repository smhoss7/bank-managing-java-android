package ir.ac.kntu;

public class Boss extends Person {
    private String userName;
    private String password;
    private Integer bossId;
    private static Integer countId = 225;
    private Boolean block;

    public Boss(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, String.valueOf(-1));
        this.userName = userName;
        this.password = password;
        this.bossId = countId++;
        this.block = false;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
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

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }


}
