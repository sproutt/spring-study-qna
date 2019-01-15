package codesquad.model;

public class User {
    private String name;
    private String userId;
    private String email;
    private String password;

    public User() {

    }

    public User(String name, String userId, String email, String password) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public String getUserId(){
        return userId;
    }
    public String getEmail(){
        return email;
    }
    @Override
    public String toString() {
        return "User " + userId + ", " + name + ", " + email;
    }
}
