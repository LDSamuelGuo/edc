import java.util.ArrayList;

class Section {
   int sectionId;
   String occupyingTrain_name;
   ArrayList<Transition> transitionsList = new ArrayList();
   ArrayList<Section> possibleDestination = new ArrayList();

   Section(int sectionId, String occ_train) {
      this.sectionId = sectionId;
      this.occupyingTrain_name = occ_train;
   }

   int getSectionId() {
      return this.sectionId;
   }

   void setOccupyingTrain_name(String train_name) {
      this.occupyingTrain_name = train_name;
   }

   String getOccupyingTrain_name() {
      return this.occupyingTrain_name;
   }

   void addTransition(Transition tran) {
      this.transitionsList.add(tran);
   }

   ArrayList<Transition> getTransitionsList() {
      return this.transitionsList;
   }

   void addDestination(Section dest) {
      this.possibleDestination.add(dest);
   }
}
