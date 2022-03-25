package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false, length=20)
	private String userId;

	private String password;
	private String name;
	private String email;

	public void update(User user){
		this.password = user.getPassword();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public boolean isSamePassword(User user){
		if (this.password.equals(user.getPassword())){
			return true;
		}
		return false;
	}

	public boolean isSameId(Long userid){
		if(this.id.equals( userid)){
			return true;
		}
		return false;
	}

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

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

