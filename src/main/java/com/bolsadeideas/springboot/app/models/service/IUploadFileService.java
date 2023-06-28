/*
 * interface que contiene los metodos del upload de la foto
 * La interface no es obligatoria, es una buena practica. Es mejor trabajar asi con tipos abstractos
 * y tener un facil mantenimiento a futuro
 */

package com.bolsadeideas.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {

	
	public Resource load(String filename) throws MalformedURLException; //carga la imagen
	
	public String copy(MultipartFile file) throws IOException; //copia la foto original al directorio y la renombra
	
	public boolean delete(String filename); //retorna un boolean para saber si se elimino o no
}
