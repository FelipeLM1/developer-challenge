package chat.gpt.src.servico;

import chat.gpt.src.modelo.Peca;
import chat.gpt.src.modelo.Tabuleiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class JogoServiceImpl implements JogoService {

    @Override
    public Tabuleiro getTabuleiroInicial() {
        Tabuleiro tabuleiro = new Tabuleiro();
        List<Peca> pecaList = new ArrayList<>();

        IntStream.range(0, 9).forEach(i -> {
            Peca v = new Peca(i + 1, i);
            pecaList.add(v);
            tabuleiro.addPeca(v);
        });

        conectarPecas(tabuleiro, pecaList);
        return tabuleiro;
    }

    @Override
    public void embaralharTabuleiro(Tabuleiro tabuleiro) {
        tabuleiro.getPecasAdj().clear();
        List<Peca> pecaList = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        IntStream.range(0, 9).forEach(indices::add);
        Collections.shuffle(indices);
        IntStream.range(0, 9).forEach(i -> {
            Peca v = new Peca(indices.get(i) + 1, i);
            pecaList.add(v);
            tabuleiro.addPeca(v);
        });

        conectarPecas(tabuleiro, pecaList);
    }

    @Override
    public void reiniciarTabuleiro(Tabuleiro tabuleiro) {
        tabuleiro.getPecasAdj().clear();
        tabuleiro.setPecasAdj(getTabuleiroInicial().getPecasAdj());
    }

    @Override
    public Boolean fazerMovimento(Tabuleiro tabuleiro, Peca pecaMovido) {
        Boolean movimentoRealizado = Boolean.FALSE;
        Peca pecaVazio = tabuleiro.getPecaVazia();
        Boolean podeMover = tabuleiro.getPecasAdj(pecaMovido).contains(pecaVazio);
        if (Boolean.TRUE.equals(podeMover)) {
            moverPeca(tabuleiro, pecaMovido);
            movimentoRealizado = Boolean.TRUE;
        }
        return movimentoRealizado;
    }

    @Override
    public Boolean verificarVitoria(Tabuleiro tabuleiro) {
        return tabuleiro.equals(getTabuleiroInicial());
    }

    private void moverPeca(Tabuleiro tabuleiro, Peca pecaMovido) {
        tabuleiro.moverPeca(pecaMovido);
    }

    private void conectarPecas(Tabuleiro tabuleiro, List<Peca> pecaList) {
        tabuleiro.conectarPecas(pecaList.get(0), pecaList.get(1));
        tabuleiro.conectarPecas(pecaList.get(0), pecaList.get(3));
        tabuleiro.conectarPecas(pecaList.get(1), pecaList.get(2));
        tabuleiro.conectarPecas(pecaList.get(1), pecaList.get(4));
        tabuleiro.conectarPecas(pecaList.get(2), pecaList.get(5));
        tabuleiro.conectarPecas(pecaList.get(3), pecaList.get(4));
        tabuleiro.conectarPecas(pecaList.get(3), pecaList.get(6));
        tabuleiro.conectarPecas(pecaList.get(4), pecaList.get(5));
        tabuleiro.conectarPecas(pecaList.get(4), pecaList.get(7));
        tabuleiro.conectarPecas(pecaList.get(5), pecaList.get(8));
        tabuleiro.conectarPecas(pecaList.get(6), pecaList.get(7));
        tabuleiro.conectarPecas(pecaList.get(7), pecaList.get(8));
    }
}
