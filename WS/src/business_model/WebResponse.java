package business_model;

import javax.validation.constraints.NotNull;

import model.Demonstrator;

public class WebResponse {
	private String token;
	private Demonstrator demonstrator;
	public WebResponse(@NotNull String token, @NotNull Demonstrator demonstrator) {
		super();
		this.token = token;
		this.demonstrator = demonstrator;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Demonstrator getDemonstrator() {
		return demonstrator;
	}
	public void setDemonstrator(Demonstrator demonstrator) {
		this.demonstrator = demonstrator;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((demonstrator == null) ? 0 : demonstrator.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebResponse other = (WebResponse) obj;
		if (demonstrator == null) {
			if (other.demonstrator != null)
				return false;
		} else if (!demonstrator.equals(other.demonstrator))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}






}
