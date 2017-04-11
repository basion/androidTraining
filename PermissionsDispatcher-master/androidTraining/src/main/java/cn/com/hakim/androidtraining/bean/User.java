package cn.com.hakim.androidtraining.bean;

public class User {

	public String name ;
	public Integer id;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, Integer id) {
		this.name = username;
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "User{" +
				"username='" + name + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
