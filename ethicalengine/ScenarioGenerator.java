package ethicalengine;

import java.util.Random;

import ethicalengine.Character.BodyType;
import ethicalengine.Character.Gender;
import ethicalengine.Person.Profession;

public class ScenarioGenerator 
{
	public enum Kind {CAT, DOG, BIRD}
	public enum Light{RED,GREEN}
	
	public long seed;
	public Random random = new Random();
	public int passengerCountMinimum = 0, passengerCountMaximum  = 0, 
			pedestrianCountMinimum = 0, pedestrianCountMaximum = 0;
//	public Light lightCondition;
	public Kind kind;
	
	public ScenarioGenerator() 
	{	
	}
	
	public ScenarioGenerator(long seed)
	{
		this.seed = seed;
		random.setSeed(seed);
	}
	
	public ScenarioGenerator(long seed, int passengerCountMinimum, 
			int passengerCountMaximum, int pedestrianCountMinimum, int pedestrianCountMaximum) 
	{
		this.seed = seed;
		this.passengerCountMaximum = passengerCountMaximum;
		this.passengerCountMinimum = passengerCountMinimum;
		this.pedestrianCountMaximum = pedestrianCountMaximum;
		this.pedestrianCountMinimum = pedestrianCountMinimum;
	}
	
	public void setPassengerCountMin(int min) 
	{
		this.passengerCountMinimum = min;
	}
	
	public void setPassengerCountMax(int max) 
	{
		this.passengerCountMaximum = max;
	}
	
	public void setPedestrianCountMin(int min) 
	{
		this.pedestrianCountMinimum = min;
	}
	
	public void setPedestrianCountMax(int max) 
	{
		this.pedestrianCountMaximum = max;
	}
	
	public Person getRandomPerson() 
	{
		Person person = new Person();
		int age = Math.abs(random.nextInt() % 101);

		person.setAge(age);
		
		
		Gender[] gender = Gender.values();
	    int i =   Math.abs(random.nextInt() % 3);
		person.setGender(gender[i]);
	
		BodyType[] bodyType = BodyType.values();
		i =   Math.abs(random.nextInt() % 4);
		person.setBodyType(bodyType[i]);
		
		Profession[] profession = Profession.values();
		i =  + Math.abs(random.nextInt() % 4);
		person.profession = profession[i];
		person.profession = person.getProfession();//use age to correct it
		
		if(random.nextInt()%2 == 0)
		{	
			person.isPregnant = true;
		    person.isPregnant= person.isPregnant();//use gender to correct it 
		}
		else 
			person.isPregnant = false;
		
		return person;
	}
	
	public Animal getRandomAnimal() 
	{
		Animal animal= new Animal();
		int age = Math.abs(random.nextInt() % 20);

		animal.setAge(age);
		
		Gender[] gender = Gender.values();
	    int i =  Math.abs(random.nextInt() % 3);
		animal.setGender(gender[i]);
	
		BodyType[] bodyType = BodyType.values();
		i =  Math.abs(random.nextInt() % 4);
		animal.setBodyType(bodyType[i]);
		
		Kind[] kind = Kind.values();
		i =  Math.abs(random.nextInt() % 3);
		animal.setSpecies(kind[i].toString().toLowerCase());
		
		if(random.nextInt()%2 == 0)
			animal.isPet = true;
		else
			animal.isPet = false;
		
		return animal;
		
		
	}

	
	public Scenario  generate() 
	{
		Scenario scenario = new Scenario();
		
		if(passengerCountMinimum == 0)
			setPassengerCountMin(1);
		if(pedestrianCountMinimum == 0)
			setPedestrianCountMin(1);
		if(passengerCountMaximum == 0)
			setPassengerCountMax(5);
		if(pedestrianCountMaximum == 0)
			setPedestrianCountMax(5);
		
		int passengerCount = 1 + passengerCountMinimum + Math.abs(random.nextInt() % passengerCountMaximum);
			
		int pedestrianCount = 1 +  pedestrianCountMinimum + Math.abs(random.nextInt() % pedestrianCountMaximum);
		
		for(int i = 0; i < passengerCount; i++) 
		{
			if(random.nextInt()%2 == 0)
				scenario.passengers[i] = getRandomPerson();
			else
				scenario.passengers[i] = getRandomAnimal();
		}
		
		for(int i = 0; i < pedestrianCount; i++ ) 
		{
			if(random.nextInt()%2 == 0)
				scenario.pedestrians[i] = getRandomPerson();
			else
				scenario.pedestrians[i] = getRandomAnimal();
			
		}
		
		Light[] light = Light.values();
		int i =  Math.abs(random.nextInt() % 2);
		scenario.lightCondition = light[i];
		
		
		return scenario;
		
	}
	
	
	
}
