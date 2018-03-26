package otp_m12511318;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
public class OneTimePadding 
{
	public static void main(String[] args) throws Exception
	{
		File file_plaintext = new File("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\plaintext.txt");
		File file_key = new File("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\key.txt");
		Scanner scanFilePlainText = new Scanner(file_plaintext);
		Scanner scanFileKey = new Scanner(file_key);
		Scanner input = new Scanner(System.in);
		String plainTextMessage = null, key = null, newKeyGen=null, newPlainTextMessage=null;
		int lambdaValue;
		while(scanFilePlainText.hasNextLine()) 
		{
			plainTextMessage = scanFilePlainText.nextLine();//Read Plain text from file
		}
		System.out.println("Message: " + plainTextMessage + "  Length = " + plainTextMessage.length());
		while(scanFileKey.hasNextLine())
		{
			key = scanFileKey.nextLine();
		}
		System.out.println("Key for Padding: " + key+ "  Length = " + key.length());
		OneTimePadding obj1 = new OneTimePadding();
		String cipherText = obj1.Encryption(plainTextMessage, key);
		obj1.Decryption(cipherText, key);	
		
		System.out.println("\nEnter the Lambda value to generate Key of lambda length in Binary: = ");
		lambdaValue = input.nextInt();
		String keyOfLambdaValue = obj1.KeyGeneration(lambdaValue);
		System.out.println("Key = "+ keyOfLambdaValue);
		
		obj1.storeKeyGenFunction(3);
		obj1.frequencyGenerationFunction();
		
		File fileNewKey = new File("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\newkey.txt");
		Scanner scanNewFileKey = new Scanner(fileNewKey);
		File fileNewPlainText = new File("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\newplaintext.txt");
		Scanner scanNewPlainText = new Scanner(fileNewPlainText);
		while(scanNewFileKey.hasNextLine()) 
		{
			newKeyGen = scanNewFileKey.nextLine();//Read newKey text from file
		}
		while(scanNewPlainText.hasNextLine()) 
		{
			newPlainTextMessage = scanNewPlainText.nextLine();//Read new Plain text from file
		}
		
		double startTime = System.currentTimeMillis();
		obj1.Encryption(newPlainTextMessage, newKeyGen);
		double endTime = System.currentTimeMillis();
		System.out.println("For (lambda = 128) Encryption Function takes:= " + (endTime - startTime) + " ms");
		
		//obj1.avgRunningTime(newPlainTextMessage, newKeyGen);
		scanFilePlainText.close();
		scanFileKey.close();
		input.close();
	}
	
	public static String Encryption(String message, String key) throws FileNotFoundException, UnsupportedEncodingException
	{
		byte[] ascii_message = message.getBytes();		//Convert the message to ASCII
		System.out.print("ASCII value for the message: ");
		for(int i=0;i<message.length();i++) {
		System.out.print(ascii_message[i]);
		}
		StringBuilder binary = new StringBuilder();  	//Convert the message to binary digit
        for (byte b : ascii_message)  
        {  
           int value = b;  
           for (int i = 0; i < 8; i++)  
           {  
              binary.append((value & 128) == 0 ? 0 : 1);  
              value <<= 1;  
           }  
        } 
        System.out.println("\nBinary encoding for message = "+binary + "  Length = "+ binary.length());
        
        if(key.length() != binary.length())
        { //Condition to check if the Key length is equal to the length of the Message in binary
        	return "error: length is incorrect! plaintext message can be of 4 characters (max)"; 
        }
        else 
        {	//Logic to convert the Message to Cipher Text in Binary and then storing it into the file ciphertext.txt
        	System.out.println("\nMESSAGE ENCRYPTED!!\n");
        	StringBuilder cipherBinary = new StringBuilder();
        	for(int i= 0; i<key.length(); i++) //Running the loop till the length of the Key or Message
        	{
        		cipherBinary.append(key.charAt(i) ^ binary.charAt(i)); // Logic to XOR the Key and the Binary encoding of message
        	}
        	System.out.println("Ciphertext in Binary is = " + cipherBinary + "   Length = "+ cipherBinary.length());
        	PrintWriter fileStore = new PrintWriter("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\ciphertext.txt");
        	fileStore.print(cipherBinary);
        	fileStore.close();       	
        	return cipherBinary.toString();
        }
	}
	
	public void Decryption(String cipherText, String key) throws FileNotFoundException
	{
		//OTP decryption has to check if the length of ciphertext and Key is equal or not
		if(cipherText.length() != key.length())
		{
			System.out.println("\nLenght of Cipher Text and Key don't match. Message cannot be Decrypted Correctly!!");
		}
		else 
		{
			System.out.println("\nMESSAGE DECRYPTED!!");
			StringBuilder decryptBinary = new StringBuilder();
			for(int i= 0; i<key.length(); i++)
			{
				decryptBinary.append(key.charAt(i) ^ cipherText.charAt(i)); //XOR of cipher text and Key to generate message
			}
			System.out.println("\nDecrypted Message in Binary is = " + decryptBinary + "   Length = "+ decryptBinary.length());
			//Convert the decrypted binary to readable message
			String temp = "";   //Null String that will append the message
			char nextChar;
			for(int i = 0; i < decryptBinary.length(); i += 8) //Logic to convert the binary message to characters
			{
			     nextChar = (char)Integer.parseInt(decryptBinary.substring(i, i+8), 2);
			     temp =temp + nextChar;
			}
			System.out.println("Decrypted Plaintext is := "+temp);
			
			//Store the Decrypted Plain text message to File result.txt
			PrintWriter fileStore = new PrintWriter("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\result.txt");
        	fileStore.print(temp);
        	fileStore.close();
		}
	}
	
	public static String KeyGeneration(int lambda) throws FileNotFoundException, UnsupportedEncodingException
	{	
		String temp="";
		if(lambda>=1 && lambda <=128)  // For Lambda greater than 1 and less than 128 
		{
			Random rand = new Random();
			for(int i=0; i<lambda; i++) 
			{
				int  numberRandomBit = rand.nextInt(2); //select a random integer from 0 0r 1
				temp = temp + Integer.toBinaryString(numberRandomBit); //appending the integer selected randomly to a string
			}
			//To Store the key genarated to newkey.txt file
			PrintWriter fileStore = new PrintWriter("C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\newkey.txt");
        	fileStore.print(temp);
        	fileStore.close();
		    return temp;
		}
		else
		{
			return "Lambda should be between 1 and 128";
		}
	}
	String[] frequencyKeys = new String[5000]; //Global variable to store array of strings
	public void storeKeyGenFunction(int lambda) throws FileNotFoundException
	{
		for(int i=0; i<5000; i++) { // Loop is run 5000 times to generate all the combinatins of 3 bit binary randomly and store in String array
			String temp="";
			Random rand = new Random();
			for(int j=0; j<lambda; j++) 
			{
				int  numberRandomBit = rand.nextInt(2);
				temp = temp + Integer.toBinaryString(numberRandomBit);
			}
        	frequencyKeys[i]= temp;
		}
	}
	//Function that calculates the frequency of all the different permutation of the 3 bit key repeated 5000 times.
	public void frequencyGenerationFunction() {
		String[] threeBitPermutation = {"000","001","010","011","100","101","110","111"};
		float count=0;
		
		for(int i=0; i<threeBitPermutation.length;i++) {
			count = 0;
			for(int j=0; j<frequencyKeys.length; j++) {
				if(frequencyKeys[j].equals(threeBitPermutation[i])) {
					count++;
				}
			}
			System.out.println("Frequency of "+ threeBitPermutation[i] + " is : = "+ (count/5000));
		}
	}
}

