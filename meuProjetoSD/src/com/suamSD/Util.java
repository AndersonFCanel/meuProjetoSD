package com.suamSD;

import java.net.InetAddress;
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
    
}
