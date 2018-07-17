package com.example.telRehber;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Entitiy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long id;
	private String isim, soyisim;
	@Size(min = 11, max = 11)
	private String telefon;

	public Entitiy() {

	}

	public Entitiy(long id, String isim, String soyisim, String telefon) {

		this.id = id;
		this.isim = isim;
		this.soyisim = soyisim;
		this.telefon = telefon;
	}

	public Entitiy(String isim, String soyisim, String telefon) {

		this.isim = isim;
		this.soyisim = soyisim;
		this.telefon = telefon;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyisim() {
		return soyisim;
	}

	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
