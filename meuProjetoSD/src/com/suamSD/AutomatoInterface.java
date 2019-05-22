package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutomatoInterface extends Remote 
{
	public void setAlfabeto    			( )throws RemoteException;
     
    public void setEstados              ( )throws RemoteException;
     
    public void setRegra             	( )throws RemoteException;
   
    public void setEstInicial           ( )throws RemoteException;
    
    public void setConjuntoEstadosFinais( )throws RemoteException;

    public void checaPalavra            ( )throws RemoteException;
    
    public Integer getContaExecucao     ( )throws RemoteException; 
	
	public void setContaThreads         ( Integer contaThread )throws RemoteException;
	
	public void imprimirAutomatoUsuario1( String alf, String est, int[ ] estadoPartida, int[ ] estadoDestino, Character[ ] le,
			String estIn, String conjuntoEstadosFinais )throws RemoteException;
	
	public void imprimirAutomatoUsuario2( String alf, String est, int[ ] estadoPartida, int[ ] estadoDestino, Character[ ] le,
			String estIn, String conjuntoEstadosFinais )throws RemoteException;
	
}
