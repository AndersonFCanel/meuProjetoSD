package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContaExecucaoInterface  extends Remote
{
	
	public  Integer getContaExecucao( )throws RemoteException; 
	
	public  void setContaThreads( Integer contaThread )throws RemoteException;
	
}
