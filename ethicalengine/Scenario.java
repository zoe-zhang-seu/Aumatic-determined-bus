package ethicalengine;

 
import ethicalengine.ScenarioGenerator.Light;
import java.util.Arrays;
/**
 * a list of passengers, a list of pedestrians, as well as additional scenario
 * conditions, such as whether pedestrians are legally crossing at the traffic
 * light.
 * 
 * @author zhangyanni
 *
 */
public class Scenario {
	public Character[] passengers = new Character[100];
	public Character[] pedestrians = new Character[100];
	public boolean isLegalCrossing ;

	public Light lightCondition;

	public Scenario() 
	{
		
	}

	public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing) {
		this.passengers = passengers;
		this.pedestrians = pedestrians;
		this.isLegalCrossing = isLegalCrossing;
	}

	public boolean hasYouInCar() 
	{ // you is belong to passengers
		boolean hasYouInCar = false;

		for (int i = 0; i < this.getPassengerCount(); i++) 
		{
			if (passengers[i].isYou()) 
			{
				hasYouInCar = true;
				break;
			}
		}

		return hasYouInCar;
	}

	public boolean hasYouInLane() 
	{
		// you is belong to pedestrians
		boolean hasYouInLane = false;

		for (int i = 0; i < this.getPedestrianCount(); i++) 
		{
			if (pedestrians[i].isYou()) 
			{
				hasYouInLane = true;
				break;
			}
		}

		return hasYouInLane;
	}



	public Character[] getPedestrains() {
		return pedestrians;
	}
	
	public  Character[] getPassengers()
	{
		return passengers;
	}
	
	
	public boolean isLegalCrossing() {
		return isLegalCrossing;
	}

	public void setLegalCrossing(boolean isLegalCrossing) {
		this.isLegalCrossing = isLegalCrossing;
	}

	public int getPassengerCount() 
	{
		int i = 0;
		int length = 0;
		for(;i < passengers.length ;i++) 
		{
			if(passengers[i]!= null) 
			{
				length++;
			}
		}
		return length;
	}

	public int getPedestrianCount() 
	{
		int i = 0;
		int length = 0;
		for(;i < pedestrians.length ;i++) 
		{
			if(pedestrians[i]!= null) 
			{
				length++;
			}
		}
		return length;
	}

	public String toString() 
	{

		String records = "";
		records +="======================================\n";
		records +="# Scenario\n";
		records +="======================================\n";
		

		String LegalCrossing;
		if (isLegalCrossing)
			LegalCrossing = " yes";
		else
			LegalCrossing = " no";
		records +="Legal Crossing:"+ LegalCrossing +"\n";
		

		int number = getPassengerCount();
		records +="Passengers ("+ number+ ")\n";
		
		for (int i = 0; i < number; i++) 
		{
			records += "- "+passengers[i].toString() +"\n";
		}

		number = getPedestrianCount();
		records += "Pedestrians ("+ number +")\n";
		for (int i = 0; i < number; i++) 
		{
			records += "- "+pedestrians[i].toString() +"\n";
			
		}

		return records;
	}

}
