class Transition {
   Direction train_direction;
   int next_section_id;

   Transition(Direction dir, int next_section_id) {
      this.train_direction = dir;
      this.next_section_id = next_section_id;
   }

   Direction getTransition_dir() {
      return this.train_direction;
   }

   int getNext_section_id() {
      return this.next_section_id;
   }
}
