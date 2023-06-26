package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;


//Marcamos y configuramos la clase como un controlador
@Controller
//SessionAttributes indica que se guarda en los atributos de la sesion el objeto cliente mapeado al formulario. Cada que se invoque el metodo crear o editar
//obtendra el objeto cliente y lo guarda en los atributos de la sesion, lo pasa a la vista y ahi queda en la sesion el objeto con el id, hasta que se envie al metodo guardar
//entonces se tendra que eliminar la sesion en el metodo Editar con SessionStatus
@SessionAttributes("cliente") 
public class ClienteController {

	//atributo del cliente DAO para realizar la consulta
	@Autowired
	//private IClienteDao clienteDao; //en lugar de inyectar de manera directa el clienteDao, inyectamos el clienteService
	private IClienteService clienteService;
	
	//atributo logger para hacer un debug de los nombres de directorio y los muestre en la consola
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	/* Se elimino el codigo en MvcConfig para crear este metodo handler que se encarga de recibir una imagen
	 * como parametro, como path variable, la convierte a un recurso (inputStream) y la carga en la respuesta http
	 * como un ResponseEntiy
	 */
	//Metodo del tipo get con una url, El punto y mas permite que Spring no borre o trunque la extension del archivo (.jpg, png, etc.)
	@GetMapping(value="/uploads/{filename:.+}")
	//El retorno es un ResponseEntity del tipo generico Resource. Como argumento al metodo se pasa el Path Variable, tipo y nombre 
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		//convertimos el filename en un path absoluto para poder cargar la imagen
		//Paths.get obtiene la primera parte del path, en este caso es la carpeta "uploads"
		//.resolv concatena otro path al path principal, este otro path es el nombre del archivo (agrega la diagonal)
		//.toAbsolutePath convierte todo a un path absoluto que incluye la ruta completa desde la raiz
		Path pathFoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
		log.info("pathFoto: " + pathFoto); //nos muestra en la consola el pathFoto
		
		//creamos el recurso nulo
		Resource recurso = null;
				
		try {
			//inicializamos el recurso. Nos pide crear una excepcion por url mal formada
			recurso = new UrlResource(pathFoto.toUri());
			//Si el recurso no existe y tamposo se puede leer
			if(!recurso.exists() && !recurso.isReadable()) {
				//lanzamos una excepcion
				throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//retornamos la respuesta
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +  recurso.getFilename() +"\"")
				.body(recurso);		
	}
	

	
	
	
	/////Metodo para ver el detalle y la foto del cliente
	@GetMapping(value="/ver/{id}")
	//Pasamos por argumento el PathVariable con el valor del id que es de tipo Long y nombre id
	//Pasamos por argumento Map para pasar datos a la vista
	//Pasamos por argumento RedirectAttributes para mensajes de flash
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		//obtenemos el cliente por id
		Cliente cliente = clienteService.findOne(id);
		//si es null mostramos mensaje flash y redirigimos al listar
		if(cliente==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD :(");
			return "redirect:/listar";
		}
		
		//si no es null pasamos el cliente a la vista junto con la foto
		model.put("cliente", cliente);
		//pasamos el titulo
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		
		//Pasamos el nombre de la vista
		return "ver";
	}
	//////Fin del metodo para ver el detalle
	
	
	
	//Metodo para listar los clientes
	//RequestMapping para validar, en value damos la ruta, method es el tipo de peticion, en este caso GET
	//si no se especifica el method, por defecto es GET, o sea que aqui se podria omitir
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	//Se importa la clase Model para pasar datos a la vista
	//RequestParam obtiene la pagina actual mediante la ruta url. int page es el tipo y nombre del parametro
	//name y defaultValue es cero porque estamos en la primera pagina
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		//creamos el objeto Pageable de forma estatica sin usar "new"
		//en el metodo of se indica la cantidad de elementos por pagina que se quieren mostrar
		Pageable pageRequest = PageRequest.of(page, 5);
		
		//invocamos el findAll
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		//Creamos el objeto PageRender y pasamos la url y pasamos la lista paginable
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes); 
		model.addAttribute("page", pageRender);
		return "listar";
	}
	
	
	
	//Metodo Request del tipo get, el segundo parametro que seria .GET lo omitimos que por defecto lo tiene
	@RequestMapping(value="/form")
	//Pasamos un mapa de java, nombre del parametro es del tipo String y el objeto que se guarda es un Object (objeto generico)
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente); //pasamos el objeto cliente a la vista
		model.put("titulo", "Crear Cliente");
		return "form";
	}
	
	
	
	@RequestMapping(value="/form/{id}")
	//Pasamos el id como argumento con PathVariable, el tipo de dato y la variable
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		//validamos que el id exista
		if(id>0) {
			//Buscamos en la base de datos usando el findOne de la clase clienteDao
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BD :(");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero :/");
			//si cliente es igual a cero, redirige a /listar
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";
	}
	
	
	
	//Metodo que procesa los datos del form. Tipo de metodo es POST
	@RequestMapping(value="/form", method=RequestMethod.POST)
	//Este metodo recibe el objeto cliente del formulario @Valid como argumento habilita la validacion
	//BindingResult siempre va enseguida del objeto del formulario
	//Agreganis RedirectAtributes flash despues de model, para mostrar los mensajes flash
	//RequestParam con el nombre del parametro "file" con el tipo MultipartFile y le damos nombre "foto"
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		
		//si el BindingResult contiene errores
		if(result.hasErrors()) {
			//no se pasa el objeto cliente aqui, ya que se pasa automaticamente cuando falla, siempre y cuando el
			//nombre de la clase se llame igual con el nombre que se pasa a la vista
			//volvemos a pasar el titulo
			model.addAttribute("titulo", "Formulario de Cliente");
			//volvemos a mostrar el formulario ahora con los mensajes de error para ser corregidos por el usuario
			return "form";
		}
		
		//si foto no esta vacio
		if(!foto.isEmpty()) {
			//el nombre del archivo (uniqueFileName) es igual a la clase Universaly Unique Identify, con un identificador
			//unico random, lo pasamos a string y le concatenamos _ mas el nombre original del archivo
			String uniqueFileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
			//creamos un path y le damos la ruta, la carpeta "uploads" estara en la raiz del proyecto (la creamos manualmente, al mismo nivel que "src" y "target")
			//se concantena el nombre del archivo usando resolve que se encarga de forma automatica anexar el nombre original del archivo
			Path rootPath = Paths.get("uploads").resolve(uniqueFileName);
			
			//Agregamos la ruta absoluta al directorio
			Path rootAbsolutePath = rootPath.toAbsolutePath();
			
			//Debug de los nombres de directorio. Esto se muestra en la consola en tiempo de ejecucion
			log.info("rootPath: " + rootPath); //Path relativo al proyecto
			log.info("rootAbsolutePath: " + rootAbsolutePath); //Path absoluto
			
			try {
				//el metodo copy obtiene el inputStream de la foto para copiar el archivo al nuevo directorio (ruta absoluta)
				Files.copy(foto.getInputStream(), rootAbsolutePath);
				
				flash.addFlashAttribute("info", "Has subido correctamente '" + foto.getOriginalFilename()+ "' :)");
				
				//Pasamos el nombre de la foto al cliente
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Validamos para saber que mensaje mostrar. Si el id es distinto de null es un cliente editado, si el id es null es un cliente nuevo
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con exito :)" : "Cliente creado con exito :)";
		
		clienteService.save(cliente);
		//Despues de guardar cerramos la sesion
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	
	
	@RequestMapping(value="/eliminar/{id}")
	//Como argumento recibe el PathVariable con el id y el tipo de dato
	//RedirectAttributes flash para mostrar mensajes flash
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if(id > 0) { ////Si el id es mayor que cero
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con exito");
		}
		return "redirect:/listar";
	}
	
}
