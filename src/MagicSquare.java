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

        //This is how I set up the main loop. I’m showing you as a hint.
        //Yours can be different. But why mess with a good thing?  :)
        int[] sortedNum;
        sortedNum = num.clone();
        while (!found && tryCt < tries) {
            int tempNumSize = sizeSqr;
            
            for(int i = 0; i < row; i++) {
                for(int j = 0; j < col; j++) {
                    int tempRandNum = randNum.nextInt(tempNumSize);
                    square[i][j] = num[tempRandNum];
                    int tempNum = num[tempRandNum];
                    num[tempRandNum] = num[tempNumSize-1];
                    num[tempNumSize-1] = tempNum;
                    tempNumSize--;
                    //System.out.println("tempNumSize: " + tempNumSize);
                }
            }

            found = magic();
            num = sortedNum.clone();
            tryCt++;
        }
        
        //This is how my method ends
        if (found)
            result = tryCt;
        else
            result = -1;
    
        return result;
    }
    /*
    // force last number in each row
    public int endOfRow(int tries) {

        while (!found && tryCt < tries) {
            int tempNumSize = sizeSqr;

            //This is how I filled the magic square array.
            //Many statements are missing, but this should
            //give you some ideas.Notice the ok variable. 
            //If at any time a row cannot befinished there’s no point in continuing to later rows.
            
            // fills rows one at a time
            for (row = 0; row < size && ok; row++) {
                
                // put random values in all but the last spot
                for (col = 0; col < size - 1; col++) {

                }
                
                // Following is the code to place the last element ina row. 
                // Again, you can change these lines if you like.
                // call local method to find needed last element
                // num is the array described above.
                // pick points to that element in the num array

                pick = find(magicSum - rowsum, num, lastNum);

                if (pick == -1)
                    ok = false;
                else {

                }
            }
            found = magic();
        }
        
        if (found)
            return tryCt;
        else
            return -1;
    }
    */
    //No hints for this one (yet), but it is hard. 
    //Concentrate on the first two
    //algorithms and work on this as time allows.
    //put pairs of numbers in rows
    //public int pairs(int tries) {

    //}
    
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
            diagnolSum2 += square[tempSize][tempSize];
            tempSize--;
        }

        if(diagnolSum1 == magicSum) {
            trueDiag++;
        }
        if(diagnolSum2 == magicSum) {
            trueDiag++;
        }

        //This function will only return true if all rows and colums are equal to the magic number as well as both diagnols
        if (trueRow == size && trueCol == size && trueDiag == size/2) {
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