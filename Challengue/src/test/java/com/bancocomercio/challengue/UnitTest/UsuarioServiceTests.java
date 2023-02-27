package com.bancocomercio.challengue.UnitTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bancocomercio.challengue.model.Usuario;
import com.bancocomercio.challengue.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTests {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void crearUsuarioTest() {
        Usuario usuario = new Usuario();
        usuario.setName("Juan");
        usuario.setLastName("Pérez");
        usuario.setCellphone("5551234567");
        usuario.setPassword("password");

        Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getName());
        assertEquals("Pérez", usuarioCreado.getLastName());
        assertEquals("5551234567", usuarioCreado.getCellphone());
    }

    @Test
    public void actualizarUsuarioTest() {
        Usuario usuario = usuarioService.obtenerUsuario(1L);
        usuario.setCellphone("5555555555");

        Usuario usuarioActualizado = usuarioService.actualizarUsuario(1L, usuario);
        assertNotNull(usuarioActualizado);
        assertEquals("5555555555", usuarioActualizado.getCellphone());
    }

    @Test
    public void eliminarUsuarioTest() {
        usuarioService.eliminarUsuario(1L);
        Usuario usuarioEliminado = usuarioService.obtenerUsuario(1L);
        assertNull(usuarioEliminado);
    }
}

