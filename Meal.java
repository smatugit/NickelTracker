import java.util.*;
import java.io.*;


class Meal{
  //declare attributes

  Scanner keebs = new Scanner (System.in);

  String nameFood;
  int numFoods;  
  String yn;
  //int foodinfo;
  String title ;
  String time;
  ArrayList<Food> mealFoods = new ArrayList <Food>();
  int tempIndex;

  //constructor
  public Meal(String title, String time)
  {
    this.title = title;
    this.time = time;
  }



  /*
  adds food to Meal's food arraylist, should implement GUI later
  @param theDB - arraylist foods, database
  void. changes obj attributes 
  */
  public void addFood(ArrayList<Food> theDB)
  {
    System.out.print ("Food?: ");
    nameFood = keebs.nextLine();


    //READ file, compare to input
    if(searchDB(nameFood,theDB)){//add to array
      System.out.print ("Number of servings?: ");
      numFoods = getPosInt(keebs);

      //for each serving, add 1 of food to mealFoods
      for(int i = 0;i<numFoods;i++){
        mealFoods.add(theDB.get(tempIndex));
      }

    }else{//add to DB instead, if doesnt exist
      addToDB(nameFood, theDB);
    }


    
  }

  /*
  Finds total Nickel in the arraylist of Foods
  Returns the count value
  */
  public double tallyNickel()
  {
    double count = 0.0;
    for (Food i: mealFoods)
      count += i.nickel;
    return count;
  }


  /*
  The name of the food is added to the txt file database
  Also adds the nickel amount to the database
  @param newName - user input name, not in DB already, LOWERCASE
  @param theDB - the database, arraylist foods
  @return theDB, modified (if user says Yes) - 1 new food added
  */
  private ArrayList<Food> addToDB(String newName, ArrayList<Food> theDB)
  {
    
    System.out.println ("Input Doesn't Compute");
    System.out.println ("Would you like to add it to the database");
    yn = keebs.nextLine();

     
    if (yn.toLowerCase().equals ("y"))
    {
      System.out.print ("What is the nickel amount?: ");
      double foodInfo = Integer.parseInt(keebs.nextLine());

      //modify arg, add to mealarray
      theDB.add(new Food(newName,foodInfo));
      mealFoods.add(new Food(newName, foodInfo));

      //REWRITE all db contents to file
      try {
      //connect to file, buffer
      FileWriter writer = new FileWriter("database.txt");
      BufferedWriter buffer = new BufferedWriter(writer);

      //write lines w/ buffer 
      for(Food x:theDB){
        buffer.write(x.name);
        buffer.newLine();
        buffer.write("" + x.nickel);
        buffer.newLine();
      }
     
      
      buffer.close(); //close
      writer.close();
    }catch(Exception ex){
      System.out.println(ex.getMessage());
    }//end write


      


    }
    return theDB;
  }



  /*
  linear search of DB txt file, 
  check if string is == to title of food in file
  creates new Food obj of what it finds
  @param input - string, to be compared
  @param theDB - arraylist foods, what the total is
  @return boolean - true if in file/DB
  */
  private boolean searchDB(String input, ArrayList<Food> theDB){

    for(Food i:theDB){
      if(input.equals(i.name)){
        tempIndex = theDB.indexOf(i);
        return true;
      }
    }
    
    return false;//else
  }




  /*
  Asks for (+) int input, gets input
  @param keyboard - Scanner, will not be closed
  @return input - int input (+)
  */
  private static int getPosInt(Scanner keyboard){

    System.out.print("Enter a positive integer: ");
    int input;
    do{
      try{
        input = Integer.parseInt(keyboard.nextLine());        
        if(input >= 0){ //if positive, end
          return input;
        }else{
          System.out.print("Value must be positive, retry: ");
        }
      }catch(NumberFormatException ex){
        System.out.print("Invalid input, retry: ");
        continue; //retry
      }
    }while(true); //does not exit until valid input reached
  }

}