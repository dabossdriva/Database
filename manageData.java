/*
Name: Odessa Elie
Due: March 31, 2020
HOMEWORK 4

The goal of this assignment is to write a program that interacts with an end-user to insert, delete, and update records as well as handle queries on the data from a previously created database. Java interacts with the database via JDBC.
*/
 
import java.sql.*; 
import java.util.Scanner;
  
public class manageData  
{ 
	public static Statement statement = null;
	public static Connection connection = null;
	public static String username = "omelie"; 
    public static String password = "Queen"; 
	
	//This is the main method
    public static void main(String args[]) 
    { 
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			System.out.println("\nConnected to database\n");
			
			//calling the display menu method to display the user's options
			displayMenu();
		} 
        
		catch(Exception e) 
        { 
            System.out.println(e); 
        }
	}		
			
	public static void displayMenu()
	{
		System.out.println("Welcome to your database management system.\n");
		Scanner input = new Scanner(System.in); 
		int option;
			
		while (true)
		{
			System.out.println("\nPlease press the number that corresponds with what you would like to do.\n\n");
			System.out.println("1. Find all agents and clients in a given city");
			System.out.println("2. Add a new client, then purchase an available policy from a particular agent");
			System.out.println("3. List all polilcies sold by a particular agent");
			System.out.println("4. Cancel a policy");
			System.out.println("5. Add a new agent to a city");
			System.out.println("6. Quit");
			
			option = input.nextInt();  // Read user input
			
			if (option == 1)
			{
				displayAgents();
				displayClients();
				
				System.out.println("Please enter the city in which you would like to find agents and clients:\n");
				Scanner in = new Scanner(System.in);
				String city = in.nextLine();
				
				findAgents(city);
				findClients(city);
			}
			
			if (option == 2)
			{	
				displayClients();
				displaySold();
				
				purchasePolicy();
				
				System.out.println("Updated Policies sold table");
				displaySold();
				
			}
			
			if (option == 3)
			{
				displaySold();
				listPolicies();
			}
			
			if (option == 4)
			{
				displaySold();
				cancelPolicy();
				
				System.out.println("Updated policies sold table");
				displaySold();
			}
			
			if (option == 5)
			{
				displayAgents();
				addAgent();
				
				System.out.println("Updated agents table");
				displayAgents();
			}
			
			if ((option > 6) || (option < 1))
			{
				System.out.println("Please enter a number from 1 to 6");
			}
			if (option == 6)
				quit();
		}
	} 
	
	public static void findAgents(String acity)
	{
		//OPTION 1: Find all agents in a given city. Prompt the user for a city. Find and list all available agents from that location. Display all information of those agents.
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			String queryAgents = "SELECT A_ID, A_NAME, A_CITY, A_ZIP from AGENTS WHERE A_CITY = '"+ acity +"'";
			
            ResultSet result = statement.executeQuery(queryAgents); 
            
			if (result.next() == false)
			{
				System.out.println("There are no agents in this city");
			}
			else
			{
				//print the header
				System.out.println("AGENTS IN "+ acity);
				System.out.println("A_ID \t A_NAME \t A_CITY \t A_ZIP");
				
				do
				{	
					int aid = result.getInt("A_ID");
					String aname = result.getString("A_NAME");
					acity = result.getString("A_CITY");
					int azip = result.getInt("A_ZIP");
					System.out.println(aid + "\t " + aname + "\t\t" + acity + "\t " + azip);
				}					
				while (result.next());
			}
		} 
        
		catch(Exception e) 
        { 
			System.out.println("There are no agents in this city");
            System.out.println(e);			
        }		
	}

	public static void findClients(String ccity)
	{
		//OPTION 1: Find all clients in a given city. Prompt the user for a city. Find and list all available clients from that location. Display all information of those clients.
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			String queryClients = "SELECT C_ID, C_NAME, C_CITY, C_ZIP from CLIENTS WHERE C_CITY = '"+ ccity +"'";
			
            ResultSet result = statement.executeQuery(queryClients); 
            
			if (result.next() == false)
			{
				System.out.println("There are no clients in this city");
			}
			else
			{
				//print the header
				System.out.println("CLIENTS IN "+ ccity);
				System.out.println("C_ID \t C_NAME \t C_CITY \t C_ZIP");
				do
				{ 
					int cid = result.getInt("C_ID");
					String cname = result.getString("C_NAME");
					ccity = result.getString("C_CITY");
					int czip = result.getInt("C_ZIP");
					
					System.out.println(cid + "\t " + cname + "\t\t" + ccity + "\t " + czip);
				} 
				while (result.next());
			}
		} 
        
		catch(Exception e) 
        { 
			System.out.println(e);
        }		
	}
	
	public static void purchasePolicy()
	{
		//OPTION 2: Add a new client, then purchase an available policy from a particular agent.     	
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			Scanner in = new Scanner(System.in);
			
			//Prompt the user for their name, city and zip code. 
			System.out.println("Please enter the new client details:");
			System.out.println("Client Name: ");
			String cname = in.nextLine();
			System.out.println("Client City: ");
			String ccity = in.nextLine();
			System.out.println("Client Zip Code: ");
			int czip = in.nextInt();
				
			//Add the user info to CLIENT Table.
			int cid = 0;
			statement.executeUpdate("INSERT INTO CLIENTS " + "VALUES ('"+cid+"','"+cname+"','"+ccity+"', '"+czip+"')");
			
			//Then prompt the user for the TYPE of the policy that they want to purchase.
			System.out.println("Enter the type of policy that you want to purchase: ");
			in.nextLine();
			String type = in.nextLine();
			
			//If the policy type is found, display the available agent of client's city, then list all available policy. If not, print an error message.
			if ((type.equals("DENTAL"))|| (type.equals("LIFE")) || (type.equals("HOME")) || (type.equals("HEALTH")) || (type.equals("VEHICLE")))
			{
				findAgents(ccity);
				String queryPolicies = "SELECT POLICY_ID, NAME, TYPE, COMMISSION_PERCENTAGE from POLICY";
			
				ResultSet result1 = statement.executeQuery(queryPolicies); 
            
				if (result1.next() == false)
				{
					System.out.println("This policy does not exist.");
				}
				else
				{
					//print the header
					System.out.println("AVAILABLE POLICIES:");
					System.out.println("POLICY_ID \t NAME \t\t TYPE \t COMMISSION_PERCENTAGE");
					
					do
					{  
						int policyid = result1.getInt("POLICY_ID");
						String name = result1.getString("NAME");
						type = result1.getString("TYPE");
						int percent = result1.getInt("COMMISSION_PERCENTAGE");
						System.out.println(policyid + "\t\t " + name + "\t\t\t" + type + "\t " + percent);
					}
					while (result1.next());
				}			
			
				//Prompt the user for the POLICY_ID and AMOUNT for the policy that they want to purchase.
				System.out.println("Please enter the policy ID:");
				int policyid = in.nextInt();
				System.out.println("Please enter the policy amount:");
				int price = in.nextInt();
				System.out.println("Enter the agent ID for the agent you want to buy from:");
				int aid = in.nextInt();
				System.out.println("Here is the updated client table:");
				displayClients();
				System.out.println("Enter your client ID from the client table");
				int cid2 = in.nextInt();
				
				int pid = 0;
				//cid2 = cid;
				
				//Add the PURCHASE_ID, AGENT_ID, CLIENT_ID, POLICY_ID, DATE_PURCHASED and AMOUNT into the POLICIES_SOLD table.
				statement.executeUpdate("INSERT INTO POLICIES_SOLD " + "VALUES ('"+pid+"','"+aid+"', '"+cid2+"','"+policyid+"','2020-03-31', '"+price+"')");
				System.out.println("Client has been entered, policy has been purchased");
			}
			else
				System.out.println("This policy does not exist");
		} 
        
		catch(Exception e) 
        { 
            System.out.println(e); 
        }	
	}
	
	public static void listPolicies()
	{
		//OPTION 3: List all polilcies sold by a particular agent. Prompt the user for the A_NAME and city. If the agent is found, display all policies sold by that agent. Then display the (NAME, TYPE, COMMISION_PERCENTAGE) for all policies sold by that agent.
		
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			Scanner input = new Scanner(System.in);
			
			System.out.println("Please enter the name of the agent you would like to get details about:\n");
			String aname = input.nextLine();
			System.out.println("Enter the agent's city:");
			String acity = input.nextLine();
			
			String q1 = "SELECT NAME, TYPE, COMMISSION_PERCENTAGE from POLICY, AGENTS INNER JOIN POLICIES_SOLD ON AGENTS.A_ID = POLICIES_SOLD.AGENT_ID WHERE AGENTS.A_NAME = '"+ aname +"' AND POLICY.POLICY_ID = POLICIES_SOLD.POLICY_ID"; 
            ResultSet result = statement.executeQuery(q1); 
            
			if (result.next() == false)
			{
				System.out.println("This agent did not sell any policies, does not exist or doesn't work in your city.");
			}
			else
			{
				//print the header
				System.out.println("Policies sold by "+aname);
				System.out.println("NAME \t\t\t TYPE \t COMMISION_PERCENTAGE");
				
				do
				{ 
					String name = result.getString("NAME");
					String type = result.getString("TYPE");
					int commission = result.getInt("COMMISSION_PERCENTAGE");
					System.out.println(name + "\t\t" + type + "\t\t\t" + commission);
				} 
				while (result.next());
			}
		} 
        
		catch(Exception e) 
        { 
            System.out.println(e); 
        }	
			
	}
	
	public static void cancelPolicy()
	{
		//OPTION 4: Cancel a policy. Display all sold policies (PURCHASE_ID, AGENT_ID, CLIENT_ID, POLICY_ID, DATE_PURCHASED, AMOUNT). Prompt the user for the PURCHASED_ID of the policy that they wish to cancel. Remove that policy from the POLICIES_SOLD table.
		
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			Scanner input = new Scanner(System.in);
			
			String q1 = "SELECT PURCHASE_ID, AGENT_ID, CLIENT_ID, POLICY_ID, DATE_PURCHASED, AMOUNT from POLICIES_SOLD"; 
            ResultSet result = statement.executeQuery(q1); 
            
			//print the header
			System.out.println("All policies sold");
			System.out.println("PURCHASE ID AGENT ID CLIENT ID POLICY ID DATE PURCHASED AMOUNT");
			
			while (result.next()) 
            { 
				int pid = result.getInt("PURCHASE_ID");
				int aid = result.getInt("AGENT_ID");
				int cid = result.getInt("CLIENT_ID");
				int policyid = result.getInt("POLICY_ID");
				String date = result.getString("DATE_PURCHASED");
				int amount = result.getInt("AMOUNT");
				System.out.println(pid + "\t\t" + aid + "\t" + cid + "\t" + policyid +"\t" + date + "\t" +amount);
            } 
			
			System.out.println("Please enter the purchase ID of the policy you would like to cancel:\n");
			int pid = input.nextInt();
			
			System.out.println("Are you sure you want to cancel this policy? Press 1 to confirm and 0 to decline.");
			int num = input.nextInt();
			if (num == 1)
			{
				String del = "DELETE FROM POLICIES_SOLD " + "WHERE PURCHASE_ID = '"+pid+"'";
				statement.executeUpdate(del);	
				System.out.println("Purchase has been deleted");
			}
			
		} 
        
		catch(Exception e) 
        { 
            System.out.println(e); 
        }	
	}

	public static void addAgent()
	{
		//OPTION 5: Add a new agent to a city. Prompt the user for the (A_ID, A_NAME, A_CITY, A_ZIP) of the new agent. Insert it into the AGENTS table. Then, display all agents in that city.
		
		try
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			Scanner input = new Scanner(System.in);
			
			System.out.println("Please enter the city you want to add the new agent to:\n");
			String acity = input.nextLine();
			System.out.println("Please enter the new agent name:\n");
			String aname = input.nextLine();
			System.out.println("Please enter the new agent zip code:\n");
			int azip = input.nextInt();
			
			int aid = 0;
					
			statement.executeUpdate("INSERT INTO AGENTS " + "VALUES ('"+aid+"', '"+aname+"','"+acity+"', '"+azip+"')");
			
			String q1 = "SELECT A_ID, A_NAME, A_CITY, A_ZIP from AGENTS"+" WHERE A_CITY = '"+acity+"'"; 
            ResultSet result = statement.executeQuery(q1); 
            
			//print the header
			System.out.println("All agents in " + acity);
			System.out.println("A_ID A_NAME  A_CITY   A_ZIP");
			
			while (result.next()) 
            { 
				aid = result.getInt("A_ID");
				aname = result.getString("A_NAME");
				acity = result.getString("A_CITY");
				azip = result.getInt("A_ZIP");
				System.out.println(aid + " " + aname + " " + acity + "\t" + azip);
            } 
			
		} 
        
		catch(Exception e) 
        { 
            System.out.println(e); 
        }	
	}
	
	public static void quit()
	{
		//OPTION 6: Quit. Disconnect from the database and exit the program.
		try{
		Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
	
		connection.close();
		statement.close();
		System.out.println("Good bye");
		System.exit(0);}
	catch(Exception e) 
        { 
            System.out.println(e); 
        }	
	
	}
	
	public static void displayAgents()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			String queryAgents = "SELECT * from AGENTS";
			
            ResultSet result = statement.executeQuery(queryAgents); 
            
			//print the header
			System.out.println("Here are all the agents in the table");
			System.out.println("A_ID \t A_NAME \t A_CITY \t A_ZIP");
			
			while (result.next()) 
            { 
				int aid = result.getInt("A_ID");
				String aname = result.getString("A_NAME");
				String acity = result.getString("A_CITY");
				int azip = result.getInt("A_ZIP");
				System.out.println(aid + "\t " + aname + "\t\t" + acity + "\t " + azip);
			} 
		}
		catch(Exception e) 
        { 
            System.out.println(e); 
        }	
		
	}
	
	public static void displayClients()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			String queryAgents = "SELECT * from CLIENTS";
			
            ResultSet result = statement.executeQuery(queryAgents); 
            
			//print the header
			System.out.println("Here are all the clients in the table");
			System.out.println("C_ID \t C_NAME \t C_CITY \t C_ZIP");
			
			while (result.next()) 
            { 
				int cid = result.getInt("C_ID");
				String cname = result.getString("C_NAME");
				String ccity = result.getString("C_CITY");
				int czip = result.getInt("C_ZIP");
				System.out.println(cid + "\t " + cname + "\t\t" + ccity + "\t " + czip);
			} 
		}
		catch(Exception e) 
        { 
            System.out.println(e); 
        }	
	}
	
	public static void displayPolicies()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			String queryAgents = "SELECT * from POLICY";
			
            ResultSet result = statement.executeQuery(queryAgents); 
            
			//print the header
			System.out.println("Here are all the available policies in the table");
			System.out.println("POLICY_ID \t NAME \t TYPE \t COMMISSION_PERCENTAGE");
			
			while (result.next()) 
            { 
				int policyid = result.getInt("POLICY_ID");
				String name = result.getString("NAME");
				String type = result.getString("TYPE");
				int commission = result.getInt("COMMISSION_PERCENTAGE");
				System.out.println(policyid + "\t " + name + "\t\t" + type + "\t " + commission);
			} 
		}
		catch(Exception e) 
        { 
            System.out.println(e); 
        }
	}
	
	public static void displaySold()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); 
            
			//Establishing the connection to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/omelie", username, password); 
            statement = connection.createStatement();
			
			String queryAgents = "SELECT * from POLICIES_SOLD";
			
            ResultSet result = statement.executeQuery(queryAgents); 
            
			//print the header
			System.out.println("Here are all the policies sold in the table");
			System.out.println("PURCHASE_ID AGENT_ID CLIENT_ID POLICY_ID DATE_PURCHASED AMOUNT");
			
			while (result.next()) 
            { 
				int pid = result.getInt("PURCHASE_ID");
				int aid = result.getInt("AGENT_ID");
				int cid = result.getInt("CLIENT_ID");
				int policyid = result.getInt("POLICY_ID");
				String date = result.getString("DATE_PURCHASED");
				int amount = result.getInt("AMOUNT");
				System.out.println(pid + "\t " + aid + "\t" + cid + "\t " + policyid + "\t" + date + "\t " + amount);
			} 
		}
		catch(Exception e) 
        { 
            System.out.println(e); 
        }
	}
}