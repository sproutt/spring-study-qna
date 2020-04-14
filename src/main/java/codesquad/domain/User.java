package codesquad.domain;

public class User {

    private String userId;
    private int password;
    private String name;
    private String email;

    public User(String userId, int password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;

        this.email = email;
    }

    public boolean isSameId(String userId) {
        return this.userId.equals(userId);
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
