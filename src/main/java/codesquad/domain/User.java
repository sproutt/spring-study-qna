package codesquad.domain;

public class User {

    private String userId;
    private int password;
    private String name;
    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public int getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
