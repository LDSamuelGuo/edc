import java.util.ArrayList;
import java.util.Iterator;

public class PetriNet {
   ArrayList<Section> sectionsList = new ArrayList();
   ArrayList<FiringPolicy> policiesList = new ArrayList();
   ArrayList<PointMachine> pointMachineList = new ArrayList();

   void init_railway() {
      for(int i = 1; i < 12; ++i) {
         Section section = new Section(i, "");
         this.sectionsList.add(section);
      }

      Section section_1 = (Section)this.sectionsList.get(0);
      Transition sect_1_tran = new Transition(Direction.South, ((Section)this.sectionsList.get(4)).getSectionId());
      section_1.addTransition(sect_1_tran);
      section_1.addDestination((Section)this.sectionsList.get(7));
      section_1.addDestination((Section)this.sectionsList.get(8));
      Section section_5 = (Section)this.sectionsList.get(4);
      Transition sect_5_tran_1 = new Transition(Direction.South, ((Section)this.sectionsList.get(7)).getSectionId());
      Transition sect_5_tran_2 = new Transition(Direction.South, ((Section)this.sectionsList.get(8)).getSectionId());
      section_5.addTransition(sect_5_tran_1);
      section_5.addTransition(sect_5_tran_2);
      Section section_9 = (Section)this.sectionsList.get(8);
      Transition sect_9_tran = new Transition(Direction.North, ((Section)this.sectionsList.get(5)).getSectionId());
      section_9.addTransition(sect_9_tran);
      section_9.addDestination((Section)this.sectionsList.get(1));
      Section section_10 = (Section)this.sectionsList.get(9);
      Transition sect_10_tran = new Transition(Direction.North, ((Section)this.sectionsList.get(5)).getSectionId());
      section_10.addTransition(sect_10_tran);
      section_10.addDestination((Section)this.sectionsList.get(1));
      Section section_6 = (Section)this.sectionsList.get(5);
      Transition sect_6_tran = new Transition(Direction.North, ((Section)this.sectionsList.get(1)).getSectionId());
      section_6.addTransition(sect_6_tran);
      Section section_3 = (Section)this.sectionsList.get(2);
      Transition sect_3_tran_1 = new Transition(Direction.South, ((Section)this.sectionsList.get(3)).getSectionId());
      Transition sect_3_tran_2 = new Transition(Direction.South, ((Section)this.sectionsList.get(6)).getSectionId());
      section_3.addTransition(sect_3_tran_1);
      section_3.addTransition(sect_3_tran_2);
      section_3.addDestination((Section)this.sectionsList.get(3));
      section_3.addDestination((Section)this.sectionsList.get(10));
      Section section_4 = (Section)this.sectionsList.get(3);
      Transition sect_4_tran = new Transition(Direction.North, ((Section)this.sectionsList.get(2)).getSectionId());
      section_4.addTransition(sect_4_tran);
      section_4.addDestination((Section)this.sectionsList.get(2));
      Section section_7 = (Section)this.sectionsList.get(6);
      Transition sect_7_tranToS = new Transition(Direction.South, ((Section)this.sectionsList.get(10)).getSectionId());
      Transition sect_7_tranToN = new Transition(Direction.North, ((Section)this.sectionsList.get(2)).getSectionId());
      section_7.addTransition(sect_7_tranToS);
      section_7.addTransition(sect_7_tranToN);
      Section section_11 = (Section)this.sectionsList.get(10);
      Transition sect_11_tran = new Transition(Direction.North, ((Section)this.sectionsList.get(6)).getSectionId());
      section_11.addTransition(sect_11_tran);
      section_11.addDestination((Section)this.sectionsList.get(2));
   }

   ArrayList<Section> getSectionsList() {
      return this.sectionsList;
   }

   void init_firingPolicies() {
      FiringPolicy S3S7 = new FiringPolicy("S3S7");
      this.policiesList.add(S3S7);
      FiringPolicy S3S4 = new FiringPolicy("S3S4");
      this.policiesList.add(S3S4);
      FiringPolicy S6S2 = new FiringPolicy("S6S2");
      this.policiesList.add(S6S2);
      FiringPolicy S1S5 = new FiringPolicy("S1S5");
      this.policiesList.add(S1S5);
      FiringPolicy S10S6 = new FiringPolicy("S10S6");
      this.policiesList.add(S10S6);
      FiringPolicy S9S6 = new FiringPolicy("S9S6");
      this.policiesList.add(S9S6);
      FiringPolicy S5S9 = new FiringPolicy("S5S9");
      this.policiesList.add(S5S9);
      FiringPolicy S5S8 = new FiringPolicy("S5S8");
      this.policiesList.add(S5S8);
   }

   ArrayList<FiringPolicy> getPoliciesList() {
      return this.policiesList;
   }

   void create_pointMachine(int machine_id, int prioritized_section_1, int prioritized_section_2, int second_section_1, int second_section_2) {
      PointMachine pointMachine = new PointMachine(machine_id);
      String prioritized_policy_name = "S" + prioritized_section_1 + "S" + prioritized_section_2;
      String second_policy_name = "S" + second_section_1 + "S" + second_section_2;

      int i;
      for(i = 0; i < this.policiesList.size(); ++i) {
         if (((FiringPolicy)this.policiesList.get(i)).getPolicy_name().equals(prioritized_policy_name)) {
            pointMachine.add_policy((FiringPolicy)this.policiesList.get(i));
         }
      }

      for(i = 0; i < this.policiesList.size(); ++i) {
         if (((FiringPolicy)this.policiesList.get(i)).getPolicy_name().equals(second_policy_name)) {
            pointMachine.add_policy((FiringPolicy)this.policiesList.get(i));
         }
      }

      pointMachine.default_enabled_policy(0, true);
      pointMachine.default_enabled_policy(1, false);
      this.pointMachineList.add(pointMachine);
   }

   void init_pointMachines() {
      this.create_pointMachine(1, 3, 7, 3, 4);
      this.create_pointMachine(2, 6, 2, 3, 4);
      this.create_pointMachine(3, 1, 5, 3, 4);
      this.create_pointMachine(4, 9, 6, 10, 6);
      this.create_pointMachine(5, 9, 6, 5, 9);
      this.create_pointMachine(6, 9, 6, 5, 8);
   }

   ArrayList<PointMachine> getPointMachineList() {
      return this.pointMachineList;
   }

   void pointMachine_changeEnabled(int machine_id) {
      PointMachine target_machine = (PointMachine)this.pointMachineList.get(machine_id - 1);
      ArrayList<FiringPolicy> tmp_policyList = target_machine.getControlled_policies();

      for(int i = 0; i < tmp_policyList.size(); ++i) {
         if (((FiringPolicy)tmp_policyList.get(i)).getEnabled()) {
            ((FiringPolicy)tmp_policyList.get(i)).setEnabled(false);
         } else {
            ((FiringPolicy)tmp_policyList.get(i)).setEnabled(true);
         }
      }

   }

   int getTrainPos(String name, ArrayList<Train> trainList) {
      for(int i = 0; i < trainList.size(); ++i) {
         if (((Train)trainList.get(i)).getTrainName().equals(name)) {
            return i;
         }
      }

      return -1;
   }

   void set_both_policy(int machine_id) {
      PointMachine target_machine = (PointMachine)this.pointMachineList.get(machine_id - 1);
      ArrayList<FiringPolicy> tmp_policyList = target_machine.getControlled_policies();

      for(int i = 0; i < tmp_policyList.size(); ++i) {
         ((FiringPolicy)tmp_policyList.get(i)).setEnabled(true);
      }

   }

   void set_second_policy(int m_id) {
      PointMachine pointMachine = (PointMachine)this.pointMachineList.get(m_id - 1);
      ArrayList<FiringPolicy> policies = pointMachine.getControlled_policies();
      if (!((FiringPolicy)policies.get(1)).getEnabled()) {
         this.pointMachine_changeEnabled(m_id);
      }

   }

   void reset_to_default(int m_id) {
      PointMachine pointMachine = (PointMachine)this.pointMachineList.get(m_id - 1);
      ArrayList<FiringPolicy> policies = pointMachine.getControlled_policies();
      if (!((FiringPolicy)policies.get(0)).getEnabled()) {
         this.pointMachine_changeEnabled(m_id);
      }

   }

   void update_PointMachine(ArrayList<Section> sectionsList, ArrayList<Train> trainList) {
      int S3S4_blocked_already = 0;
      Iterator var4 = this.pointMachineList.iterator();

      while(var4.hasNext()) {
         PointMachine pointMachine = (PointMachine)var4.next();
         int sec9TrainPos2;
         Train sec9Train2;
         if (pointMachine.getMachine_id() == 1) {
            if ((((Section)sectionsList.get(2)).getOccupyingTrain_name().equals("") || ((Section)sectionsList.get(3)).getOccupyingTrain_name().equals("")) && ((Section)sectionsList.get(6)).getOccupyingTrain_name().equals("")) {
               this.set_second_policy(1);
            }

            if (((Section)sectionsList.get(3)).getOccupyingTrain_name().equals("")) {
               this.set_both_policy(1);
            }

            if (((Section)sectionsList.get(2)).getOccupyingTrain_name().equals("") && !((Section)sectionsList.get(6)).getOccupyingTrain_name().equals("")) {
               sec9TrainPos2 = this.getTrainPos(((Section)sectionsList.get(6)).getOccupyingTrain_name(), trainList);
               sec9Train2 = (Train)trainList.get(sec9TrainPos2);
               if (sec9Train2.getTrain_direction() == Direction.South) {
                  this.set_second_policy(1);
               } else if (sec9Train2.getTrain_direction() == Direction.North) {
                  this.reset_to_default(1);
                  ++S3S4_blocked_already;
               }
            }
         }

         if (pointMachine.getMachine_id() == 2) {
            if (((Section)sectionsList.get(5)).getOccupyingTrain_name().equals("") && S3S4_blocked_already == 0) {
               this.set_second_policy(2);
            } else {
               this.reset_to_default(2);
            }
         }

         if (pointMachine.getMachine_id() == 3) {
            if (((Section)sectionsList.get(0)).getOccupyingTrain_name().equals("") && S3S4_blocked_already == 0) {
               this.set_second_policy(3);
            } else {
               this.reset_to_default(3);
            }
         }

         if (pointMachine.getMachine_id() == 4) {
            if (((Section)sectionsList.get(5)).getOccupyingTrain_name().equals("") && ((Section)sectionsList.get(8)).getOccupyingTrain_name().equals("")) {
               this.set_second_policy(4);
            } else if (((Section)sectionsList.get(5)).getOccupyingTrain_name().equals("") && !((Section)sectionsList.get(8)).getOccupyingTrain_name().equals("")) {
               sec9TrainPos2 = this.getTrainPos(((Section)sectionsList.get(8)).getOccupyingTrain_name(), trainList);
               sec9Train2 = (Train)trainList.get(sec9TrainPos2);
               if (sec9Train2.getTrain_direction() == Direction.South) {
                  this.set_second_policy(4);
               }
            } else if (!((Section)sectionsList.get(5)).getOccupyingTrain_name().equals("") || !((Section)sectionsList.get(8)).getOccupyingTrain_name().equals("")) {
               this.reset_to_default(4);
            }
         }

         if (pointMachine.getMachine_id() == 5) {
            if (((Section)sectionsList.get(8)).getOccupyingTrain_name().equals("")) {
               this.set_second_policy(5);
            } else {
               this.reset_to_default(5);
            }
         }

         if (pointMachine.getMachine_id() == 6) {
            if (((Section)sectionsList.get(8)).getOccupyingTrain_name().equals("")) {
               this.set_second_policy(6);
            } else if (!((Section)sectionsList.get(8)).getOccupyingTrain_name().equals("")) {
               sec9TrainPos2 = this.getTrainPos(((Section)sectionsList.get(8)).getOccupyingTrain_name(), trainList);
               sec9Train2 = (Train)trainList.get(sec9TrainPos2);
               if (sec9Train2.getTrain_direction() == Direction.South) {
                  this.set_second_policy(6);
               } else if (sec9Train2.getTrain_direction() == Direction.North) {
                  this.reset_to_default(6);
               }
            }
         }
      }

   }
}
