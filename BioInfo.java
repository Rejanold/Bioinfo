/**
 * Homeowrk Bio Info will accept Strings and read them in insert them into a Singly Linked List
 * Many methods are included to print the entire array of lists or just one position in the array
 * We can remove a List int he array completely. we can splice compatible bases from one list onto
 * another list, can clip a designated sequence with a start and end or just a start tot he end of the
 * list and insert that clip as the new list, we can swap bases with two lists of compatible types,
 * we can search for overlap of two compatible lists and check the suffix of the last list to the
 * prefix of the second list and return the bases that were found to be overlapping, we can manipulate
 * a list that is type DNA and convert it into RNA, and finally a translate method that will search
 * for start and end codons and convert the RNA sequence into and Amino Acid and replace that position
 * in the Array.
 * Class: CS245
 * Instructor: Dr. Amthauer
 * Authors: Robert Hable, Blake Furlano and Mason Waters
 * Completion Date :30 Oct 2019
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class BioInfo {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("");
        if (args.length == 2) {
            input = new File(bioFile(args[1]));// Accepts a file from the command line for use in our program
            int n = Integer.parseInt(args[0]); // Determins the length of our Array based on user command line arguments
            Sequence[] dnaSequensce = new Sequence[n]; //Array for holding our List objects
            Scanner fileScanner = new Scanner(input);
            while (fileScanner.hasNextLine()) { //Walking the passed file looking for input
                String line = fileScanner.nextLine(); //Checks for lines with valid input
                if (!line.trim().equals("")) {
                    Scanner lineScan = new Scanner(line);
                    String command = lineScan.next(); //grabs first command token
                    // The below commands are all commands we expect to recieve from Dr. Amthauers file.
                    if (command.equalsIgnoreCase("insert")) {
                        insertCommand(lineScan, dnaSequensce);

                    }
                    if (command.equalsIgnoreCase("print")) {
                        print(dnaSequensce, lineScan);
                    }
                    /**if (command.equalsIgnoreCase("print pos")) {
                     printPos(lineScan, dnaSequensce);
                     }*/
                    if (command.equalsIgnoreCase("remove")) {
                        remove(lineScan, dnaSequensce);
                    }
                    if (command.equalsIgnoreCase("splice")) {
                        spliceInto(lineScan, dnaSequensce);

                    }
                    if (command.equalsIgnoreCase("copy")) {
                        copy(lineScan, dnaSequensce);
                    }
                    if (command.equalsIgnoreCase("clip")) {
                        clip(dnaSequensce, lineScan);
                    }
                    if (command.equalsIgnoreCase("swap")) {
                        swapping(dnaSequensce, lineScan);
                    }
                    if (command.equalsIgnoreCase("overlap")) {
                        overlap(dnaSequensce, lineScan);
                    }
                    if (command.equalsIgnoreCase("transcribe")) {
                        transcribe(dnaSequensce, lineScan);
                    }
                    if (command.equalsIgnoreCase("translate")) {
                        translate(dnaSequensce, lineScan);
                    }

                }


            }

        } else { // Message given if command line arguments are incorrect.
            System.out.println("You entered the incorrect Input. Please see the ReadMe.txt file for instructions");
        }


    }

    /**
     * Accepts a filename and checks if its valid.
     *
     * @param fileName the name of the file
     * @return returns the filename for processing
     * @throws FileNotFoundException
     */
    public static String bioFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("That is not present");
            System.exit(0);
        }
        return fileName;
    }

    /**
     * After hours of painful work the insert command is finished and it does exactly what it should
     * It walks the file looking for a type in the 3rd position then sends that type and bases
     * to a helper method to verify that the bases are compatible with the type. If compatible it returns
     * true and we can insert the tokens into Elements in our Linked list.
     *
     * @param lineScan    Scanner for the text file
     * @param dnaSequence array for our Linked List
     */
    public static void insertCommand(Scanner lineScan, Sequence[] dnaSequence) {
        int n = lineScan.nextInt();//grab index we are inserting we are on
        if (n < 0 || n > dnaSequence.length - 1) {//position to insert is out of bounds silly.
            System.out.println("Invaild position to insert at position: " + n);
        } else {//valid position
            String type = lineScan.next();//grabs type
            //this if checks to see if the existing type is dna or was empty. if either of these are true,
            //we check to see if the type passed in the text file is equal to dna
            if (type.equalsIgnoreCase("dna")) {
                if (lineScan.hasNext()) {
                    String bases = lineScan.next();
                    if (validInput(type, bases)) {//we are happy with the bases and can create a new list
                        dnaSequence[n] = new Sequence(type, bases.toUpperCase());
                    } else { //The bases were not compatible with the given type.
                        System.out.println("Given bases were not correct for given type at position: " + n);
                    }
                } else {//Insert a type with no bases
                    dnaSequence[n] = new Sequence(type);
                }
            } else if (type.equalsIgnoreCase("rna")) {
                if (lineScan.hasNext()) {
                    String bases = lineScan.next();
                    if (validInput(type, bases)) {//we are happy with the bases and can create a new list
                        dnaSequence[n] = new Sequence(type, bases.toUpperCase());
                    } else {//The bases were not compatible with the given type.
                        System.out.println("Given bases were not correct for given type at position: " + n);
                    }
                } else {//Insert a type with no bases
                    dnaSequence[n] = new Sequence(type);
                }
            } else {//Passed a type that is not valid.
                System.out.println("The type you passed does not exist");
            }
        }
    }

    /**
     * Method to print everything in the Sequence Array
     * Or just a given position withing the Array.
     *
     * @param dnaSequence The array full of awesome objects
     * @param lineScan    Scanner so we can read stuff!
     */
    public static void print(Sequence[] dnaSequence, Scanner lineScan) {
        if (!lineScan.hasNextInt()) { //Why work when you dont have to?
            for (int i = 0; i < dnaSequence.length; i++) {
                if (dnaSequence[i] != null) { // Cant print if it doesnt exist!
                    dnaSequence[i].print(i); // Is there something to print? cool Print it!

                } else {
                    System.out.println(i); // Just print the position because this index is lonely
                }
            }
        } else { // A Print pos method we have two spots handling this because we werent sure if we needed to methods for this.
            int x = lineScan.nextInt();
            if (x < 0 || x > dnaSequence.length - 1) { // Out of bounds!
                System.out.println("The position " + x + " does not exist");
            } else {
                if (dnaSequence[x] == null) { // Printing nothing is bad for the environment!
                    System.out.println("There is no sequence to print at " + x);
                } else {//Hey Hey send that position back and show the user!
                    dnaSequence[x].print(x);
                }
            }
        }

    }

    /**
     * Simple method to remove a sequence from a given position in an array
     *
     * @param lineScan    Scanner to check the position passed
     * @param dnaSequence The actual array we are removing our object from
     */
    public static void remove(Scanner lineScan, Sequence[] dnaSequence) {
        int n = lineScan.nextInt(); //What position recieves the punishment?
        if (n < 0 || n > dnaSequence.length - 1) { //Cant erase something that isnt following the rules of bounds
            System.out.println("The position " + n + " is out of bounds, cannot remove.");
        } else {//Someone must have erased this position already
            if (dnaSequence[n] == null) { //Checking to see that there is actually something to remove
                System.out.println("There is no sequence to remove at position: " + n);
            } else { //Bring the pain you go away now.
                dnaSequence[n] = null;
            }
        }

    }

    /**
     * This method handles splicing given bases into a compatible pre existing Bases List
     *
     * @param lineScan    Scanner to walk the given information
     * @param dnaSequence the array passed so we can do some dirty work!
     */
    public static void spliceInto(Scanner lineScan, Sequence[] dnaSequence) {
        int n = lineScan.nextInt();
        if (n < 0 || n > dnaSequence.length - 1) { //Keeping the user in the bounds of our array
            System.out.println("The position " + n + " is out of bounds.");
        } else if (dnaSequence[n] == null) { // Cant splice into the void.
            System.out.println("There is no Sequence at position " + n);
        } else {// Time to do some microscopic dna work!
            String type = lineScan.next();
            String bases = lineScan.next();
            int start = lineScan.nextInt();
            if (validInput(type, bases)) {
                if (dnaSequence[n].getType().equalsIgnoreCase(type)) {
                    dnaSequence[n].splice(bases, start);
                } else {// Has to be matching types
                    System.out.println("Cannot splice if the types do not agree.");
                }
            } else {//checking for valid bases
                System.out.println("Type is not made up of valid bases.");
            }

        }
    }

    /**
     * Method to check if we can manipulate DNA sequences
     *
     * @param type  Type of DNA or RNA
     * @param bases DNA or RNA to be tested
     * @return returns a boolean to see if we can manipulate the DNA
     */
    public static boolean validInput(String type, String bases) {
        if (type.equalsIgnoreCase("dna")) { //Verifying that all of our critera for insert are met
            for (int i = 0; i < bases.length() - 1; i++) {
                if (!bases.substring(i, i + 1).equalsIgnoreCase("A")
                        && !bases.substring(i, i + 1).equalsIgnoreCase("T")
                        && !bases.substring(i, i + 1).equalsIgnoreCase("C")
                        && !bases.substring(i, i + 1).equalsIgnoreCase("G")) {
                    return false; // If its not valid print a message and move on
                }

            }
            return true;
        }

        if (type.equalsIgnoreCase("rna")) { //Same method as DNA above just checking for RNA
            for (int i = 0; i < bases.length() - 1; i++) {
                if (!bases.substring(i, i + 1).equalsIgnoreCase("A")
                        && !bases.substring(i, i + 1).equalsIgnoreCase("U")
                        && !bases.substring(i, i + 1).equalsIgnoreCase("C")
                        && !bases.substring(i, i + 1).equalsIgnoreCase("G")) {

                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Method that will walk our list find the position to clip inserts it into a new list and returns
     *
     * @param dnaSequence array with out list objects
     * @param lineScan    Scanner to walk because I cant walk
     */
    public static void clip(Sequence[] dnaSequence, Scanner lineScan) {
        int n = lineScan.nextInt();
        if (n < 0 || n > dnaSequence.length - 1) { //Keeping the user in the bounds of our array
            System.out.println("The position " + n + " is out of bounds");
        } else if (dnaSequence[n] == null) { //Sequence has a type but no sequence of bases.
            System.out.println("There is no Sequence at position " + n);
        } else {
            int start = lineScan.nextInt(); // the first position passed by the user to start clipping
            if (!lineScan.hasNextInt()) { //Did the user only pass us a start position
                int end = dnaSequence[n].getLength() - 1; //End becomes the end of the list
                if ((start < 0 || start > dnaSequence[n].getBases().length() - 1) || (end < 0 || end > dnaSequence[n].getBases().length() - 1)) {
                    System.out.println("Invalid position to clip please play again!");//Checking valid position
                } else {
                    dnaSequence[n].clipMe(start, end); //Print the cliped Bases
                }
            } else { //We have a start and end position passed by the user
                int end = lineScan.nextInt(); // end position passed by the user
                if ((start < 0 || start > dnaSequence[n].getBases().length() - 1) || (end < 0 || end > dnaSequence[n].getBases().length() - 1)) {
                    System.out.println("Invalid position to clip please play again!"); // Does the position exist
                } else if (end < start) { // Did they pass silly start and end that make no sense
                    String temp = dnaSequence[n].getType(); //Preserve the type
                    dnaSequence[n] = null; //bases are null
                    dnaSequence[n] = new Sequence(temp); // pass the type
                } else {
                    dnaSequence[n].clipMe(start, end); //print the clipped position
                }
            }
        }
    }

    /**
     * Method to copy a list at the first position and places an independent copy in the 2nd position.
     *
     * @param lineScan Scanner because someone has to work
     * @param sequence The overly abused apartment complex housing lists that move to much
     */
    public static void copy(Scanner lineScan, Sequence[] sequence) {
        int pos1 = lineScan.nextInt(); //First position
        int pos2 = lineScan.nextInt(); //Second Position
        if (pos1 < 0 || pos1 > sequence.length - 1) { //bounds pos 1
            System.out.println("Invalid position to clip at position: " + pos1);
        }
        if (pos2 < 0 || pos2 > sequence.length - 1) { //bounds pos 2
            System.out.println("Invalid position to clip at position: " + pos2);
        }
        if (sequence[pos1] == null) { // Anyone home?
            System.out.println("There is no sequence to copy at position: " + pos1);
        } else {
            if (sequence[pos1] != null) { //Someone is home!
                // put that copy cat in its own home
                sequence[pos2] = new Sequence(sequence[pos1].getType(), sequence[pos1].getBase());

            }
        }
    }

    /**
     * A method that accepts to Lists and 2 positions 1 for each list.
     * If these lists are of the same type we will swap the ends of each given position.
     *
     * @param dnaSequence More partially filled dwellings
     * @param lineScan    The thing that learned to read stuff and give good book reports
     */
    public static void swapping(Sequence[] dnaSequence, Scanner lineScan) {
        int pos1 = lineScan.nextInt(); //users first choice of a dwelling
        int start1 = lineScan.nextInt(); //which residents are we giving a new home from list 1
        int pos2 = lineScan.nextInt(); // users second dwelling choice
        int start2 = lineScan.nextInt(); //residents that are gonna move into the first dwellers beds

        if (pos1 < 0 || pos1 > dnaSequence.length - 1) { //Keeping the user in the bounds of our array
            System.out.println("The position " + pos1 + " is out of bounds");
        } else if ((dnaSequence[pos1] == null) || (dnaSequence[pos1].getBases().length() == 0)) { //Sequence has a type but no sequence of bases.
            System.out.println("There are no bases at position " + pos1);
        } else if (pos2 < 0 || pos2 > dnaSequence.length - 1) { //Keeping the user in the bounds of our array
            System.out.println("The position " + pos2 + " is out of bounds");
        } else if ((dnaSequence[pos2] == null) || (dnaSequence[pos2].getBases().length() == 0)) { //Sequence has a type but no sequence of bases.
            System.out.println("There are no bases at position " + pos2);
        } else if (!dnaSequence[pos1].getType().equalsIgnoreCase(dnaSequence[pos2].getType())) {
            System.out.println("The two types do not match cannot swap!");
        } else if (dnaSequence[pos1].getBases().length() < start1) { //Sequence has a type but no sequence of bases.
            System.out.println("There are no bases at position " + pos1);
        } else if (dnaSequence[pos2].getBases().length() < start2) { //Sequence has a type but no sequence of bases.
            System.out.println("There are no bases at position " + pos2);
        } else { //Sweet we survived error checking time to make some temp lists for the swap
            SLList<String> temp1 = dnaSequence[pos1].getBases(); //temp list 1
            SLList<String> temp2 = dnaSequence[pos2].getBases(); // temp list 2
            if (temp1.length() == start1 && temp2.length() == start2) {
                System.out.println("Both positions have no bases!"); //I dont remember why we did this but it doesnt work without it!
            } else {
                dnaSequence[pos1].listSwap(temp2, start1, start2); //Call the movers the paperwork is finished


            }
        }

    }

    /**
     * The allmighty overlap method does some sweet non big O efficient nested for loops to see what suffix
     * of pos 1 agrees with the prefix of pos 2, if there is none we head to the bar for some milk.
     *
     * @param dnaSequence the array of lists
     * @param lineScan    The person reading stuff for us
     */
    public static void overlap(Sequence[] dnaSequence, Scanner lineScan) {
        int pos1 = lineScan.nextInt(); //users first position
        int pos2 = lineScan.nextInt(); //users second position
        if (pos1 < 0 || pos1 > dnaSequence.length - 1) { //Keeping the user in the bounds of our array
            System.out.println("The position " + pos1 + " is out of bounds");
        } else if ((dnaSequence[pos1] == null) || (dnaSequence[pos1].getBases().length() == 0)) { //Sequence has a type but no sequence of bases.
            System.out.println("There are no bases at position " + pos1);
        } else if (pos2 < 0 || pos2 > dnaSequence.length - 1) { //Keeping the user in the bounds of our array
            System.out.println("The position " + pos2 + " is out of bounds");
        } else if ((dnaSequence[pos2] == null) || (dnaSequence[pos2].getBases().length() == 0)) { //Sequence has a type but no sequence of bases.
            System.out.println("There are no bases at position " + pos2);
        } else if (!dnaSequence[pos1].getType().equalsIgnoreCase(dnaSequence[pos2].getType())) {
            System.out.println("The two types do not match cannot swap!");
        } else {
            //Makes a nice result in accordance with Dr. Amthauers example stating start spot and No Arrow nodes!
            dnaSequence[pos1].overlapList(dnaSequence[pos2].getBases()).toStringNoArrow();
        }

    }

    /**
     * The method to confuse 100 programmers, after multiple arguments about Dr. Amthauers example being incorrect
     * we realised that we in fact were in correct and so here we are again with 2 for loops but not nested. This
     * amazing method takes DNA and convers the type to RNA and swaps bases to appropriate bases for RNA with stuff
     * that compliments stuff.
     *
     * @param dnaSequence array with lists that are about to get operated on
     * @param lineScan    The nurse reading from the book telling the doctor how to do his job!
     */
    public static void transcribe(Sequence[] dnaSequence, Scanner lineScan) {
        int pos1 = lineScan.nextInt(); //users position for transcribing
        if (pos1 < 0 || pos1 > dnaSequence.length - 1) { // bounds
            System.out.println("The position at " + pos1 + " is out of bounds");
        }
        //check type at pos1
        else if (dnaSequence[pos1].getType().equalsIgnoreCase("RNA")) {
            System.out.println("The sequence you are trying to transcribe is type RNA. To" +
                    " transcribe you need to have type DNA");
        }
        //check to see if the array list of bases is empty
        else if (dnaSequence[pos1].getBases() == null) {
            System.out.println("The sequence you are trying to transcribe is empty");
        } else {
            dnaSequence[pos1].transcribe(); //Commence surgery
        }


    }

    /**
     * Ive never wrote more code for one method in my life, But this thing of beauty will
     * accept a List of type RNA and walk codons starting with AUG and translating them
     * into Amino Acid Codons. It does this until it finds a stop codon if it does not find
     * a stop codon no changes are made. If it does find a stop type is changed to
     * Amino Acid and the new list of bases will be presented tot he user.
     *
     * @param dnaSequence Array of lists
     * @param lineScan    Kinda like the scanner in your office just not really
     */
    public static void translate(Sequence[] dnaSequence, Scanner lineScan) {
        int n = lineScan.nextInt(); //users pos to translate
        String test = dnaSequence[n].getType(); //gets the type to test
        if (n < 0 || n > dnaSequence.length - 1) {
            System.out.println("The given position " + n + " is out of bounds.");
        } else if (dnaSequence[n] == null) {
            System.out.println("Theire is no sequence at position " + n);
        } else if (test.equalsIgnoreCase("dna")) {
            System.out.println("Sequence must be RNA to translate."); //we can only translate RNA
        } else {
            SLList<String> temp = dnaSequence[n].translating(dnaSequence[n].getBases());//Temporary list of Amino acid
            if (temp.getHead() != null) { // Checking to see if it passed the codon check from sequence
                dnaSequence[n].setType("AA"); //it passed time to evolve
                dnaSequence[n].setBases(temp); //new bases for the Amino

            }
        }

    }
}