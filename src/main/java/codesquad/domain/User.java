package codesquad.domain;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;

        this.email = email;
    }

    public boolean isSameId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void update(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
