import java.util.ArrayList;

class PointMachine {
   int machine_id;
   ArrayList<FiringPolicy> controlled_policies = new ArrayList();

   PointMachine(int assigned_id) {
      this.machine_id = assigned_id;
   }

   int getMachine_id() {
      return this.machine_id;
   }

   void add_policy(FiringPolicy policy) {
      this.controlled_policies.add(policy);
   }

   ArrayList<FiringPolicy> getControlled_policies() {
      return this.controlled_policies;
   }

   void default_enabled_policy(int index, boolean permission) {
      ((FiringPolicy)this.controlled_policies.get(index)).setEnabled(permission);
   }
}
