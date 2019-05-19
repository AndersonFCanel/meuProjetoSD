package com.suamSD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente de uma aplicação Java RMI
 *
 * http://docente.ifsc.edu.br/mello
 */
public class Cliente {

    // Constantes que indicam onde está sendo executado o serviço de registro,
    // qual porta e qual o nome do objeto distribuído
    private static final String IPSERVIDOR  = "127.0.0.1";
    private static final int    PORTA       = 1234;
    private static final String NOMEOBJDIST = "MeuAutomato";

    public static void main(String args[]) {
        try {
            // Obtendo referência do serviço de registro
            Registry registro = LocateRegistry.getRegistry(IPSERVIDOR, PORTA);

            // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
            AutomatoInterface stub = (AutomatoInterface) registro.lookup(NOMEOBJDIST);

            // Invocando métodos do objeto distribuído
            //System.out.println("Valor atual: " + stub.obtemValorAtual());
            //System.out.println("Solicitando ao servidor para incrementar o contador");
            //stub.incrementa();
           // System.out.println("Valor atual: " + stub.obtemValorAtual());

            stub.setAlfabeto();
            stub.setEstados();
            stub.setRegra();
            stub.setEstInicial();
            stub.setConjuntoEstadosFinais();
            stub.checaPalavra();
            
            System.out.println("Fim da execução do cliente!");

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
