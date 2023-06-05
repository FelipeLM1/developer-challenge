package chat.gpt.src.servico;

import chat.gpt.src.modelo.Tabuleiro;
import chat.gpt.src.modelo.Peca;

public interface JogoService {

    Tabuleiro getTabuleiroInicial();

    void embaralharTabuleiro(Tabuleiro tabuleiro);

    void reiniciarTabuleiro(Tabuleiro tabuleiro);

    Boolean fazerMovimento(Tabuleiro tabuleiro, Peca pecaMovido);

    Boolean verificarVitoria(Tabuleiro tabuleiro);

}
