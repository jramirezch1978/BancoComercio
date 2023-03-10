package com.bancocomercio.challengue.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cellphone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Post> posts;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime fechaUltimaModificacion;

    public Usuario() {}

    public Usuario(String cellphone, String name, String lastName, String password) {
        this.cellphone = cellphone;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.posts = new ArrayList<>();
        this.fechaAlta = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

	public Usuario(long Id, String cellphone, String name, String lastName, String password, LocalDateTime now,
			Object object) {
		this.cellphone = cellphone;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.posts = new ArrayList<>();
        this.fechaAlta = now;
        this.fechaModificacion = LocalDateTime.now();
	}

    // getters y setters
}

