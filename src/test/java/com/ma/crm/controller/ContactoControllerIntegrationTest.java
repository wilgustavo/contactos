package com.ma.crm.controller;

import static io.restassured.RestAssured.*; 
import static io.restassured.config.DecoderConfig.*;

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

import io.restassured.config.RestAssuredConfig;

/**
 * ContactoControllerIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase()
public class ContactoControllerIntegrationTest {

  @LocalServerPort
	private int port;

	private String url;
	
	@PostConstruct
	public void init() {
		url = "http://localhost:" + port;
  }

  @Test
  @Sql({"/sql/contacto.sql"}) 
  public void deberia_listar_contactos() {
    given()
      .config(RestAssuredConfig.config().decoderConfig(decoderConfig().defaultContentCharset("UTF-8")))
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .queryParam("q", "AR")
    .when()
      .get(url + "/contactos")
    .then()
      .statusCode(200)
      .body("datos[0].nombre", is("Carmen Córdova"))      
      .body("datos[1].nombre", is("Cesar Pelli"))      
      .body("datos[2].nombre", is("Marina Waisman"));      
  }

  @Test
  @Sql({"/sql/contacto.sql"}) 
  public void deberia_retornar_lista_vacia_si_no_encuentra_datos() {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .queryParam("q", "ZZZZ")
    .when()
      .get(url + "/contactos")
    .then()
      .statusCode(200)
      .body("cantidad", is(0))
      .body("datos", empty())
      .body("total", is(0));      
  }

  @Test
  @Sql({"/sql/localidad.sql", "/sql/contacto.sql"}) 
  public void deberia_consultar_un_contacto() {
    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 222)
    .when()
      .get(url + "/contactos/{id}")
    .then()
      .statusCode(200)
      .body("id", is(222));      
  }

  @Test
  @Sql({"/sql/localidad.sql", "/sql/contacto.sql"}) 
  public void deberia_crear_un_contacto() {
    given()
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .body("{\"nombre\": \"Cosme Fulanito\"," +
      " \"nacimiento\": \"1990-01-30\"," +
      " \"email\": \"cosme@email.com\"," +
      " \"telefono\": \"432-3212334\"," +
      " \"direccion\": \"Prueba 212\"," +
      " \"localidadId\": 13013 }")
    .when()
      .post(url + "/contactos")
    .then()
      .statusCode(201)
      .body("id", is(365));      

    given()
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .pathParam("id", 365)
    .when()
      .get(url + "/contactos/{id}")
    .then()
      .statusCode(200)
      .body("id", is(365))
      .body("nombre", is("Cosme Fulanito"));      
  }

}