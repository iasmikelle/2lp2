package sistema;

import modelos.*;
import java.util.*;

public class Sistema {
    private Map<String, Cliente> clientes;
    private Cliente usuarioLogado;
    private Map<Integer, Agencia> agencias;
    private Map<Integer, Conta> contas;

    public Sistema() {
        this.clientes = new HashMap<>();
        this.agencias = new HashMap<>();
        this.contas = new HashMap<>();
        this.usuarioLogado = null;
    }

    // Métodos de cadastro
    public void cadastrarCliente(String nome, String cpf, String senha) {
        if (!clientes.containsKey(cpf)) {
            clientes.put(cpf, new Cliente(nome, cpf, senha));
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("CPF já cadastrado.");
        }
    }

    public void cadastrarAgencia(String nome, int codigo) {
        if (!agencias.containsKey(codigo)) {
            agencias.put(codigo, new Agencia(nome, codigo));
            System.out.println("Agência cadastrada com sucesso!");
        } else {
            System.out.println("Código da agência já existe.");
        }
    }

    // Autenticação
    public void autenticar(String cpf, String senha) {
        Cliente cliente = clientes.get(cpf);
        if (cliente != null && cliente.autenticar(cpf, senha)) {
            usuarioLogado = cliente;
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("CPF ou senha inválidos.");
        }
    }

    public void logout() {
        if (usuarioLogado != null) {
            System.out.println("Logout realizado com sucesso!");
            usuarioLogado = null;
        } else {
            System.out.println("Nenhum usuário logado.");
        }
    }

    // Criar Conta
    public void criarConta(String tipo, double saldoInicial) {
        if (usuarioLogado != null) {
            System.out.print("Digite o código da agência: ");
            Scanner scanner = new Scanner(System.in);
            int codigoAgencia = scanner.nextInt();
            Agencia agencia = agencias.get(codigoAgencia);

            if (agencia != null) {
                Conta novaConta = null;

                switch (tipo.toLowerCase()) {
                    case "corrente":
                        novaConta = new ContaCorrente(saldoInicial, usuarioLogado, 20.0);
                        break;
                    case "poupança":
                        novaConta = new ContaPoupanca(saldoInicial, usuarioLogado, 0.5);
                        break;
                    case "salario":
                        novaConta = new ContaSalario(saldoInicial, usuarioLogado, 5);
                        break;
                    default:
                        System.out.println("Tipo de conta inválido.");
                        return;
                }

                agencia.adicionarConta(novaConta);
                contas.put(novaConta.getNumeroConta(), novaConta);
                usuarioLogado.adicionarConta(novaConta);
                System.out.println("Conta criada com sucesso! Número da conta: " + novaConta.getNumeroConta());
            } else {
                System.out.println("Agência não encontrada.");
            }
        } else {
            System.out.println("Faça login para criar uma conta.");
        }
    }

    // Operações bancárias
    public void realizarDeposito(int numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void realizarSaque(int numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            if (!conta.sacar(valor)) {
                System.out.println("Erro ao realizar o saque.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void realizarTransferencia(int numeroContaOrigem, int numeroContaDestino, double valor) {
        Conta contaOrigem = contas.get(numeroContaOrigem);
        Conta contaDestino = contas.get(numeroContaDestino);

        if (contaOrigem != null && contaDestino != null) {
            if (contaOrigem.transferir(contaDestino, valor)) {
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Erro ao realizar a transferência.");
            }
        } else {
            System.out.println("Uma das contas não foi encontrada.");
        }
    }

    public void consultarSaldo(int numeroConta) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            System.out.println("Saldo da conta " + numeroConta + ": R$ " + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void exibirMovimentacoes(int numeroConta) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            conta.exibirMovimentacoes();
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    // Exibir dados
    public void exibirAgencias() {
        if (agencias.isEmpty()) {
            System.out.println("Nenhuma agência cadastrada.");
        } else {
            for (Agencia agencia : agencias.values()) {
                System.out.println("Agência " + agencia.getCodigo() + ": " + agencia.getNome());
                agencia.exibirContas();
            }
        }
    }
}
