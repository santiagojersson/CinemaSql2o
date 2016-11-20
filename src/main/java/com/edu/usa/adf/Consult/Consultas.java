package com.edu.usa.adf.Consult;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import com.edu.usa.adf.Modelo.Funcion;
import com.edu.usa.adf.Modelo.Pelicula;
import com.edu.usa.adf.Modelo.Sala;
import com.edu.usa.adf.Modelo.Silla;
import com.edu.usa.adf.Modelo.Ticket;

public class Consultas {

	private Sql2o sql;

	public Consultas() {
		this.sql = new Sql2o("jdbc:mysql://localhost:3306/cinema", "root", "root");
	}

	public Connection getConexion() {
		Connection cone = null;
		try {
			cone = sql.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cone;
	}

	public boolean insertarPelicula(Pelicula p) {
		String sql = "insert into Pelicula (nombre, genero, duracion) values ( :name, :genero, :duracion )";
		boolean b = false;
		try (Connection c = getConexion()) {
			c.createQuery(sql, true).addParameter("name", p.getNombre()).addParameter("genero", p.getGenero())
					.addParameter("duracion", p.getDuracion()).executeUpdate();
			b = true;
		}
		return b;
	}

	public List<Pelicula> getPeliculas() {

		String sql = "SELECT idPelicula, nombre, genero, duracion " + "FROM Pelicula";

		try (Connection con = getConexion()) {
			return con.createQuery(sql).executeAndFetch(Pelicula.class);
		}

	}

	public boolean insertSala(int capacidadSala, int cantidadFilas){
		boolean b= false;
		
		String query = "INSERT INTO Sala(capacidad, cantidadFilas) VALUES (:capa, :canti)";
		try (Connection c = getConexion()) {
			c.createQuery(query, true).addParameter("capa",capacidadSala).addParameter("canti",cantidadFilas)
					.executeUpdate();
			
			b = true;
		}
		int idSala=getIdSala();
		insertarSillas(capacidadSala, cantidadFilas, idSala);
		return b;
	}
	
	public  Integer getIdSala() {
		String sql="SELECT MAX(idSala) AS id FROM Sala";
		try(Connection c= getConexion()){
			Integer ret=(Integer)c.createQuery(sql).executeScalar();
			return ret.intValue();
		}
		
	}

	public boolean insertarSillas(int capacidadSala, int cantidadFilas, int idSala) {
		boolean b = false;
		int sillasFila = capacidadSala / cantidadFilas;
		int sillasFaltantes = capacidadSala - (sillasFila * cantidadFilas);
		
		
		String query = "INSERT INTO Silla(numero, fila, Sala_idSala) VALUES (:num, :fila, :sala)";
		

		try (Connection con = sql.beginTransaction()) {
			Query quer = con.createQuery(query);
			
			for (int i = 1; i <= cantidadFilas; i++) {
				for (int j = 1; j <=sillasFila ; j++) {
					quer.addParameter("num", j).addParameter("fila", +i).addParameter("sala", idSala)
					.addToBatch();
				}
			}
			if (sillasFaltantes != 0) {
				for (int i = 1; i <= sillasFaltantes; i++) {
					quer.addParameter("num", i).addParameter("fila", +cantidadFilas+1).addToBatch();
				}
			}
			quer.executeBatch(); // executes entire batch
			con.commit(); // remember to call commit(), else sql2o will
			b=true;			// automatically rollback.
		}
		
		return b;
	}
	
	public List<Silla> getNumeroSillas(int salaId){
		String sql="SELECT idSilla, numero, fila, Sala_idSala FROM Silla WHERE Sala_idSala= :sala ";
		 try (Connection con = getConexion()) {
		        return con.createQuery(sql)
		            .addParameter("sala", salaId)
		            .executeAndFetch(Silla.class);
		    }
	}
	
	public  Integer getIdFuncion() {
		String sql="SELECT MAX(idFuncion) AS id FROM Funcion";
		try(Connection c= getConexion()){
			Integer ret=(Integer)c.createQuery(sql).executeScalar();
			return ret.intValue();
		}
		
	}
	
	public boolean insertarFuncion(Funcion funcion){
		boolean b=false;
		String sql="INSERT INTO Funcion(fecha, horaInicio, horaFin, Pelicula_idPelicula, Sala_idSala, ticketsDisponibles) VALUES"
				+ "(:fecha, :inicio, :fin, :peli, :sala, :tickets)";
		int tickets= getCapacidadSala(funcion.getSala_idSala());
		try(Connection con= getConexion()){
			con.createQuery(sql, true)
			.addParameter("fecha",funcion.getFecha())
			.addParameter("inicio",funcion.getHoraInicio())
			.addParameter("fin", funcion.getHoraFin())
			.addParameter("peli", funcion.getPelicula_idPelicula())
			.addParameter("sala", funcion.getSala_idSala())
			.addParameter("tickets", tickets)
			.executeUpdate();
			b=true;
		}
		int fun= getIdFuncion();
		crearTickets(fun, getNumeroSillas(funcion.getSala_idSala()));
		return b;
	}
	
	private Integer getCapacidadSala(int sala_idSala) {
		String sql="SELECT capacidad FROM sala WHERE idSala= :sala ";
		try(Connection c= getConexion()){
			Integer ret=(Integer)c.createQuery(sql).addParameter("sala", sala_idSala).executeScalar();
			return ret.intValue();
		}
	}

	public boolean crearTickets(int idFuncion, List<Silla> sillas){
		boolean b=false;
		String query= "INSERT INTO Ticket (Funcion_idFuncion, vendido, numeroSilla, sala) "
				+ "VALUES (:funcion, :vendido, :numSilla, :sala)";
		try (Connection con = sql.beginTransaction()) {
			Query quer = con.createQuery(query);
			for (Silla silla : sillas) {
				quer.addParameter("funcion", idFuncion)
				.addParameter("vendido", false)
				.addParameter("numSilla", silla.getFila()+"-"+silla.getNumero())
				.addParameter("sala", silla.getSala_idSala())
				.addToBatch();
			}
			quer.executeBatch(); // executes entire batch
			con.commit(); // remember to call commit(), else sql2o will
			b=true;	
		}
		return b;
	}
	
	
	public List<Sala> getSala() {

		String sql = "SELECT idSala, capacidad, cantidadFilas " + "FROM Sala";

		try (Connection con = getConexion()) {
			return con.createQuery(sql).executeAndFetch(Sala.class);
		}

	}
	
	public List<Silla> getSilla() {

		String sql = "SELECT idSilla, numero, fila, Sala_idSala " + "FROM Silla";

		try (Connection con = getConexion()) {
			return con.createQuery(sql).executeAndFetch(Silla.class);
		}

	}
	
	
	public List<Funcion> getFunciones() {

		String sql = "SELECT idFuncion, fecha, horaInicio, horaFin, Pelicula_idPelicula, Sala_idSala, ticketsDisponibles "
		+ "FROM Funcion";

		try (Connection con = getConexion()) {
			return con.createQuery(sql).executeAndFetch(Funcion.class);
		}

	}
	
	public List<Ticket> getTickets(int idFuncion) {

		String sql = "SELECT idTicket, Funcion_idFuncion, vendido, numeroSilla, sala FROM Ticket WHERE Funcion_idFuncion= :funcion";

		try (Connection con = getConexion()) {
			return con.createQuery(sql).addParameter("funcion", idFuncion).executeAndFetch(Ticket.class);
		}

	}
	
	public boolean comprarTicket(int idFuncion, String numeroTicket){
		boolean b=false;
		String update="update ticket set vendido = true where numeroSilla = :silla and Funcion_idFuncion=:fun ";
		String update2="UPDATE funcion SET ticketsDisponibles=ticketsDisponibles-1 WHERE idFuncion=:fun"; 
		try(Connection con= getConexion()){
			con.createQuery(update)
				.addParameter("silla", numeroTicket)
				.addParameter("fun", idFuncion)
				.executeUpdate();
			con.createQuery(update2)
				.addParameter("fun", idFuncion)
				.executeUpdate();
			b=true;
		}
		return b;
	}
	
	public int duracionPelicula(int idPelicula){
		String sql="SELECT duracion FROM pelicula where idPelicula= :peli";
		try(Connection c= getConexion()){
			Integer ret=(Integer)c.createQuery(sql).addParameter("peli", idPelicula).executeScalar();
			return ret.intValue();
		}
	}
	
}
