package com.suamSD;

import java.awt.Dimension;
import java.io.ObjectInputStream.GetField;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
          
        static void info( ) 
        {    
        	String texto = "Teoria dos autômatos é o estudo das máquinas abstratas ou autômatos, \n"           +
                           "bem como problemas computacionais que podem ser resolvidos usando esses \n"        +
                           "objetos. É objeto de estudo tanto da Ciência da Computação Teórica como \n"        +
                           "da Matemática Discreta. A palavra autômato vem da palavra grega αὐτόματα \n"       +
                           "que significa “autoação” (em tradução livre), isto é, sem influência externa.\n\n" ;
        	
        	String texto2 = "Autômatos\r\n\n"                                                                                +
        			        "Definição de máquina de estados finita:\r\n"                                                    +
        		            "Um autômato determinístico finito é representado formalmente por uma 5-tupla (Q,Σ,δ,q0,F),\n "    +
        			        "onde:\r\n"                                                                                      +
        			        "Q é um conjunto finito de estados.\r\n"                                                         +
        			        "Σ é um conjunto finito de símbolos, chamado de alfabeto do autômato.\r\n"                       +
        			        "δ é a função de transição, isto é, δ: Q x Σ → Q.\r\n"                                           +
        			        "q0 é o estado inicial,\n"                                                                       +
        			        "   isto é, o estado do autômato antes de qualquer entrada ser processada, onde q0 ∈ Q.\r\n"      +
        			        "F é um conjunto de estados de Q (isto é, F ⊆ Q) chamado de estados de aceitação."               +
        			        "\n\n"  																				         ;                                                                                         
        	
        	String texto3 = "Palavra de entrada\r\n\n"                                                                +
        			        "Um autômato lê uma string (cadeia de caracteres) finita de símbolos a1,a2,...., an,\n"   +
        			        "onde ai ∈ Σ, que é chamada de palavra de entrada. \n"                                     +
        			        "O conjunto de todas as palavras é denotado por Σ*."									  +
        			        "\n\n"																					  ;                               
        	
        	String texto4 = "Execução ( breve descrição )\r\n\n"                                             +
        			        "A execução de um autômato sobre uma palavra de entrada w = a1,a2,....,\n "      +
        			        "an ∈ Σ*, é uma sequência de estados q0, q1,q2,...., qn,\n "                      +
        			        "onde qi ∈ Q tal que q0 é o estado inicial e qi = δ(qi-1,ai) para 0 < i ≤ n.\n "  +
        			        "Em outras palavras, no começo o autômato está no estado inicial q0, \n"         +
        			        "e então o autômato lê símbolos da palavra de entrada sequencialmente. \n"       +
        			        "Quando o autômato lê o símbolo ai ele pula para o estado qi = δ(qi-1,ai). \n"   +
        			        "Diz-se que qn é o estado final da execução."                                    +
        			        "\n\n"                                                                           ;                            
        			
        	        	
        	JTextArea textArea = new JTextArea( texto + texto2 + texto3 + texto4 );
        	JScrollPane scrollPane = new JScrollPane(  textArea );
        	textArea.setWrapStyleWord(true );
        	scrollPane.setPreferredSize( new Dimension( 550, 550 ) );
        	JOptionPane.showMessageDialog(null, scrollPane, "Informações sobre os autômatos finitos determonistícos ou AFD", 
        			JOptionPane.INFORMATION_MESSAGE);

     }
}
