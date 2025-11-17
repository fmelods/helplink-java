package com.fiap.helplink.service;

import com.fiap.helplink.model.Doacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Fila assíncrona em memória usando BlockingQueue + @Async.
 * Atende ao requisito de mensageria com filas assíncronas.
 */
@Service
public class MensageriaService {

    private static final Logger log = LoggerFactory.getLogger(MensageriaService.class);

    private final BlockingQueue<String> filaNotificacoes = new LinkedBlockingQueue<>();

    public void enviarMensagem(String mensagem) {
        filaNotificacoes.offer(mensagem);
        processarFilaAsync();
    }

    public void enviarMensagemDoacaoCriada(Doacao doacao) {
        String msg = String.format(
                "Nova doação criada - ID: %d | Usuário: %s | Instituição: %s | Status: %s",
                doacao.getIdDoacao(),
                doacao.getUsuario().getNome(),
                doacao.getInstituicao().getNome(),
                doacao.getStatus()
        );
        enviarMensagem(msg);
    }

    @Async("mensageriaExecutor")
    public void processarFilaAsync() {
        while (!filaNotificacoes.isEmpty()) {
            String msg = filaNotificacoes.poll();
            if (msg != null) {
                // Aqui você poderia integrar com e-mail, SMS, etc.
                log.info("[MENSAGERIA-ASYNC] Processando mensagem da fila: {}", msg);
            }
        }
    }
}
