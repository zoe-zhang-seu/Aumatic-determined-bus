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

public class Animal extends Character
{
	
		String species;
		boolean isPet;
		boolean isPregnant;
		boolean isYou;
		
		/**
		 * constructor
		 */
		public Animal() 
		{
			super();
		}
		
		/**
		 * 
		 * @param species: read from file or  genarated randomly
		 */
		public Animal(String species) 
		{
			super();
			this.species = species.toLowerCase();
		}
		/**
		 * 
		 * @param otherAnimal : use object to create 
		 */
		public Animal(Animal otherAnimal) 
		{
			super();
			this.species = otherAnimal.getSpecies().toLowerCase();
			
		}
		
		public String getSpecies() 
		{
			return species;
		}

		public void setSpecies(String species) 
		{
			this.species = species;
		}
		
		public boolean isPet() 
		{
			if(isPet)
				return true;
			else
				return false;
		}
		
		public void setPet(Boolean isPet) 
		{
			this.isPet =  isPet;
		}
		
		public String toString() 
		{
			if(isPet)
				return species + " is pet";
			else
				return species ;
		}

		@Override
		public boolean isYou() {
			// TODO Auto-generated method stub
			return isYou;
		}
		public void setAsYou(boolean isYou) 
		{
			this.isYou = isYou;
			
		}

		@Override
		public boolean isPregnant() {
			// TODO Auto-generated method stub
			return isPregnant;
		}
		public void setPregnant(boolean pregnant) 
		{
			this.isPregnant = pregnant;
		}
		

		@Override
		public AgeCategory getAgeCategory(int age) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Profession getProfession() {
			// TODO Auto-generated method stub
			return null;
		}



	

	

		

}
