import { Component } from '@angular/core';
import { BugTrackerService } from '../bug-tracker.service';

@Component({
  selector: 'app-bug-close',
  templateUrl: './bug-close.component.html',
  styleUrls: ['./bug-close.component.css']
})
export class BugCloseComponent {
  bugId: number = 0;

  constructor(private bugService: BugTrackerService) { }

  closeBug() {
    this.bugService.closeBug(this.bugId).subscribe(
      response => {
        console.log('Bug closed successfully:', response);
      },
      error => {
        console.error('Error closing bug:', error);
      }
    );
  }
}
