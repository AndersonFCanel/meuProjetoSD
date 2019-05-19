package com.suamSD.tela;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;

public class Tela extends JPanel {

	/**
	 * Create the panel.
	 */
	public Tela() {
		setBorder(new CompoundBorder());
		setBackground(Color.GRAY);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Taxa de Câmbio");
		lblNewLabel.setBounds(159, 5, 132, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBackground(Color.GRAY);
		add(lblNewLabel);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(191, 215, 89, 23);
		add(btnEnviar);

	}
}
