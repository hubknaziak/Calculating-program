package lab06_pop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Choice;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Klient {  

	private JFrame frmKlient;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField_3;
	
    private static final String HOST = "localhost";
	
	static Socket socket = null;
	private PrintWriter out;
    private BufferedReader in;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Klient window = new Klient();
					window.frmKlient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Klient() {
		initialize();
	}
	
	private void initialize() {
		frmKlient = new JFrame();
		frmKlient.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmKlient.setTitle("Klient");
		frmKlient.setBounds(100, 100, 450, 374);
		frmKlient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKlient.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(89, 11, 86, 20);
		frmKlient.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPort.setBounds(33, 14, 46, 14);
		frmKlient.getContentPane().add(lblPort);
		
		JLabel lblDane = new JLabel("Dane:");
		lblDane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDane.setBounds(33, 43, 46, 14);
		frmKlient.getContentPane().add(lblDane);
		
		JLabel lblX = new JLabel("x:");
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblX.setBounds(33, 68, 17, 14);
		frmKlient.getContentPane().add(lblX);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setBounds(53, 66, 86, 20);
		frmKlient.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblY = new JLabel("y:");
		lblY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblY.setBounds(183, 69, 25, 14);
		frmKlient.getContentPane().add(lblY);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_2.setBounds(204, 65, 86, 20);
		frmKlient.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblDzialanie = new JLabel("Dzialanie:");
		lblDzialanie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDzialanie.setBounds(33, 109, 63, 20);
		frmKlient.getContentPane().add(lblDzialanie);
		
		JRadioButton rdbtnDodawanie = new JRadioButton("Dodawanie");
		rdbtnDodawanie.setSelected(true);
		buttonGroup.add(rdbtnDodawanie);
		rdbtnDodawanie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnDodawanie.setBounds(33, 136, 109, 23);
		frmKlient.getContentPane().add(rdbtnDodawanie);
		
		JRadioButton rdbtnOdejmowanie = new JRadioButton("Odejmowanie");
		buttonGroup.add(rdbtnOdejmowanie);
		rdbtnOdejmowanie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnOdejmowanie.setBounds(183, 137, 109, 23);
		frmKlient.getContentPane().add(rdbtnOdejmowanie);
		
		JRadioButton rdbtnMnoenie = new JRadioButton("Mnozenie");
		buttonGroup.add(rdbtnMnoenie);
		rdbtnMnoenie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMnoenie.setBounds(33, 172, 109, 23);
		frmKlient.getContentPane().add(rdbtnMnoenie);
		
		JRadioButton rdbtnDzielenie = new JRadioButton("Dzielenie");
		buttonGroup.add(rdbtnDzielenie);
		rdbtnDzielenie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnDzielenie.setBounds(183, 172, 109, 23);
		frmKlient.getContentPane().add(rdbtnDzielenie);
		
		JRadioButton rdbtnPierwiastek = new JRadioButton("Pierwiastek");
		buttonGroup.add(rdbtnPierwiastek);
		rdbtnPierwiastek.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnPierwiastek.setBounds(33, 208, 109, 23);
		frmKlient.getContentPane().add(rdbtnPierwiastek);
		
		JRadioButton rdbtnKwadrat = new JRadioButton("Kwadrat");
		buttonGroup.add(rdbtnKwadrat);
		rdbtnKwadrat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnKwadrat.setBounds(33, 240, 109, 23);
		frmKlient.getContentPane().add(rdbtnKwadrat);
		
		JLabel lblOdpowiedz = new JLabel("Komunikat:");
		lblOdpowiedz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOdpowiedz.setBounds(33, 286, 86, 20);
		frmKlient.getContentPane().add(lblOdpowiedz);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_3.setBounds(129, 288, 295, 20);
		frmKlient.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnDodaj = new JButton("Dodaj");

		
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(arg0.getSource()==btnDodaj)
				{
					String x = textField_1.getText();
					String y = textField_2.getText();
					if(x.isEmpty() || y.isEmpty()) 
					{
						JOptionPane.showMessageDialog(null, "Nalezy podac obie wartosci!");
						return;
					}
					String komenda="";
					if(rdbtnDodawanie.isSelected())  komenda = "+;"+x+";"+y+"\n";
					if(rdbtnOdejmowanie.isSelected())  komenda = "-;"+x+";"+y+"\n";
					if(rdbtnMnoenie.isSelected())  komenda = "*;"+x+";"+y+"\n";
					if(rdbtnDzielenie.isSelected())  komenda = "/;"+x+";"+y+"\n";
					if(rdbtnKwadrat.isSelected())  komenda = "^;"+x+";"+y+"\n";
					if(rdbtnPierwiastek.isSelected())  komenda = "sqrt;"+x+";"+y+"\n";
					String czynnosc = "dodaj";
					
					out.write(czynnosc+"!"+komenda);
					out.flush();
					textField_3.setText("Wyslano dane");
					
				}
				
			}
		});
		btnDodaj.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDodaj.setBounds(183, 208, 89, 23);
		frmKlient.getContentPane().add(btnDodaj);
		
		JButton btnUsun = new JButton("Usun");
		btnUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==btnUsun)
				{
					String x = textField_1.getText();
					String y = textField_2.getText();
					if(x.isEmpty() || y.isEmpty()) 
					{
						JOptionPane.showMessageDialog(null, "Nalezy podac obie wartosci!");
						return;
					}
					String komenda="";
					if(rdbtnDodawanie.isSelected())  komenda = "+;"+x+";"+y+"\n";
					if(rdbtnOdejmowanie.isSelected())  komenda = "-;"+x+";"+y+"\n";
					if(rdbtnMnoenie.isSelected())  komenda = "*;"+x+";"+y+"\n";
					if(rdbtnDzielenie.isSelected())  komenda = "/;"+x+";"+y+"\n";
					if(rdbtnKwadrat.isSelected())  komenda = "^;"+x+";"+y+"\n";
					if(rdbtnPierwiastek.isSelected())  komenda = "sqrt;"+x+";"+y+"\n";
					String czynnosc = "usun";
					
					out.write(czynnosc+"!"+komenda);
					out.flush();
					textField_3.setText("Wyslano dane");
				}
			}
		});
		btnUsun.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUsun.setBounds(183, 240, 89, 23);
		frmKlient.getContentPane().add(btnUsun);
		
		JButton btnPolacz = new JButton("Polacz");
		btnPolacz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==btnPolacz)
				{
					int port;
					String przejscie=textField.getText();
					port = Integer.parseInt(przejscie);
					btnPolacz.setSelected(true);
					try {
						socket = new Socket(HOST, port);
						out = new PrintWriter(socket.getOutputStream(),true);
					    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					} catch (UnknownHostException e1) {
						JOptionPane.showMessageDialog(null, "Blad Hosta");
					}catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Podane porty sa od siebie rozne");
					}
				}
				textField_3.setText("Nawiazano polaczenie");
			}
		});
		btnPolacz.setBounds(222, 11, 89, 23);
		frmKlient.getContentPane().add(btnPolacz);
		
		JButton btnRozlacz = new JButton("Rozlacz");
		btnRozlacz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==btnRozlacz)
				{

					String czynnosc = "zerwij";
					out.write(czynnosc+"!"+"..."+"\n");
					out.flush();
					try 
					{
						out.close();
						in.close();
						socket.close();
					} catch (IOException e1) 
					{
						JOptionPane.showMessageDialog(null,"Niepowodzenia zamkniecia polaczenia");
					}
					textField_3.setText("Zerwano polaczenie");	
				}
			}
		});
		btnRozlacz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRozlacz.setBounds(335, 11, 89, 23);
		frmKlient.getContentPane().add(btnRozlacz);
		
		
	}
}
