package codesquad.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void update(User updatedUser) {
        this.setName(updatedUser.getName());
        this.setEmail(updatedUser.getEmail());
        this.setPassword(updatedUser.getPassword());
    }

    public boolean isWriterIsSame(Question question) {
        if (this.getName().equals(question.getWriter().getName()))
            return true;
        else
            return false;
    }

    public boolean isSameUser(User comparedUser) {
        if (this.id.equals(comparedUser.getId()))
            return true;
        else
            return false;

    }
}
