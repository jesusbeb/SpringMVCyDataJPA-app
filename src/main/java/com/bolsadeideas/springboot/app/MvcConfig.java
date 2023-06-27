/* 
 * 
 */
package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

	//atributo log opcional
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	/*
	 * Comentamos el siguiente codigo ya que se cargaran las imagenes de forma programatica con codigo Java a
	 * traves del recurso Input Stream Resource, un String de entrada que contiene toda la informacion de la imagen
	 * en los bytes
	 */
	/*
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Sobreescribimos el metodo addResourceHandlers para agregar directorios recursos a nuestro proyecto
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//hacemos resourcePath absoluto. toUri toma el Path y le agrega el esquema "file"
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		//hacemos un debug (se muestra en consola) del resourcePath. opcional
		log.info(resourcePath);
		
		//usamos registry para registrar la nueva ruta como recurso estatico, que es la misma en ver.html
		//doble asterisco para mapear al nombre de la imagen y se pueda cargar
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations("file:/C:/Temp/uploads/"); //directorio fisico
	}
	*/
}
