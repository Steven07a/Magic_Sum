/*
 * Author: Steven Herrera
 * Class: CS 282 
 * Meeting Time: M,W 3:30 - 4:45pm
 * Assignment #1
 * Project: MagicSquare
 * Purpose: To test three different algorithims which are meant to find a Magic Square 
 *          which is a square where all rows columns and diagnols add up to the same number.
 * 
 * Date turned in: 
 * Notes: 
 */
import java.util.Random;

class MagicSquare {
    private int size, tryCt, row, col, magicSum, sizeSqr;
    private int[] num;
    private int[][] square;
    private boolean found;
    private Random randNum;

    // constructor -- set the size and instantiate the array
    public MagicSquare(int size) {
        this.size = size;
        this.row = size;
        this.col = size;
        this.square = new int[row][col];
        this.found = false;
        this.magicSum = ((size * (size * size + 1)) / 2);
        this.sizeSqr = size * size;
        this.num = new int[sizeSqr];
        this.randNum = new Random();

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                this.square[i][j] = 0;
            }
        }

        for(int i = 0; i < (sizeSqr); i++) {
            num[i] = (i + 1);
        }
    }

    // purely random -- give up after "tries" tries
    public int purelyRandom(int tries) {
        int result = 0;
        int[] sortedNum;
        sortedNum = num.clone();
        while (!found && tryCt < tries) {
            int tempNumSize = sizeSqr;
            for(int i = 0; i < row; i++) {
                for(int j = 0; j < col; j++) {
                    //We get a random number and place it into the square this continues 
                    //till the entire square is filled
                    int tempRandNum = randNum.nextInt(tempNumSize);
                    square[i][j] = num[tempRandNum];
                    int tempNum = num[tempRandNum];
                    num[tempRandNum] = num[tempNumSize-1];
                    num[tempNumSize-1] = tempNum;
                    tempNumSize--;
                }
            }

            found = magic();
            num = sortedNum.clone();
            tryCt++;
        }
        
        //If found is true then that means we have a correct Magic Square
        if (found)
            result = tryCt;
        else
            result = -1;
    
        return result;
    }
    
    // Algorithim which forces last number in each row
    public int endOfRow(int tries) {
        while (!found && tryCt < tries) {
            boolean ok = true;
            int tempNumSize = sizeSqr, rowSum = 0, lastIndex = 0, pick = 0;
            
            int[] sortedNum;
            sortedNum = num.clone();

            // fills rows one at a time
            for (int rowCt = 0; rowCt < size && ok; rowCt++) {
                rowSum = 0;
                // put random values in all but the last spot
                for (int colCt = 0; colCt < size - 1; colCt++) {
                    int tempRandNum = randNum.nextInt(tempNumSize);
                    square[rowCt][colCt] = num[tempRandNum];
                    rowSum += num[tempRandNum];
                    swap(tempRandNum, tempNumSize-1, num);
                    tempNumSize--;
                }
                // Searches for the final number which would make this row work for a 
                // magic square if none is found then set ok to false 
                lastIndex = tempNumSize;
                pick = find(magicSum - rowSum, num, lastIndex);

                if (pick == -1)
                    ok = false;
                else {
                    // If we found a number which works then we add that number to the square 
                    // and remove that number from our pool of available numbers
                    int tempNum = num[pick];
                    swap(pick, tempNumSize-1, num);
                    tempNumSize--;
                    square[rowCt][size-1] = tempNum;
                }
            }
            found = magic();
            num = sortedNum.clone();
            tryCt++;
        }
        
        if (!found) {
            tryCt = -1;
        }

        return tryCt;
    }

    //This algorithim works by getting a random number from our available numbers then searching for its pair.
    //it does this until the square is either complete or until we are unable to find one at which point it exits the loop 
    public int pairs(int tries) {
        int[] indexArr = new int[size];
        int pairMagicSum = magicSum/(size/2);
        boolean ok = true;
        tryCt = 0;
        found = false;

        for(int i = 0; i < size; i++) {
            indexArr[i] = i;
        }

        while(!found && tryCt < tries) {
            int indexArrSize = size, tempNumSize = sizeSqr, firstNumPair = 0, pick = 0;

            for(int rowCt = 0; rowCt < size && ok; rowCt++) {
                firstNumPair = 0;
                indexArrSize = size;
                for(int colCt = 0; colCt < size/2; colCt++) {
                    // we get a random number and random column and place our number into that spot inside the square
                    int tempRandNum = randNum.nextInt(tempNumSize);
                    int randCol = randNum.nextInt(indexArrSize);
                    
                    firstNumPair = num[tempRandNum];
                    square[rowCt][indexArr[randCol]] = num[tempRandNum];
                    swap(randCol, indexArrSize-1, indexArr);
                    swap(tempRandNum, tempNumSize-1, num);
                    indexArrSize--;
                    tempNumSize--;
                    //we use the find function to search through our number array if we cant find that number then we exit 
                    pick = find(pairMagicSum - firstNumPair, num, tempNumSize);

                    if(pick == -1) {
                        ok = false;
                    } else {
                        //When the other half of the pair is found then we insert it into the array at a random column and remove that number from our 
                        //array of possible columns and numbers
                        randCol = randNum.nextInt(indexArrSize);
                        square[rowCt][indexArr[randCol]] = num[pick];
                        swap(randCol, indexArrSize-1, indexArr);
                        swap(pick, tempNumSize-1, num);
                        indexArrSize--;
                        tempNumSize--;
                    }
                }

            }
            found = magic();
            tryCt++;
        }

        if (!found) {
            tryCt = -1;
        }

        return tryCt;

    }
    
    // determine if a magic square has been created, i.e., check all rows, columns,
    // and diagonals sum to the same value
    private boolean magic() {
        int rowSum = 0, colSum = 0, diagnolSum1 = 0, diagnolSum2 = 0, trueDiag = 0, trueRow = 0, trueCol = 0;

        //This checks all rows and columns to see if they add up to the magic sum if they do add one to trueRow or trueCol
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                rowSum += square[i][j];
                colSum += square[j][i];
            }
            if(rowSum == magicSum) {
                trueRow++;
            }
            if(colSum == magicSum) {
                trueCol++;
            }
            rowSum = 0;
            colSum = 0;
        }

        //checks the two diagnols to see if they are equal to magic sum
        int tempSize = size-1;
        
        for(int i = 0; i < size; i++){
            diagnolSum1 += square[i][i];
            diagnolSum2 += square[tempSize][i];
            tempSize--;
        }

        if(diagnolSum1 == magicSum) {
            trueDiag++;
        }
        if(diagnolSum2 == magicSum) {
            trueDiag++;
        }

        //This function will only return true if all rows and colums are equal to the magic number as well as both diagnols
        if (trueRow == size && trueCol == size && trueDiag == 2) {
            found = true;
        }

        return found;
    }
    
    // output the magic square (or whatever is in the array if it is not)
    public void out() {
        int row, col;
        for (row = 0; row < size; row++) {
            for (col = 0; col < size; col++) {
                System.out.print(String.format("%3d", square[row][col]));
            }
            System.out.println();
        }
    }
    
    // takes the number to find and does a linear search through the number array if its found return its index otherwise return -1
    private static int find(int numToFind,int[] numArr,int lastIndex) {
        int index = 0;
        if(numToFind <= numArr.length) {
            while(numArr[index] != numToFind && index < lastIndex-1) {
                index++;
            }
        } else {
            //Number is not found return index of -1
            index = -1;
        }
        if(index == -1 || numArr[index] != numToFind) {
            index = -1;
        }
        return index;
    }

    //function which is used to swap numbers inside of an array
    private static void swap(int firstIndex, int secondIndex, int[] arr){
        if(secondIndex == 0) {
            secondIndex = 0;
        }
        int tempNum = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = tempNum;
    }

    // change to false if this algorithm was not implemented
    public boolean rowLastImplemented() {
        return true;
    }
    
    // change to false if this algorithm was not implemented
    public boolean pairsImplemented() {
        return true;
    }
    
    // put your name here
    public static String myName() {
        return "Steven Herrera";
    }
}