import { Component } from '@angular/core';
import { BugTrackerService } from '../bug-tracker.service';

@Component({
  selector: 'app-bug-assign',
  templateUrl: './bug-assign.component.html',
  styleUrls: ['./bug-assign.component.css']
})
export class BugAssignComponent {
  bugId: number = 0;
  bugOwner: string = "";

  constructor(private bugService: BugTrackerService) { }

  assignBug() {
    this.bugService.assignBug(this.bugId, this.bugOwner).subscribe(
      response => {
        console.log('Bug assigned successfully:', response);
      },
      error => {
        console.error('Error assigning bug:', error);
      }
    );
  }
}
