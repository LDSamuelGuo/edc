import org.junit.Assert;
import org.junit.Test;

public class InterlockingImpl_Test {
   @Test(
      expected = IllegalArgumentException.class
   )
   public void test_invalidPath() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 9);
   }

   @Test(
      expected = IllegalArgumentException.class
   )
   public void test_repeatedAddTrain() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 11);
      interlockingImpl.addTrain("A", 4, 3);
   }

   @Test(
      expected = IllegalArgumentException.class
   )
   public void test_repeatedNameAsExitedTrain() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 4, 3);
      String[] trainsToMove1 = new String[]{"A"};
      interlockingImpl.moveTrains(trainsToMove1);
      interlockingImpl.addTrain("B", 11, 3);
      String[] trainsToMove2 = new String[]{"B"};
      interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals(interlockingImpl.getSection(3), (Object)null);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), -1L);
      Assert.assertEquals(((Train)interlockingImpl.exited_train_list.get(0)).getTrainName(), "A");
      interlockingImpl.addTrain("A", 3, 4);
   }

   @Test(
      expected = IllegalStateException.class
   )
   public void test_occupiedSection() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 11);
      interlockingImpl.addTrain("B", 3, 4);
   }

   @Test(
      expected = IllegalArgumentException.class
   )
   public void test_invalidSectionId() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.getSection(12);
   }

   @Test(
      expected = IllegalArgumentException.class
   )
   public void test_invalidTrainName() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 11);
      interlockingImpl.addTrain("B", 4, 3);
      interlockingImpl.getTrain("C");
   }

   @Test
   public void check_init() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      Assert.assertEquals((long)interlockingImpl.sections_list.size(), 11L);
      Assert.assertEquals((long)interlockingImpl.petriNet.policiesList.size(), 8L);
      Assert.assertEquals((long)interlockingImpl.petriNet.pointMachineList.size(), 6L);
   }

   @Test
   public void check_addTrain() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 11);
      Assert.assertEquals(interlockingImpl.getSection(3), "A");
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 3L);
      Assert.assertEquals((long)((Train)interlockingImpl.present_train_list.get(0)).getDest_SectionId(), 11L);
      Assert.assertEquals(((Train)interlockingImpl.present_train_list.get(0)).getTrain_direction(), Direction.South);
   }

   @Test
   public void check_firingPolicy_S11ToS3() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 11, 3);
      String[] trainsToMove1 = new String[]{"A"};
      interlockingImpl.moveTrains(trainsToMove1);
      interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 3L);
      Assert.assertEquals(interlockingImpl.getSection(3), "A");
   }

   @Test
   public void check_basicTrainMove() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 11);
      String[] trainsToMove1 = new String[]{"A"};
      interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 7L);
      Assert.assertEquals(interlockingImpl.getSection(7), "A");
      Assert.assertEquals(interlockingImpl.getSection(3), (Object)null);
      String[] trainsToMove2 = new String[]{"A"};
      interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 11L);
      Assert.assertEquals(interlockingImpl.getSection(11), "A");
      Assert.assertEquals(interlockingImpl.getSection(7), (Object)null);
      interlockingImpl.addTrain("B", 10, 2);
      String[] trainsToMove3 = new String[]{"B"};
      interlockingImpl.moveTrains(trainsToMove3);
      Assert.assertEquals(((Train)interlockingImpl.exited_train_list.get(0)).getTrainName(), "A");
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), -1L);
      Assert.assertEquals(interlockingImpl.getSection(11), (Object)null);
      Assert.assertEquals(interlockingImpl.getSection(6), "B");
      interlockingImpl.addTrain("C", 10, 2);
      String[] trainsToMove4 = new String[]{"B", "C"};
      interlockingImpl.moveTrains(trainsToMove4);
      Assert.assertEquals(interlockingImpl.getSection(2), "B");
      Assert.assertEquals(interlockingImpl.getSection(6), "C");
      String[] trainsToMove5 = new String[]{"C"};
      interlockingImpl.moveTrains(trainsToMove5);
      Assert.assertEquals(interlockingImpl.getSection(2), "C");
   }

   @Test
   public void check_firingPolicy_S9S6_S10S6() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 9, 2);
      interlockingImpl.addTrain("B", 10, 2);
      String[] trainsToMove1 = new String[]{"A", "B"};
      int moved_trains = interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)moved_trains, 1L);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 6L);
      Assert.assertEquals(interlockingImpl.getSection(6), "A");
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 10L);
      Assert.assertEquals(interlockingImpl.getSection(10), "B");
      String[] trainsToMove2 = new String[]{"A", "B"};
      int moved_trains1 = interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)moved_trains1, 2L);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 2L);
      Assert.assertEquals(interlockingImpl.getSection(2), "A");
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 6L);
      Assert.assertEquals(interlockingImpl.getSection(6), "B");
      String[] trainsToMove3 = new String[]{"B"};
      int moved_trains2 = interlockingImpl.moveTrains(trainsToMove3);
      Assert.assertEquals((long)moved_trains2, 1L);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 2L);
      Assert.assertEquals(interlockingImpl.getSection(2), "B");
   }

   @Test
   public void check_firingPolicy_S9S6_S5S9_S5S8() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 9, 2);
      interlockingImpl.addTrain("B", 1, 9);
      String[] trainsToMove = new String[]{"B"};
      interlockingImpl.moveTrains(trainsToMove);
      interlockingImpl.addTrain("C", 1, 8);
      String[] trainsToMove1 = new String[]{"B", "A", "C"};
      int moved_trains1 = interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 6L);
      Assert.assertEquals(interlockingImpl.getSection(6), "A");
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 9L);
      Assert.assertEquals(interlockingImpl.getSection(9), "B");
      Assert.assertEquals((long)moved_trains1, 3L);
      String[] trainsToMove2 = new String[]{"C"};
      int moved_trains2 = interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)moved_trains2, 1L);
      Assert.assertEquals((long)interlockingImpl.getTrain("C"), 8L);
      Assert.assertEquals(interlockingImpl.getSection(8), "C");
      Assert.assertEquals(interlockingImpl.getSection(9), (Object)null);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), -1L);
   }

   @Test
   public void check_firingPolicy_S4ToS3() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 4, 3);
      String[] trainsToMove1 = new String[]{"A"};
      interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 3L);
      Assert.assertEquals(interlockingImpl.getSection(3), "A");
   }

   @Test
   public void check_firingPolicy_S3ToS4() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 4);
      String[] trainsToMove1 = new String[]{"A"};
      interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 4L);
      Assert.assertEquals(interlockingImpl.getSection(4), "A");
   }

   @Test
   public void check_firingPolicy_S3S4_S3S7() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 3, 4);
      interlockingImpl.addTrain("B", 11, 3);
      String[] trainsToMove1 = new String[]{"B"};
      interlockingImpl.moveTrains(trainsToMove1);
      String[] trainsToMove2 = new String[]{"B", "A"};
      interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 4L);
      Assert.assertEquals(interlockingImpl.getSection(4), "A");
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 3L);
      Assert.assertEquals(interlockingImpl.getSection(3), "B");
   }

   @Test
   public void check_firingPolicy_S3S4_S3S7_1() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 4, 3);
      interlockingImpl.addTrain("B", 11, 3);
      String[] trainsToMove1 = new String[]{"B"};
      interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 7L);
      Assert.assertEquals(interlockingImpl.getSection(7), "B");
      String[] trainsToMove2 = new String[]{"A", "B"};
      interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 3L);
      Assert.assertEquals(interlockingImpl.getSection(3), "B");
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 4L);
      Assert.assertEquals(interlockingImpl.getSection(4), "A");
   }

   @Test
   public void check_firingPolicy_S3S4_S3S7_2() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 4, 3);
      interlockingImpl.addTrain("B", 3, 11);
      String[] trainsToMove = new String[]{"B", "A"};
      int first_round_moved = interlockingImpl.moveTrains(trainsToMove);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 7L);
      Assert.assertEquals(interlockingImpl.getSection(7), "B");
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 3L);
      Assert.assertEquals(interlockingImpl.getSection(3), "A");
      Assert.assertEquals((long)first_round_moved, 2L);
      String[] trainsToMove2 = new String[]{"B"};
      int second_round_moved = interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 11L);
      Assert.assertEquals(interlockingImpl.getSection(11), "B");
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), -1L);
      Assert.assertEquals(interlockingImpl.getSection(3), (Object)null);
      Assert.assertEquals((long)second_round_moved, 1L);
   }

   @Test
   public void check_firingPolicy_S3S4_S6S2_S3S7() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("A", 4, 3);
      interlockingImpl.addTrain("B", 10, 2);
      interlockingImpl.addTrain("C", 1, 8);
      interlockingImpl.addTrain("D", 11, 3);
      String[] trainsToMove = new String[]{"B"};
      interlockingImpl.moveTrains(trainsToMove);
      String[] trainsToMove1 = new String[]{"B", "C", "D", "A"};
      int num = interlockingImpl.moveTrains(trainsToMove1);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), 2L);
      Assert.assertEquals(interlockingImpl.getSection(2), "B");
      Assert.assertEquals((long)interlockingImpl.getTrain("C"), 5L);
      Assert.assertEquals(interlockingImpl.getSection(5), "C");
      Assert.assertEquals((long)interlockingImpl.getTrain("D"), 7L);
      Assert.assertEquals(interlockingImpl.getSection(7), "D");
      Assert.assertEquals((long)interlockingImpl.getTrain("A"), 4L);
      Assert.assertEquals(interlockingImpl.getSection(4), "A");
      Assert.assertEquals((long)num, 3L);
   }

   @Test
   public void moreThanOneTrain_exit_at_theSameTime() {
      InterlockingImpl interlockingImpl = new InterlockingImpl();
      interlockingImpl.addTrain("B", 10, 2);
      interlockingImpl.addTrain("C", 1, 8);
      interlockingImpl.addTrain("D", 11, 3);
      String[] trainsToMove1 = new String[]{"B", "C"};
      interlockingImpl.moveTrains(trainsToMove1);
      interlockingImpl.moveTrains(trainsToMove1);
      String[] trainsToMove2 = new String[]{"D"};
      interlockingImpl.moveTrains(trainsToMove2);
      Assert.assertEquals((long)interlockingImpl.getTrain("B"), -1L);
      Assert.assertEquals(interlockingImpl.getSection(2), (Object)null);
      Assert.assertEquals((long)interlockingImpl.getTrain("C"), -1L);
      Assert.assertEquals(interlockingImpl.getSection(8), (Object)null);
      Assert.assertEquals((long)interlockingImpl.getTrain("D"), 7L);
      Assert.assertEquals(interlockingImpl.getSection(7), "D");
   }
}
