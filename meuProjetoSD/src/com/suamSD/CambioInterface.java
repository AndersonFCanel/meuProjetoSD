package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CambioInterface extends Remote {
	public double getTaxa() throws RemoteException;

	public void setTaxa( double taxa ) throws RemoteException;
	
	public double getValor() throws RemoteException;

	public void setValor( double taxa ) throws RemoteException;
	
	public void calculaValorTaxa( ) throws RemoteException;
	
}
