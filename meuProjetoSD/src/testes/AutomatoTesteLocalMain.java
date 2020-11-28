package testes;

import java.awt.HeadlessException;
import java.io.IOException;
import java.rmi.NotBoundException;

import javax.swing.JOptionPane;

import com.suamSD.gravadorArquivo.CriaArquivo;
import com.suamSD.service.AutomatoImplementsService;

/**
 * Classe responsável por criar uma instância do objeto serviceAutomato e
 * registrá-la em um serviço de registro de objetos distribuídos
 *
 */
public class AutomatoTesteLocalMain {
	
	static AutomatoImplementsService automato = new AutomatoImplementsService();

	public static void main(String args[])
			throws InterruptedException, NotBoundException, HeadlessException, IOException {

		String entrada;
		String requestIsValid;

		/**
		 * Execução do primeiro método - Passo 1 - entrarConjuntoCaracteres_Alfabeto
		 */

		do {
			entrada = FrontEndService.entrarConjuntoCaracteres_Alfabeto();
			requestIsValid = automato.setAlfabeto(entrada);

			if (!"OK".equals(requestIsValid))
				JOptionPane.showMessageDialog(null, requestIsValid);
			else {
				JOptionPane.showMessageDialog(null, "Entrada Verificada");

			}
			System.out.println("PASSO CORRENTE: " + automato.getMetetodoCorrente());
		} while (!"OK".equals(requestIsValid));

		// Executando segundo método - Passo 2
		do {
			entrada = FrontEndService.entraConjuntoEstado();
			requestIsValid = automato.setEstados(entrada);

			if (!"OK".equals(requestIsValid))
				JOptionPane.showMessageDialog(null, requestIsValid);
			else {
				JOptionPane.showMessageDialog(null, "Entrada Verificada");
				// automatoRemoto.incrementaContaPasso( );
			}
			System.out.println("PASSO CORRENTE: " + automato.getMetetodoCorrente());
		} while (!"OK".equals(requestIsValid));
		System.out.println("Usuário 'B' efetuou entrada de estados!");

		/**
		 * Execução do terceiro método - Passo 3
		 */
		automato.zeraContadorFuncTran();
		int cont1 = automato.getTamanhoCjtFuncTran();
		int cont2 = automato.getContadorFuncTran();
		FrontEndService.tutorialTransicao();

		do {
			while (cont2 < cont1) {
				cont2 = automato.getContadorFuncTran() + 1;

				entrada = FrontEndService.entraFuncaoTransicao();

				// Entrada defaut para demonstração
				if ("d".equals(entrada)) {
					automato.setAlfabeto("a,b,c");
					automato.setEstados("0,1,2");
					automato.zeraContadorFuncTran();
					String[] deltaTeste = new String[] { "0,a;1", "1,b;2", "2,c;0", "", "0,b;2", "1,a;1", "", "", "" };
					for (String string : deltaTeste) {
						requestIsValid = automato.setRegra(string);
						System.out.println("PASSO CORRENTE: " + (automato.getMetetodoCorrente() + 1)
								+ "\nPosição função corrente: " + automato.getContadorFuncTran() + " de " + cont1);
					}

					if (automato.getContadorFuncTran() == 9) {
						requestIsValid = "OK";
						break;
					}
				}

				requestIsValid = automato.setRegra(entrada);

				if (!"OK".equals(requestIsValid)) {
					cont2 = automato.getContadorFuncTran();
					JOptionPane.showMessageDialog(null, requestIsValid);
				} else {
					JOptionPane.showMessageDialog(null, "Entrada Verificada");
				}
				System.out.println("PASSO CORRENTE: " + (automato.getMetetodoCorrente()) + "\nPosição função corrente: "
						+ automato.getContadorFuncTran() + " de " + cont1);
			}
		} while (!"OK".equals(requestIsValid));
		// Fim passo - 3
		System.out.println("Usuário 'A' efetuou entrada do cjt delta!");

		// Executando Quarto Método - Passo 4
		do {
			entrada = FrontEndService.entraEstIN();
			requestIsValid = automato.setEstInicial(entrada);

			if (!"OK".equals(requestIsValid))
				JOptionPane.showMessageDialog(null, requestIsValid);
			else {
				JOptionPane.showMessageDialog(null, "Entrada Verificada");
				// automatoRemoto.incrementaContaPasso( );
			}
			System.out.println("PASSO CORRENTE: " + automato.getMetetodoCorrente());
		} while (!"OK".equals(requestIsValid));
		System.out.println("Usuário 'B' efetuou entrada de estado inicial!");

		/**
		 * Execução do quinto método - Passo 5
		 */
		do {
			entrada = FrontEndService.entraCjtEstFinal();
			requestIsValid = automato.setConjuntoEstadosFinais(entrada);

			if (!"OK".equals(requestIsValid))
				JOptionPane.showMessageDialog(null, requestIsValid);
			else {
				JOptionPane.showMessageDialog(null, "Entrada Verificada");
				// automatoRemoto.incrementaContaPasso( );
			}
			System.out.println("PASSO CORRENTE: " + automato.getMetetodoCorrente());
		} while (!"OK".equals(requestIsValid));
		System.out.println("Usuário 'A' efetuou entrada de estados finais!");

		do {
			entrada = FrontEndService.entraPalavra();
			if (entrada == null) {
				System.out.println("Clinete encerrado!");
				break;
			}
			requestIsValid = automato.checaAceitacaoPalavra(entrada);

			if (!"OK".equals(requestIsValid))
				JOptionPane.showMessageDialog(null, requestIsValid);
			else // implementar entrada igual a 0
			{
				JOptionPane.showMessageDialog(null, "Entrada Verificada");
				// automatoRemoto.incrementaContaPasso( );
			}
			System.out.println("PASSO CORRENTE: " + automato.getMetetodoCorrente() + "\n" + "Palavra: " + entrada
					+ " : =>>  " + requestIsValid);
		} while (!"OK".equals(requestIsValid));
		System.out.println("Usuário 'B' entrou com palavra(s)");
		automato.setMetetodoCorrente(7);

		// método responsável pela criação do arquivo (automato.txt)
		CriaArquivo f = new CriaArquivo();
		try {
			f.criarArq(automato.imprimirAutomatoCliente('P'));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}