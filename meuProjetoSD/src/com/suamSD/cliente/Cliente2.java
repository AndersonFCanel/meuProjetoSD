//package com.suamSD.cliente;
//
//import java.rmi.Naming;
//
//import javax.swing.JOptionPane;
//import com.suamSD.AutomatoInterface;
//import com.suamSD.Util;
//
//public class Cliente2 {
//
//	private static Double valor;
//
//	public static void main(String[] args) {
//
//		try {
//			// invocando servidor
//			AutomatoInterface obj = (AutomatoInterface) Naming.lookup( "rmi://localhost/" 
//																+ Util.NOMEOBJDIST );
//
//			valor = Double.parseDouble( JOptionPane.showInputDialog(
//					null, "Informe o valor a converter",
//					"Valor ", JOptionPane.QUESTION_MESSAGE ) );
//
//			obj.setValor( valor );
//
//			JOptionPane.showMessageDialog( null, " Voc� digitou:\n " + obj.getValor( ) );
//			
//			obj.calculaValorTaxa( );
//
//			while( valor != null ) 
//			{
//				// valor = JOptionPane.showInputDialog("Digite uma mensagem para um servidor: ");
//				// obj.setTaxa(resp);
//				// JOptionPane.showMessageDialog(null, " Voc� digitou:\n " + obj.getTaxa());
//			}
//			if ( valor == null )
//				System.out.println( "Cliente cancelou a opera��o" );
//		} catch( Exception e ) 
//		{
//			e.printStackTrace( );
//		}
//
//	}
//
//}
