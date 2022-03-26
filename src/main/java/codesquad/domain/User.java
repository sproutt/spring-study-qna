package codesquad.domain;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(User user) {
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public boolean equalsPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isSameUser(String userId) {
        return this.userId.equals(userId);
    }
}
