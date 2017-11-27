class Contact
{
// <sms protocol="0" address="+17575377286" contact_name="null" date="1432917139711" readable_date="Fri, 29 May 2015 09:32:19 MST" type="2" subject="null" body="WHAT&apos;S THE THIRD" toa="null" sc_toa="null" service_center="null" read="1" status="-1" locked="0" />
	public String address;
	public String contact_name;
	public int sentTo;
	public int recievedFrom;
	public int characterCountTo;
	public int characterCountFrom;

	public Contact(Sms firstMessage)
	{
		this.sentTo = this.recievedFrom = 0;
		this.characterCountTo = this.characterCountFrom = 0;
		this.address = firstMessage.address;
		this.contact_name = firstMessage.contact_name;
		this.add(firstMessage);
	}

	public void add(Sms message)
	{
		if (message.type == 1) // The contact sent a message to the owner of the backup
		{
			this.recievedFrom++;
			this.characterCountFrom += message.body.length();
		}
		else if (message.type == 2) // The owner of the backup sent a message to the contact
		{
			this.sentTo++;
			this.characterCountTo += message.body.length();
		}
	}

	public String asString()
	{
		int decimalPrecision = 3;

		String contactAsString = "";

		String messageRatio = "";
		if (this.sentTo == 0)
		{
			messageRatio = "0";
		}
		else if (this.recievedFrom == 0)
		{
			messageRatio = "Inf";
		}
		else
		{
			messageRatio = "" + (double)this.sentTo/this.recievedFrom;
			if (messageRatio.length() > decimalPrecision + 2)
			{
				messageRatio = messageRatio.substring(0,4);
			}
		}
		String characterRatio = "";
		if (this.characterCountTo == 0)
		{
			characterRatio = "0";
		}
		else if (this.characterCountFrom == 0)
		{
			characterRatio = "Inf";
		}
		else
		{
			characterRatio = "" + (double)this.characterCountTo/this.characterCountFrom;
			if (characterRatio.length() > decimalPrecision + 2)
			{
				characterRatio = characterRatio.substring(0,4);
			}
		}


		contactAsString += this.contact_name + " (" + this.address + ")" + "\n";
		contactAsString += "Sent:\t\t" + this.sentTo + " messages (" + this.characterCountTo + " chars)" + "\n";
		contactAsString += "Recieved:\t" + this.recievedFrom + " messages (" + this.characterCountFrom + " chars)" + "\n";
		contactAsString += "Sent/Recieved:\t" + messageRatio + " messages (" + characterRatio + " chars)" + "\n";
		return contactAsString;
	}
}
