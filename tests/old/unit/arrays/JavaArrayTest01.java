
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class JavaArrayTest01 {
  char c;
  public JavaArrayTest01(char c) {
    this.c = c;
  }

  public static void main(String[] args) {
    /** Parameters:
     * 1. size 1 of array
     * 2. size 2 of array (for measure resize time)
     * 3. number of outer reps
     * 4. number of inner reps
     * 5. "true" if a batch output format is desired
     */
    int SIZE1;
    int SIZE2;
    int MAX_COUNT;
    int ITER_REPS;
    boolean isBatchOutput;

    if (args.length != 5) {
      System.out.println("I need 5 arguments - size1, size2, maxCount, iterReps, isBatchOutput");
      return;
    }

    SIZE1 = Integer.parseInt(args[0]);
    SIZE2 = Integer.parseInt(args[1]);
    MAX_COUNT = Integer.parseInt(args[2]);
    ITER_REPS = Integer.parseInt(args[3]);
    isBatchOutput = args[4].equals("true")?true:false;

    long startTime, startInitialize, startReadIteration, startWriteIteration, startResize, endTime;
    float creation=0, initialization=0, reading=0, replacing=0, resizing=0;

    JavaArrayTest01 element1 = new JavaArrayTest01('f');
    JavaArrayTest01 element2 = new JavaArrayTest01('o'); 

    int percentage = 0;
    int step = 5;

    long startTest = System.currentTimeMillis();
    for (int count = 0; count < MAX_COUNT; count++) {

      // create array
      startTime = System.currentTimeMillis();
      JavaArrayTest01[] testArray;
      testArray = new JavaArrayTest01[SIZE1];

      // initialize array with element 1
      startInitialize = System.currentTimeMillis();
      for (int i = 0; i < SIZE1; i++) {
        testArray[i] = element1;
      }


      // read entire array multiple times
      startReadIteration = System.currentTimeMillis();
      for (int j = 0; j < ITER_REPS; j++)
        for (int i = 0; i < SIZE1; i++) {
          JavaArrayTest01 element = testArray[i];
        }

      // replace all elements of the array multiple times
      startWriteIteration = System.currentTimeMillis();
      for (int j = 0; j < ITER_REPS; j++)
        for (int i = 0; i < SIZE1; i++) {
          testArray[i] = element2;
        }

      // resize array
      startResize = System.currentTimeMillis();
      JavaArrayTest01[] testArrayNew  = new JavaArrayTest01[SIZE2];
      for (int i = 0; i < SIZE1; i++) {
        testArrayNew[i] = testArray[i]; 
      }
      testArray = testArrayNew;

      // end time
      endTime = System.currentTimeMillis();

      // bookkeeping
      creation += startInitialize - startTime;
      initialization += startReadIteration - startInitialize;
      reading += startWriteIteration - startReadIteration;
      replacing += startResize - startWriteIteration;
      resizing += endTime - startResize;

      // show progress
      if (!isBatchOutput && count == (percentage * MAX_COUNT)/100) {
        System.out.print(percentage + "%");
        long timeNow = System.currentTimeMillis() - startTest;
        if (percentage > 0) {
          long estimatedEnd = (timeNow*100)/percentage;
          System.out.println("(" + timeNow + "ms/" + estimatedEnd + "ms)");
        } else {
          System.out.println();
        }
        percentage += step;
      }

    }
    if (!isBatchOutput) System.out.println("100%Completed");

    creation = creation * 1000 / MAX_COUNT;
    initialization = initialization * 1000 / MAX_COUNT;
    float initializationPer = initialization/SIZE1;
    reading = reading * 1000 / (MAX_COUNT*ITER_REPS);
    float readingPer = reading/SIZE1;
    replacing = replacing * 1000 / (MAX_COUNT*ITER_REPS);
    float replacingPer = replacing/SIZE1;
    resizing = resizing * 1000 / MAX_COUNT;
    
    NumberFormat formatter = new DecimalFormat("#0.0000");

    if (!isBatchOutput) {
      System.out.println("\nArray Creation: " + formatter.format(creation)
          + "us\nArray Initialization: " + formatter.format(initialization)
          + "us (" + formatter.format(initializationPer) 
          + "us)\nReading Elements: " + formatter.format(reading)
          + "us (" + formatter.format(readingPer) 
          + "us)\nReplacing Elements: " + formatter.format(replacing)
          + "us (" + formatter.format(replacingPer)
          + "us)\nResizing Array: " + formatter.format(resizing) + "us"); 
    } else {
      System.out.println("0 " + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] +
          " javaarray " + creation + " " + formatter.format(initialization)
          + " " + formatter.format(initializationPer) 
          + " " + formatter.format(reading)
          + " " + formatter.format(readingPer) 
          + " " + formatter.format(replacing) + " " + formatter.format(replacingPer)
          + " " + formatter.format(resizing));
    }
  }
}