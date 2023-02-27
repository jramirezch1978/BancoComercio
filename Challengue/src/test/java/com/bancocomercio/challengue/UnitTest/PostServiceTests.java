package com.bancocomercio.challengue.UnitTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bancocomercio.challengue.model.Post;
import com.bancocomercio.challengue.model.Usuario;
import com.bancocomercio.challengue.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @Test
    public void crearPostTest() {
        Post post = new Post();
        post.setText("Este es un nuevo post.");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        post.setUsuario(usuario);

        Post postCreado = postService.crearPost(1L, post);
        assertNotNull(postCreado);
        assertEquals("Este es un nuevo post.", postCreado.getText());
    }

    @Test
    public void actualizarPostTest() {
        Post post = postService.obtenerPost(1L);
        post.setText("Este post ha sido actualizado.");

        Post postActualizado = postService.actualizarPost(1L, post);
        assertNotNull(postActualizado);
        assertEquals("Este post ha sido actualizado.", postActualizado.getText());
    }

    @Test
    public void eliminarPostTest() {
        postService.eliminarPost(1L, postService.obtenerPost(1L).getUsuario());
        Post postEliminado = postService.obtenerPost(1L);
        assertNull(postEliminado);
    }
}

