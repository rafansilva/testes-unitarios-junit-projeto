package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

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
    final class Cadastrar {

        @Spy
        Post post = new Post("Olá Java", "Olá mundo Java", editor, true, true);

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
                Post postEnviado = invocacao.getArgument(0, Post.class);
                postEnviado.setId(1L);
                return postEnviado;
            });

            cadastroPost.criar(post);

            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_retornar_id_valido() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
                Post postEnviado = invocacao.getArgument(0, Post.class);
                postEnviado.setId(1L);
                return postEnviado;
            });

            Post postSalvo = cadastroPost.criar(post);

            assertEquals(1L, postSalvo.getId());

        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_retornar_post_com_ganhos() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
                Post postEnviado = invocacao.getArgument(0, Post.class);
                postEnviado.setId(1L);
                return postEnviado;
            });

            Mockito.when(calculadoraGanhos.calcular(post)).thenReturn(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(40)));

            Post postSalvo = cadastroPost.criar(post);

            Mockito.verify(post, Mockito.times(1)).setGanhos(Mockito.any(Ganhos.class));
            assertNotNull(postSalvo.getGanhos());
        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_retorna_post_com_slug() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
                Post postEnviado = invocacao.getArgument(0, Post.class);
                postEnviado.setId(1L);
                return postEnviado;
            });

            Post postSalvo = cadastroPost.criar(post);

            Mockito.verify(postSalvo, Mockito.times(1)).setSlug(Mockito.anyString());
            assertNotNull(postSalvo.getSlug());
        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_calcular_ganhos_antes_de_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
                Post postEnviado = invocacao.getArgument(0, Post.class);
                postEnviado.setId(1L);
                return postEnviado;
            });

            cadastroPost.criar(post);

            InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
            inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
        }

//        @Test
//        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_gerar_slug_e_salvar() {
//            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
//                    .thenAnswer(invocacao -> {
//                        Post postEnviado = invocacao.getArgument(0, Post.class);
//                        postEnviado.setId(1L);
//                        return postEnviado;
//                    });
//
//            try (MockedStatic<ConversorSlug> conversorSlug = Mockito.mockStatic(ConversorSlug.class)) {
//                cadastroPost.criar(post);
//
//                InOrder inOrder = Mockito.inOrder(armazenamentoPost, ConversorSlug.class);
//                inOrder.verify(conversorSlug, () -> ConversorSlug.converterJuntoComCodigo(post.getTitulo()), Mockito.times(1));
//                inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
//            }
//        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_enviar_notificacao_apos_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
                Post postEnviado = invocacao.getArgument(0, Post.class);
                postEnviado.setId(1L);
                return postEnviado;
            });

            cadastroPost.criar(post);

            InOrder inOrder = Mockito.inOrder(armazenamentoPost, gerenciadorNotificacao);
            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            inOrder.verify(gerenciadorNotificacao, Mockito.times(1)).enviar(Mockito.any(Notificacao.class));
        }

        @Test
        void Dado_um_post_valido__Quando_cadastrar__Entao_deve_enviar_notificacao_com_titulo_do_post() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).thenAnswer(invocacao -> {
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
        void Dado_um_post_null__Quando_cadastrar__Entao_deve_lancar_exception_e_nao_deve_salvar() {
            assertThrows(NullPointerException.class, () -> cadastroPost.criar(null));
            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any());
            Mockito.verify(gerenciadorNotificacao, Mockito.never()).enviar(Mockito.any());
        }
    }

    @Nested
    final class Editar {

        Ganhos ganhos = new Ganhos(BigDecimal.TEN, 400, new BigDecimal("2000"));

        @Spy
        Post post = new Post(1L, "Olá Java", "Olá mundo Java", editor, "ola-java-1234356", ganhos, true, true);

        @Test
        void Dado_um_post_valido__Quando_editar__Entao_deve_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            cadastroPost.editar(post);

            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
        }

        @Test
        void Dado_um_post_valido__Quando_editar__Entao_deve_alterar_post_salvo() {
            Post postAlterado = new Post(1L, "Olá Java", "Olá Java", editor, "ola-java", new Ganhos(BigDecimal.TEN, 4, BigDecimal.TEN), true, true);

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            cadastroPost.editar(postAlterado);

            Mockito.verify(post, Mockito.times(1)).atualizarComDados(postAlterado);

            InOrder inOrder = Mockito.inOrder(post, armazenamentoPost);
            inOrder.verify(post).atualizarComDados(postAlterado);
            inOrder.verify(armazenamentoPost).salvar(post);
        }

        @Test
        void Dado_um_post_valido__Quando_editar__Entao_deve_retornar_o_mesmo_id() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            assertEquals(1L, postSalvo.getId());
        }

        @Test
        void Dado_um_post_pago__Quando_editar__Entao_deve_retornar_post_com_os_mesmo_ganhos_sem_recalcular() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            Mockito.verify(post, Mockito.never()).setGanhos(Mockito.any(Ganhos.class));
            Mockito.verify(post, Mockito.times(1)).isPago();
            assertNotNull(postSalvo.getGanhos());
        }

        @Test
        void Dado_um_post_nao_pago__Quando_editar__Entao_deve_recalcular_ganhos_antes_de_salvar() {
            post.setConteudo("Conteúdo editado");
            post.setPago(false);
            Ganhos novoGanho = new Ganhos(BigDecimal.TEN, 2, BigDecimal.valueOf(20));

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            Mockito.when(calculadoraGanhos.calcular(post)).thenReturn(novoGanho);

            Post postSalvo = cadastroPost.editar(post);

            Mockito.verify(postSalvo, Mockito.times(1)).setGanhos(novoGanho);
            assertNotNull(postSalvo.getGanhos());
            assertEquals(novoGanho, postSalvo.getGanhos());

            InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
            inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);

        }

        @Test
        void Dado_um_post_com_titulo_alterado__Quando_editar__Entao_deve_retornar_post_com_a_mesma_slug_sem_alterar() {
            post.setTitulo("Olá Teste");

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            Mockito.verify(post, Mockito.never()).setSlug(Mockito.anyString());
            assertEquals("ola-java-1234356", postSalvo.getSlug());
        }

        @Test
        void Dado_um_post_null__Quando_editar__Entao_deve_lancar_exception_e_nao_deve_salvar() {
            assertThrows(NullPointerException.class, () -> cadastroPost.editar(null));
            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
        }
    }
}