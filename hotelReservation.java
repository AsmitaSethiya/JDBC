import java.sql.*;
import java.util.Scanner;

class hotelReservation
{
	public static void main(String args[])
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			 String url = "jdbc:mysql://localhost:3306/hotel_db";
			 String username = "root";
			 String password = "System#123";
			
			Connection con = DriverManager.getConnection(url,username,password);
		
			//menu 
			while(true)
			{
				System.out.println();
				System.out.println("HOTEL MANAGEMENT SYSTEM");
				Scanner scanner = new Scanner(System.in);
				System.out.println("1. Reserve a Room");
				System.out.println("2. View Reservation");
				System.out.println("3. Get Room Number");
				System.out.println("4. Update Reservation Details");
				System.out.println("5. Delete Reservation");
				System.out.println("0. Exit");
				System.out.println("Choose an option: ");
				int choice = scanner.nextInt();
				
				switch(choice)
				{
				case 1:
					reserveRoom(con, scanner);
					break;
				case 2:
					viewReserveation(con);
					break;
				case 3:
					getRoomNumber(con, scanner);
					break;
				case 4:
					updateReservation(con,scanner);
					break;
				case 5:
					deleteReservation(con,scanner);
					break;
				case 0:
					exit();
					scanner.close();
					return;
				default:
					System.out.println("Invalid choice, Try Again...");
					
				}
				
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private static void reserveRoom(Connection con, Scanner scanner)
	{
		
		try
		{
			String q = "insert into reservation (guest_name,room_number,contact_number) values(?,?,?)";
			
			PreparedStatement st = con.prepareStatement(q);
			
			System.out.println("Enter guest name: ");
			String guestname = scanner.next();
			scanner.nextLine();
			System.out.println("Enter guest room number: ");
			int roomno = scanner.nextInt();
			System.out.println("Enter guest contact number: ");
			String contactno = scanner.next();
			
		
			
			st.setString(1,guestname);
			st.setInt(2,roomno);
			st.setString(3,contactno);
			int affextedRow = st.executeUpdate();
			
			if(affextedRow > 0)
			{
				System.out.println("Reservation Successful...");
			}
			else
			{
				System.out.println("Reservation Failed...");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	
	}
	
	private static void viewReserveation(Connection con)
	{
		
		try
		{
			Statement st = con.createStatement();
			String q  = "Select * from reservation";
			
			ResultSet set = st.executeQuery(q);
			
			while(set.next())
			{
				int reservationid =  set.getInt(1);
				String guestname = set.getString(2);
				int roomno = set.getInt(3);
				String contactno = set.getString(4);
				String reservationtime = set.getTimestamp(5).toString();
				
				System.out.println(reservationid + " " + guestname + " " + roomno + " " + contactno + " " + reservationtime  );
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
		
	private static void getRoomNumber(Connection con, Scanner scanner)
	{
		try
		{
			System.out.println("Enter guest name: ");
			String guestname = scanner.next();
			System.out.println("Enter reservation id: ");
			int reservationid = scanner.nextInt();
			
			Statement st = con.createStatement();
			String q = "select room_number from reservation where guest_name = '"+guestname+"' and reservation_id = '" + reservationid +"'";
	
			ResultSet set  = st.executeQuery(q);
			
			if(set.next())
			{
				int roomNumber = set.getInt("room_number");
				System.out.println("Room no for guest : " + guestname + "and reservation id : " + reservationid + "is " + roomNumber);
			}
			else
			{
				System.out.println("Room no not found for given guest name and reservation ID");
			}
				
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void updateReservation(Connection con, Scanner scanner)
	{
		try
		{
			System.out.println("Enter Reservation id to update");
			int id = scanner.nextInt();
			scanner.nextLine();
			
			System.out.println("Enter new guest name");
			String newGuestName = scanner.nextLine();
			System.out.println("Enter new room number");
			int newroomno = scanner.nextInt();
			System.out.println("Enter new contact number");
			String newContactNo = scanner.next();
			
			String q = "update reservation set guest_name = '"+newGuestName+"' ,  room_number = "+newroomno+" , contact_number = '"+newContactNo+"' where reservation_id = "+id+"";
			
			Statement st = con.createStatement();
			int affectrow = st.executeUpdate(q);
			
			if(affectrow  > 0)
			{
				System.out.println("Reservation details updated successfully !!");
			}
			else
			{
				System.out.println("Reservation details updation failed !!");
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	private static void deleteReservation(Connection con, Scanner scanner)
	{
		try
		{
			System.out.println("Enter reservation id to delete: ");
			int id = scanner.nextInt();
			
			String q = "Delete from reservation where reservation_id = "+id+"";
			
			Statement st = con.createStatement();
			int rownum = st.executeUpdate(q);
			
			if(rownum > 0)
			{
				System.out.println("Reservation deleted successfully!!");
			}
			else
			{
				System.out.println("Reservation deletion failed!!");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private static void exit() throws InterruptedException
	{
		System.out.println("Existing system");
		
		int i  = 5;
		while(i!=0)
		{
			System.out.print(".");
			Thread.sleep(450);
			i--;
		}
		System.out.println();
		System.out.println("Thank you for using Hotel Reservation System");
	}
}	

