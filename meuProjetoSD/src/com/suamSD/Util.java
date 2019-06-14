package com.suamSD;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Util {

    /*
     * Constantes que indicam onde está sendo executado o serviço de registro, qual
     * porta e qual o nome do objeto distribuído
     */
    public static final String LOCALHOST   = "localhost";
    public static final String IPSERVIDOR  = "127.0.0.1";
    public static final int    PORTA       =  1099;
    public static final String NOMEOBJDIST = "MeuAutomato";
    
    public static final String NOK         = "NOK";
    public static final String OK          = "OK";
    public static final String NULL        = "*";
    
    private static final Pattern PATTERN_IP = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    
    public static String defineIP( ) 
    {      
    	String ip = "";
    	boolean b = false;
    	
    	do
    	{
    		b = false;
    		try 
            {
                System.out.println( "Seu Ip local é: " + InetAddress.getLocalHost( ).getHostAddress( ) );
                
                ip = JOptionPane.showInputDialog( null,
                        "Entre com o IP do servidor:\n "
                        + "Para 127.0.0.1 'localhost', apenas clique OK!\n");
                
                /*String[ ] options = new String[ ] {"10.0.200.45", "10.0.200.16", "Cancelar" };
                
                Integer response = JOptionPane.showOptionDialog( null, "Indique a localização do servidor:", "Configurando IP",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[ 0  ] );
                // response == 0 para "10.0.200.45";  1 para "10.0.200.16"; 2 para Escape/Cancel.
                

                if ( response == 2 ) 
                {
                	try 
                	{
						interrompeThread( );
					} 
                	catch ( InterruptedException e ) 
                	{
						e.printStackTrace();
					}
                }
            
                if ( response == 0 ) 
                {
                	  //ip = "10.0.200.45";
                	ip = "192.168.0.11";
                }
                
                if ( response == 1 ) 
            	{
            	     //ip = "10.0.200.8";
                	ip = "192.168.0.22";
                	
            	}*/
                
                if( ip.isEmpty( ) )
                {
                	try 
                	{
                		  JOptionPane.showMessageDialog( null,  "O ip local é: " + InetAddress.getLocalHost( ).getHostAddress( ) );
                		  ip = InetAddress.getLocalHost( ).getHostAddress( );
                	}
                	catch ( HeadlessException | UnknownHostException e1 ) 
                	{
            			e1.printStackTrace();
            			System.out.println( "IP inválido" );
                        Util.interrompeThread( );
            		}
                }
                
                if( !PATTERN_IP.matcher( ip ).matches( ) && !( "".equals( ip ) ) )
                {
                	 JOptionPane.showMessageDialog( null, "Formato inválido" );
                	 b = true;
                }
            } 
            catch (  Exception e)
            {
            	e.printStackTrace( );
    	    }
    	}
    	while ( b );
    	
        if ( ip != null  )
        {
            switch ( ip ) 
            {
            case "":
                ip = IPSERVIDOR;
                break;

            default:
                break;
            }
        }
        
        
        JOptionPane.showMessageDialog( null, "O IP do servidorAutomato é: " + ip );
        System.out.println( "O IP do servidorAutomato é: "+ ip );
        
        return ip;
    }

	public static void interrompeThread( ) 
			throws InterruptedException
	{
		Thread.currentThread( ).interrupt( ) ;
	    if ( Thread.interrupted( ) ) 
	    {
	    	System.out.println( "Thread interrompida!" );
	    	throw new InterruptedException( );
	    }
	}
    
	public static String infoAutomato( )
	{
		String texto = "Teoria dos autômatos é o estudo das máquinas abstratas ou autômatos, \n"           +
                       "bem como problemas computacionais que podem ser resolvidos usando esses \n"        +
                       "objetos. É objeto de estudo tanto da Ciência da Computação Teórica como \n"        +
                       "da Matemática Discreta. A palavra autômato vem da palavra grega αὐτόματα \n"       +
                       "que significa “autoação” (em tradução livre), isto é, sem influência externa.\n\n" ;
	    
	    String texto2 = "Autômatos\r\n\n"                                                                                +
	    		        "Definição de máquina de estados finita:\r\n"                                                    +
	    	            "Um autômato determinístico finito é representado formalmente por uma 5-tupla (Q,Σ,δ,q0,F),\n "  +
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
	    		        "\n\n";                                                                       
	
        return  texto + texto2 + texto3 + texto4;	
	}
}
