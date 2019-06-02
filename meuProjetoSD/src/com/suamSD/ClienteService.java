package com.suamSD;

import java.awt.Dimension;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClienteService
{

	/*
     ****************************************************************
     * MÉTODOS PARA ENTRADA DE DADOS
     ****************************************************************
     */
	
    /**
     * 
     * @return
     * @throws InterruptedException 
     */
     public static String entrarConjuntoCaracteres_Alfabeto( ) 
    		 throws InterruptedException 
        {       
            String alfabeto;
            boolean b = false;
            do 
            {
                alfabeto = JOptionPane.showInputDialog( null,
                        "Entre com o alfabeto Σ:\n\n"
                        + "ATENÇÃO AS INSTRUÇÕES: \n"
                        + "Cada caracter deve ser separado por virgula,\n "
                        + "não sendo permitido a utilização de espaço.\n"
                        + "EX: a,b,c,d,e ...\n"
                        + "Lembre-se, tratando de conjunto(Teoria dos conjuntos) \n"
                        + "não são permitidos elementos duplicados.\n\n"
                        + "Tamanho permitido (Número de elementos possíveis):\n" 
                        + "       Mínimo = 1(um)   elemento. \n"
                        + "       Máximo = 3(três) elementos.\n\n" );
                
                if ( alfabeto == null)
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
             
            } 
            while ( b );
            
            return alfabeto;
        }
         
        public static String entraConjuntoEstado( ) 
        		throws RemoteException, NotBoundException, InterruptedException 
        {
            String  estados;
            boolean b = false;
            
            do {
            	b = false;
            	
                estados  = JOptionPane.showInputDialog( null,
                		 "Entre com o conjunto de estados terminais e não terminias:  Q:\n\n"
                				 + "ATENÇÃO AS INSTRUÇÕES: \n"
                                 + "Cada ESTADO deve ser separado por virgula.\n "
                                 + "Não é permitida a utilização de espaço.\n"
                                 + "EX: A,B,C ... e1,e2,e3...\n"
                                 + "Lembre-se, tratando de conjunto(Teoria dos conjuntos) \n"
                                 + "não são permitidos elementos duplicados.\n\n"
                                 + "Tamanho permitido (Número de elementos possíveis):\n" 
                                 + "       Mínimo = 1(um)   elemento. \n"
                                 + "       Máximo = 3(três) elementos.\n\n" );
                
                if ( estados == null)
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
                
                if ( "?".trim( ).equalsIgnoreCase( estados ) )
                {
                	 b = true;
                	 
                	// Obtendo referência do serviço de registro
                     Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                     // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                     AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                     
                     //Caracter que idenifica entradas globais de ambos os clientes
                     JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( '@' ) );
				}
                
            } 
            while ( b );
            
        return estados;
        }
    
        // TUTORIAL DE ENTRADA PARA FUNȿO DE TRANSIÇÃO
        public static void tutorialTransicao( ) 
        {
            JOptionPane.showMessageDialog( null,
                    "       * Conjunto de regras de transição (Regra de Produção), funciona da seguinte forma: *\n"
                            + "         {* # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)# *}"
                            + "\n\nATENÇÃO AOS PASSOS PARA ENTRADA DA FUNÇÃO DE TRANSIÇÃO DE ESTADOS\n"
                            + "PASSO 1: Primeiro entre com o estado inicial - EX: q0\n"
                            + "PASSO 2: DIGITE UMA VIRGULA \",\"\n"
                            + "PASSO 3: Entre com o caracter a ser consumido pelo estado inicial - EX: a\n"
                            + "PASSO 4: DIGITE PONTO E VIRGULA. \";\"\n"
                            + "PASSO 5: Entre com o estado de destino - EX: q1\n" + "PASSO 6: APERTE ENTER\n"
                            + "A entrada pode ser verificada com a inserção da letra i + enter\n"
                            + "e a mesma deve estar da forma do exemplo abaixo:\n" + "EX: q0,a;q1",
                    "WARNING", JOptionPane.WARNING_MESSAGE );
        }
       
        
        // ENTRADA DA FUNÇÃO DE TRANSIÇAO
        public static String entraFuncaoTransicao( ) 
        		throws InterruptedException, RemoteException, NotBoundException
        {
            String delta;
            boolean b = false;
            
            do {
            	b = false;
            	
                delta = JOptionPane.showInputDialog( null,
                							"Entre com as transiçãos de estado (δ: Q × Σ → Q):\n" +
                                            "Para ver o tutorial  novamente : 'i'\n"              +
                                            "Para sair : 's'\n"                                   + 
                                            "Para checar dados do autômato  digite '?'.\n\n" );
                
                if ( delta == null)
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
                
                if ("I".equalsIgnoreCase( delta.trim( ) ) )  
                {
                    tutorialTransicao( );
                }
                
                if ( "?".equalsIgnoreCase( delta.trim( ) ) )
                {
                	 b = true;
                	 
                	// Obtendo referência do serviço de registro
                     Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                     // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                     AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                     
                     JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( '@' ) );
				}
            } 
            while ( b );
            
            return delta;
        }
        
        // ENTRADA DO ESTADO INICIAL
        public static String entraEstIN( ) throws RemoteException, NotBoundException, InterruptedException
        {
            boolean b = false;
            String  estadoInicial; 
            
            do 
            {
            	b = false;
            	
                estadoInicial = JOptionPane.showInputDialog( null, 
                		"Entre com o estado inicial q0: \n\n"
                		+ "ATENÇÃO AS INSTRUÇÕES:\n"
                		+ "O estado inicial deve ser inserido na forma 'estado',\n"
                		+ " sem vírgulas ou qualquer outro adereço,\n"
                		+ "o estado inicial é único.\n "
                        + "Para checar dados do autômato  digite '?'.\n\n");


                if ( estadoInicial == null )
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
                
                if ( "?".trim( ).equalsIgnoreCase( estadoInicial.trim( ) ) )
                {
                	 b = true;
                	 
                	// Obtendo referência do serviço de registro
                     Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                     // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                     AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                     
                     //Caracter que idenifica entradas globais de ambos os clientes
                     JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( '@' ) );
				}
                 
            } 
            while ( b );

            return estadoInicial;
        }
        
        // ENTRADA DO ELEMENTOS CONJUNTO DE ESTADOS FINAIS
        public static String entraCjtEstFinal( ) 
        		throws RemoteException, NotBoundException, InterruptedException 
        {
            boolean b = false;
            String  cjtEstFinal; 
            
            do 
            {
            	b = false;
            	
                 cjtEstFinal = JOptionPane.showInputDialog( null, 
                		  "Entre com o conjunto dos estados finais F:\n\n"
                				  + "ATENÇÃO AS INSTRUÇÕES: \n"
                                  + "Cada ESTADO deve ser separado por virgula.\n "
                                  + "Não é permitida a utilização de espaço.\n"
                                  + "EX: A,B,C ... e1,e2,e3...\n"
                                  + "Lembre-se, tratando de conjunto(Teoria dos conjuntos) \n"
                                  + "não são permitidos elementos duplicados.\n\n"
                                  + "Tamanho permitido (Número de elementos possíveis):\n" 
                                  + "       Mínimo = 1(um)   elemento. \n"
                                  + "       Máximo = 3(três) elementos.\n\n" );  
                 
                if ( cjtEstFinal == null )
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
             
                if ( "?".trim( ).equalsIgnoreCase( cjtEstFinal.trim( ) ) )
                {
                	 b = true;
                	 
                	// Obtendo referência do serviço de registro
                     Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                     // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                     AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                     
                     //Caracter que idenifica entradas globais de ambos os clientes
                     JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( '@' ) );
				}
            } 
            while ( b );
        
            return cjtEstFinal;
        } 
        
        //Pegando valores atuais no servidor
        public static boolean valoresAtuais( String info, Character idUser ) throws RemoteException, NotBoundException
        {
            if ( info.trim( ).equalsIgnoreCase( "?" ) ) 
            {
                // Obtendo referência do serviço de registro
                Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                
                JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( idUser ) );
                
                return false;
            }   
            return true;
        }
        
        public static String entraPalavra( ) 
        		throws InterruptedException, RemoteException, NotBoundException 
        {
            String  palavra;
            boolean b = false;
            
            do {
            	
            	b = false;
            	
                palavra  = JOptionPane.showInputDialog( null,
                                  "Entre com a palavra a ser verificada: \n"
                                + "Para conferir os valores dos conjuntos e regras de produção digite '?'\n"
                                + "Para sair entre cancelar." ); 
              	
                if ( palavra == null)
                {
                    b = false;
                    JOptionPane.showMessageDialog( null, "Você saiu!", "WARNING", JOptionPane.WARNING_MESSAGE );

                    // Obtendo referência do serviço de registro
                    Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                    // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                    AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                 
                    stub.incrementaContaPasso( ); 

                    //break;                    
                    //Util.interrompeThread ( ); 
                }
                
                if ( "?".trim( ).equals( palavra ) )
                {
                	 b = true;
                	 
                	// Obtendo referência do serviço de registro
                     Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                     // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                     AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                     
                     //Caracter que idenifica entradas globais de ambos os clientes
                      JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( '@' ) );

				}
                
            } 
            while ( b );
            
        return palavra;
        }
        
        //USUÁRIO VEZ
        static void aguardarVezOutroUsuarioCli( String idCli )
        {
            JOptionPane.showMessageDialog( null, "Cliente "+ idCli +" \nAguarde outro user terminar!\nAguarde 30 segundos e tente novamente!" );
        }
          
        /**
         * Informações sobre o que é um autômato e suas aplicações.
         */
        static void info( String texto ) 
        {    
        	        	
        	JTextArea textArea = new JTextArea( texto  );
        	JScrollPane scrollPane = new JScrollPane(  textArea );
        	textArea.setWrapStyleWord(true );
        	scrollPane.setPreferredSize( new Dimension( 550, 550 ) );
        	JOptionPane.showMessageDialog(null, scrollPane, "Informações sobre os autômatos finitos determonistícos ou AFD", 
        			JOptionPane.INFORMATION_MESSAGE);

     }
}
