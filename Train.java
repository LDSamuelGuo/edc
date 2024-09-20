class Train {
   String trainName;
   int occupying_SectionId;
   int dest_SectionId;
   Direction train_direction;
   int moved_times;

   Train(String name, Direction dir, int destId) {
      this.trainName = name;
      this.train_direction = dir;
      this.dest_SectionId = destId;
   }

   String getTrainName() {
      return this.trainName;
   }

   void setOccupying_SectionId(int id) {
      this.occupying_SectionId = id;
   }

   int getOccupying_SectionId() {
      return this.occupying_SectionId;
   }

   int getDest_SectionId() {
      return this.dest_SectionId;
   }

   Direction getTrain_direction() {
      return this.train_direction;
   }

   int getMoved_times() {
      return this.moved_times;
   }

   void incrementMovedTimes_By1() {
      this.moved_times = 1;
   }

   void reset_movedTimes_zero() {
      this.moved_times = 0;
   }
}
