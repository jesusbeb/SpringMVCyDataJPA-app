/*
 * Clase service que implementa la interface y se encarga de administrar toda la logica del upload de la foto
 * Se extrajo codigo del controlador se acomodo mas limpio y se inyecta nuevamente al controlador
 * De esta manera queda mas limpio el controlador y se encarga de lo que debe (manejar peticiones web de los usuarios
 * comunicarse con las clases de logica de negocios: daos, services, pasar datos a la vista)
 */

package com.bolsadeideas.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	// atributo logger para hacer un debug de los nombres de directorio y los
	// muestre en la consola
	private final Logger log = LoggerFactory.getLogger(getClass());

	// uploads es una carpeta que se tiene que crear manualmente en la raiz del
	// proyecto al mismo nivel que "src" y "target"
	private final static String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException { // manejamos excepcion en el lanzamiento del
																			// metodo. Lo declaramos

		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto); // nos muestra en la consola el pathFoto

		Resource recurso = new UrlResource(pathFoto.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// uniqueFileName contiene el nombre del archivo, el cual tiene un identificador
		// unico random, creado
		// por la clase Universaly Unique Identify, se convierte a String, se le
		// concatena _ y finalmente
		// se le concatena su nombre original. Esto es para que fotos con el mismo
		// nombre, no se reemplazen
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		// Se obtiene el path absoluto
		Path rootPath = getPath(uniqueFileName);

		// Debug de los nombres de directorio. Esto se muestra en la consola en tiempo
		// de ejecucion
		log.info("rootPath: " + rootPath); // Path relativo al proyecto
		
		Files.copy(file.getInputStream(), rootPath);

		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		// obtenemos la ruta absoluta de la foto
		Path rootPath = getPath(filename);
		// obtenemos el file, de esta manera ya podemos eliminar
		File archivo = rootPath.toFile();

		// validamos si el archivo existe y se pueda leer
		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	
	
	// metodo que crea la ruta absoluta. Como este codigo se repetia mucho, se
	// implementa en un metodo para solo llamarlo
	// El metodo recibe el nombre del archivo
	public Path getPath(String filename) {
		// Paths.get obtiene la primera parte del path, en este caso es la carpeta
		// "uploads" almacenada en la variable UPLOADS_FOLDER
		// .resolv concatena otro path al path principal, este otro path es el nombre
		// del archivo (agrega la diagonal)
		// .toAbsolutePath convierte todo a un path absoluto que incluye la ruta
		// completa desde la raiz, para poder cargar la imagen
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	//Borra la carpeta uploads
	@Override
	public void deleteAll() {
		//Usamos la clase FileSystem... con su metodo deleteRecursively y le pasamos la ruta a eliminar
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	//Crea la carpeta uploads
	@Override
	public void init() throws IOException {
		//Usamos la clase Files con su metodo createDirectories y le pasamos la ruta y carpeta a crear
		Files.createDirectories(Paths.get(UPLOADS_FOLDER));
	}

}
