package lab06_pop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Egzekutor extends Thread { 

	
    private static final String HOST = "localhost";
	 
	static Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;
	    
	private JFrame frmEgzekutor;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnPolacz;
	private JButton btnNewButton;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Egzekutor window = new Egzekutor();
					window.frmEgzekutor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Egzekutor() {
		initialize();
	}

	
	private void initialize() {
		frmEgzekutor = new JFrame();
		frmEgzekutor.setTitle("Egzekutor");
		frmEgzekutor.setBounds(100, 100, 436, 218);
		frmEgzekutor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEgzekutor.getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPort.setBounds(25, 11, 46, 14);
		frmEgzekutor.getContentPane().add(lblPort);
		
		textField = new JTextField();
		textField.setBounds(64, 9, 86, 20);
		frmEgzekutor.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblIdZadania = new JLabel("ID zadania:");
		lblIdZadania.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIdZadania.setBounds(53, 70, 74, 20);
		frmEgzekutor.getContentPane().add(lblIdZadania);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setBounds(137, 70, 86, 20);
		frmEgzekutor.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnWykonaj = new JButton("Wykonaj");
		btnWykonaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String id = "";
				id = textField_1.getText();
				if(id.isEmpty()) 
				{
					JOptionPane.showMessageDialog(null, "Nalezy podac numer id zadania!");
					return;
				}
				String czynnosc = "oblicz";
				out.write(czynnosc+"!"+id+"\n");
				out.flush();
				textField_2.setText("Wyslano dane");
				String kod="";
				boolean czytaj=true;
				try {
					while(czytaj==true)
					{
						kod = in.readLine();
						if(!kod.equals("")) czytaj=false;
					}
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				if(kod.equals("Error")) 
				{
					out.write("Error"+"\n"); 
					out.flush();
					return;
				}
				else
				{
					String dane[] = new String[3];
					dane = kod.split(";");
					double x = Double.parseDouble(dane[1]);
					double y = Double.parseDouble(dane[2]);
					double wynik1 = 0;
					double wynik2 = 0;
					if(dane[0].equals("+")) wynik1=x+y;
					else if(dane[0].equals("-")) wynik1=x-y;
					else if(dane[0].equals("*")) wynik1=x*y;
					else if(dane[0].equals("/")) wynik1=x/y;
					else if(dane[0].equals("^")) {wynik1=x*x; wynik2=y*y;}
					else if(dane[0].equals("sqrt")) {wynik1=Math.sqrt(x); wynik2=Math.sqrt(y);}
					
					String wynik="";
					if(wynik2==0)
					{
						wynik = Double.toString(wynik1);
					}
					else
					{
						 wynik = Double.toString(wynik1) +","+ Double.toString(wynik2);
					}
					textField_2.setText("Wynik zadania wynosi: "+ wynik);
					out.write(wynik+"\n");
					out.flush();
				}
			}
		});
		btnWykonaj.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnWykonaj.setBounds(268, 69, 89, 23);
		frmEgzekutor.getContentPane().add(btnWykonaj);
		
		JLabel lblNewLabel = new JLabel("Wynik:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(53, 117, 46, 14);
		frmEgzekutor.getContentPane().add(lblNewLabel);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_2.setBounds(137, 114, 220, 20);
		frmEgzekutor.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		btnPolacz = new JButton("Polacz");
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
					} 
					catch (UnknownHostException e1) 
					{
						JOptionPane.showMessageDialog(null, "Blad Hosta");
					}
					catch (IOException e1) 
					{
						JOptionPane.showMessageDialog(null, "Podane porty sa od siebie rozne");
					}
				}
				textField_2.setText("Nazwiazano polaczenie");
				}
			
		});
		btnPolacz.setBounds(211, 8, 89, 23);
		frmEgzekutor.getContentPane().add(btnPolacz);
		
		btnNewButton = new JButton("Rozlacz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==btnNewButton)
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
					textField_2.setText("Zerwano polaczenie");	
				}
				}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(321, 7, 89, 23);
		frmEgzekutor.getContentPane().add(btnNewButton);
	}
}
