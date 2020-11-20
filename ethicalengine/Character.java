package ethicalengine;
/**
 * 
 * @author zhangyanni
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */
import ethicalengine.Person.AgeCategory;
import ethicalengine.Person.Profession;

public abstract class Character
{
	public enum Gender { FEMALE, MALE, UNKNOWN}
	public enum BodyType {AVERAGE, ATHLETIC, OVERWEIGHT,UNSPECIFIED}
	
	public int  age;
	public Gender gender;
	public BodyType bodyType;
	
	/*@ invariant age >= 0;  @*/ //class invariant
	
	//constructor 1 as default value
	public Character() 
	{
		age = 0;
		gender = Gender.UNKNOWN;
		bodyType = BodyType.UNSPECIFIED;
		
	}
	
	//constructor 2 to initialize value when input is parameter
	/*@ requires age >= 0; @*/
	public Character(int age,Gender gender,BodyType bodyType) 
	{
		this.age = age;
		this.gender = gender;
		this.bodyType = bodyType;
	}
	
	//constructor 3 to initialize the value when input is an object
	public Character(Character c)
	{
		age = c.getAge();
		gender = c.getGender();
		bodyType = c.getBodyType();
		
	}
	
	public abstract boolean isYou(); 
	public abstract boolean isPregnant();
	public abstract AgeCategory getAgeCategory(int age);
	public abstract Profession getProfession();
	public abstract String getSpecies();
	public abstract boolean isPet();
	//getter
	public int getAge()
	{
		return age;
	}
	
	public Gender getGender() 
	{
		return gender;
	}

	public BodyType getBodyType() 
	{
		return bodyType;
	}
	
	//setter
	
	/*@
	 @requires age >= 0;
	 @ensures this.age = age;
	 @*/
	public void setAge(int age) 
	{
		this.age = age;
	}
	
	
	public void setGender(Gender gender) 
	{
		this.gender = gender;
	}
		
	
	public void setBodyType(BodyType bodyType) 
	{
		this.bodyType = bodyType;
		
	}
	
	
}