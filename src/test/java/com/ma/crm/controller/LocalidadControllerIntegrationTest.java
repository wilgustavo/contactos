package com.ma.crm.controller;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*; 
import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * LocalidadRepositoryIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase()
public class LocalidadControllerIntegrationTest {

  @LocalServerPort
	private int port;

	private String url;
	
	@PostConstruct
	public void init() {
		url = "http://localhost:" + port;
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_retornar_una_lista_de_localidades() throws Exception {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .queryParam("q", "COR")
    .when()
      .get(url + "/localidades")
    .then()
      .statusCode(200)
      .body("cantidad", is(2))
      .body("datos[0].nombre", is("CORONEL PRINGLES"))
      .body("datos[1].nombre", is("CORONEL SUAREZ"))
      .body("total", is(2));      
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_retornar_una_lista_vacia_si_no_ecuentra_localidades() throws Exception {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .queryParam("q", "ZZZ")
    .when()
      .get(url + "/localidades")
    .then()
      .statusCode(200)
      .body("cantidad", is(0))
      .body("datos", empty())
      .body("total", is(0));      
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_consultar_una_localidad() throws Exception {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 12909)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(200)
      .body("id", is(12909))
      .body("nombre", is("MUNRO"))
      .body("codigoPostal", is("4187"))
      .body("provincia", is("Bs.As."));      
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_dar_error_al_consultar_una_localidad_que_no_existe() throws Exception {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 2909)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(404)
      .body("mensaje", is("Localidad 2909 no existe"));      
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_crearse_una_localidad() {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 14411)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(404);      

    given()
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .body("{ \"nombre\": \"RAWSON\", " +
             " \"codigoPostal\": \"3290\", " +
             " \"provincia\": \"Chubut\" }")
    .when()
      .post(url + "/localidades")
    .then()
      .statusCode(201)
      .body("id", is(14411))
      .body("nombre", is("RAWSON"))
      .body("codigoPostal", is("3290"))
      .body("provincia", is("Chubut"));

    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 14411)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(200)
      .body("id", is(14411))
      .body("nombre", is("RAWSON"))
      .body("codigoPostal", is("3290"))
      .body("provincia", is("Chubut"));
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_dar_error_al_crear_localidad_no_valida() {
    given()
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .body("{ \"codigoPostal\": \"3290\", " +
             " \"provincia\": \"Chubut\" }")
    .when()
      .post(url + "/localidades")
    .then()
      .statusCode(400)
      .body("mensaje", is("Errores de validación"))
      .body("errores[0].campo", is("nombre"))  
      .body("errores[0].texto", is("no puede estar vacío"));  
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_guardar_una_localidad() {
    given() 
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 13503)
      .body("{ \"nombre\": \"Miramar\", " +
             " \"codigoPostal\": \"1447\", " +
             " \"provincia\": \"Buenos Aires\" }")
    .when()
      .put(url + "/localidades/{id}")
    .then()
      .statusCode(200)
      .body("id", is(13503))
      .body("nombre", is("Miramar"))
      .body("codigoPostal", is("1447"))
      .body("provincia", is("Buenos Aires"));  

    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 13503)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(200)
      .body("id", is(13503))
      .body("nombre", is("Miramar"))
      .body("codigoPostal", is("1447"))
      .body("provincia", is("Buenos Aires"));  
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_dar_error_al_guardar_una_localidad_que_no_existe() {
    given() 
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 13000)
      .body("{ \"nombre\": \"Miramar\", " +
             " \"codigoPostal\": \"1447\", " +
             " \"provincia\": \"Buenos Aires\" }")
    .when()
      .put(url + "/localidades/{id}")
    .then()
      .statusCode(404)
      .body("mensaje", is("Localidad 13000 no existe"));  
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_dar_error_al_guardar_una_localidad_no_valida() {
    given() 
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 13503)
      .body("{ \"nombre\": \"Miramar\", " +
             " \"provincia\": \"Buenos Aires\" }")
    .when()
      .put(url + "/localidades/{id}")
    .then()
      .statusCode(400)  
      .body("mensaje", is("Errores de validación"))
      .body("errores[0].campo", is("codigoPostal"))  
      .body("errores[0].texto", is("no puede estar vacío"));  
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void deberia_borrar_una_localidad() {
    long id = 13503;
    given() 
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", id)
    .when()
      .delete(url + "/localidades/{id}")
    .then()
      .statusCode(204);

    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", id)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(404);      
  }

  @Test
  @Sql("/sql/localidad.sql") 
  public void no_deberia_dar_error_al_borrar_una_localidad_que_no_existe() {
    long id = 13993;
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", id)
    .when()
      .get(url + "/localidades/{id}")
    .then()
      .statusCode(404);      

    given() 
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", id)
    .when()
      .delete(url + "/localidades/{id}")
    .then()
      .statusCode(204);
  }

}