package chat.gpt.src.view;

import chat.gpt.src.exception.JogoException;
import chat.gpt.src.modelo.Peca;
import chat.gpt.src.modelo.Tabuleiro;
import chat.gpt.src.servico.JogoService;
import chat.gpt.src.servico.JogoServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class View extends JFrame {

    private final Tabuleiro tabuleiro;
    private final JogoService service;
    private final List<Botao> botaoPecas;

    public View(Tabuleiro tabuleiro) {
        super("Jogo dos oito");
        this.tabuleiro = tabuleiro;
        this.service = new JogoServiceImpl();
        this.botaoPecas = new ArrayList<>(9);
        inicializarInterfaceGrafica();
    }

    private void inicializarInterfaceGrafica() {
        criarJanela();
        criarBotoes();
        ativarInterfaceGrafica();
    }

    private void criarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLayout(new GridLayout(4, 3));
    }

    private void criarBotoes() {
        inicializarBotoesPecas();
        criarBotaoEmbaralhar();
        criarBotaoReinicar();
    }

    private void inicializarBotoesPecas() {
        IntStream.range(0, 9).forEach(i -> botaoPecas.add(criarBotao(i)));
    }

    private Botao criarBotao(Integer posicao) {
        Peca v = tabuleiro.getPecaPorPosicao(posicao);
        return new Botao(criarJButton(), v.getValor());
    }

    private JButton criarJButton() {
        JButton botao = new JButton();
        botao.setFont(new Font("Arial", Font.BOLD, 36));
        botao.addMouseListener(getMouseListenerFunction());
        add(botao);
        return botao;
    }

    private void criarBotaoEmbaralhar() {
        JButton botaoEmbaralhar = new JButton("Embaralhar");
        botaoEmbaralhar.addActionListener(e -> embaralhar());
        add(botaoEmbaralhar);
    }

    private void embaralhar() {
        service.embaralharTabuleiro(this.tabuleiro);
        atualizarInterfaceTabuleiro();
    }

    private void criarBotaoReinicar() {
        JButton botaoReiniciar = new JButton("Reiniciar");
        botaoReiniciar.addActionListener(e -> reiniciar());
        add(botaoReiniciar);
    }

    private void reiniciar() {
        service.reiniciarTabuleiro(tabuleiro);
        atualizarInterfaceTabuleiro();
    }

    private void ativarInterfaceGrafica() {
        setFocusable(true);
        atualizarInterfaceTabuleiro();
        setVisible(true);
    }

    private void atualizarInterfaceTabuleiro() {
        IntStream.range(0, 9).forEach(i -> {
                    botaoPecas.get(i).getjButton().setText(tabuleiro.getPecaPorPosicao(i).getLabel());
                    botaoPecas.get(i).setValor(tabuleiro.getPecaPorPosicao(i).getValor());
                }
        );
    }

    private MouseAdapter getMouseListenerFunction() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton jButtonClicado = (JButton) e.getSource();
                Botao botaoClicado = getBotaoClicado(jButtonClicado);
                Boolean movimentoFeito = service.fazerMovimento(tabuleiro, tabuleiro.getPecaPorValor(botaoClicado.getValor()));
                atualizarInterfaceTabuleiro();
                if (Boolean.TRUE.equals(movimentoFeito)) {
                    verificarVitoria();
                }
            }
        };
    }

    private void verificarVitoria() {
        Boolean vitoria = service.verificarVitoria(tabuleiro);
        if (vitoria) {
            JOptionPane.showMessageDialog(null, "Parabéns! Você venceu o jogo!");
        }
    }

    private Botao getBotaoClicado(JButton jButtonClicado) {
        return botaoPecas.stream().filter(botao -> botao.getjButton().equals(jButtonClicado))
                .findFirst().orElseThrow(() -> new JogoException("Botao clicado nao foi encontrado!"));
    }
}
