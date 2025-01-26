src/

├── app/

│   └── BancoAppMain.java          (Arquivo principal com menu e integração com o Sistema)

├── modelos/

│   ├── Agencia.java           (Modelo para Agências)

│   ├── Conta.java             (Classe base de Conta)

│   ├── ContaCorrente.java    (Conta Corrente com taxas)

│   ├── ContaPoupanca.java    (Conta Poupança com rendimento)

│   ├── ContaSalario.java     (Conta Salário com limites de saque)

│   ├── Cliente.java           (Modelo de Cliente)

│   └── Movimentacao.java     (Modelo de Movimentação)

└── sistema/

    ├── Sistema.java           (Lógica principal de autenticação, cadastro e operações bancárias)

    └── Persistencia.java      (Classe para carregar e salvar os dados)
