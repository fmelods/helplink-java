package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM")
    private Long idItem;

    @NotBlank(message = "Título é obrigatório")
    @Column(name = "TITULO", nullable = false, length = 100)
    private String titulo;

    @Column(name = "FOTO_URL", length = 250)
    private String fotoUrl;

    @Column(name = "ESTADO_CONSERVACAO", length = 50)
    private String estadoConservacao;

    @Column(name = "DESCRICAO", length = 250)
    private String descricao;

    @Column(name = "DT_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime dtRegistro = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    // 🔥 REMOVIDO — não existe mais ligação Item → Doacao
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "ID_DOACAO")
    // private Doacao doacao;

    // 🔥 REMOVIDO — evitava o erro de mappedBy, pois 'item' não existe mais em DoacaoItem
    // @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    // private List<DoacaoItem> doacaoItens = new ArrayList<>();


    // ================================
    // CONSTRUTORES
    // ================================

    public Item() {}

    public Item(Long idItem, String titulo, String fotoUrl, String estadoConservacao,
                String descricao, LocalDateTime dtRegistro, Usuario usuario,
                Categoria categoria) {

        this.idItem = idItem;
        this.titulo = titulo;
        this.fotoUrl = fotoUrl;
        this.estadoConservacao = estadoConservacao;
        this.descricao = descricao;
        this.dtRegistro = dtRegistro;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    // ================================
    // GETTERS & SETTERS
    // ================================

    public Long getIdItem() { return idItem; }
    public void setIdItem(Long idItem) { this.idItem = idItem; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getEstadoConservacao() { return estadoConservacao; }
    public void setEstadoConservacao(String estadoConservacao) { this.estadoConservacao = estadoConservacao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDtRegistro() { return dtRegistro; }
    public void setDtRegistro(LocalDateTime dtRegistro) { this.dtRegistro = dtRegistro; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // ================================
    // EQUALS, HASHCODE, TO STRING
    // ================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(idItem, item.idItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem);
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", titulo='" + titulo + '\'' +
                ", estadoConservacao='" + estadoConservacao + '\'' +
                '}';
    }
}
