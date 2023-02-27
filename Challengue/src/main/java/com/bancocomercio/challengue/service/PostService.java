package com.bancocomercio.challengue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancocomercio.challengue.model.Post;
import com.bancocomercio.challengue.model.Usuario;
import com.bancocomercio.challengue.repository.PostRepository;
import com.bancocomercio.challengue.repository.UsuarioRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Post> listarPostsPorUsuario(Long usuarioId) {
        return postRepository.findByUsuarioId(usuarioId);
    }

    public Post crearPost(Long usuarioId, Post post) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return null;
        }
        post.setUsuario(usuario);
        post.setFechaPublicacion(LocalDateTime.now());
        post.setFechaModificacion(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post obtenerPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post actualizarPost(Long id, Post post) {
        Post postExistente = postRepository.findById(id).orElse(null);
        if (postExistente == null || !postExistente.getUsuario().equals(post.getUsuario())) {
            return null;
        }
        post.setId(id);
        post.setFechaModificacion(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void eliminarPost(Long id, Usuario usuario) {
        Post postExistente = postRepository.findById(id).orElse(null);
        if (postExistente != null && postExistente.getUsuario().equals(usuario)) {
            postRepository.deleteById(id);
        }
    }
}

