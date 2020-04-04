package codesquad.domain;

public class User {
    private String userId;
    private String password;
    private String changedPassword;
    private String name;
    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
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

    public String getChangedPassword() {
        return changedPassword;
    }

    public boolean isSameUser(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isSamePassword(User user) {
        return this.password.equals(user.getPassword());
    }

    public void changeUserInfo(User user) {
        this.password = user.getChangedPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
