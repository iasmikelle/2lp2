package app;

import sistema.*;
import modelos.*;
import java.util.Scanner;

public class BancoApp {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();  // Mantendo o sistema original
        Persistencia persistencia = new Persistencia(sistema);  // Criando a persistência

        // Carregar dados persistidos ao iniciar o sistema
        persistencia.carregarDados();

        // Adicionar um hook para salvar os dados quando o sistema for fechado
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            persistencia.salvarDados();
            System.out.println("Dados salvos com sucesso!");
        }));

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Banco 2.0 ===");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Login");
            System.out.println("3. Criar conta");
            System.out.println("4. Realizar depósito");
            System.out.println("5. Realizar saque");
            System.out.println("6. Realizar transferência");
            System.out.println("7. Consultar saldo");
            System.out.println("8. Exibir movimentações");
            System.out.println("9. Logout");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    sistema.cadastrarCliente(nome, cpf, senha);
                    break;

                case 2:
                    System.out.print("CPF: ");
                    cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    senha = scanner.nextLine();
                    sistema.autenticar(cpf, senha);
                    break;

                case 3:
                    System.out.print("Tipo da conta (Corrente/Poupança): ");
                    String tipo = scanner.nextLine();
                    System.out.print("Saldo inicial: ");
                    double saldo = scanner.nextDouble();
                    sistema.criarConta(tipo, saldo);
                    break;

                case 4:
                    System.out.print("Digite o número da conta: ");
                    int numeroConta = scanner.nextInt();
                    System.out.print("Digite o valor do depósito: ");
                    double valorDeposito = scanner.nextDouble();
                    sistema.realizarDeposito(numeroConta, valorDeposito);
                    break;

                case 5:
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextInt();
                    System.out.print("Digite o valor do saque: ");
                    double valorSaque = scanner.nextDouble();
                    sistema.realizarSaque(numeroConta, valorSaque);
                    break;

                case 6:
                    System.out.print("Digite o número da conta de origem: ");
                    int numeroContaOrigem = scanner.nextInt();
                    System.out.print("Digite o número da conta de destino: ");
                    int numeroContaDestino = scanner.nextInt();
                    System.out.print("Digite o valor da transferência: ");
                    double valorTransferencia = scanner.nextDouble();
                    sistema.realizarTransferencia(numeroContaOrigem, numeroContaDestino, valorTransferencia);
                    break;

                case 7:
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextInt();
                    sistema.consultarSaldo(numeroConta);
                    break;

                case 8:
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextInt();
                    sistema.exibirMovimentacoes(numeroConta);
                    break;

                case 9:
                    sistema.logout();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }
}
