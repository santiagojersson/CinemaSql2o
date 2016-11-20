package com.edu.usa.adf.Modelo;




public class Silla {
	
	 
	private int idSilla;
	private int numero;
	private int fila;
	private int Sala_idSala;	
	
	public Silla() {
		
	}

	public int getIdSilla() {
		return idSilla;
	}

	public void setIdSilla(int idSilla) {
		this.idSilla = idSilla;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getSala_idSala() {
		return Sala_idSala;
	}

	public void setSala_idSala(int sala_idSala) {
		Sala_idSala = sala_idSala;
	}

	@Override
	public String toString() {
		return "Silla [idSilla=" + idSilla + ", numero=" + numero + ", fila=" + fila + ", Sala_idSala=" + Sala_idSala
				+ "]";
	}

	

	
	
}
