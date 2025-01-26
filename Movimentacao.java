import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimentacao {
    private String descricao;
    private double valor;
    private String dataHora;

    public Movimentacao(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public String toString() {
        return dataHora + " - " + descricao + ": R$ " + valor;
    }
}
