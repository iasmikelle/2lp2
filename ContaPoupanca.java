public class ContaPoupanca extends Conta {
    private double rendimentoMensal;

    public ContaPoupanca(Cliente cliente, double saldoInicial, double rendimentoMensal) {
        super(cliente, saldoInicial);
        this.rendimentoMensal = rendimentoMensal;
    }

    public void aplicarRendimento() {
        double rendimento = getSaldo() * rendimentoMensal;
        depositar(rendimento);
        System.out.println("Rendimento aplicado: R$ " + rendimento);
    }
}
