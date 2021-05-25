package edu.utez.sisabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	public Role(String role) {
		this.role = role;
	}

	@Id
	private String id;
	private String role;

	@Override
	public String toString() {
		return "Role{" +
				"id='" + id + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
