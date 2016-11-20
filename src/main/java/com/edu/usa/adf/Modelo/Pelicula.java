package com.edu.usa.adf.Modelo;

public class Pelicula {
	private int idPelicula;
	private String nombre;
	private String genero;
	private int duracion;    //duracion en minutos de la pelicula
	
	public Pelicula(){
		
	}
	
	public int getIdPelicula() {
		return idPelicula;
	}
	public void setIdPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "idPelicula=" + idPelicula + ", Nombre=" + nombre + ", Genero=" + genero + ", Duracion="
				+ duracion + "";
	}
	
	
}
