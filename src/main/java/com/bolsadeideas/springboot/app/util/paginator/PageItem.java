/*
 * Clase para representar cada una de las paginas
 */

package com.bolsadeideas.springboot.app.util.paginator;

public class PageItem {

	private int numero; //numero de paginas
	private boolean actual; //booleano para indicar si es actual o no

	//Constructor
	public PageItem(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isActual() {
		return actual;
	}

}
