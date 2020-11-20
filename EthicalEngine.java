
import java.util.Scanner;
import ethicalengine.Animal;
//import ethicalengine.Character;
import ethicalengine.Person;
import ethicalengine.Scenario;
//import ethicalengine.ScenarioGenerator;


import ethicalengine.Character.BodyType;
import ethicalengine.Character.Gender;
import ethicalengine.InvalidCharacteristicException;
import ethicalengine.InvalidDataFormatException;
import ethicalengine.InvalidInputException;
//import ethicalengine.NumberFormatException;
import ethicalengine.Person.AgeCategory;
import ethicalengine.Person.Profession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;




/**
 * 
 * 
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */

/***********Refelection part:****************************
 * desision:
 * 	  based on the fact that every life is born to be equal. The decision is he
 * to protect those who are following the rules -->  then comes to protect more life -->
 * to protect the pregnanet people
 * 		in this program, i really spent a lot of time to get the string args input.
 * Actually it is a better way that to receive the keyword  compared with receive input from
 * scanner. The concept of java is based on object- oriected manner is strengthed when i
 * invoke parameters from other class. 
 * 		I really have to learn how to use local variable more than creating the instance
 * belong to this class
 * ***************************************************************
 * 
 * @author zhangyanni
 *
 */

public class EthicalEngine 
{

	public enum Decision{PEDESTRIANS,PASSENGERS}
	static Scenario[] scenarios = new Scenario[100];
	static Decision userDecision = null;
	static Scanner command =  new Scanner(System.in);
	static int lineNumber = 0;
	public static Decision decide(Scenario scenario) 
	{
		
		Decision decision = null;

		if(scenario.isLegalCrossing())
			decision = Decision.PEDESTRIANS;  
		else
		{   //protect you
			if(scenario.hasYouInCar())
				decision = Decision.PASSENGERS;
			else if(scenario.hasYouInLane())
				decision = Decision.PEDESTRIANS;
			else 
			{   // protect more people
				if(scenario.getPassengerCount() > scenario.getPedestrianCount())
					decision = Decision.PASSENGERS;
				else if(scenario.getPassengerCount() < scenario.getPedestrianCount())
					decision = Decision.PEDESTRIANS;
				else 
				{	
					int pregnantCountInCar = 0, pregnantCountInLane = 0;
					for(int i = 0; i < scenario.getPassengerCount(); i++)
					{
						if(scenario.passengers[i].isPregnant())
							pregnantCountInCar++;
					}
					
					for(int i = 0; i < scenario.getPedestrianCount(); i++) 
					{
						if(scenario.pedestrians[i].isPregnant())
							pregnantCountInLane++;
					}
					
					if(pregnantCountInCar >= pregnantCountInLane)
						decision = Decision.PASSENGERS;
					else
						decision = Decision.PEDESTRIANS;					
				}					
			}
		}
		return decision;
	}

	public static void main(String[] args) throws IOException 
	{
		
		
		
		boolean isInteractive = false ;
		boolean toOpenFile= false;
		boolean toManual= false;
		boolean isRandom= false;
		boolean toResult= false;
		String filePath = null;
		String userFilePath = null;
		String resultFilePath = null;
		String[] array ;
		 for(String flag: args) 
		 { 
			 if(flag.contains("/")) 
			 {

				 filePath =flag;
				 array = flag.split("/");
				 for(int i = 0;i< array.length;i++) 
				 {
					 
					 
					 if(array[i].contains("user"))
					 { 
						 userFilePath =array[i];
					 	 break;
					 }
					 if(array[i].contains("result"))
					 { 
						 resultFilePath =array[i];
					 	 break;
					 }
				 }
			 }
			 if(flag!=null) 
			 {
				 switch(flag) 
				 {
				 case "-i":
					 isInteractive = true;		
					 break;
				 case "interactive":
					 isInteractive = true;		
					 break;
				 case "-c":
					 toOpenFile = true;
					 break;
				 case "config":
					 toOpenFile = true;
					 break;
				 case "-h":
					 toManual = true;
					 break;
				 case "help":
					 toManual = true;
					 break;
				 case "results":
					 toResult= true;
					 break;
				 case "-r":
					 toResult= true;
					 break;
				default:
				     break;
				 } 
			 }
			 
		 }
		 		 
			Audit audit = new Audit(scenarios); 
			boolean wantCollect;
			boolean wantContinue;
	
		 if(toManual) 
		 {
			 welcomePage();
			 manual();
			 System.out.print("$ java EthicalEngine ");
			 String input = command.nextLine();
		 }
		 
		 if(isInteractive) 
		 { 
			 if(toOpenFile ==false) 
				 isRandom = true;
			 
			if(toOpenFile) 
			{	
				 audit = readFile(filePath);
				 welcomePage();
				 wantCollect = askForCollection(command);
					if(wantCollect)
						if(userFilePath!=null)
					    	audit.printToFile(userFilePath);
						else
							audit.printToFile("user.log");
				audit.setAuditType("User");
				audit.run();						
				Quit(command);
			} else if(isRandom)
			{	
				audit.setAuditType("User");
				audit.scenarioGenerator.generate();
				audit = new Audit();
				audit.runs = 3;
				audit.decide = askForDecision(command);  
				audit.run(audit.runs);
			
				wantContinue = askForContinue();
				
					if(wantContinue) 
					{
						audit.run(audit.runs);
						wantContinue = askForContinue();
						while(wantContinue) 
						{
							audit.run(audit.runs);
							wantContinue = askForContinue();
						}
					}

				audit.printStatistic();
				wantCollect = askForCollection(command);
				if(wantCollect)
					if(userFilePath!=null)
				    	audit.printToFile(userFilePath);
					else
						audit.printToFile("user.log");
				Quit(command);
				
			}
		 }
		 if(toResult) 
		 {
			 audit.printStatistic();
			 if(resultFilePath!=null)
				 audit.printToFile(resultFilePath);
			 else
				 manual();
			 	System.exit(0);
			 
			 
			 
		 }
//		System.out.print("$ java EthicalEngine ");		
//		while(flag != null) 
//		{
//			
//			}else if(details[0].contains("results")||details[0].contains("r")) 
//			{
//	
//				 audit.run(); 
//				 audit.printStatistic();
//				 
//				 if(details.length !=1) 
//				 {
//					 audit.printToFile(details[1]);
//					 System.exit(0);
//				 }
//				
//			}
////				System.out.print("$ java EthicalEngine ");
//				flag = command.nextLine();
//						
//		}
		
	}
	
	public static void  Quit(Scanner command) 
	{
		System.out.print("That's all. Press Enter to quit.\n");
		System.exit(0);		
	}
	
	
	public static Boolean askForContinue() 
	{
		
		
		System.out.print("Would you like to continue? (yes/no)\n");
		
		String answer = command.nextLine();
		boolean validAnswer; 
		boolean toContinue;
		validAnswer = answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("no");
		while(!validAnswer) 
		{
			try 
			{	if(!validAnswer)		
				throw new InvalidInputException();
				
			}catch (InvalidInputException e) 
			{
				System.out.print(e.getMessage());
			}
			System.out.print("Would you like to continue? (yes/no)\n");
			answer = command.nextLine();
			validAnswer = answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("no");

		}
		if(answer.equalsIgnoreCase("yes"))
			toContinue = true;
		else 
			toContinue = false;
			
		
		return toContinue;
	}
	

	
	public static Decision askForDecision(Scanner command) 
	{
		Decision userDecision;
		
		System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
		
		String answer = command.nextLine();
		Boolean isValid = true;
		isValid = answer.equals("passenger")||answer.equals("passengers")||answer.equals("1")||
				answer.equals("pedestrian")||answer.equals("pedestrians")||answer.equals("2");
		
		while(!isValid) 
		{
			try 
			{	if(!isValid)		
					throw new InvalidInputException();
				
			}catch (InvalidInputException e) 
			{
				System.out.print(e.getMessage());
			}
			System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
			answer = command.nextLine();
			isValid = answer.equals("passenger")||answer.equals("passengers")||answer.equals("1")||
					answer.equals("pedestrian")||answer.equals("pedestrians")||answer.equals("2");
			
		}
		
		if(answer.contains("passenger")||answer.contains("1")) 
		{
			userDecision = Decision.PASSENGERS;
			
		}else 
		{
			userDecision = Decision.PEDESTRIANS;
		}
		
		return userDecision;
	}
	
	
	public static Decision getUserDecision() 
	  {
		  userDecision = askForDecision(command); 
		  return userDecision; 
	  }
	  
	public static Decision returnUserDecision() 
	  {
		  return userDecision;
	  }
	 
	
	
	
	public static Boolean askForCollection(Scanner command) 
	{
		System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n");
		
		String answer = command.nextLine();
		boolean validAnswer = true; 
		boolean collection = true;
		validAnswer = answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("no");
		while(!validAnswer) 
		{
			try 
			{			
				if(!validAnswer)		
					throw new InvalidInputException();
				
			}catch (InvalidInputException e) 
			{
				System.out.print(e.getMessage());
			}
			System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n");
			answer = command.nextLine();
			validAnswer = answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("no");

		}
		if(answer.equalsIgnoreCase("yes"))
			collection = true;
		else
			collection = false;
		return collection;
	}

	public static Audit  readFile(String filePath) 
	{
		Scenario[] scenarios = new Scenario[100];
		Scenario scenario;
		
		
		int count = 0;
		try 
		{
			File csvFile = new File(filePath);
			if (csvFile.isFile()) 
			{
			    // create BufferedReader and read data from csv
				BufferedReader inputStream =
						new BufferedReader(new FileReader(filePath));
				String row;
				String[] data;
				String[] light;
				boolean isLegalCrossing;
				
				
				
				while ((row = inputStream.readLine()) != null) 
				{
					lineNumber++;
				    data = row.split(",");
				    
				    light = data[0].split(":");
				    
				    while(light[0].equals("scenario")) 
				    {
				    		
				    		scenario = new Scenario();
				    		
					    	if(light[1].equals("green")) 
					    		isLegalCrossing = true;
					    	else 
					    		isLegalCrossing = false;
					    	
					    	
					    	scenario.setLegalCrossing(isLegalCrossing);
	
					    	row = inputStream.readLine();
					    	data = row.split(",");
					    	lineNumber++;
							while(data[0].equals("animal")|| data[0].equals("person"))
							{	
									try 
									{
										if(data.length == 10)
										{
											if(data[9].equals("passenger"))
									    	{
									    		readCharacter(scenario,data);
									    	}else // pedestrians
									    	{
									    		readCharacter(scenario,data);						    		
									    	}
											row = inputStream.readLine();
									    	
									    	if(row != null)
									    		data = row.split(",");
									    	else
									    		break;
										}else 
										{
											row = inputStream.readLine();
											data = row.split(",");
									    		throw new InvalidDataFormatException(lineNumber);
										}
											
									}
									catch(InvalidDataFormatException e) 
									{	
										System.out.println(e.toString()); 
									}
									finally 
									{
										lineNumber++;
									}
							    	
						      }
							scenarios[count] = scenario;
							scenario.toString();
							light = data[0].split(":");						
							count++;
							
				     } 
				}
				inputStream.close();
			}
			
		}catch(FileNotFoundException e) 
		{
			String message = "ERROR: could not find config file.\n"; 
			System.out.println(message); 
		}catch(IOException e) 
		{
			String message = "cannot read line.\n"; 
			System.out.println(message); 
		}		
		 Audit audit = new Audit(scenarios); 
		 audit.runs = count;		
		return audit;
		
		
	}
	
	public  static void readCharacter(Scenario scenario, String[] data) 
	{
		if(data[0].equalsIgnoreCase("person"))
			readPerson(scenario,data);
		else
			readAnimal(scenario,data);
	}
	
	public static void readPerson(Scenario scenario, String[] data) 
	{
		
		Person person = new Person();
		int age = 1;
		try 
		{
			age = Integer.parseInt(data[2].trim());
			
			//check gender
			boolean genderFormat = false;
			for(int i  = 0;i< 3; i++ ) 
			{
				if(data[1].toUpperCase().equalsIgnoreCase(Gender.values()[i].name())) 
				{
					genderFormat = true;
				}
			}
			if(!genderFormat)
				throw new InvalidCharacteristicException(lineNumber);		
			
			//check bodyType
			boolean bodyTypeFormat = false;
			for(int i = 0;i< 4; i++ ) 
			{
				if(data[3].toUpperCase().equalsIgnoreCase(BodyType.values()[i].name())) 
				{
					bodyTypeFormat = true;
				}
			}
			if(!bodyTypeFormat)
				throw new InvalidCharacteristicException(lineNumber);
			
			//check profession format
			boolean professionFormat = false;
			for(int i  = 0;i< 7; i++ ) 
			{
				if(data[4].toUpperCase().equalsIgnoreCase(Profession.values()[i].name())) 
				{
					professionFormat = true;
				}else if(data[4].equalsIgnoreCase("")) 
				{
					professionFormat = true;
				}
			}
			if(!professionFormat)
				throw new InvalidCharacteristicException(lineNumber);
			
		}
		catch(NumberFormatException e) 
		{
			System.out.println(e.getMessage()+lineNumber);
			age = 1;
		}
		catch(InvalidCharacteristicException e) 
		{
			System.out.println(e.toString());
		}
			
			//give value to Person instance
			person.setGender(Gender.valueOf(data[1].toUpperCase()));
			person.setAge(age);
			person.setBodyType(BodyType.valueOf(data[3].toUpperCase()));
			
			if(!data[4].equalsIgnoreCase(""))
				person.setProfession(Profession.valueOf(data[4].toUpperCase()));
			person.setPregnant(Boolean.valueOf(data[5].toLowerCase()));
			person.setAsYou(Boolean.valueOf(data[6].toLowerCase()));
		
		
		
		
		int i;
		if(data[9].equalsIgnoreCase("passenger"))
		{
				i = scenario.getPassengerCount();
				
				scenario.passengers[i] = person;
		}
		else
		{
				i = scenario.getPedestrianCount();
				scenario.pedestrians[i] =person;
		}
			
		
		
	}
	
	public static void readAnimal(Scenario scenario, String[] data) 
	{
		
		Animal animal = new Animal();
	
		int age = 1;
		
		try 
		{
			age = Integer.parseInt(data[2].trim());
			
			boolean genderFormat = false;
			for(int i  = 0;i< 3; i++ ) 
			{
				if(data[1].toUpperCase().equalsIgnoreCase(Gender.values()[i].name())) 
				{
					genderFormat = true;
				}
			}
			if(!genderFormat)
				throw new InvalidCharacteristicException(lineNumber);
			
		}catch(NumberFormatException e)
		{
			System.out.println(e.getMessage()+lineNumber);
			age = 1;
		}catch(InvalidCharacteristicException e) 
		{
			System.out.println(e.toString());
		}
		
		animal.setGender(Gender.valueOf(data[1].toUpperCase()));
		animal.setAge(Integer.valueOf(data[2]));
		animal.setPregnant(Boolean.valueOf(data[5].toLowerCase()));
		animal.setAsYou(Boolean.valueOf(data[6].toLowerCase()));
		animal.setSpecies(data[7]);
		animal.setPet(Boolean.valueOf(data[8]));
		
		

		int i;
		if(data[9].equalsIgnoreCase("passenger"))
		{
			i = scenario.getPassengerCount();
			scenario.passengers[i] = animal;
		}
		else
		{
			i = scenario.getPedestrianCount();
			scenario.pedestrians[i] = animal;
		}
	}
	
	public static void manual() 
	{
		System.out.print("EthicalEngine - COMP90041 - Final Project\n\n");
		System.out.print("Usage: java EthicalEngine [arguments]\n\n");
		System.out.print("Arguments:\n\t"
				+ "-c or --config\tOptional: path to config file\n\t"
				+ "-h or --help\tPrint Help(this message) and exit\n\t"
				+ "-r or --results\tOptional: path to results log file\n\t"
				+ "-i or --interactive\tOptional: launches interactive mode\n");
	}


	public static void welcomePage() throws FileNotFoundException, IOException 
	{

		String fileName = "welcome.ascii";
		File afile = new File( fileName );
		try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			if(afile.exists()) 
			{
				int iLine = 0;
			    String line;
			    while ((line = br.readLine()) != null) 
			    {
			        System.out.println(line);
			        iLine++;
			        
			    }
			}
			
		}catch(FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			
		}catch(IOException e) 
		{
			System.out.println(e.getMessage());
		}
		
	}

}
