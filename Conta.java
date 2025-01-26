import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private static int contadorContas = 1000;
    private int numeroConta;
    private Cliente cliente;
    private double saldo;
    private List<Movimentacao> movimentacoes;

    public Conta(Cliente cliente, double saldoInicial) {
        this.numeroConta = contadorContas++;
        this.cliente = cliente;
        this.saldo = saldoInicial;
        this.movimentacoes = new ArrayList<>();
        registrarMovimentacao("Abertura de Conta", saldoInicial);
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            registrarMovimentacao("Depósito", valor);
        } else {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            registrarMovimentacao("Saque", valor);
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
    }

    protected void registrarMovimentacao(String descricao, double valor) {
        movimentacoes.add(new Movimentacao(descricao, valor));
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }
}
