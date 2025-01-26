package sistema;

import java.io.*;
import java.util.*;
import modelos.*;

public class Persistencia {
    private Banco banco;

    public Persistencia(Banco banco) {
        this.banco = banco;
    }

    // Salva todos os dados
    public void salvarDados() {
        try {
            salvarAgencias();
            salvarClientes();
            salvarContas();
            salvarMovimentacoes();
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // Carrega todos os dados
    public void carregarDados() {
        try {
            carregarAgencias();
            carregarClientes();
            carregarContas();
            carregarMovimentacoes();
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void salvarAgencias() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/agencias.csv"))) {
            writer.println("codigo,nome");
            for (Agencia agencia : banco.getAgencias()) {
                writer.println(agencia.getCodigo() + "," + agencia.getNome());
            }
        }
    }

    private void salvarClientes() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/clientes.csv"))) {
            writer.println("cpf,nome");
            for (Cliente cliente : banco.getClientes()) {
                writer.println(cliente.getCpf() + "," + cliente.getNome());
            }
        }
    }

    private void salvarContas() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/contas.csv"))) {
            writer.println("numero,tipo,saldo,codigoAgencia,cpfCliente,adicional");
            for (Agencia agencia : banco.getAgencias()) {
                for (Conta conta : agencia.getContas()) {
                    String adicional = "";
                    if (conta instanceof ContaCorrente) {
                        adicional = String.valueOf(((ContaCorrente) conta).getTaxaManutencao());
                    } else if (conta instanceof ContaPoupanca) {
                        adicional = String.valueOf(((ContaPoupanca) conta).getRendimentoMensal());
                    } else if (conta instanceof ContaSalario) {
                        adicional = String.valueOf(((ContaSalario) conta).getLimiteSaques());
                    }
                    writer.println(conta.getNumero() + "," + conta.getTipo() + "," + conta.getSaldo() + "," +
                            agencia.getCodigo() + "," + conta.getCliente().getCpf() + "," + adicional);
                }
            }
        }
    }

    private void salvarMovimentacoes() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/movimentacoes.csv"))) {
            writer.println("numeroConta,descricao,valor,dataHora");
            for (Agencia agencia : banco.getAgencias()) {
                for (Conta conta : agencia.getContas()) {
                    for (Movimentacao movimentacao : conta.getMovimentacoes()) {
                        writer.println(conta.getNumero() + "," + movimentacao.getDescricao() + "," +
                                movimentacao.getValor() + "," + movimentacao.getDataHora());
                    }
                }
            }
        }
    }

    private void carregarAgencias() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/agencias.csv"))) {
            String linha = reader.readLine(); // Ignora cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int codigo = Integer.parseInt(partes[0]);
                String nome = partes[1];
                banco.cadastrarAgencia(nome, codigo);
            }
        }
    }

    private void carregarClientes() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/clientes.csv"))) {
            String linha = reader.readLine(); // Ignora cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String cpf = partes[0];
                String nome = partes[1];
                banco.cadastrarCliente(nome, cpf);
            }
        }
    }

    private void carregarContas() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/contas.csv"))) {
            String linha = reader.readLine(); // Ignora cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int numero = Integer.parseInt(partes[0]);
                String tipo = partes[1];
                double saldo = Double.parseDouble(partes[2]);
                int codigoAgencia = Integer.parseInt(partes[3]);
                String cpfCliente = partes[4];
                String adicional = partes[5];

                Agencia agencia = banco.buscarAgencia(codigoAgencia);
                Cliente cliente = banco.buscarCliente(cpfCliente);

                switch (tipo) {
                    case "Corrente":
                        double taxa = Double.parseDouble(adicional);
                        agencia.adicionarConta(new ContaCorrente(numero, saldo, cliente, taxa));
                        break;
                    case "Poupanca":
                        double rendimento = Double.parseDouble(adicional);
                        agencia.adicionarConta(new ContaPoupanca(numero, saldo, cliente, rendimento));
                        break;
                    case "Salario":
                        int limite = Integer.parseInt(adicional);
                        agencia.adicionarConta(new ContaSalario(numero, saldo, cliente, limite));
                        break;
                }
            }
        }
    }

    private void carregarMovimentacoes() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/movimentacoes.csv"))) {
            String linha = reader.readLine(); // Ignora cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int numeroConta = Integer.parseInt(partes[0]);
                String descricao = partes[1];
                double valor = Double.parseDouble(partes[2]);
                String dataHora = partes[3];

                Conta conta = banco.buscarContaPorNumero(numeroConta);
                if (conta != null) {
                    conta.adicionarMovimentacao(new Movimentacao(descricao, valor, dataHora));
                }
            }
        }
    }
}
