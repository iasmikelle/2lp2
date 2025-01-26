public class ContaCorrente extends Conta {
    private double taxaManutencao;

    public ContaCorrente(Cliente cliente, double saldoInicial, double taxaManutencao) {
        super(cliente, saldoInicial);
        this.taxaManutencao = taxaManutencao;
    }

    public void aplicarTaxaManutencao() {
        try {
            sacar(taxaManutencao);
            System.out.println("Taxa de manutenção aplicada: R$ " + taxaManutencao);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Saldo insuficiente para aplicar taxa de manutenção.");
        }
    }
}