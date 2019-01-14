package codesquad.model;

public class User {
    private String name;
    private String userId;
    private String email;
    private String password;

    public void setName(String name){
        this.name = name;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString(){
        return "User " + userId + ", " + name + ", " + email;
    }
}
