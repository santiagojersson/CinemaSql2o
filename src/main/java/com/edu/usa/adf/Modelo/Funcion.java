package com.edu.usa.adf.Modelo;

import java.util.Date;



public class Funcion {
	
	
	private int idFuncion;
	private Date fecha;
	private Date horaInicio;
	private Date horaFin;
	private int Pelicula_idPelicula;
	private int Sala_idSala;
	private int ticketsDisponibles;
	
	public Funcion(){
		
	}
	
	public int getIdFuncion() {
		return idFuncion;
	}
	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}
	public int getPelicula_idPelicula() {
		return Pelicula_idPelicula;
	}
	public void setPelicula_idPelicula(int pelicula_idPelicula) {
		Pelicula_idPelicula = pelicula_idPelicula;
	}
	public int getSala_idSala() {
		return Sala_idSala;
	}
	public void setSala_idSala(int sala_idSala) {
		Sala_idSala = sala_idSala;
	}

	public int getTicketsDisponibles() {
		return ticketsDisponibles;
	}

	public void setTicketsDisponibles(int ticketsDisponibles) {
		this.ticketsDisponibles = ticketsDisponibles;
	}

	@Override
	public String toString() {
		return "Funcion [idFuncion=" + idFuncion +", horaInicio=" + horaInicio + ", horaFin="
				+ horaFin + ", Pelicula=" + Pelicula_idPelicula + ","
				+ ", ticketsDisponibles=" + ticketsDisponibles + "]";
	}
	
	
	
	
}
