package com.marlabs.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name="TBL_AUTHENTICATE")
public class AuthenticationModel {

	@Id
	@Column(name="JWT_TOKEN", unique = true, nullable = false)
	private String jwtToken; 

	@Column(name="AUTH_QUERY")
	private String authQuery;
	
	@Column(name="RESULT")
	private int result;

	@CreationTimestamp
	private LocalDateTime createDateTime;

	@UpdateTimestamp
	private LocalDateTime updatedDateTime;

	public AuthenticationModel() {
		super();
	}

	public AuthenticationModel(String jwtToken, String authQuery, int result) {
		super();
		this.jwtToken = jwtToken;
		this.authQuery = authQuery;
		this.result = result;
	}

	public String getJwt_token() {
		return jwtToken;
	}

	public void setJwt_token(String jwt_token) {
		this.jwtToken = jwt_token;
	}

	public String getQuery() {
		return authQuery;
	}

	public void setQuery(String query) {
		this.authQuery = query;
	}

	public int getSum() {
		return result;
	}

	public void setSum(int sum) {
		this.result = sum;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	
}
