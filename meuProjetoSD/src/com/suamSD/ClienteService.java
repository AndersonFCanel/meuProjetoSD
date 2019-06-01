package com.suamSD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

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
                      + "\nCada caracter deve ser separado por virgula,\n "
                      + "não é permitida a utilização de espaço.\n"
                      + "EX: a,b,c,d,e ...\n"
                      + "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
                      + "\nTamanho permitido (Número de elementos possíveis):\n" 
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
                		  "ATENÇÃO AS INSTRUÇÕES: \n\n"
                        + "\nCada ESTADO deve ser separado por virgula,\n "
                        + "não é permitida a utilização de espaço.\n"
                        + "EX: A,B,C ... e1,e2,e3...\n"
                        + "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
                        + "\nTamanho permitido (Número de elementos possíveis):\n" 
                        + "       Mínimo = 1(um)   elemento. \n"
                        + "       Máximo = 3(três) elementos.\n"
                        + "Para checar dados do autômato  digite '?'.\n\n" );
                
                if ( estados == null)
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
                
                if ( "?".trim( ).equals( estados ) )
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
                        "\nEntre com as transiçãos de estado (δ: Q × Σ → Q):\n" + "\nPara ver o tutorial  novamente : 'i'"
                                + "\nPara sair : 's'\n" 
                                //+ "\nVer entradas anteriores 'a'" + "\n "
                                + "Para checar dados do autômato  digite ?\n\n" );
                
                if ( delta == null)
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
                
                if ( "?".trim( ).equals( delta ) )
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
            	
                estadoInicial = JOptionPane.showInputDialog( null, "ESTADO INICIAL:\nEntre com o estado inicial q0: \n"
                		+ "ATENÇÃO:\n"
                		+ "O estado inicial deve ser inserido na forma 'estado', sem vírgulas ou qualquer outro adereço,\n"
                		+ "o estado inicial é único.\n "
                        + "Para checar dados do autômato  digite '?'.\n");


                if ( estadoInicial == null )
                {
                    b = true;
                    
                    Util.interrompeThread ( ); 
                }
                
                if ( estadoInicial.trim( ).equalsIgnoreCase( "?" ) ) 
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
            	
                 cjtEstFinal = JOptionPane.showInputDialog( null, "\nEntre com o conjunto dos estados finais F:"
                        + "\nCada estado deve ser separado por virgula, sem espaços.\nEX: A,B,C ... e1,e2,e3 ...\n"
                        + "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
                        + "\nTamanho permitido:\n"
                        + "       Mínimo = 1(um)   elemento. \n"
                        + "       Máximo = 3(três) elementos.\n"
                        + "Para checar dados do autômato  digite '?'.\n\n" );
        
                if ( cjtEstFinal == null )
                {
                    b = true;
                    
                    Thread.currentThread( ).interrupt( ) ;
                    if ( Thread.interrupted( ) ) 
                    {
                    	System.out.println("FIM");
                    	throw new InterruptedException( );
                    }
                }
                else
                { 
                    if ( cjtEstFinal.trim( ).equalsIgnoreCase( "?" ) ) 
                    {
                    	b = false;
                    	
                        // Obtendo referência do serviço de registro
                        Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );
        
                        // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                        AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                        
                        stub.imprimirAutomatoCliente( '@' );
                    }
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
                
                JOptionPane.showInputDialog( null, stub.imprimirAutomatoCliente( idUser ) );
                
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
                                  "Entre com a palavra a ser verificada: "
                                + "\nPara conferir os valores dos conjuntos e regras de produção digite '?'"
                                + "\nPara sair entre cancelar." );
                                                
              	  
              	
                if ( palavra == null)
                {
                    b = false;
                    JOptionPane.showMessageDialog( null, "Você saiu!", "WARNING", JOptionPane.WARNING_MESSAGE );

                    // Obtendo referência do serviço de registro
                    Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                    // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                    AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                 
                    stub.incrementaContaPasso( ); 
                    
                    
                    if( stub.getIdentificaUsuario( ) =='A' )
                    {
                        ClienteService.valoresAtuais( "?", '@' );
                    }
                    else
                    {
                    	ClienteService.valoresAtuais( "?", 'B' );
                    		
                    }
                    //break;
                    
                    Util.interrompeThread ( ); 
                    
                }
                
                if ( "?".trim( ).equals( palavra ) )
                {
                	 b = true;
                	 
                	// Obtendo referência do serviço de registro
                     Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

                     // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
                     AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
                     
                     //Caracter que idenifica entradas globais de ambos os clientes
                     Character k = '@';
                     
                     JOptionPane.showMessageDialog( null, stub.imprimirAutomatoCliente( k ) );
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
          
}
