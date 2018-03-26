Language Used: Java 
Version: java version "1.8.0_161"
IDE compiled: Eclipse Oxygen Release (4.7.0)

STEPS TO CHECK THE CORRECTNESS OF THE PROGRAM

1. Setup the file path before executing. Program would not run correctly if all the file paths are not set up. 
	Files that needs to be set up are. ciphertext.txt, generateKey.txt, key.txt, newkey.txt, newplaintext.txt, plaintext.txt, result.txt
	In my case the file path is C:\\Users\\Niraj\\eclipse-workspace\\otp_m12511318\\data\\*.txt. Replace this with the desired location on 
	your computing system.
2. Once the file locations have been set-up. You are ready to run your program. 
3. Before executing if any changes are required to made to the above file please do it. Names of the files and functions are mentioned below.


NOTE: to check the time taken by the encryption function please enter 
	Enter the Lambda value to generate Key of lambda length in Binary: = 128 when asked. Orelse will give a wrong output and messaage will not be encrypted.

	Do not enter more than 4 character in plaintext.txt. Program will not run as desigred. Though it will catch all exceptions and give message in output window.

Key.txt : - contains 32 bit key
plaintext :- contains the message to be encrypted
newkey.txt :- contains the new key value according to the lambda value
ciphertext.txt :- contains the cipher text after encryption
result.txt :- contains the decrypted message
newplaintext :- contains character length 16 messsage to check the encryption taken by encryption using lambda = 128