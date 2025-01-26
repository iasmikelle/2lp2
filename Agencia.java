import java.util.ArrayList;
import java.util.List;

public class Agencia {
    private String nome;
    private int codigo;
    private List<Conta> contas;

    public Agencia(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public Conta buscarConta(int numeroConta) throws ContaNaoEncontradaException {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        throw new ContaNaoEncontradaException("Conta " + numeroConta + " não encontrada.");
    }
}
