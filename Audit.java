
import ethicalengine.Character;
import ethicalengine.Person;
import ethicalengine.Scenario;
import ethicalengine.ScenarioGenerator;
import ethicalengine.Animal;
import java.io.ObjectOutputStream;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.Arrays;

import ethicalengine.Character.BodyType;
import ethicalengine.Character.Gender;
import ethicalengine.Person.AgeCategory;
import ethicalengine.Person.Profession;

/**
 * 
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */

public class Audit 
{
	int runs = 0;
	String name = "Unspecified";
	ethicalengine.ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
	
	Scenario[] scenarios = new Scenario[100];
	Character[] survival = new Character[100];
	Character[] death = new Character[100];
	int total = 0;
	double avgAgeSum = 0;
	EthicalEngine.Decision decide;
	int haveRun = 0;
	
	int redSurvival = 0, greenSurvival= 0;
	/**
	 *  empty param 
	 */
	public Audit() 
	{
		
	}
	
	//constructor for file I/O
	/**
	 * 
	 * @param scenarios
	 */
	public Audit(Scenario[] scenarios)
	{
		this.scenarios = scenarios;
	}
	
	/**
	 * analysis the random scenarios genereated
	 * @param runs
	 */
	public void run(int runs) 
	{	
		int i = getArrayLength(scenarios);

		
		
		if(this.getAuditType().contains("User")) 
		{
			
			for(int j = 0 ; j < runs; j ++) 
			{
						
				if(i<runs) //base case
				{

					scenarios[j] = scenarioGenerator.generate();
					storeOutcome(decide, scenarios,j);
					

				}else if(i>=runs)//append
				{
					scenarios[j+i] = scenarioGenerator.generate();
					storeOutcome(decide, scenarios,j+i);
				}
				haveRun++;
			}
			
		}else if(this.getAuditType().contains("Unspecified"))
		{
			for(int j = 0 ; j < runs; j ++) 
			{
						
				if(i<=runs) //base case
				{
					scenarios[j] = scenarioGenerator.generate();
					decide = EthicalEngine.decide(scenarios[j]);
					storeOutcome(decide, scenarios,j);
					
				}else if(i>runs)//append
				{
					scenarios[j+i] = scenarioGenerator.generate();
					decide = EthicalEngine.decide(scenarios[j+i]);
					storeOutcome(decide, scenarios,j+i);
				}
				haveRun++;
			}
		}
	}
	
	/*
	 * analyze the file based scenarios
	 */
	public void run() 
	{	
		boolean toContinue = true;
		boolean toAskContinue = true;
		
		if(runs <= 3) 
		{
			for(int i = 0 ; i < runs; i++) 
			{
				if(name.equalsIgnoreCase("Unspecified")) 
				{
											
								decide = EthicalEngine.decide(scenarios[i]);
								storeOutcome(decide, scenarios,i);
								haveRun++;
						
						
				}else if(name.equalsIgnoreCase("User")) 
				{
												
								System.out.print(scenarios[i].toString());
								decide = EthicalEngine.getUserDecision();
								storeOutcome(decide, scenarios,i);						
								haveRun++;
						
				 }
				
			}
			this.printStatistic();
			
		}
		else if(runs> 3) 
		{
			for(int cycle = 0; cycle < runs/3 +1 ; cycle++) 
			{
					if(toContinue) 
					{
							int x = 3;// in selfTest is 3, if we want to have each cycle 4 or 5 , just change x
							int remain = runs - haveRun;
							int thisCycleRunNumbers = 0;
							if(remain>=x)
								thisCycleRunNumbers = x;
							else if(remain < x )
							{
								thisCycleRunNumbers = remain;
								toAskContinue = false;
							}
								
							
							if(name.equalsIgnoreCase("Unspecified")) 
							{
									for(int j = 0 ; j < thisCycleRunNumbers; j ++) 
									{					
											decide = EthicalEngine.decide(scenarios[j+x*cycle]);
											storeOutcome(decide, scenarios,j+x*cycle);
											haveRun++;
									}
									
							}else if(name.equalsIgnoreCase("User")) 
							{
									for(int j = 0 ; j < thisCycleRunNumbers; j ++) 
									{						
											System.out.print(scenarios[j+x*cycle].toString());
											decide = EthicalEngine.getUserDecision();
											storeOutcome(decide, scenarios,j+x*cycle);						
											haveRun++;
									} 
							 }
							
					}
				
					this.printStatistic();
					if(toAskContinue) 
						toContinue = EthicalEngine.askForContinue();
			}		
			
		}
		
	}
	
	/**
	 * 
	 * @param decide: enum type of decision made by the user
	 * @param scenarios:  store the outcome for different scenarios
	 * @param x:  index for the scenario[x]
	 */
	public void storeOutcome(EthicalEngine.Decision decide, Scenario[] scenarios,int x) 
	{
		total += scenarios[x].getPassengerCount()+scenarios[x].getPedestrianCount();
		
		switch(decide) 
		{
			case PASSENGERS:
				for(int j = this.getArrayLength(survival),i =0; i < scenarios[x].getPassengerCount() ; j++,i++) 
				{
					survival[j] = scenarios[x].passengers[i];
					if(scenarios[x].isLegalCrossing) 
					{
						greenSurvival++;
					}else 
					{
						redSurvival++;
					}
				}
				for(int j = this.getArrayLength(death),i =0; i < scenarios[x].getPedestrianCount() ; j++,i++) 
				{
					death[j] = scenarios[x].pedestrians[i];
				}
				break;
			case PEDESTRIANS:
				for(int j = this.getArrayLength(survival),i =0; i < scenarios[x].getPedestrianCount(); j++,i++) 
				{
					survival[j] = scenarios[x].pedestrians[i];
					if(scenarios[x].isLegalCrossing) 
					{
						greenSurvival++;
					}else 
					{
						redSurvival++;
					}
				}
				for(int j = this.getArrayLength(death),i =0; i < scenarios[x].getPassengerCount(); j++,i++) 
				{
					death[j] = scenarios[x].passengers[i];
				}
				break;
			default:
				System.out.println("Error: decision is not available.");
				break;
		}
	}
	
	/**
	 * setter
	 * @param name: audit type: "user", "unspecified"
	 */
	public void setAuditType(String name) 
	{
		this.name = name;
	}
	/**
	 * getter
	 * @return : return auditType
	 */
	public String getAuditType() 
	{
		return name;
	}
	
	/**
	 * store the different types of character
	 * store into a 2d dimesion , use the row/10 to record 0.1, 0.2, 0.3
	 */
	public String toString() 
	{
		String[][] simulation = new String[100][100];
		String statistic = "";
		
		if(scenarios == null)
		{	
			statistic = "no audit available";
			return statistic;
		}
		else
		{
			storeAge(simulation);
			storeGender(simulation);
			storeBodyType(simulation);
			storeProfession(simulation);
			storePregnant(simulation);
			
			storeClassType(simulation);
			storeIsPet(simulation);
			storeSpecies(simulation);
			storeLegality(simulation);
			storeIsYou(simulation);
						
			for(int row = 10; row >=0 ; row--) 
			{
			
				double ratioRecord = ((double)row)/10.0;
				if(getArrayLength(simulation,row)!= 0) 
				{
					for(int col = 0 ; col< getArrayLength(simulation,row); col++) 
					{
						statistic += simulation[row][col]+": " +roundoff(ratioRecord)+"\n";
					}
				}
			}
			return statistic;

		}
		
	}
	
	/**
	 * if file exist ,append the data
	 * if not, create a new file
	 * @param filepath:  read from strinng[] args
	 */
	public void checkFileToPrint(String filepath) 
	{
		File aFile = new File(filepath);
		if (!aFile.exists()) 
		{
			ObjectOutputStream outputStream  = null;
//System.out.println("check file");
			try 
			{
				outputStream = new ObjectOutputStream(new FileOutputStream(filepath));			    
			}catch(IOException e) 
			{
				 System.err.format("createFile error: %s%n\n", e);
			}
		}
		
	}
	
	
	/**
	 * 
	 * @param filepath read from strinng[] args
	 */
	public void printToFile(String filepath) 
	{
		checkFileToPrint(filepath);//check exist
		
		ObjectOutputStream outputStream  = null;
		try 
		{
			File csvFile = new File(filepath);
			if (csvFile.isFile()) 
			{
				outputStream = new ObjectOutputStream(new FileOutputStream("results.log",true));	       
				outputStream.writeObject(toString());
		        outputStream.close();
		       
			}
			
		}catch(FileNotFoundException e) 
		{
			String message = "ERROR: could not print results. Target directory does not exist.\n"; 
			System.out.println(message); 
		}catch(IOException e) 
		{
			String message = "cannot read line.\n"; 
			System.out.println(message); 
		}
		
	}
	
	
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeGender(String[][] simulation) 
	{

		Gender gender;
		int femaleSurvival= 0, maleSurvival = 0, unknownSurvival = 0;
		int[] genderSurvival = new int[100];
		int[] genderDeath = new int[100];
		double[] genderSuirvivalRatio = new double[100];
		Arrays.fill(genderSurvival, 0);
		Arrays.fill(genderDeath,0);
		String[] genderName = {"female","male","unkonwn"};
		
		for(int i = 0; i < this.getArrayLength(survival); i++)
		{
			if(survival[i] instanceof Person) 
			{
				gender = survival[i].getGender();
				switch(gender) 
				{
					case FEMALE:
						genderSurvival[0]++;
						break;
					case MALE:
						genderSurvival[1]++;
						break;
					case UNKNOWN:
						genderSurvival[2]++;
						break;
					default:
						System.out.println("Error: gender is not available.");
						break;
				}
			}	
		}
		int femaleDeath= 0, maleDeath = 0, unknownDeath = 0;
		for(int i = 0; i < this.getArrayLength(death); i++)
		{
			if(death[i] instanceof Person) 
			{
				gender = death[i].getGender();
				switch(gender) 
				{
					case FEMALE:
						genderDeath[0]++;
						break;
					case MALE:
						genderDeath[1]++;
						break;
					case UNKNOWN:
						genderDeath[2]++;
						break;
					default:
						System.out.println("Error: gender is not available.");
						break;
				}
			}	
		}
		
		
		for(int i = 0; i < 3; i ++) 
		{
			if(genderSurvival[i]+ genderDeath[i]!= 0) 
			{

				genderSuirvivalRatio[i] = ((double)genderSurvival[i])/(genderDeath[i]+ genderSurvival[i]);
				for(int j = 0; j< 11; j++) 
				{

					int n = getArrayLength(simulation,j);
					
					if(roundoff(genderSuirvivalRatio[i]) == ((double) j)/10)						 
						simulation[j][n] = genderName[i];
				}
			}
		}
		
	}
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeAge(String[][] simulation) 
	{
		
		AgeCategory ageCate;
		int ageSum = 0;
		int countPerson = 0;
		
		int[] ageSurvival = new int[100];
		int[] ageDeath = new int[100];
		double[] ageSuirvivalRatio = new double[100];
		Arrays.fill(ageSurvival, 0);
		Arrays.fill(ageDeath,0);
		String[] ageName = {"baby","child","adult","senior"};

		for(int i = 0; i < this.getArrayLength(survival);i++) 
		{
			if(survival[i] instanceof Person) 
			{
				ageSum += survival[i].getAge();
				countPerson++;
				ageCate = survival[i].getAgeCategory(survival[i].getAge());
				
				switch(ageCate) 
				{
				case BABY:
					ageSurvival[0]++;
					break;
				case CHILD:
					ageSurvival[1]++;
					break;
				case ADULT:
					ageSurvival[2]++;
					break;
				case SENIOR:
					ageSurvival[3]++;
					break;
				default:
					System.out.println("Error: ageCategory is not available.");
					break;
				}
			}

		}
		
		for(int i = 0; i < this.getArrayLength(death);i++) 
		{			
			if(death[i] instanceof Person) 
			{
				ageCate = death[i].getAgeCategory(death[i].getAge());
				
				switch(ageCate) 
				{
				case BABY:
					ageDeath[0]++;
					break;
				case CHILD:
					ageDeath[1]++;
					break;
				case ADULT:
					ageDeath[2]++;
					break;
				case SENIOR:
					ageDeath[3]++;
					break;
				default:
					System.out.println("Error: ageCategory is not available.");
					break;
				}
			}
		}
	
		for(int i = 0; i < 6; i ++) 
		{
			if(ageSurvival[i]+ ageDeath[i]!= 0) 
			{
				ageSuirvivalRatio[i] = ((double)ageSurvival[i])/(ageDeath[i]+ ageSurvival[i]);
				for(int j = 0; j< 11; j++) 
				{
					int n = getArrayLength(simulation,j);
					
					if(roundoff(ageSuirvivalRatio[i]) == ((double) j)/10)						 
						simulation[j][n] = ageName[i];
				}
			}
		}		
		avgAgeSum =  ((double)ageSum)/countPerson;
		avgAgeSum = roundoff(avgAgeSum);
		
	}
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeBodyType(String[][] simulation) 
	{
		BodyType bodyType;
		int avgSurvial = 0, athSurvival = 0, overSurvival = 0, unsSurvival = 0;
		int avgDeath = 0, athDeath = 0, overDeath = 0, unsDeath = 0;
		
		int[] bodyTypeSurvival = new int[100];
		int[] bodyTypeDeath = new int[100];
		double[] bodyTypeRatio = new double[100];
		String[] bodyTypeName = {"average","athletic","overweight","unspecified"};
		Arrays.fill(bodyTypeSurvival, 0);
		Arrays.fill(bodyTypeDeath, 0);
		
		
		for(int i = 0; i < this.getArrayLength(survival);i++) 
		{
			if(survival[i] instanceof Person)
			{	bodyType = survival[i].getBodyType();
				
				switch(bodyType) 
				{
				case AVERAGE:
					bodyTypeSurvival[0]++;
					break;
				case ATHLETIC:
					bodyTypeSurvival[1]++;
					break;
				case OVERWEIGHT:
					bodyTypeSurvival[2]++;
					break;
				case UNSPECIFIED:
					bodyTypeSurvival[3]++;
					break;
				default:
					System.out.println("Error: bodyType is not available.");
					break;
					
				}
			}
		}
		
		for(int i = 0; i < this.getArrayLength(death);i++) 
		{

			if(death[i] instanceof Person)
			{	bodyType = death[i].getBodyType();
				
				switch(bodyType) 
				{
				case AVERAGE:
					bodyTypeDeath[0]++;
					break;
				case ATHLETIC:
					bodyTypeDeath[1]++;
					break;
				case OVERWEIGHT:
					bodyTypeDeath[2]++;
					break;
				case UNSPECIFIED:
					bodyTypeDeath[3]++;
					break;
				default:
					System.out.println("Error: bodyType is not available.");
					break;
					
				}
			}
		}
		
	
		for(int i = 0; i < 6; i ++) 
		{
			if(bodyTypeSurvival[i]+ bodyTypeDeath[i]!= 0) 
			{
				bodyTypeRatio[i] = ((double)bodyTypeSurvival[i])/(bodyTypeDeath[i]+ bodyTypeSurvival[i]);
				for(int j = 0; j< 11; j++) 
				{
					int n = getArrayLength(simulation,j);
					if(roundoff(bodyTypeRatio[i]) == ((double) j)/10.0) 
					{
						simulation[j][n] = bodyTypeName[i];
						
					}						 
						
				}
			
			}
		}
		
		
		
   }
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeProfession(String[][] simulation) 
	{
		
		Profession profession;
		int[] professionSurvival = new int[100];
		int[] professionDeath = new int[100];
		double[] survivalRatio = new double[100];
		String[] professionName = {"doctor","ceo","criminal","homeless", "unemployed","unknown","none"};
		Arrays.fill(professionSurvival, 0);
		Arrays.fill(professionDeath, 0);
		Arrays.fill(survivalRatio, 0);
		for(int i = 0; i < this.getArrayLength(survival);i++) 
		{
			if(survival[i] instanceof Person)
			{	
				profession = this.survival[i].getProfession();
//String[] professionName = {DOCTOR,CEO,CRIMINAL,HOMELESS, UNEMPLOYED,UNKNOWN,NONE};			
				switch(profession) 
				{
				case DOCTOR:
					professionSurvival[0]++;
					break;
				case CEO:
					professionSurvival[1]++;
					break;
				case CRIMINAL:
					professionSurvival[2]++;
					break;
				case HOMELESS:
					professionSurvival[3]++;
					break;
				case UNEMPLOYED:
					professionSurvival[4]++;
					break;
				case UNKNOWN:
					professionSurvival[5]++;
					break;
				case NONE:
					professionSurvival[6]++;
					break;
				default:
					System.out.println("Error: bodyType is not available.");
					break;
					
				}
			}
			
		}	
		
		for(int i = 0; i < this.getArrayLength(death);i++) 
		{
			if(death[i] instanceof Person)
			{	
				profession = this.death[i].getProfession();
//DOCTOR,CEO,CRIMINAL,HOMELESS, UNEMPLOYED,UNKNOWN,NONE				
				switch(profession) 
				{
				case DOCTOR:
					professionDeath[0]++;
					break;
				case CEO:
					professionDeath[1]++;
					break;
				case CRIMINAL:
					professionDeath[2]++;
					break;
				case HOMELESS:
					professionDeath[3]++;
					break;
				case UNEMPLOYED:
					professionDeath[4]++;
					break;
				case UNKNOWN:
					professionDeath[5]++;
					break;
				case NONE:
					professionDeath[6]++;
					break;
				default:
					System.out.println("Error: bodyType is not available.");
					break;
					
				}
			}
			
		}	
		
		for(int i = 0; i < 6; i ++) 
		{
			if(professionDeath[i]+ professionSurvival[i]!= 0) 
			{
				survivalRatio[i] = ((double)professionSurvival[i])/(professionDeath[i]+ professionSurvival[i]);
				for(int j = 0; j< 11; j++) 
				{
					int n = getArrayLength(simulation,j);
					
					if(roundoff(survivalRatio[i]) == ((double) j)/10.0)						 
						simulation[j][n] = professionName[i];
				}
			}
		}
			
	}
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storePregnant(String[][] simulation) 
	{
		
		int pregnantSurSum = 0,pregnantDeathSum = 0;
		
		for(int i = 0; i < getArrayLength(survival);i++) 
		{
			if(survival[i].isPregnant()) 
				pregnantSurSum++;
		}
			
				
		for(int j = 0; j < getArrayLength(death);j++) 
		{
			if(death[j].isPregnant())
				pregnantDeathSum++;			
		}
			
		
		double pregnantSurvivalRatio = ((double)pregnantSurSum)/(pregnantSurSum + pregnantDeathSum);

		if(pregnantSurSum + pregnantDeathSum != 0) 
		{
			
			for(int j = 0; j< 11; j++) 
			{
				int n = getArrayLength(simulation,j);
				
				if(roundoff(pregnantSurvivalRatio) == ((double) j)/10.0)						 
					simulation[j][n] = "pregnant";
			}
		}
			
	}
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeClassType(String[][] simulation) 
	{
		int[] sum = new int[100];
		Arrays.fill(sum, 0);
		//sum[0] personSurvival, sum[1] person Death, sum[2] animalSurvival, sum[3]animalDeath
		
		for(int i = 0; i < this.getArrayLength(survival);i++) 
		{
			if(survival[i] instanceof Person) 
			{
				sum[0]++;
			}else if(survival[i] instanceof Animal) 
			{
				sum[2]++;
			}
		}
		for(int i = 0; i < this.getArrayLength(death);i++) 
		{
			if(death[i] instanceof Person) 
			{
				sum[1]++;
			}else if(death[i] instanceof Animal) 
			{
				sum[3]++;
			}
		}
		
		
		
		double personRatio = ((double)sum[0])/(sum[0]+sum[1]);
		double animalRatio = ((double)sum[2])/(sum[2]+sum[3]);

		//person appear
		if(sum[0]+sum[1]!= 0) 
		{
			for(int j = 0; j< 11; j++) 
			{
				int n = getArrayLength(simulation,j);
				
				if(roundoff(personRatio )== ((double) j)/10)						 
					simulation[j][n] = "person";
			}
		}

		if(sum[2]+sum[3] != 0) 
		{
			for(int j = 0; j< 11; j++) 
			{
				int n = getArrayLength(simulation,j);
				
				if(roundoff(animalRatio) == ((double) j)/10.0)						 
					simulation[j][n] = "animal";
			}
		}		
		
	}
	
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeSpecies(String[][] simulation) 
	{
		String[] speciesStore = new String[100];
		int[] alive = new int[100];
		int[] noAlive = new int[100];
		double[] speciesRatio = new double[100];
 		boolean newSpecies = true;
		Arrays.fill(alive, 0);
			
		for(int i = 0; i < this.getArrayLength(survival);i++) 
		{
				
				if(survival[i] instanceof Animal) 
				{	
						newSpecies = true;
						if(getArrayLength(speciesStore)== 0)
								newSpecies = true;
						for(int j = 0 ; j <  getArrayLength(speciesStore); j++) 
						{
								if(survival[i].getSpecies().equalsIgnoreCase(speciesStore[j]))
								{	
									newSpecies = false;
									alive[j]++;
								}											
						}
						if(newSpecies)
						{
								int n = getArrayLength(speciesStore);
								speciesStore[n] = survival[i].getSpecies();
								alive[n]++;
						}
				}
		}
		
		
		for(int i = 0; i < this.getArrayLength(death);i++) 
		{
				if(death[i] instanceof Animal) 
				{	
						newSpecies = true;
						if(getArrayLength(speciesStore)== 0)
								newSpecies = true;
						for(int j = 0 ; j <  getArrayLength(speciesStore) ; j++) 
						{
								if(death[i].getSpecies().equalsIgnoreCase(speciesStore[j]))
								{	 noAlive[j]++;
									newSpecies= false;
								}
						}
								
						if(newSpecies)
						{
								int n = getArrayLength(speciesStore);
								speciesStore[n] = death[i].getSpecies();
								noAlive[n]++;	
						}				
				}
		}
		
		for(int i = 0; i < speciesStore.length; i++) 
		{		
				if(alive[i]+noAlive[i] != 0) 
				{
						speciesRatio[i] = ((double)alive[i])/(alive[i]+noAlive[i]);
						for(int j = 0; j< 11; j++) 
						{
								int n = getArrayLength(simulation,j);						
								if(roundoff(speciesRatio[i]) == ((double) j)/10.0)						 
									simulation[j][n] =speciesStore[i] ;
						}
				}
		}
	}
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeIsYou(String[][] simulation) 
	{
			int isSurvival = 0, isDeath = 0;
			for(int i = 0; i < this.getArrayLength(survival);i++) 
			{
					if(survival[i].isYou()) 
						isSurvival++;	
			}
			for(int i = 0; i < this.getArrayLength(death);i++) 
			{
					if(death[i].isYou()) 
						isDeath++;	
			}
			
			
			if(isSurvival +isDeath != 0)
			{
					double ratioSurvival = ((double)isSurvival)/(isSurvival +isDeath );
					for(int j = 0; j< 11; j++) 
					{
							int n = getArrayLength(simulation,j);				
							if(roundoff(ratioSurvival) == ((double) j)/10)						 
								simulation[j][n] = "you";
					}
			}
	}
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeIsPet(String[][] simulation) 
	{
		int isPetSurvival = 0, isPetDeath = 0;
		
		for(int i = 0; i < this.getArrayLength(survival);i++) 
			if(survival[i] instanceof Animal) 
				if(survival[i].isPet()) 
					isPetSurvival++;				
		
		for(int i = 0; i < this.getArrayLength(death);i++) 
			if(death[i] instanceof Animal) 
				if(death[i].isPet()) 
					isPetDeath++;				
		
		double isPetRatio = ((double)isPetSurvival)/(isPetSurvival + isPetDeath);
		
		if(isPetSurvival + isPetDeath != 0) 
		{
			for(int j = 0; j< 11; j++) 
			{
				int n = getArrayLength(simulation,j);
				
				if(roundoff(isPetRatio )== ((double) j)/10.0)						 
					simulation[j][n] ="pet";
			}
		}
//			System.out.printf("pet: %.1f\n",isPetRatio);
		
		
	}
	/**
	 * use an array to store the different enum value by index
	 * if survival + death is not 0, the character need to be stored
	 * @param simulation: 2d array to record the ranking data
	 */
	public void storeLegality(String[][] simulation) 
	{
		int redCount = 0, greenCount= 0;
		
		double[] legalityRatio = new double[100];
		Arrays.fill(legalityRatio,-1);


		for(int i = 0; i < haveRun;i++) 
		{
			if(scenarios[i].isLegalCrossing) 
				greenCount +=	scenarios[i].getPedestrianCount() + scenarios[i].getPassengerCount();			
			else
				redCount +=	scenarios[i].getPedestrianCount() + scenarios[i].getPassengerCount();	
		}	
		
		double redRatio = ((double)redSurvival)/redCount;
		double greenRatio = ((double)greenSurvival)/greenCount;
				
		if(redCount != 0) 
		{
			for(int j = 0; j< 11; j++) 
			{
				int n = getArrayLength(simulation,j);
				
				if(roundoff(redRatio) == ((double) j)/10)						 
					simulation[j][n] ="red";
			}
		}
		if(greenCount != 0) 
		{
			for(int j = 0; j< 11; j++) 
			{
				int n = getArrayLength(simulation,j);
				
				if(roundoff(greenRatio) == ((double) j)/10.0)						 
					simulation[j][n] ="green";
			}
		}
		

	}
	
	/**
	 * to print statistics have be run
	 */
	public void printStatistic()
	{
		System.out.print("======================================\n");
		System.out.printf("# %s Audit\n",name);
		System.out.print("======================================\n");

		System.out.print("- % ");
		System.out.printf("SAVED AFTER %d RUNS\n",haveRun);
		System.out.print(toString());
		
		System.out.printf("--\naverage age: %.1f\n",avgAgeSum);
	}
	
	/**
	 * 
	 * @param character
	 * @return: array length of not null value
	 */
	public int getArrayLength(Character[] character) 
	{
		int length = 0;
		for(int i = 0; i < character.length; i++) 
		{
			if(character[i]!= null)
				length++;
			else
				break;
		}
		return length;
	}
	
	public int getArrayLength(String[] string) 
	{
		int length = 0;
		for(int i = 0; i < string.length; i++) 
		{
			if(string[i]!= null)
				length++;
			else
				break;
			
		}
		return length;
	}
	
	public int getArrayLength(double[] array) 
	{
		int length = 0;
		for(int i = 0; i < array.length; i++) 
		{
			if(array[i]!= -1 )
				length++;
			else
				break;
			
		}
		return length;
	
	}
	
	
	
	public int getArrayLength(String[][] string,int row) 
	{
		int length = 0;
		
		for(int col = 0; col < string[row].length; col++) 
		{
			if(string[row][col] != null )
				length++;
			else
				break;
			
		}
		return length;
		
	}
	public int getArrayLength(Scenario[] scenarios) 
	{
		int length = 0;
		
		for(int i = 0; i < scenarios.length; i++) 
		{

			if(scenarios[i] != null )
				length++;
			else
				break;
			
		}
		return length;
	}
	
	public double roundoff(double value) //keep 1 decinal
	{
		double roundOff = ((double) Math.floor(value * 10 ))/ 10;
		
		return roundOff;
	}

}
