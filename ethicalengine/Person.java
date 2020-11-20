package ethicalengine;

/**
 * 
 * @author zhangyanni
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */

public class Person extends Character
{
	
	
	public enum Profession{DOCTOR,CEO,CRIMINAL,HOMELESS, UNEMPLOYED,UNKNOWN,NONE}
	public enum AgeCategory {BABY,CHILD,ADULT,SENIOR}
	
	public Profession profession;
	public AgeCategory ageCategory;
	public boolean isPregnant;
	public boolean isYou = false;
	
	public Person() 
	{
		super();
	}
	
	public Person(int age,Profession profession,Gender gender,BodyType bodyType, boolean isPregnant)
	{
		super(age,gender,bodyType);
		this.profession = profession;
		this.isPregnant = isPregnant;
		this.isPregnant = isPregnant;
	}
	
	public Person(int age,Gender gender,BodyType bodyType)
	{
		this.age = age;
		this.gender = gender;
		this.bodyType = bodyType;
	}

	public Person(Person otherPerson) 
	{
		super(otherPerson);
		this.profession = otherPerson.profession;
		this.isPregnant = otherPerson.isPregnant;
		
		
	}
	
	public AgeCategory getAgeCategory(int age) 
	{
		
		if(age >= 0 && age <= 4)
			return AgeCategory.BABY;
		else if(age >= 5 && age <= 16)
			return AgeCategory.CHILD;
		else if(age >= 17 && age <= 68)
			return AgeCategory.ADULT;
		else 
			return AgeCategory.SENIOR;
	
	}
	
	public void setAgeCategory(AgeCategory ageCategory) 
	{
		this.ageCategory = ageCategory;
	}
	
	
	public Profession getProfession() 
	{	
		if (getAgeCategory(getAge()) == AgeCategory.ADULT)
			return profession;
		else
			return Profession.NONE;//remain to change
		
	}
	
	public void setProfession(Profession profession) 
	{
		this.profession = profession;
	}
	
	public boolean isPregnant()
	{
		if(getGender() != Gender.FEMALE) 
			return false;
		else if(isPregnant)
			return true;
		else
			return false;
	}
	
	public void setPregnant(boolean pregnant) 
	{
		this.isPregnant = pregnant;
	}
	
	public boolean isYou() 
	{	
		return isYou;
	}
	
	
	public void setAsYou(boolean isYou) 
	{
		this.isYou = isYou;
	}
	
	public String toString()
	{
		String personString= ""; 
		
		if(isYou)
			personString  += "you ";
	 
		personString += bodyType.name().toLowerCase() + " ";// convert type enum to string  --> to lower case. a lot of time spent to think of it.
		personString += this.getAgeCategory(age).name().toLowerCase() + " ";//use getter to calculate the answer
			
		if(profession != null && !profession.equals(Profession.NONE))
			personString += profession.name().toLowerCase() + " ";
		
		
		
		if(isPregnant)
		{
			personString += gender.name().toLowerCase() + " " + "pregnant";
		}else 
		{
			personString += gender.name().toLowerCase(); 
		}
		
		return personString;	
	}

	@Override
	public String getSpecies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPet() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
}
