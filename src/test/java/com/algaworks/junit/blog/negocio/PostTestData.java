package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;

import java.math.BigDecimal;

public class PostTestData {

    private PostTestData() {

    }

    public static Post.Builder umPostNovo() {
        return Post.builder()
                .comTitulo("Olá Java")
                .comConteudo("Olá mundo Java")
                .comAutor(EditorTestData.umEditorNovo().build())
                .comPago(false)
                .comPublicado(true);
    }

    public static Post.Builder umPostExistente() {
        return umPostNovo()
                .comId(1L)
                .comSlug("ola-java-1234356")
                .comGanhos(new Ganhos(BigDecimal.TEN, 400, new BigDecimal("2000")))
                .comPago(true);
    }

}
