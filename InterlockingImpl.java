import java.util.ArrayList;
import java.util.Iterator;

public class InterlockingImpl implements Interlocking {
   ArrayList<Train> present_train_list = new ArrayList();
   ArrayList<Train> exited_train_list = new ArrayList();
   ArrayList<Section> sections_list = new ArrayList();
   PetriNet petriNet = new PetriNet();

   InterlockingImpl() {
      this.petriNet.init_railway();
      this.sections_list = this.petriNet.getSectionsList();
      this.petriNet.init_firingPolicies();
      this.petriNet.init_pointMachines();
   }

   boolean checkIfTrainInRailCorridor(String name) {
      boolean exist = false;

      for(int i = 0; i < this.present_train_list.size(); ++i) {
         if (((Train)this.present_train_list.get(i)).getTrainName().equals(name)) {
            exist = true;
            break;
         }
      }

      return exist;
   }

   boolean checkIfTrainNameUsed(String name) {
      boolean exist = false;

      int i;
      for(i = 0; i < this.present_train_list.size(); ++i) {
         if (((Train)this.present_train_list.get(i)).getTrainName().equals(name)) {
            exist = true;
            break;
         }
      }

      for(i = 0; i < this.exited_train_list.size(); ++i) {
         if (((Train)this.exited_train_list.get(i)).getTrainName().equals(name)) {
            exist = true;
            break;
         }
      }

      return exist;
   }

   boolean checkValidPath(int entry_section, int dest_section) {
      boolean valid = false;
      Section entrySection = (Section)this.sections_list.get(entry_section - 1);
      Iterator var5 = entrySection.possibleDestination.iterator();

      while(var5.hasNext()) {
         Section sec = (Section)var5.next();
         if (dest_section == sec.sectionId) {
            valid = true;
            break;
         }
      }

      return valid;
   }

   int getTrainPos(String name) {
      for(int i = 0; i < this.present_train_list.size(); ++i) {
         if (((Train)this.present_train_list.get(i)).getTrainName().equals(name)) {
            return i;
         }
      }

      return -1;
   }

   boolean check_ifTrainExistsButLeft(String trainName) {
      for(int i = 0; i < this.exited_train_list.size(); ++i) {
         if (((Train)this.exited_train_list.get(i)).getTrainName().equals(trainName)) {
            return true;
         }
      }

      return false;
   }

   public void addTrain(String trainName, int entryTrackSection, int destinationTrackSection) {
      boolean name_exist = this.checkIfTrainNameUsed(trainName);
      boolean path_valid = this.checkValidPath(entryTrackSection, destinationTrackSection);
      if (!name_exist && path_valid) {
         if (!((Section)this.sections_list.get(entryTrackSection - 1)).occupyingTrain_name.equals("")) {
            throw new IllegalStateException();
         } else {
            Section enterSection = (Section)this.sections_list.get(entryTrackSection - 1);
            Direction newTrain_dir = ((Transition)enterSection.transitionsList.get(0)).train_direction;
            Train newTrain = new Train(trainName, newTrain_dir, destinationTrackSection);
            enterSection.setOccupyingTrain_name(trainName);
            newTrain.setOccupying_SectionId(entryTrackSection);
            this.present_train_list.add(newTrain);
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   int nextSection_toMove(String trainName) {
      int id_toReturn = -1;
      int trainPos = this.getTrainPos(trainName);
      Train moving_train = (Train)this.present_train_list.get(trainPos);
      int current_sec = moving_train.getOccupying_SectionId();
      ArrayList<Transition> sec_tran = ((Section)this.sections_list.get(current_sec - 1)).getTransitionsList();

      for(int i = 0; i < sec_tran.size(); ++i) {
         Transition next_tran;
         if (((Transition)sec_tran.get(i)).getNext_section_id() == moving_train.getDest_SectionId()) {
            next_tran = (Transition)sec_tran.get(i);
            id_toReturn = next_tran.getNext_section_id();
            break;
         }

         if (((Transition)sec_tran.get(i)).getTransition_dir() == moving_train.getTrain_direction()) {
            next_tran = (Transition)sec_tran.get(i);
            id_toReturn = next_tran.getNext_section_id();
         }
      }

      return id_toReturn;
   }

   boolean checkIfSectionEmpty(int sectionId) {
      Section sec = (Section)this.sections_list.get(sectionId - 1);
      return sec.getOccupyingTrain_name().equals("");
   }

   String[] sort_train_list(String[] original_list) {
      for(int i = 0; i < original_list.length; ++i) {
         String passenger_train_2;
         String tmp2;
         if (this.getTrain(original_list[i]) == 6) {
            passenger_train_2 = original_list[i];
            if (this.getTrain(original_list[0]) != 1) {
               tmp2 = original_list[0];
               original_list[0] = passenger_train_2;
               original_list[i] = tmp2;
            } else {
               tmp2 = original_list[1];
               original_list[1] = passenger_train_2;
               original_list[i] = tmp2;
            }
         }

         if (this.getTrain(original_list[i]) == 1) {
            passenger_train_2 = original_list[i];
            if (this.getTrain(original_list[0]) != 6) {
               tmp2 = original_list[0];
               original_list[0] = passenger_train_2;
               original_list[i] = tmp2;
            } else {
               tmp2 = original_list[1];
               original_list[1] = passenger_train_2;
               original_list[i] = tmp2;
            }
         }
      }

      return original_list;
   }

   public int moveSingleTrain(String trainName) {
      int next_section_id = this.nextSection_toMove(trainName);
      if (next_section_id == -1) {
         System.out.println("Error in getting next section, program stops.");
         throw new IllegalStateException();
      } else {
         int trainPos = this.getTrainPos(trainName);
         Train moving_train = (Train)this.present_train_list.get(trainPos);
         int current_sec = moving_train.getOccupying_SectionId();
         boolean canPass = true;
         String policy_to_check;
         if ((current_sec != 7 || next_section_id != 3) && (current_sec != 4 || next_section_id != 3)) {
            policy_to_check = "S" + current_sec + "S" + next_section_id;
         } else {
            policy_to_check = "S" + next_section_id + "S" + current_sec;
         }

         ArrayList<FiringPolicy> policiesList = this.petriNet.getPoliciesList();

         for(int i = 0; i < policiesList.size(); ++i) {
            FiringPolicy policy = (FiringPolicy)policiesList.get(i);
            if (policy.getPolicy_name().equals(policy_to_check) && !policy.getEnabled()) {
               canPass = false;
               break;
            }
         }

         if (canPass) {
            boolean isEmpty = this.checkIfSectionEmpty(next_section_id);
            if (isEmpty) {
               moving_train.incrementMovedTimes_By1();
               ((Section)this.sections_list.get(next_section_id - 1)).setOccupyingTrain_name(trainName);
               ((Section)this.sections_list.get(current_sec - 1)).setOccupyingTrain_name("");
               moving_train.setOccupying_SectionId(next_section_id);
               return 1;
            } else {
               return 0;
            }
         } else {
            return 0;
         }
      }
   }

   public int moveTrains(String[] trainNames) {
      int movedTrains = 0;
      String[] var3 = trainNames;
      int i = trainNames.length;

      int first_round_count;
      String scanning_train;
      for(first_round_count = 0; first_round_count < i; ++first_round_count) {
         scanning_train = var3[first_round_count];
         boolean validTrain = this.checkIfTrainInRailCorridor(scanning_train);
         if (!validTrain) {
            throw new IllegalArgumentException();
         }
      }

      var3 = trainNames;
      i = trainNames.length;

      Train current_train;
      int pos;
      for(first_round_count = 0; first_round_count < i; ++first_round_count) {
         scanning_train = var3[first_round_count];
         pos = this.getTrainPos(scanning_train);
         current_train = (Train)this.present_train_list.get(pos);
         current_train.reset_movedTimes_zero();
      }

      ArrayList<Integer> index_to_remove = new ArrayList();

      for(i = 0; i < this.present_train_list.size(); ++i) {
         first_round_count = ((Train)this.present_train_list.get(i)).getOccupying_SectionId();
         if (first_round_count == ((Train)this.present_train_list.get(i)).getDest_SectionId()) {
            ((Section)this.sections_list.get(first_round_count - 1)).setOccupyingTrain_name("");
            this.exited_train_list.add((Train)this.present_train_list.get(i));
            index_to_remove.add(i);
         }
      }

      for(i = 0; i < index_to_remove.size(); ++i) {
         this.present_train_list.remove(index_to_remove.get(i));
      }

      this.petriNet.update_PointMachine(this.sections_list, this.present_train_list);
      String[] sortedlist = this.sort_train_list(trainNames);
      first_round_count = 0;

      int i;
      for(i = 0; i < sortedlist.length; ++i) {
         pos = this.moveSingleTrain(sortedlist[i]);
         first_round_count += pos;
         movedTrains += pos;
         this.petriNet.update_PointMachine(this.sections_list, this.present_train_list);
      }

      while(first_round_count > 0) {
         first_round_count = 0;

         for(i = 0; i < sortedlist.length; ++i) {
            pos = this.getTrainPos(sortedlist[i]);
            current_train = (Train)this.present_train_list.get(pos);
            if (current_train.getMoved_times() == 0) {
               int count = this.moveSingleTrain(sortedlist[i]);
               first_round_count += count;
               movedTrains += count;
               this.petriNet.update_PointMachine(this.sections_list, this.present_train_list);
            }
         }
      }

      return movedTrains;
   }

   public int getTrain(String trainName) {
      int trainPos = this.getTrainPos(trainName);
      boolean exitedTrain = this.check_ifTrainExistsButLeft(trainName);
      if (trainPos == -1 && !exitedTrain) {
         throw new IllegalArgumentException();
      } else if (exitedTrain) {
         return -1;
      } else {
         int occupying_section = ((Train)this.present_train_list.get(trainPos)).getOccupying_SectionId();
         return occupying_section;
      }
   }

   public String getSection(int trackSection) {
      if (trackSection >= 1 && trackSection <= 11) {
         String occupying_trainName = ((Section)this.sections_list.get(trackSection - 1)).getOccupyingTrain_name();
         return !occupying_trainName.equals("") ? occupying_trainName : null;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
