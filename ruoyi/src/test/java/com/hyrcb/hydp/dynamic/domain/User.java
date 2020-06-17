/**
 * File Name: User.java
 * Date: 2020-06-04 16:16:02
 */
package com.hyrcb.hydp.dynamic.domain;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-06-04
 * @version 1.0
 */
public class User {
	
	private Long id;
	
    private String name;
    
    private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
}
