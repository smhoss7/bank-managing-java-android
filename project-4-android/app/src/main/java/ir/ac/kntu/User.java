package ir.ac.kntu;

import java.io.Serializable;
import java.util.*;

public class User extends Person implements Serializable {
    private Boolean block;
    private Integer userId;
    private static Integer counterId = 115683;
    private Long personalCode;
    private String password;
    private Integer acountID;
    private boolean authentication;
    private String aboutAuthen;
    private boolean haveContact;
    private List<Person> contact = new ArrayList<>();
    //private Map<Person, Boolean> contact = new HashMap<>();
    private List<Request> requests = new ArrayList<>();       //first is request and secound is answered

    public User(String firstName, String lastName, String phone, Long personalCode, String password) {
        super(firstName, lastName, phone);
        this.personalCode = personalCode;
        this.password = password;
        this.userId = counterId++;
        this.authentication = false;
        this.haveContact = false;
        this.aboutAuthen = "";
        this.acountID = null;
        this.block = false;
        this.haveContact = true;
    }


    public void getCont(Person person, String firstName, String last) {
        for (Person person1 : this.contact) {
            if (person.getPhone().equals(person1.getPhone())) {
                System.out.println("befor change"+person1);
                person1.setFirstName(firstName);
                person1.setLastName(last);
                System.out.println("change peron"+person1);
            }
        }
    }

    public Person getAddedContact(Person contact){
        for (Person person1 : this.contact) {
            if (contact.getPhone().equals(person1.getPhone())) {
                return person1;
            }
        }
        return null;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public static boolean checkPass(String password) {
        if (password.matches(".*[a-z].*")) {
            if (password.matches(".*[A-Z].*")) {
                if (password.matches(".*[0-9].*")) {
                    if (password.matches(".*[!@#$%&?].*")) {
                        return password.matches("[0-9a-zA-z!@#$%&?]+");
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkPersinalCode(String personalCode) {
        return personalCode.matches("\\d+");
    }

  /*  @Override
    public String toString() {
        return super.toString() + " " + this.personalCode + " " + " about block" + this.block;
    }*/

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static Integer getCounterId() {
        return counterId;
    }

    public static void setCounterId(Integer counterId) {
        User.counterId = counterId;
    }

    public Long getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(Long personalCode) {
        this.personalCode = personalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAcountID() {
        if (this.acountID == null) {
            return null;
        }
        return acountID;
    }

    public void setAcountID(Integer acountID) {
        this.acountID = acountID;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getAboutAuthen() {
        return aboutAuthen;
    }

    public void setAboutAuthen(String aboutAuthen) {
        this.aboutAuthen = aboutAuthen;
    }

    public boolean isHaveContact() {
        return haveContact;
    }

    public void setHaveContact(boolean haveContact) {
        this.haveContact = haveContact;
    }

    public List<Person> getContact() {
        return contact;
    }

    public void addContact(Person person) {
        this.contact.add(person);
    }

    public void setContact(List<Person> contact) {
        this.contact = contact;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void addRequest(Request request) {
        if (!this.requests.contains(request)) {
            this.requests.add(request);
        }
    }

    public boolean togetherContact(User user) {
        return contact.stream().anyMatch(person -> user.getPhone().equals(person.getPhone()));
    }

    public void removeRequest(Request request) {
        if (this.requests.contains(request)) {
            this.requests.remove(request);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User user)) {
            return false;
        }
        return authentication == user.authentication && haveContact == user.haveContact && Objects.equals(userId, user.userId) && Objects.equals(personalCode, user.personalCode) && Objects.equals(password, user.password) && Objects.equals(acountID, user.acountID) && Objects.equals(aboutAuthen, user.aboutAuthen) && Objects.equals(contact, user.contact) && Objects.equals(requests, user.requests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, personalCode, password, acountID, authentication, aboutAuthen, haveContact, contact, requests);
    }
}
