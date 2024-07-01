import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';  // Import FormsModule
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { ProjectCreateComponent } from './project-create/project-create.component';
import { BugAssignComponent } from './bug-assign/bug-assign.component';
import { BugCloseComponent } from './bug-close/bug-close.component';
import { routes } from './app.routes';  // Import routes

@NgModule({
  declarations: [
    AppComponent,
    ProjectCreateComponent,
    BugAssignComponent,
    BugCloseComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,  // Add FormsModule to imports array
    HttpClientModule,
    RouterModule.forRoot(routes)  // Use RouterModule.forRoot with routes
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
