package com.edu.usa.adf.Modelo;

import java.util.List;




public class Sala {

	private int idSala;	
	private int capacidad;
	private int cantidadFilas;
	
	public Sala() {
		
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	
	
	public int getCantidadFilas() {
		return cantidadFilas;
	}

	public void setCantidadFilas(int cantidadFilas) {
		this.cantidadFilas = cantidadFilas;
	}

	@Override
	public String toString() {
		return "Sala [Sala Id=" + idSala + ", capacidad=" + capacidad + ", cantidadFilas=" + cantidadFilas + "]";
	}

	
}
