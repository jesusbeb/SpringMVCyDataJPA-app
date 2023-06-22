/*
 * Clase que calcula parametros desde donde hasta donde tiene que partir y a medida que va avanzando se va
 * corriendo hacia los siguientes registros de forma dinamica o si regresamos vaya desapareciendo las ultimas paginas
 * y vaya avanzando hacia las primeras paginas
 */

package com.bolsadeideas.springboot.app.util.paginator;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.domain.Page;

// <> Generics de java
public class PageRender<T> {
	
	private String url;
	private Page<T> page; //listado paginable de los clientes del tipo generic
	
	private int totalPaginas;
	
	private int numElementosPorPagina;
	
	private int paginaActual;
	
	private List<PageItem> paginas; //coleccion de varias paginas de la clase PageItem
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>(); //inicializamos
		
		numElementosPorPagina = page.getSize(); //page se inicializo en el controlador
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1; //ya que parte desde cero
		
		int desde, hasta;
		//si el total de paginas es menor o igual al numero de elementos por pagina, va a mostrar el paginador completo
		if(totalPaginas <= numElementosPorPagina) {
			desde = 1; //partimos desde la primer pagina
			hasta = totalPaginas; //hasta la ultima pagina
		} else {
			if(paginaActual <= numElementosPorPagina/2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if(paginaActual >= totalPaginas - numElementosPorPagina/2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {
				desde = paginaActual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
			}
		}
	
		//recorremos el hasta y empezamos llenar las paginas con cada uno de los item con su numero y si es actual o no
		for(int i=0 ; i<hasta; i++) {
			//paginas.add agrega el PageItem(pasamos el numero, si es actual o no) si paginaActual es igual desde+i, es la pagina actual
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}	
	}
	
	public String getUrl() {
		return url;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	
	//Metodos para saber si es la primera pagina o la ultima o si hay paginas adelante o atras
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
}
	
