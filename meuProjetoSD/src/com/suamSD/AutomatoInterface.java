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
    
    public  int getContaExecucao        ( ) throws RemoteException;
	
	public void setContaThreads         ( int contaThread )throws RemoteException;
	
}
