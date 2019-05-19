package com.suamSD.servidor;

import java.rmi.Naming;
import java.rmi.RemoteException;

import com.suamSD.AutomatoInterface;
import com.suamSD.Util;

public class Servidor 
{
	
	public static void Servico_servidor( ) {  
        try {  
        	AutomatoInterface servidor = new AutomatoInterface() {
				
				@Override
				public void setRegra() throws RemoteException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setEstadosFinais() throws RemoteException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setEstados() throws RemoteException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setEstIni() throws RemoteException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setAlfabeto() throws RemoteException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void checaPalavra() throws RemoteException {
					// TODO Auto-generated method stub
					
				}
			};  
            Naming.rebind("rmi://localhost:1099/MensageiroService", servidor);  
            System.out.println( "Servidor started"); 
        }  
        catch( Exception e ) {  
            System.out.println( "Trouble: " + e );  
        }  
    }  

    public static void main(String[] args) throws RemoteException {  

        //java.rmi.registry.LocateRegistry.createRegistry(1099); 
        Servidor servico_servidor = new Servidor();

    } 
	
	/*public static void main( String[ ] args ) 
	{
		// C�digo para startar RMI REGISTRY sem necessidade do cmd "start rmiregistry"
		// no prompt
		try 
		{
			java.rmi.registry.LocateRegistry.createRegistry( Util.PORTA );
		} catch ( RemoteException e1 ) {
			System.out.println( "Ocorreu um erro ao executar o "
					+ "'createRegistry', o erro foi :" + e1 );
			e1.printStackTrace( );
		}

		try 
		{
			AutomatoInterface servidor = new ServiceAutomato( );
			
			Naming.rebind( Util.NOMEOBJDIST , servidor );
			
			System.out.println( "Servidor no ar" );
		
		} 
		catch ( Exception e )
		{
			e.printStackTrace( );
			System.out.println( "Algo deu errado: "+e );
		
		}
	}*/

}
