package br.com.machadowelton.domain.enums;

public enum EStatus {
	
	EM_USO("EM USO"), USADO("USADO");
	
	private final String value;
	
	private EStatus(String status) {
		this.value = status;
	}
	
	public String toString() {
		return this.value;
	}
	
}
