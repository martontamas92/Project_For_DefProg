package business_model;

import org.jetbrains.annotations.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Neptun_Code {
	private String neptun;

	private Neptun_Code(String neptun) {
		//super();
		if(canBuild(neptun)) {
			this.neptun = neptun;
		}else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((neptun == null) ? 0 : neptun.hashCode());
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
		Neptun_Code other = (Neptun_Code) obj;
		if (neptun == null) {
			if (other.neptun != null)
				return false;
		} else if (!neptun.equals(other.neptun))
			return false;
		return true;
	}

	private boolean canBuild(@NotNull String neptun) {
		 Pattern p = Pattern.compile("[^A-Za-z0-9]");
	     Matcher m = p.matcher(neptun);
	     boolean b = m.find();
		 return neptun != "" || neptun.length() > 8 || b ;
	}

	public static Neptun_Code buildNeptun_Code(@NotNull String neptun) {
		return new Neptun_Code(neptun);
	}

	@Override
	public String toString() {
		return "Neptun_Code [neptun=" + neptun + "]";
	}

	public String getNeptun() {
		return this.neptun;
	}

	public void setNeptun(@NotNull String neptun) {
		this.neptun = neptun;
	}




}
