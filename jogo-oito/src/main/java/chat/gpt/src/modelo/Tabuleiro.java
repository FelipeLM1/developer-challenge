package chat.gpt.src.modelo;

import chat.gpt.src.exception.JogoException;

import java.util.*;

public class Tabuleiro {

    private Map<Peca, Set<Peca>> pecasAdj;
    private final Integer VALOR_VAZIO = 9;

    public Tabuleiro() {
        this.pecasAdj = new HashMap<>();
    }

    public Set<Peca> getPecasAdj(Peca v) {
        return pecasAdj.get(v);
    }

    public Peca getPecaPorPosicao(Integer posicao) {
        return pecasAdj.keySet()
                .stream()
                .filter(vertex -> posicao.equals(vertex.getPosicao()))
                .findFirst()
                .orElseThrow(() -> new JogoException("Não foi possivel encontrar peca nessa posicao! " + posicao));
    }

    public Peca getPecaPorValor(Integer valor) {
        return pecasAdj.keySet()
                .stream()
                .filter(vertex -> valor.equals(vertex.getValor()))
                .findFirst()
                .orElseThrow(() -> new JogoException("Não foi possivel encontrar peca com esse valor! " + valor));
    }

    public void moverPeca(Peca pecaSelecionada) {
        Peca pecaVazia = getPecaVazia();
        Integer posicaoPecaSelecionada = pecaSelecionada.getPosicao();
        removerConexaoPecas(pecaVazia, pecaSelecionada);

        Set<Peca> vizinhosPecaSelecionada = pecasAdj.get(pecaSelecionada);
        Set<Peca> vizinhosPecaVazia = pecasAdj.get(pecaVazia);

        removerPeca(pecaVazia);
        removerPeca(pecaSelecionada);

        pecaSelecionada.setPosicao(pecaVazia.getPosicao());
        pecaVazia.setPosicao(posicaoPecaSelecionada);

        addPeca(pecaVazia);
        addPeca(pecaSelecionada);

        vizinhosPecaSelecionada.forEach(v -> conectarPecas(pecaVazia, v));
        vizinhosPecaVazia.forEach(v -> conectarPecas(pecaSelecionada, v));
        conectarPecas(pecaVazia, pecaSelecionada);
    }


    public Peca getPecaVazia() {
        return getPecaPorValor(VALOR_VAZIO);
    }

    public void addPeca(Peca v) {
        pecasAdj.putIfAbsent(v, new HashSet<>());
    }

    public void removerPeca(Peca v) {
        pecasAdj.values().forEach(e -> e.remove(v));
        pecasAdj.remove(v);
    }

    public void conectarPecas(Peca v1, Peca v2) {
        pecasAdj.get(v1).add(v2);
        pecasAdj.get(v2).add(v1);
    }

    public void removerConexaoPecas(Peca p1, Peca p2) {
        Set<Peca> eV1 = pecasAdj.get(p1);
        Set<Peca> eV2 = pecasAdj.get(p2);
        if (eV1 != null)
            eV1.remove(p2);
        if (eV2 != null)
            eV2.remove(p1);
    }

    public Map<Peca, Set<Peca>> getPecasAdj() {
        return pecasAdj;
    }

    public void setPecasAdj(Map<Peca, Set<Peca>> pecasAdj) {
        this.pecasAdj = pecasAdj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tabuleiro tabuleiro = (Tabuleiro) o;

        if (!Objects.equals(pecasAdj, tabuleiro.pecasAdj)) return false;
        return VALOR_VAZIO.equals(tabuleiro.VALOR_VAZIO);
    }

    @Override
    public int hashCode() {
        int result = pecasAdj != null ? pecasAdj.hashCode() : 0;
        result = 31 * result + VALOR_VAZIO.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjVertices=" + pecasAdj +
                '}';
    }
}
