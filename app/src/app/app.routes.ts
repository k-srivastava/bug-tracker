import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";

import {LoginComponent} from "./components/login/login.component";
import {ProjectCreateComponent} from "./components/project-create/project-create.component";
import {ProjectComponent} from "./components/project/project.component";

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'projects/create', component: ProjectCreateComponent},
    {path: 'projects/:id', component: ProjectComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
