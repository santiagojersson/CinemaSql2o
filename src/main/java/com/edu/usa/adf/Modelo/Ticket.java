package com.edu.usa.adf.Modelo;




public class Ticket {
	
	
	private int idTicket;	
	private int Funcion_idFuncion;
	private boolean vendido;
	private String numeroSilla;
	private String sala;
	
	
	public Ticket(){
		
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public int getFuncion_idFuncion() {
		return Funcion_idFuncion;
	}

	public void setFuncion_idFuncion(int funcion_idFuncion) {
		Funcion_idFuncion = funcion_idFuncion;
	}

	public boolean isVendido() {
		return vendido;
	}

	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}

	public String getNumeroSilla() {
		return numeroSilla;
	}

	public void setNumeroSilla(String numeroSilla) {
		this.numeroSilla = numeroSilla;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", Funcion=" + Funcion_idFuncion + ", vendido=" + vendido
				+ ", numeroSilla=" + numeroSilla + ", sala=" + sala + "]";
	}
	
	
	
	
}
