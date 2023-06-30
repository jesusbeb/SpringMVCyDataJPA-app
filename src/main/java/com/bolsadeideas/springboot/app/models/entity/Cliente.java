package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//Entity nos sirve para anotar que una clase pojo que esta mapeada a una tabla es una entidad de JPA
//@Table es opcional, si lo omitimos es porque queremos que la clase Entiy se llame igual que la tabla en la BD
//En la anotacion @Table le damos el nombre de la tabla de la BD. Por convencion las tablas en MySQL son en minusculas y plural
//a diferencia de los nombres de las clases en Java que empiezan en mayuscula y singular
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	//@Id Indica que es la llave primaria
	//@GeneratedValue indica la estrategia en como se genera la llave el motor de la BD (autoincrementable, identity, sequence, etc.)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//Los atributos de una clase anotada como Entity se van a mapear automaticamente con los campos de la BD llamados exactamen igual
	//NotEmpty validacion solo para String
	@NotEmpty 
	@Size(min=2, max=30) //valida que numero de caracteres debe haber
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email //validacion para email
	private String email;

	//@Column sirve para indicar un nombre de campo distinto al atributo de la clase
	//en java los nombres compuestos se usa Camel Case, en Base de datos se separa con guion bajos los nombres compuestos
	@Column(name = "create_at")
	//@Temporal indica el formato en que se guardara la fecha. DATE guarda solo la fecha sin horas ni minutos
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd") //patron de fecha que se usara
	@NotNull //validacion para objeto diferente de String
	private Date createAt;
	
	//Un cliente puede tener muchas facturas por lo tanto se usa un List
	//mappedBy se le asigna el atributo de la otra clase a la que se relaciona, hace que sea bidireccional y
	//de forma automatica crea la llave foranea cliente id en la tabla facturas para relacionar ambas tablas en la BD
	//fetch es carga perezosa
	//CascadeType.ALL todas las consultas se haran en cadena, en cascada. Cascada muestra o elimina con sus elementos hijos
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Factura> facturas;
	
	//Constructor que inicializa el ArrayList
	public Cliente() {
		facturas = new ArrayList<Factura>();
	}



	private String foto;

	//atributo que se genera al heredar de Serializable. En JPA serializamos cuando guardamos un objeto en 
	//la sesion http es recomendable siempre implementar serializable para las clases Entity con JPA
	private static final long serialVersionUID = 1L;
	
	/* Se agregara ahora por formulario
	//PrePersist es para que se ejecute antes de que se guarde en la BD. Aqui asignamos la fecha
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	*/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	//Recibe una factura y la agrega al List una por una
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}
	
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
