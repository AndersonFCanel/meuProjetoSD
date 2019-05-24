package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutomatoInterface extends Remote 
{
	public String setAlfabeto    		 ( String s )throws RemoteException;
     
    public String setEstados             ( String s )throws RemoteException;
     
    public String setRegra             	 ( String s )throws RemoteException;
   
    
    public void setIdentificaUsuario     ( Character i )throws RemoteException;
    
    public Character getIdentificaUsuario( )throws RemoteException; 
	
    //Prono até aqui
    
    
    public void setEstInicial            ( )throws RemoteException;
    
    public void setConjuntoEstadosFinais ( )throws RemoteException;

    public void checaPalavra             ( )throws RemoteException;
	  
    public Integer getContaPasso         ( )throws RemoteException; 
    
	  
    public void incrementaContaPasso     ( )throws RemoteException;

    public String  imprimirAutomatoCliente( ) throws RemoteException;
    
    
    
    
    
    
	//FAZER AJUSTES
	public void imprimirAutomatoUsuario1( String alf, String est, int[ ] estadoPartida, int[ ] estadoDestino, Character[ ] le,
			String estIn, String conjuntoEstadosFinais )throws RemoteException;
	
	public void imprimirAutomatoUsuario2( String alf, String est, int[ ] estadoPartida, int[ ] estadoDestino, Character[ ] le,
			String estIn, String conjuntoEstadosFinais )throws RemoteException;
	
}
