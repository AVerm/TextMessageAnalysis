import java.util.Scanner;

class Sms
{
// <sms protocol="0" address="+17575377286" contact_name="null" date="1432917139711" readable_date="Fri, 29 May 2015 09:32:19 MST" type="2" subject="null" body="WHAT&apos;S THE THIRD" toa="null" sc_toa="null" service_center="null" read="1" status="-1" locked="0" />
	public int protocol;
	public String address;
	public String contact_name;
	public long date;
	public String readable_date;
	public int type;
	public String subject;
	public String body;
	public String toa;
	public String sc_toa;
	public String service_center;
	public boolean read;
	public int status;
	public boolean locked;

	public Sms (String recordLine)
	{
		this.parseRecord(recordLine);
	}

	public void parseRecord(String smsString)
	{
		String messageAttributes[] = smsString.split("\"");
		this.protocol = Integer.parseInt(messageAttributes[1]);
		this.address = messageAttributes[3];
		this.contact_name = messageAttributes[5];
		this.date = Long.parseLong(messageAttributes[7]);
		this.readable_date = messageAttributes[9];
		this.type = Integer.parseInt(messageAttributes[11]);
		this.subject = messageAttributes[13];
		this.body = messageAttributes[15];
		this.toa = messageAttributes[17];
		this.sc_toa = messageAttributes[19];
		this.service_center = messageAttributes[21];
		this.read = (messageAttributes[23].equals("1") ? true : false);
		this.status = Integer.parseInt(messageAttributes[25]);
		this.locked = (messageAttributes[27].equals("1") ? true : false);
	}

	public String parseBody(String uncleanBody)
	{
		String cleanBody = "";
		for (int firstCharacterIndex = 0; firstCharacterIndex < uncleanBody.length(); firstCharacterIndex++)
		{
			char thisCharacter = ' ';
			if (uncleanBody.charAt(firstCharacterIndex) == '&')
			{
				for (int secondCharacterIndex = firstCharacterIndex + 1; secondCharacterIndex < uncleanBody.length(); secondCharacterIndex++)
				{
					String characterBuffer = "";
					if (uncleanBody.charAt(secondCharacterIndex) != ';')
					{
						characterBuffer += uncleanBody.charAt(secondCharacterIndex);
					}

					switch (characterBuffer)
					{
						case "apos":
							thisCharacter = '\'';
							break;
						case "amp":
							thisCharacter = '&';
							break;
						case "#10:":
							thisCharacter = ' '; // I think it is ASCII 10 (Linefeed)
							break;
						case "quot":
							thisCharacter = '\"';
							break;
						default:
							thisCharacter = ' ';
							break;
					}
					firstCharacterIndex++;
				}
			}
			else
			{
				thisCharacter = uncleanBody.charAt(firstCharacterIndex);
			}
			cleanBody += thisCharacter;
		}
		return cleanBody;
	}
}
