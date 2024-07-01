import { Component } from '@angular/core';
import { BugTrackerService } from '../bug-tracker.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-project-create',
  templateUrl:'./project-create.component.html',
  styleUrls: ['./project-create.component.css']
})
export class ProjectCreateComponent {
 projectId:number=0;
 projectName:string="";
 projectOwner:string="";

  constructor(private bugService: BugTrackerService) {}

  // createProject() {
  //   this.bugService.createProject(this.projectData).subscribe(
  //     response => console.log('Project created successfully:', response),
  //     error => console.error('Error creating project:', error)
  //   );
 // }
}
