package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroPostTest {

    @Spy
    Editor editor = new Editor(null, "Rafael", "rafael@email.com", BigDecimal.TEN, true);

    @Mock
    ArmazenamentoPost armazenamentoPost;

    @Mock
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    @Captor
    ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

    @Nested
    class Cadastro {

        @Spy
        Post post = new Post("Ecossistema java", "Olá mundo Java", editor, true, true);

        @Test
        void Dado_um_post_valido_Quando_criar_Entao_deve_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .thenAnswer(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
        }

        @Test
        void Dado_um_post_valido_Quando_criar_Entao_deve_enviar_uma_notificacao() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .thenAnswer(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            Mockito.verify(gerenciadorNotificacao).enviar(notificacaoArgumentCaptor.capture());

            Notificacao notificacao = notificacaoArgumentCaptor.getValue();

            assertEquals("Novo post criado -> " + post.getTitulo(), notificacao.getConteudo());
        }

        @Test
        void Dado_um_post_valido_Quando_criar_Entao_deve_enviar_notificaçao_apos_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .thenAnswer(invocacao ->  invocacao.getArgument(0, Post.class));

            cadastroPost.criar(post);

            InOrder order = Mockito.inOrder(armazenamentoPost, gerenciadorNotificacao);
            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            Mockito.verify(gerenciadorNotificacao, Mockito.times(1)).enviar(Mockito.any(Notificacao.class));

        }

        @Test
        void Dado_um_post_null_Quando_criar_Entao_deve_lancar_exception() {
            assertThrows(NullPointerException.class, () -> cadastroPost.criar(null));
            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any());
            Mockito.verify(gerenciadorNotificacao, Mockito.never()).enviar(Mockito.any());
        }
    }


}