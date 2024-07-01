import { Routes } from '@angular/router';
import { ProjectCreateComponent } from './project-create/project-create.component';
import { BugAssignComponent } from './bug-assign/bug-assign.component';
import { BugCloseComponent } from './bug-close/bug-close.component';

export const routes: Routes = [
  { path: 'create-project', component: ProjectCreateComponent },
  { path: 'assign-bug', component: BugAssignComponent },
  { path: 'close-bug', component: BugCloseComponent },
  { path: '', redirectTo: '/create-project', pathMatch: 'full' }
];
