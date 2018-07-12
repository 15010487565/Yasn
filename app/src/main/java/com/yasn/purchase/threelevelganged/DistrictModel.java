package com.yasn.purchase.threelevelganged;

public class DistrictModel {
	private String name;
	private String zipcode;
	private int id;

	public DistrictModel() {
		super();
	}
	public DistrictModel(String name, int id) {
		super();
		this.name = name;
		this.id = id;

	}
	public DistrictModel(String name, String zipcode,int id) {
		super();
		this.name = name;
		this.zipcode = zipcode;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "DistrictModel [name=" + name + ", zipcode=" + zipcode + "]";
	}

}
