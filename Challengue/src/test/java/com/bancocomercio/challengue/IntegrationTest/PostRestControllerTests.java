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

import com.bancocomercio.challengue.model.Post;
import com.bancocomercio.challengue.model.Usuario;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostRestControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void listarPostsTest() {
        List<Post> posts = Arrays.asList(
                new Post(1L, "Este es un post.", LocalDateTime.now(), LocalDateTime.now(), new Usuario(1L, "Juan", "Pérez", "5551234567", "password", LocalDateTime.now(), null)),
                new Post(2L, "Este es otro post.", LocalDateTime.now(), LocalDateTime.now(), new Usuario(1L, "Juan", "Pérez", "5551234567", "password", LocalDateTime.now(), null))
        );

        given()
            .when()
                .get("/usuarios/1/posts")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", is(2))
                .body("id", containsInAnyOrder(1, 2))
                .body("text", containsInAnyOrder("Este es un post.", "Este es otro post."));
    }

    @Test
    public void crearPostTest() {
        Post post = new Post();
        post.setText("Este es un nuevo post.");

        ResponseEntity<Post> respuesta = restTemplate.postForEntity("/usuarios/1/posts", post, Post.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Este es un nuevo post.", respuesta.getBody().getText());
    }

    @Test
    public void actualizarPostTest() {
        Post post = restTemplate.getForObject("/posts/1", Post.class);
        post.setText("Este post ha sido actualizado.");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Post> entity = new HttpEntity<>(post, headers);

        ResponseEntity<Post> respuesta = restTemplate.exchange("/usuarios/1/posts/1", HttpMethod.PUT, entity, Post.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Este post ha sido actualizado.", respuesta.getBody().getText());
    }

    @Test
    public void eliminarPostTest() {
        restTemplate.delete("/usuarios/1/posts/1");
        ResponseEntity<Post> respuesta = restTemplate.getForEntity("/posts/1", Post.class);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

}

