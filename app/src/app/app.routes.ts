import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";

import {LoginComponent} from "./components/login/login.component";
import {ProjectCreateComponent} from "./components/project-create/project-create.component";
import {ProjectComponent} from "./components/project/project.component";
import {AuthGuard} from "./auth.guard";
import {ProjectListComponent} from "./components/project-list/project-list.component";

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'projects', component: ProjectListComponent, canActivate: [AuthGuard]},
    {path: 'projects/create', component: ProjectCreateComponent, canActivate: [AuthGuard]},
    {path: 'projects/:id', component: ProjectComponent, canActivate: [AuthGuard]}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
