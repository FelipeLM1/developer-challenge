package chat.gpt;

import chat.gpt.src.modelo.Tabuleiro;
import chat.gpt.src.servico.JogoService;
import chat.gpt.src.servico.JogoServiceImpl;
import chat.gpt.src.view.View;

public class App {

    public static void main(String[] args) {
        JogoService service = new JogoServiceImpl();
        Tabuleiro tabuleiro = service.getTabuleiroInicial();
        new View(tabuleiro);
    }

}
