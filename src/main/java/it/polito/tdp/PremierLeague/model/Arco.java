package it.polito.tdp.PremierLeague.model;

public class Arco {

	private Match match1;
	private Match match2;
	private Integer peso;
	
	public Arco(Match match1, Match match2, Integer peso) {
		this.match1 = match1;
		this.match2 = match2;
		this.peso = peso;
	}

	public Match getMatch1() {
		return match1;
	}

	public void setMatch1(Match match1) {
		this.match1 = match1;
	}

	public Match getMatch2() {
		return match2;
	}

	public void setMatch2(Match match2) {
		this.match2 = match2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((match1 == null) ? 0 : match1.hashCode());
		result = prime * result + ((match2 == null) ? 0 : match2.hashCode());
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
		Arco other = (Arco) obj;
		if (match1 == null) {
			if (other.match1 != null)
				return false;
		} else if (!match1.equals(other.match1))
			return false;
		if (match2 == null) {
			if (other.match2 != null)
				return false;
		} else if (!match2.equals(other.match2))
			return false;
		return true;
	}
	
}
