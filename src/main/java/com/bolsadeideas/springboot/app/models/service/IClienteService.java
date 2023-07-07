/*
 * Capa Servicio o Service Layer
 * La idea es tener en la clase Service los metodos de la clase Dao. Esto es una buena práctica
 */

package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IClienteService {
	
	public List<Cliente> findAll();
	
	//Pageable retorna Page que es un iterable
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public List<Producto> findByNombre(String term);

}
