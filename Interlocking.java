public interface Interlocking {
   void addTrain(String var1, int var2, int var3) throws IllegalArgumentException, IllegalStateException;

   int moveTrains(String[] var1) throws IllegalArgumentException;

   String getSection(int var1) throws IllegalArgumentException;

   int getTrain(String var1) throws IllegalArgumentException;
}
