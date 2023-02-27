package com.bancocomercio.challengue.IntegrationTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bancocomercio.challengue.model.Usuario;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioRestControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void listarUsuariosTest() {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Juan", "Pérez", "5551234567", "password", LocalDateTime.now(), null),
                new Usuario(2L, "María", "González", "5559876543", "password", LocalDateTime.now(), null),
                new Usuario(3L, "Carlos", "Sánchez", "5555432167", "password", LocalDateTime.now(), null)
        );

        given()
            .when()
                .get("/usuarios")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", is(3))
                .body("id", containsInAnyOrder(1, 2, 3))
                .body("name", containsInAnyOrder("Juan", "María", "Carlos"))
                .body("lastName", containsInAnyOrder("Pérez", "González", "Sánchez"))
                .body("cellphone", containsInAnyOrder("5551234567", "5559876543", "5555432167"));
    }

    @Test
    public void crearUsuarioTest() {
        Usuario usuario = new Usuario();
        usuario.setName("Juan");
        usuario.setLastName("Pérez");
        usuario.setCellphone("5551234567");
        usuario.setPassword("password");

        ResponseEntity<Usuario> respuesta = restTemplate.postForEntity("/usuarios", usuario, Usuario.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Juan", respuesta.getBody().getName());
        assertEquals("Pérez", respuesta.getBody().getLastName());
        assertEquals("5551234567", respuesta.getBody().getCellphone());
    }

    @Test
    public void obtenerUsuarioTest() {
        Usuario usuario = restTemplate.getForObject("/usuarios/1", Usuario.class);
        assertEquals("Juan", usuario.getName());
        assertEquals("Pérez", usuario.getLastName());
        assertEquals("5551234567", usuario.getCellphone());
    }

    @Test
    public void actualizarUsuarioTest() {
        Usuario usuario = restTemplate.getForObject("/usuarios/1", Usuario.class);
        usuario.setName("Juan Carlos");
        usuario.setLastName("Pérez González");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Usuario> entity = new HttpEntity<>(usuario, headers);

        ResponseEntity<Usuario> respuesta = restTemplate.exchange("/usuarios/1", HttpMethod.PUT, entity, Usuario.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Juan Carlos", respuesta.getBody().getName());
        assertEquals("Pérez González", respuesta.getBody().getLastName());
    }

    @Test
    public void eliminarUsuarioTest() {
        restTemplate.delete("/usuarios/1");
        ResponseEntity<Usuario> respuesta = restTemplate.getForEntity("/usuarios/1", Usuario.class);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

}


