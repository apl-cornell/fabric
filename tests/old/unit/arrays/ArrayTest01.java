public class ArrayTest01 {
  char c;
  public ArrayTest01(char c) {
    this.c = c;
  }

  public static void main(String[] args) {

    int MAX_COUNT=2000;
    int ITER_REPS=50;
    int SIZE1=3000;
    int SIZE2=10000;

    long startTime, startIteration, endIteration, setLength, endIteration2;
    float creation=0, iteration1=0, modify=0, iteration2=0;
    
    long objectCreation = System.currentTimeMillis();
    ArrayTest01[] cachedElements = new ArrayTest01[SIZE2];
    for (int i = 0; i < SIZE2; i++) {
        cachedElements[i] = new ArrayTest01((char)(i*i));
    }
    System.out.println("Object creation took " + (System.currentTimeMillis() - objectCreation) + "ms");
    int percentage = 0;
    int step = 5;
        
    for (int count = 0; count < MAX_COUNT; count++) {
            
            startTime = System.currentTimeMillis();
            ArrayTest01[] testArray = new ArrayTest01[SIZE1];
            startIteration = System.currentTimeMillis();
            for (int j = 0; j < ITER_REPS; j++)
            for (int i = 0; i < SIZE1; i++) {
                testArray[i] = cachedElements[i];
            }
            endIteration = System.currentTimeMillis();
            testArray = new ArrayTest01[SIZE2];
            setLength = System.currentTimeMillis();
            for (int j = 0; j < ITER_REPS; j++)
            for (int i = SIZE1; i < SIZE2; i++) {
                testArray[i] = cachedElements[i];
            }
            endIteration2 = System.currentTimeMillis();

            // bookkeeping
            creation+=startIteration - startTime;
            iteration1+=endIteration - startIteration;
            modify+=setLength - endIteration;
            iteration2+=endIteration2 - setLength;
            if (count == (percentage * MAX_COUNT)/100) {
                System.out.print(percentage + "%");
                percentage += step;
            }

        }

        
    creation/=MAX_COUNT;iteration1/=MAX_COUNT*ITER_REPS;
    modify/=MAX_COUNT;iteration2/=MAX_COUNT*ITER_REPS;

    System.out.println("\nArray Creation: " + creation
        + "ms\nIteration 1: " + iteration1
        + "ms\nModifying Length: " + modify
        + "ms\nIteration 2: " + iteration2);     
  }
}