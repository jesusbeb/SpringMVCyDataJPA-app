package com.bolsadeideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;

//Marcamos y configuramos la clase como un controlador
@Controller
public class ClienteController {

	//atributo del cliente DAO para realizar la consulta
	@Autowired
	private IClienteDao clienteDao;
	
	//Metodo para listar los clientes
	//RequestMapping para validar, en value damos la ruta, method es el tipo de peticion, en este caso GET
	//si no se especifica el method, por defecto es GET, o sea que aqui se podria omitir
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	//Se importa la clase Model para pasar datos a la vista
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}
}
