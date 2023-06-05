package chat.gpt.src.modelo;

public class Peca {
    private final Integer valor;
    private Integer posicao;

    public Peca(Integer valor, Integer posicao) {
        this.valor = valor;
        this.posicao = posicao;
    }

    public Integer getValor() {
        return valor;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getLabel() {
        return valor == 9 ? " " : valor.toString();
    }

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peca peca = (Peca) o;

        if (!valor.equals(peca.valor)) return false;
        return posicao.equals(peca.posicao);
    }

    @Override
    public int hashCode() {
        int result = valor.hashCode();
        result = 31 * result + posicao.hashCode();
        return result;
    }
}