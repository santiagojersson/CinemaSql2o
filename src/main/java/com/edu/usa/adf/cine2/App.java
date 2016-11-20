package com.edu.usa.adf.cine2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.usa.adf.Consult.Consultas;
import com.edu.usa.adf.Modelo.Funcion;
import com.edu.usa.adf.Modelo.Pelicula;
import com.edu.usa.adf.Modelo.Sala;
import com.edu.usa.adf.Modelo.Silla;
import com.edu.usa.adf.Modelo.Ticket;

/**
 * Hello world!
 *
 */
public class App {
	
	private Consultas controller = new Consultas(); 
	private BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
	
	public void start() {
		try {
			int opc=0;
			System.out.println("Bienvenido Digite su accion a realizar");
			while (opc!=5) {
				System.out.println("1. Crear Sala\n2. Crear Pelicula\n Funcion\n\t3. Crear Funcion\n\t4. Comprar Ticket\n5. Exit");
				opc=Integer.parseInt(bf.readLine());	
				switch (opc) {
				case 1: crearSala(); break;
				case 2: crearPelicula(); break;
				case 3: crearFuncion(); break;
				case 4: comprarTicket(); break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void crearSala() {
		try {
			System.out.println("Ingrese la Capacidad Sala");
			int capacidadSala= Integer.parseInt(bf.readLine());
			System.out.println("Digite la Cantiada de Filas");
			int cantidadFilas=Integer.parseInt(bf.readLine());
			if (controller.insertSala(capacidadSala, cantidadFilas)) {
				System.out.println("\033[32mSala Creada con exito\033[0m");
			}else{
				System.out.println("Digita bien los datos por favor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void crearPelicula() {
		try {
			Pelicula peli= new Pelicula();
			System.out.println("Digite Nombre de la pelicula");
			peli.setNombre(bf.readLine());
			System.out.println("Digite Genero de la pelicula");
			peli.setGenero(bf.readLine());
			System.out.println("Digite duracion de la pelicula en minutos");
			peli.setDuracion(Integer.parseInt(bf.readLine()));
			if (controller.insertarPelicula(peli)) {
				System.out.println("\033[32mPelicula Creada con exito\033[0m");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void crearFuncion() {
		try {
			System.out.println("----- Peliculas -------");
			for (Pelicula peli : controller.getPeliculas()) {
				System.out.println(peli.toString());
			}
			System.out.println("Digite el ID de la pelicula");
			
			Funcion funcion= new Funcion();
			funcion.setPelicula_idPelicula(Integer.parseInt(bf.readLine()));
			System.out.println("Digite la fecha de la funcion ( AAAA/MM/DD)");
			funcion.setFecha(convertirFecha(bf.readLine()));
			System.out.println("Ingrese la hora de la funcion  HH:MM");
			funcion.setHoraInicio(sumarHorasFecha(funcion.getFecha(), bf.readLine()));
			
			int duracionPeli=controller.duracionPelicula(funcion.getPelicula_idPelicula());
			int horaFinal= duracionPeli/60;
			int minutoFinal= duracionPeli-(horaFinal*60);
			funcion.setHoraFin(sumarHorasFecha(funcion.getHoraInicio(), horaFinal+":"+minutoFinal));
			
			System.out.println("----- Sala -------");
			for (Sala salita: controller.getSala()) {
				System.out.println(salita.toString());
			}
			
			System.out.println("Digite la Sala Id donde desea proyectar la funcion");
			funcion.setSala_idSala(Integer.parseInt(bf.readLine()));
					
			if (controller.insertarFuncion(funcion)) {
				System.out.println("\033[32mFuncion Creada con exito\033[0m");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void comprarTicket() {
		try {
			List<Funcion> listaFunciones=controller.getFunciones();
			System.out.println("------ Funciones -------");
			for (Funcion fun : listaFunciones) {
				System.out.println(fun.toString());
			}
			
			System.out.println("Digite el Id de la funcion que desea ver");
			int idFuncion= Integer.parseInt(bf.readLine());
			Funcion fun= buscarFuncion(listaFunciones,idFuncion);
			Sala salita= buscarSala(fun.getSala_idSala());
		
			System.out.println("------ Tickets -------");
			mostrarTickets(fun,salita);
			System.out.println("Digite El numero del ticket que desea comprar");
			String numeroTicket= bf.readLine();
			
			if (controller.comprarTicket(idFuncion, numeroTicket)) {
				System.out.println("\033[32mTicket Comprado con exito\033[0m");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void mostrarTickets(Funcion fun, Sala salita) {
		int sillasFila = salita.getCapacidad() / salita.getCantidadFilas();
		int sillasFaltantes = salita.getCapacidad() - (sillasFila * salita.getCantidadFilas());
		List<Ticket> listaTicket= controller.getTickets(fun.getIdFuncion());
		int cont=0;
		for (int i = 1; i <= salita.getCantidadFilas(); i++) {
			for (int j = 1; j <=sillasFila ; j++) {
				Ticket tick=listaTicket.get(cont);
				if (tick.isVendido()) {
					System.out.print("\033[1;31m"+tick.getNumeroSilla()+" "+"\033[0m");
				}else{
					System.out.print("\033[1;32m"+tick.getNumeroSilla()+" "+"\033[0m");
				}
				cont++;
			}
			System.out.println();
		}
		if (sillasFaltantes != 0) {
			for (int i = 1; i <= sillasFaltantes; i++) {
				Ticket tick=listaTicket.get(cont);
				if (tick.isVendido()) {
					System.out.print("\033[1;31m"+tick.getNumeroSilla()+" "+"\033[0m");
				}else{
					System.out.print("\033[1;32m"+tick.getNumeroSilla()+" "+"\033[0m");
				}
			}
		}
		
	}

	private Sala buscarSala(int sala_idSala) {
		Sala sala= null;
		for (Sala salita : controller.getSala()) {
			if (salita.getIdSala()==sala_idSala) {
				return salita;
			}
		}
		return sala;
	}

	private Funcion buscarFuncion(List<Funcion> listaFunciones, int idFuncion) {
		Funcion f= null;
		for (Funcion funcion : listaFunciones) {
			if (funcion.getIdFuncion()==idFuncion) {
				return funcion;
			}
		}
		return f;
	}

	public Date convertirFecha(String date) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd");

		Date fecha = null;
		try {

			return fecha = formatoDelTexto.parse(date);

		} catch (ParseException ex) {

			ex.printStackTrace();

		}
		return fecha;
	}

	public Date sumarHorasFecha(Date fecha, String hora) {

		Calendar calendar = Calendar.getInstance();
		String[] hour= hora.split(":");
		calendar.setTime(fecha); // Configuramos la fecha que se recibe

		calendar.add(Calendar.HOUR, Integer.parseInt(hour[0]));// numero de horas a añadir, o
											// restar en caso de horas<0
        calendar.add(Calendar.MINUTE, Integer.parseInt(hour[1]));
		return calendar.getTime(); // Devuelve el objeto Date con las nuevas
									// horas añadidas

	}
}
