import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BancoSimples {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        HashMap<String, String> user = new HashMap<>();
        user.put("user1", "123");
        user.put("user2", "456");

        HashMap<String, Double> saldos = new HashMap<>();
        HashMap<String, List<String>> extratos = new HashMap<>();

        String usuarioConectado = logar(scanner, user, extratos);

        boolean executando = true;

        while (executando) {

            switch (verPainel(scanner)) {
                case 1 -> verSaldo(saldos, usuarioConectado);

                case 2 -> saldos.put(usuarioConectado, verDeposito(scanner, saldos, extratos, usuarioConectado));

                case 3 -> saldos.put(usuarioConectado, verSaque(scanner, saldos, extratos, usuarioConectado));

                case 4 -> verExtrato(extratos, usuarioConectado);

                case 5 -> executando = verSair();

                default -> System.out.println("Opção inválida. Por favor, escolha uma opção de 1 a 5.");
            }
        }
        scanner.close();
    }

    public static String logar (Scanner scanner, HashMap<String, String> user, HashMap<String, List<String>> extratos) {
        int tentativas = 3; 

        while (tentativas > 0) {
            System.out.println("\n=== Login Banco Simples ===");
            System.out.print("Digite o seu usuário: ");
            String usuario = scanner.nextLine();
            System.out.print("Digite a sua senha: ");
            String senha = scanner.nextLine();
            if (user.containsKey(usuario) && senha.equals(user.get(usuario))) {
                if (!extratos.containsKey(usuario)) {
                    extratos.put(usuario, new ArrayList<>());
                }
                System.out.println("Login bem-sucedido! Acessando o Banco Simples...");
                return usuario;
            } else {
                tentativas -= 1;
                System.out.println("Usuário ou senha incorretos! Tentativas restantes: " + tentativas);
                if (tentativas == 0) {
                    System.out.println("Por motivos de segurança o sistema irá se fechar!");
                    System.exit(0);
                }
            }
        }

        return null;
    }

    public static int verPainel(Scanner scanner) {
        System.out.println("\n=== Banco Simples ===\n");
        System.out.println("1. Ver saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Ver extrato");
        System.out.println("5. Sair \n");
        System.out.print("Digite o número da funcionalidade que você deseja utilizar: ");
        int funcao = scanner.nextInt();

        return funcao;
    }

    public static void verSaldo (HashMap<String, Double> saldos, String usuarioConectado) {
        if (!saldos.containsKey(usuarioConectado)) {
            saldos.put(usuarioConectado, 0.0);
        }

        System.out.println("\n=== Saldo ===");
        System.out.printf("Seu saldo atual é: R$ %.2f\n", saldos.get(usuarioConectado));
    }

    public static double verDeposito (Scanner scanner, HashMap<String, Double> saldos, HashMap<String, List<String>> extratos, String usuarioConectado) {
        System.out.println("\n=== Depósito ===");
        System.out.print("Quanto você deseja depositar? R$ ");
        double deposito = scanner.nextDouble();

        if (!saldos.containsKey(usuarioConectado)) {
            saldos.put(usuarioConectado, 0.0);
        }

        saldos.put(usuarioConectado, saldos.get(usuarioConectado) + deposito);
        extratos.get(usuarioConectado).add("Depósito de R$ " + String.format("%.2f", deposito));
        System.out.printf("Depósito de R$ %.2f realizado com sucesso!\n", deposito);

        return saldos.get(usuarioConectado);
    }

    public static double verSaque (Scanner scanner, HashMap<String, Double> saldos, HashMap<String, List<String>> extratos, String usuarioConectado) {
        System.out.println("\n=== Saque ===");
        System.out.print("Quanto você deseja sacar? R$ ");
        double saque = scanner.nextDouble();
        double saldo = saldos.get(usuarioConectado);

        if (!saldos.containsKey(usuarioConectado)) {
            saldos.put(usuarioConectado, 0.0);
        }

        if (saque <= saldos.get(usuarioConectado)) {
            saldos.put(usuarioConectado, saldos.get(usuarioConectado) - saque);
            extratos.get(usuarioConectado).add("Saque de R$ " + String.format("%.2f", saque));
            System.out.printf("Saque de R$ %.2f autorizado!\n", saque);
        } else {
            System.out.printf("Saldo insuficiente! Você possui R$ %.2f disponíveis.\n", saldo);
        }

        return saldos.get(usuarioConectado);
    }

    public static void verExtrato (HashMap<String, List<String>> extratos, String usuarioConectado) {
        System.out.println("\n=== Extrato ===");
        if (extratos.get(usuarioConectado).isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
        } else {
            for (String i : extratos.get(usuarioConectado)) {
                System.out.println(i);
            }
        }
    }

    public static boolean verSair () {
        System.out.println("Obrigado por utilizar o Banco Simples. Até logo!");

        return false;
    }
}