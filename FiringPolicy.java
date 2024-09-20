class FiringPolicy {
   String policy_name;
   boolean enabled;

   FiringPolicy(String policy_name) {
      this.policy_name = policy_name;
   }

   String getPolicy_name() {
      return this.policy_name;
   }

   boolean getEnabled() {
      return this.enabled;
   }

   void setEnabled(boolean permission) {
      this.enabled = permission;
   }
}
