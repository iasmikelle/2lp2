public class ContaSalario extends Conta {
    private int limiteSaques;
    private int saquesRealizados;

    public ContaSalario(Cliente cliente, double saldoInicial, int limiteSaques) {
        super(cliente, saldoInicial);
        this.limiteSaques = limiteSaques;
        this.saquesRealizados = 0;
    }

    @Override
    public void sacar(double valor) throws NumeroMaximoSaquesException, SaldoInsuficienteException {
        if (saquesRealizados >= limiteSaques) {
            throw new NumeroMaximoSaquesException("Número máximo de saques atingido.");
        }
        super.sacar(valor);
        saquesRealizados++;
    }
}
