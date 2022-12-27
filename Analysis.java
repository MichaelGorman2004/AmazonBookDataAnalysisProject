import java.util.*;
import java.io.*;

public class Analysis {
//below is a method that fills a rating list with the ratings from the properly formatted file
   public static void fillRatingList(String fileName, List<Double> ratingList) {
      try {
         //int counter = 0;//initialize counter
         File fileString = new File(fileName); //declare file
         Scanner input = new Scanner(fileString);//declare scanner
         String firstLine = input.nextLine();//gets rid of first line which doesn't contain data
         while(input.hasNext()) { //while more to read
            String fileLine = input.nextLine(); //stores file line in string
            String[] tempLine = fileLine.split(","); //get temp line by splitting at "," and creates array
            double tempInt = Double.parseDouble(tempLine[2]); //converts rating to int value
            ratingList.add(tempInt); //fills list with rating value
            //counter++;//increments counter
         }
         input.close();//close scanner
      }catch(FileNotFoundException e) {
         System.out.println("ERROR - File " + fileName + " not found");//error statement
      }
   }
   //below is a method that calculates the mean rating of ratingList
   public static double calcMeanList(List<Double> ratingList) {
      int length = ratingList.size();//gets length of the list
      double sum = 0;//initializes sum
      for(int i = 0; i < length; i++) {//runs through list
         sum = sum + ratingList.get(i);//builds sum
      }
      double mean = sum/length;//calc mean
      return mean;//return mean
   }
   //below is a method that calculates the median in ratingList
   public static double calcMedianList(List<Double> ratingList) {
      int length = ratingList.size();//gets length
      double divLength = Double.valueOf(length);//converts length to double for division with median calculation
      double median;//initializes median
      if(length%2 == 0) {//if even number of books
         double mid1 = (divLength/2) - 0.5;
         double mid2 = (divLength/2) + 0.5;
         int val1 = (int)mid1;//converts mid1 to val1 int
         int val2 = (int)mid2;//converts mid2 to val2 int
         median = (ratingList.get(val1) + ratingList.get(val2)) / 2;//calculates median
      }else {
         median = ratingList.get(length/2);//calculates median
      }
      return median;//return median
   }
   //below is a method that calculates the standard deviation of ratingList
   public static double calcStandardDeviationList(List<Double> ratingList, double meanVal) {
      double stDev;//initializes standard deviation
      int length = ratingList.size();//initializes length
      double sum = 0;//initializes sum
      double val;//initializes val
      for(int i = 0; i < length; i++) {//runs through array
         val = Math.abs(ratingList.get(i) - meanVal);//finds absolute value distance between array value and mean
         sum = sum + val*val;//adds to sum that value squared
      }
      stDev = Math.sqrt(sum/length);//divides sum of distances to mean squared by the number of points in the array and then takes the square root which returns the stDev
      return stDev;
   }
   //below is a method that properly puts everything together and prints the histogram with the stats necessary underneath
   public static void printHistogram(List<Double> ratingList, double lowestVal, double highestVal, double meanVal, double medianVal, double stDevVal) {
      if(ratingList.isEmpty()){
      }else{
      System.out.println("Histogram of Amazon Bestseller Ratings");//start with desired header
      System.out.println("--------------------------------------");//lines
      int counter = 0;//initialize counter
      if(Math.abs(highestVal - 5) >= 0.001) {//if highest val is 5
         highestVal = 4.9;//This doesn't apply to these particular datasets because there is no 5 star rating, but if there were this changes it to 4.9 so that the histogram doesn't portray 5.1 since ratings only go to 5, in the loop below 0.1 is added to highestVal so it needs to be reassigned to 4.9 if it is higher than 4.9
      }
      for(double i = lowestVal - 0.1; i <= highestVal + 0.1; i = i + 0.1) { //for loop that runs through 0.1 under lowestVal and 0.1 above highest val
         for(int j = 0; j < ratingList.size(); j++) {//runs through ratingList
            if(Math.abs(ratingList.get(j) - i) <= 0.0001) {//if ratingList of j is same as i, the value that is currently being tested
               counter++;//increment the counter to keep track of how many times it appears
            }
         } 
         System.out.format("%1.1f ", i);//print the rating cut off at 1 decimal
         for(int k = 0; k < counter; k++) {//for as many times as the counter is currently valued at
            System.out.print("*");//print that many stars
         }
         System.out.println();//new line for next rating
         counter = 0;//reset counter so the stars don't accumulate
      }
      System.out.println("--------------------------------------");//lines for format
      System.out.println("Total books rated: " + ratingList.size());//print statement for total books rated
      System.out.format("Median score: %1.1f\n", medianVal);//print statement for median value of ratings
      System.out.format("Average score: %1.1f\n", meanVal);//print statement for mean value of ratings
      System.out.format("Standard Deviation: %1.2f\n", stDevVal);//print statement for standard deviation of ratings
      }
   }
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);//declare scanner
      System.out.print("Enter a filename: ");//prompts user for file name
      String fileName = input.nextLine();//stores file name
      List<Double> ratingList = new ArrayList<Double>();//initializes Arraylist ratingList
      fillRatingList(fileName, ratingList);//fills the ratingList with the ratings in the file
      Collections.sort(ratingList);//sorts the rating list from lowest to highest
      if(ratingList.isEmpty()){//runs nothing besides the error message if it is empty because it will give index out of bounds errors because the length will be 0
      }else{//if it's not empty, proceed
      double maxVal = ratingList.get(ratingList.size() - 1);//since sorted in ascending order, size - 1 is last value and thus the highest
      double minVal = ratingList.get(0);//since ascending, first value is lowest
      double meanVal = calcMeanList(ratingList);//calculates the mean value
      double medianVal = calcMedianList(ratingList);//calculates the median value
      double stDevVal = calcStandardDeviationList(ratingList, meanVal);//calculates the standard deviation
      printHistogram(ratingList, minVal, maxVal, meanVal, medianVal, stDevVal);//prints all stats and histogram
      }
      input.close();//closes scanner
   }
}