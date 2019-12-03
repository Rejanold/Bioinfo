import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Sequence Class for BioInfo Homework
 * CS245
 * Professor: Dr. Amthauer
 * Authors Robert Hable, Blake Furlano and Mason Waters
 */
public class Sequence {
    private String type; //private data fields
    private SLList bases;
    private String base;

    /**
     * Constructor
     * @param type DNA RNA or AA for types with no bases
     */
    public Sequence(String type) {
        this.type = type.toUpperCase();
        this.bases = new SLList<String>();
    }

    /**
     * Constructor for a type with bases
     * @param type DNA RNA or AA
     * @param base the corresponding bases for the type
     */
    public Sequence(String type, String base) {
        this.type = type.toUpperCase();
        this.base = base.toUpperCase();
        this.bases = addBasetoSLList(base);
    }

    /**
     * Prints our lists.
     * @param i the position in the array
     */
    public void print(int i) {
        if (bases.length() == 0) {
            System.out.println(i + "    " + type);
        } else {
            System.out.println(i + "    " + type + " ==> " + bases.toString().toUpperCase());
        }

    }

    /**
     * We wrote this method and I dont believe we ever used it
     * @param line when we were being naughty and tried manipulating strings
     */
    public void inputLine(String line) {
        Scanner linerScan = new Scanner(line);
        while (linerScan.hasNext()) {
            String result = linerScan.next(); //Reads each token on a given line
            System.out.println(result);

        }
    }

    /**
     * After reading in bases from the file we walk each token of the bases and insert
     * into its node int he list.
     * @param base the String representation of the base from the file
     * @return the newly created SLList
     */
    public SLList<String> addBasetoSLList(String base) {
        int n = base.length();
        SLList<String> temp = new SLList<String>();
        for (int i = 0; i < n; i++) {
            temp.add(base.substring(i, i + 1));
        }
        return temp;

    }

    /**
     * gets the Type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * used to change the type when needed
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Does the dirty work for splice
     * @param splice the bases we are passing from BioInfo to splice
     * @param i the position of the bases
     */
    public void splice(String splice, int i) {
        SLList<String> spliceList = addBasetoSLList(splice); // List of bases
        if (i < this.bases.length() - 1 && i > 0) {
            this.bases.insertList(spliceList, i); //Sends bases to SLList for splicing
        } else {
            System.out.println("Woa there cowboy that position doesn't exist.");
        }
    }

    /**
     * returns the bases
     * @return the bases
     */
    public SLList<String> getBases() {
        return this.bases;
    }

    /**
     * gets the string representation the base
     * @return the base
     */
    public String getBase() {
        return base;
    }

    /**
     * A method to pass to SLList so we can cut nodes off or evict tenants however you wish to see it.
     * @param start the start pos
     * @param end the end pos
     */
    public void clipMe(int start, int end) {
        this.bases.clipPos(start, end);
    }

    /**
     * used to clear the list but that is already in SLList now im just too scared to delete it.
     */
    public void clear() {
        this.bases.clear();
    }

    /**
     * Its good to know the length of the list
     * @return the length!
     */
    public int getLength() {
        int length = this.bases.length();
        return length;
    }

    /**
     * This method is fun we manipulate two lists to swap bases
     * @param swapDna the second list
     * @param start1 where we start chopping nodes from this.bases then attaching list 1
     * @param start2 where we start chopping nodes from the passed list then re attaching
     */
    public void listSwap(SLList<String> swapDna, int start1, int start2) {
        if (start1 < 0 || start1 > this.bases.length()) {
            throw new IndexOutOfBoundsException(Integer.toString(start1));

        } else {
            this.bases.swap(swapDna, start1, start2);
        }
    }

    /**
     * we wrote this for some reason thinking we could manipulate the size here.
     * Follish attempt but now I am to scared to delete it cause the program works
     * @param n would have been the array index
     */
    public void setSize(int n) {
        this.bases.setLength(n);
    }

    /**
     * Method to get the nodes that are the suffix of list 0 then test them against
     * the prefix nodes of list 2. This then returns the position where overlap begins
     * and the new list of overlapped nodes
     * @param pos2 the second list to check prefix
     * @return
     */
    public SLList<String> overlapList(SLList<String> pos2) {
        SLList<String> overlapHold = new SLList<String>();
        int m = 0; //length of list 1
        int size = 0; // used to calculate what position overlap begins
        while (m < this.getLength()) { //how we escape
            int k = m; //reference to list 1 for checking
            for (int j = 0; j <= pos2.length(); j++) {//pos2 walk
                if (k < this.getLength()) { //can we walf
                    if (this.bases.getValue(k).equals(pos2.getValue(j))) {//do the bases match
                        overlapHold.insert(j, pos2.getValue(j)); //insert into our new list
                        k++;
                        if (k == this.getLength()) {
                            m = this.getLength();
                        }
                    } else {//bases didnt match empty the list and start from the next base in pos 1
                        j = pos2.length() + 1;
                        overlapHold.clear();
                    }
                } else {
                    j = pos2.length();
                    m++;
                    size = getLength() - overlapHold.length();//the beginning of overlap

                    System.out.println("Overlap begins at position " + size + " " + overlapHold.toStringNoArrow().toUpperCase());
                }
            }
            m++;

        }
        //System.out.println(size);
        if (overlapHold.length() == 0) {
            System.out.println("There is no OverLap");
        }
        return overlapHold;
    }

    /**
     * Converts DNA into RNA and compliments letters accordingly
     */
    public void transcribe(){
        for(int i = 0; i < this.bases.length(); i++) {
            //starts with replacing all the t's
            if (this.bases.getValue(i).equals("T")) {

                this.bases.setElement(i, "U");
            }
        }//When all the t's have been changed we compliment
        for(int i = 0; i < this.bases.length(); i++){
            if(this.bases.getValue(i).equals("A")) {

                this.bases.setElement(i, "U");
            }
            else if(this.bases.getValue(i).equals("C")) {
                this.bases.setElement(i, "G");
            }
            else if(this.bases.getValue(i).equals("U")) {
                this.bases.setElement(i, "A");
            }
            else if(this.bases.getValue(i).equals("G")) {
                this.bases.setElement(i, "C");
            }
        }//turnnnnn arrrrounnnd
        this.bases.reverse();
        //this is my type now
        this.type = "RNA";
    }

    /**
     * Effectivly the most tedious method Ive ever written, however Im sure there is way worse methods in this
     * world, keeping track of Ifs is unbelievably important. Anyhoo this behemoth takes any RNA sequence
     * and walks its bases checking for a start codon and if it has one then we examine each codon group
     * and if a valid stop codon exists we translate that sequence into an Amino Acid.
     * @param bases Dont really need this could have just uses this alot but It works so thumbs up!
     * @return the new SLLlist with an Ammino acid
     */
    public SLList<String> translating(SLList<String> bases) {
        SLList<String> AAList = new SLList<String>();
        //Cant start without a start codon so test it!
        for (int i = 0; i < bases.length(); i++) {
            if (bases.getValue(i).equalsIgnoreCase("A")
                    && bases.getValue(i + 1).equalsIgnoreCase("U")
                    && bases.getValue(i + 2).equalsIgnoreCase("G")) {
                AAList.add("M");
                i+=3; //keeping track of my position was depressing to figure out


                //Every part below is checking each base then walking the next two bases checking
                //for a valid codon group and adding it to our list
                //If the sequence didnt have an end codon clear the list and change nothing
                //If we have a valid start and walk to a valid End stop and return the list

                for (int j = i; j < bases.length()-2; j++) {

                    //Checking all Codons that begin with the letter U.
                    if (bases.getValue(j).equalsIgnoreCase("U")) {
                        if (bases.getValue(j + 1).equalsIgnoreCase("A")) {
                            if (bases.getValue(j + 2).equals("A")
                                    || bases.getValue(j + 2).equalsIgnoreCase("G")) {
                                return AAList;
                            }else if (bases.getValue(j+2).equalsIgnoreCase("U")
                                    || bases.getValue(j+2).equalsIgnoreCase("C")){
                                AAList.add("Y");
                                j+=2;
                            }

                        }else if(bases.getValue(j+1).equalsIgnoreCase("G")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")){
                                AAList.add("C");
                                j+=2;
                            }
                            else if(bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("W");
                                j+=2;
                            }
                            else if(bases.getValue(j+2).equalsIgnoreCase("A")){
                                return AAList;
                            }

                        }
                        else if (bases.getValue(j + 1).equalsIgnoreCase("U")){
                            if (bases.getValue(j + 2).equalsIgnoreCase("U")
                                    || bases.getValue(j + 2).equalsIgnoreCase("C")){
                                AAList.add("F");
                                j+=2;
                                //System.out.println(AAListSize);
                            }else if (bases.getValue(j + 2).equalsIgnoreCase("A")
                                    || bases.getValue(j + 2).equalsIgnoreCase("G")) {
                                AAList.add("L");
                                j+=2;
                            }
                        }else if (bases.getValue(j + 1).equalsIgnoreCase("C")) {
                            if (bases.getValue(j + 2).equalsIgnoreCase("U")
                                    || bases.getValue(j + 2).equalsIgnoreCase("C")
                                    || bases.getValue(j + 2).equalsIgnoreCase("A")
                                    || bases.getValue(j + 2).equalsIgnoreCase("G")) {
                                AAList.add("S");
                                j+=2;
                            }
                        }
                    }
                    //Checking all codons that begin with the letter C.
                    else if (bases.getValue(j).equalsIgnoreCase("C")) {
                        if (bases.getValue(j + 1).equalsIgnoreCase("U")) {
                            if (bases.getValue(j + 2).equalsIgnoreCase("U")
                                    || bases.getValue(j + 2).equalsIgnoreCase("C")
                                    || bases.getValue(j + 2).equalsIgnoreCase("A")
                                    || bases.getValue(j + 2).equalsIgnoreCase("G")) {
                                AAList.add("L");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("C")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")
                            || bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("P");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("A")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")){
                                AAList.add("H");
                                j+=2;
                            }else if(bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("Q");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("G")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")
                            || bases.getValue(j+2).equalsIgnoreCase("C")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("R");
                                j+=2;
                            }
                        }
                    }
                    //Checking all codons that begin with the letter A.
                    else if (bases.getValue(j).equalsIgnoreCase("A")) {
                        if (bases.getValue(j + 1).equalsIgnoreCase("U")) {
                            if (bases.getValue(j + 2).equalsIgnoreCase("U")
                                    || bases.getValue(j + 2).equalsIgnoreCase("C")
                                    || bases.getValue(j + 2).equalsIgnoreCase("A")) {
                                AAList.add("I");
                                j+=2;
                            }
                            else if (bases.getValue(j + 2).equalsIgnoreCase("G")) {
                             AAList.add("M");
                             j+=2;
                             }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("C")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")
                            || bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("T");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("A")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")){
                                AAList.add("N");
                                j+=2;
                            }
                            else if(bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("K");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("G")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")){
                                AAList.add("S");
                                j+=2;
                            }else if(bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("R");
                                j+=2;
                            }
                        }
                    }
                    //Checking all codons that begin with the letter G.
                    else if (bases.getValue(j).equalsIgnoreCase("G")) {
                        if (bases.getValue(j + 1).equalsIgnoreCase("U")) {
                            if (bases.getValue(j + 2).equalsIgnoreCase("U")
                                    || bases.getValue(j + 2).equalsIgnoreCase("C")
                                    || bases.getValue(j + 2).equalsIgnoreCase("A")
                                    || bases.getValue(j + 2).equalsIgnoreCase("G")) {
                                AAList.add("V");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("C")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")
                            || bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("A");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("A")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")){
                                AAList.add("D");
                                j+=2;
                            }else if(bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("E");
                                j+=2;
                            }
                        }else if(bases.getValue(j+1).equalsIgnoreCase("G")){
                            if(bases.getValue(j+2).equalsIgnoreCase("U")
                            || bases.getValue(j+2).equalsIgnoreCase("C")
                            || bases.getValue(j+2).equalsIgnoreCase("A")
                            || bases.getValue(j+2).equalsIgnoreCase("G")){
                                AAList.add("G");
                                j+=2;
                            }
                        }
                    }
                }
                System.out.println("No reading frame in the sequence");
                AAList.clear();//no stop codon
                return AAList;
            }
        }
        setBases(AAList); //got some new bases to work with
        return AAList;


    }

    /**
     * needed this for translate
     * @param bases the bases
     */
    public void setBases(SLList bases) {
        this.bases = bases;
    }
}