import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class TextAnalysis
{
	static void record(Sms currentData, ArrayList<Contact> recordList)
	{
		for (Contact contactIter : recordList)
		{
			if (currentData.address.equals(contactIter.address))
			{
				contactIter.add(currentData);
				return;
			}
		}
		recordList.add(new Contact(currentData));
	}

	static public ArrayList<Contact> read(String file)
	{
		ArrayList<Contact> contactData = new ArrayList<Contact>();
		try
		{
			File messageFile = new File("SignalPlaintextBackup.xml");
			Scanner messages = new Scanner(messageFile);
			boolean searching = true;
			messages.nextLine();
			messages.nextLine();
			messages.nextLine();
			while (messages.hasNextLine() && searching)
			{
				String info = messages.nextLine();
				if (info.equals("</smses>"))
				{
					searching = false;
					break;
				}
				record(new Sms(info), contactData); //Add the data to the ArrayList
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return contactData;
	}

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		System.out.println("What is the name of the backup file?");
		String filename = in.next();
		ArrayList<Contact> data = read(filename);
		for (Contact contactIter : data)
		{
			System.out.println(contactIter.asString());
		}
	}
}
