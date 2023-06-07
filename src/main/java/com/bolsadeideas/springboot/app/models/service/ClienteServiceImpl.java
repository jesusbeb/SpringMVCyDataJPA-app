package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

/*
 * Anotamos con Service, una clase Service esta basada en el patron de diseño facade o fachada. Es un punto de acceso a
 * diferentes DAO o repositorios. Dentro de una clase servicio se puede operarar con diferentes clases Dao
 * Asi no se accede de manera directa a los Dao desde los controladores 
 */
@Service
public class ClienteServiceImpl implements IClienteService{

	//Inyectamos el cliente Dao
	@Autowired
	private IClienteDao clienteDao;
	
	//Movemos los Transactional de la clase Dao a la Service
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		
		return clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.delete(id);
		
	}

}
