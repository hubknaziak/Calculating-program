package lab06_pop;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;

public class Rejestrator extends Thread { 
	
	private static ServerSocket serverSocket;
    private static Socket clientSocket;

	private JFrame frmRejestrator;
	public String id;
	public String zadanie;
	public String status;
	public String wynik;
	public static List<Rejestrator> dane = new ArrayList<Rejestrator>();
	private static JTextField textField;
	private static TextField textField_2;
	private static TextArea textArea;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				serverSocket = null;
				try {
					Rejestrator window = new Rejestrator();
					window.frmRejestrator.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Rejestrator() {
		initialize();
	}
	
	public Rejestrator(Socket s)
	{
		clientSocket=s;
	}
	
	public Rejestrator(String id, String zadanie, String status, String wynik)
	{
		this.id=id;
		this.zadanie=zadanie;
		this.status=status;
		this.wynik=wynik;
	}
	
	public synchronized String wczytaj()
	{
		dane.removeAll(dane);
		String komunikat = "";
		Scanner s = null;
		try
		{
			File plik = new File("Dane.txt");
			s = new Scanner(plik);
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Blad przy odczycie pliku");
		} 
		
			String typ="";
			while(s.hasNextLine())
			{
			String linia = s.nextLine();
			String tekst[] = null;
			tekst = linia.split(":");
			Rejestrator r1 = new Rejestrator(tekst[0],tekst[1],tekst[2],tekst[3]);
			dane.add(r1);
			String zadanie[]=null;
			zadanie = tekst[1].split(";");
			if(zadanie[0].equals("+")) typ="Dodawanie";
			else if(zadanie[0].equals("-")) typ="Odejmowanie";
			else if(zadanie[0].equals("*")) typ="Mnozenie";
			else if(zadanie[0].equals("/")) typ="Dzielenie";
			else if(zadanie[0].equals("^")) typ="Kwadrat";
			else if(zadanie[0].equals("sqrt")) typ="Pierwiastek";
			
			komunikat = komunikat + "ID zadania:" + tekst[0] + "\t  Typ zadania: " + typ + "\t  Wartosci: " +  zadanie[1] + "," + zadanie[2] + "\t  Status: " + tekst[2] + "\t  Wynik: " + tekst[3] + "\n";
		}
			s.close();
			return komunikat;
	}
	
	public synchronized void utworz(String kod)
	{
		String[] tekst = new String[4];
		int licznik=0;
		int id=0;
		for(int i=0; i<dane.size();i++)
		{
			licznik++;
		}
		id=licznik;
		tekst[0]=Integer.toString(id);
		String[] wyrazenie = kod.split("!");
		tekst[1]=wyrazenie[1];
		tekst[2]="zgloszone";
		tekst[3]="null";
		Rejestrator r1 = new Rejestrator(tekst[0],tekst[1],tekst[2],tekst[3]);
		dane.add(r1);
		
		PrintWriter zapis = null;
		try
		{
			 zapis = new PrintWriter(new FileWriter("Dane.txt",true));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Blad przy odczycie pliku");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		zapis.println();
		zapis.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
		zapis.close();
	}
	
	public synchronized void usun(String kod)
	{
		String[] wyrazenie = kod.split("!");
		Scanner s1 = null;
		Scanner s2 = null;
		PrintWriter zapis1 = null;
		PrintWriter zapis2 = null;
		
		try
		{
			File plik1 = new File("Dane.txt");
			s1 = new Scanner(plik1);
			zapis1 = new PrintWriter("Pomocniczy.txt");
			zapis2 = new PrintWriter(new FileWriter("Pomocniczy.txt",true));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "B³ad przy odczycie pliku");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int licznik = 0;
		while(s1.hasNextLine())
		{
			String linia = s1.nextLine();
			String tekst[] = null;
			tekst = linia.split(":");
			if(wyrazenie[1].equals(tekst[1]) && (tekst[2].equals("zgloszone") || tekst[2].equals("wykonane")) )
			{
				if(licznik==0)
				{
					zapis1.print(tekst[0]+":"+tekst[1]+":"+"usunieto"+":"+ "Usuniety");
				}
				else
				{
					zapis2.println();
					zapis2.print(tekst[0]+":"+tekst[1]+":"+"usunieto"+":"+"Usuniety");
				}
			}
			else
			{
				if(licznik == 0)
				{
					zapis1.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
				}
				else
				{
					zapis2.println();
					zapis2.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
				}
			}
			licznik++;
		}
		zapis1.close();
		zapis2.close();
		s1.close();
		
		PrintWriter zapis3 = null;
		PrintWriter zapis4 = null;
		try
		{
			File plik2 = new File("Pomocniczy.txt");
			s2 = new Scanner(plik2);
			zapis3 = new PrintWriter("Dane.txt");
			zapis4 = new PrintWriter(new FileWriter("Dane.txt",true));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "B³ad przy odczycie pliku");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		licznik = 0;
		while(s2.hasNextLine())
		{
			String linia = s2.nextLine();
			String tekst[] = null;
			tekst = linia.split(":");
			if(licznik==0)
			{
				zapis3.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
			}
			else
			{
				zapis4.println();
				zapis4.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
			}
			licznik++;
		}
		zapis3.close();
		zapis4.close();
		s2.close();
	}
	
	private synchronized void wyniki(String wynik, Integer id)
	{
		Scanner s1 = null;
		Scanner s2 = null;
		PrintWriter zapis1 = null;
		PrintWriter zapis2 = null;
		
		try
		{
			File plik1 = new File("Dane.txt");
			s1 = new Scanner(plik1);
			zapis1 = new PrintWriter("Pomocniczy.txt");
			zapis2 = new PrintWriter(new FileWriter("Pomocniczy.txt",true));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "B³ad przy odczycie pliku");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int licznik = 0;
		while(s1.hasNextLine())
		{
			String linia = s1.nextLine();
			String tekst[] = null;
			tekst = linia.split(":");
			String numer = Integer.toString(id);
			if(tekst[0].equals(numer) && dane.get(id).status.equals("pobrane"))
			{
				dane.get(id).status="wykonano";
				if(licznik==0)
				{
					zapis1.print(tekst[0]+":"+tekst[1]+":"+"wykonano"+":"+ wynik);
				}
				else
				{
					zapis2.println();
					zapis2.print(tekst[0]+":"+tekst[1]+":"+"wykonano"+":"+wynik);
				}
			}
			else
			{
				if(licznik == 0)
				{
					zapis1.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
				}
				else
				{
					zapis2.println();
					zapis2.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
				}
			}
			licznik++;
		}
		zapis1.close();
		zapis2.close();
		s1.close();
		
		PrintWriter zapis3 = null;
		PrintWriter zapis4 = null;
		try
		{
			File plik2 = new File("Pomocniczy.txt");
			s2 = new Scanner(plik2);
			zapis3 = new PrintWriter("Dane.txt");
			zapis4 = new PrintWriter(new FileWriter("Dane.txt",true));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "B³ad przy odczycie pliku");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		licznik = 0;
		while(s2.hasNextLine())
		{
			String linia = s2.nextLine();
			String tekst[] = null;
			tekst = linia.split(":");
			if(licznik==0)
			{
				zapis3.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
			}
			else
			{
				zapis4.println();
				zapis4.print(tekst[0]+":"+tekst[1]+":"+tekst[2]+":"+tekst[3]);
			}
			licznik++;
		}
		zapis3.close();
		zapis4.close();
		s2.close();
	}
	
	
	
	private void initialize() {
		frmRejestrator = new JFrame();
		frmRejestrator.setTitle("Rejestrator");
		frmRejestrator.setBounds(100, 100, 450, 378);
		frmRejestrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRejestrator.getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPort.setBounds(10, 11, 46, 14);
		frmRejestrator.getContentPane().add(lblPort);
		
		this.textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(49, 8, 86, 20);
		frmRejestrator.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblKomunikat = new JLabel("Komunikat:");
		lblKomunikat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKomunikat.setBounds(35, 285, 70, 20);
		frmRejestrator.getContentPane().add(lblKomunikat);
		
		this.textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(35, 67, 371, 192);
		frmRejestrator.getContentPane().add(textArea);
		
		JLabel label = new JLabel("Zadania:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(188, 41, 60, 20);
		frmRejestrator.getContentPane().add(label);
		
		JButton btnPolacz = new JButton("Polacz");
		btnPolacz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==btnPolacz)
				{
					btnPolacz.setSelected(true);
					
					try
			    	{
						int port;
						String przejscie=textField.getText();
						port = Integer.parseInt(przejscie);
			    		serverSocket = new ServerSocket(port);
			    		  while(true)
			    	        {
			    			  Socket s = serverSocket.accept();
			    			  Rejestrator r1 = new Rejestrator(s);	  
			    			  r1.start();
			    	        }
			    	 }
			    	 catch(Exception e1){}      
				}
			}
		});
		btnPolacz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnPolacz.setBounds(235, 7, 89, 23);
		frmRejestrator.getContentPane().add(btnPolacz);
		
		String komunikat = wczytaj();
    	textArea.setText(komunikat);
    	
    	JButton btnR = new JButton("Rozlacz");
    	btnR.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) 
    		{
    			try 
    			{
					serverSocket.close();
				} catch (IOException e1) 
    			{
					e1.printStackTrace();
				}
    			textField_2.setText("Zerwano polaczenie");
    		}
    	});
    	btnR.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	btnR.setBounds(334, 8, 89, 23);
    	frmRejestrator.getContentPane().add(btnR);
    	
    	this.textField_2 = new TextField();
    	textField_2.setEditable(false);
    	textField_2.setBounds(111, 283, 295, 22);
    	frmRejestrator.getContentPane().add(textField_2);
		
	}
	 public void run()
     {
         try
         {
         	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
         	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             while(true)
             {
             	 String polecenia[] = new String [2];
                 String input = in.readLine();
                 polecenia = input.split("!");
                 if(polecenia[0].equals("dodaj"))
                 {
                       utworz(input);
                       textField_2.setText("Zadanie zostalo dodane pomyslnie!");
                       String komunikat = wczytaj();
                       textArea.setText(komunikat);
                       Thread.sleep(1000);
                 }
                 if(polecenia[0].equals("usun"))
                 {
                 	 usun(input);
                 	 textField_2.setText("Zadanie zostalo usuniete pomyslnie!");
                     String komunikat = wczytaj();
                     textArea.setText(komunikat);
                     Thread.sleep(1000);
                 }
                 if(polecenia[0].equals("oblicz"))
                 {
                 	String wynik="";
                 	String szukane;
                 	int id=0;
                 	for(int i=0; i<dane.size(); i++)
                 	{
                 		szukane = dane.get(i).id;
                 		if(polecenia[1].equals(szukane) && dane.get(i).wynik.equals("null") && dane.get(i).status.equals("zgloszone"))
                 		{
                 			id=i;
                 			dane.get(id).status="pobrane";
                 			out.write(dane.get(id).zadanie+"\n");
                 			out.flush();
                 		}
                 		if(polecenia[1].equals(szukane) && !dane.get(i).wynik.equals("null") && !dane.get(i).status.equals("zgloszone"))
                 		{
                 			textField_2.setText("To zadanie zostalo usuniete lub wykonane!");
                 			out.write("Error"+"\n");
                 			out.flush();
                 		}
                 	}
                 	boolean czytaj=true;
     				try 
     				{
     					while(czytaj==true)
     					{
     						wynik = in.readLine();
     						if(!wynik.equals("")) czytaj=false;
     					}
     				} 
     				catch (IOException e1) 
     				{
     					e1.printStackTrace();
     				}
     				if(!wynik.equals("Error"))
     				{
	                 	textField_2.setText("Obliczenia zostaly wykonane pomyslnie!");
	                 	wyniki(wynik,id);
	                 	String komunikat = wczytaj();
	                    textArea.setText(komunikat);
	                    Thread.sleep(1000);
     				}
                 }
                 if(polecenia[0].equals("zerwij"))
                 {
                 	out.close();
                    in.close();
                    clientSocket.close();
                    serverSocket.close();
                    textField_2.setText("Zerwano polaczenie");
                    break;
                 }
             }
        }
         catch(Exception e)
         {
         	
         }
     }
}
