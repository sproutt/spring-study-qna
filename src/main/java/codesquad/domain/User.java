package codesquad.domain;

public class User {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void createId(Long id) {
        this.id = id;
    }

    public void editInfo(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public boolean isIdEqual(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
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

    public boolean isUserIdEqual(String userId) {
        return this.userId.equals(userId);
    }



}
