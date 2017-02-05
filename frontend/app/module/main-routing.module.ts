import {NgModule} from "@angular/core";
import {CommonModule, Location, LocationStrategy, PathLocationStrategy} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {HttpModule} from "@angular/http";
import {UiSwitchModule} from "angular2-ui-switch";
import "underscore";
import {LoginComponent} from "./login.component";
import {SearchComponent} from "./page-component/search.component";
import {LoginInfoComponent} from "./page-component/login-info.component";
import {FiltersComponent} from "./page-component/filters.component";
import {ActionsComponent} from "./page-component/actions.component";
import {NavigationBarComponent} from "./page-component/navigation-bar.component";
import {RegisterComponent} from "./register.component";
import {PageNotFoundComponent} from "./page-not-found.component";
import {AccessDeniedComponent} from "./access-denied.component";
import {ShellComponent} from "./shell.component";
import {AdminComponent} from "./admin/admin.component";
import {AdminAuditComponent} from "./admin/admin-audit.component";
import {AdminConfigComponent} from "./admin/admin-config.component";
import {AdminUsersComponent} from "./admin/admin-users.component";
import {TemplateComponent} from "./common-page/template.component";
import {TaskComponent} from "./task/task.component";
import {TaskMaintenanceComponent} from "./task/task-maintenance.component";
import {TaskJobComponent} from "./task/task-job.component";
import {TaskPaymentComponent} from "./task/task-payment.component";
import {TaskPurchaseComponent} from "./task/task-purchase.component";
import {DataProvider} from "./service/data.service";
import {SlickGridProvider} from "./service/slick-grid.service";
import {FormsModule} from "@angular/forms";
import {EqualValidator} from "./equal-validator.directive";
import {ActionService} from "./service/action.service";
import {Auth} from "./service/auth.service";


const routes: Routes = [
    {path: 'login', component: LoginComponent, canActivate: [Auth]},
    {path: 'register', component: RegisterComponent, canActivate: [Auth]},
    {path: '', redirectTo: 'login', pathMatch: 'full'},

    {path: 'admin', component: TemplateComponent, canActivate:[Auth], children: [
        {path: '', component: AdminComponent, children: [
            {path: '', redirectTo: 'users'},
            {path: 'users', component: AdminUsersComponent},
            {path: 'config', component: AdminConfigComponent},
            {path: 'audit', component: AdminAuditComponent}
        ]},
    ]},

    {path: 'task', component: TemplateComponent, canActivate:[Auth], children: [
        {path: '', component: TaskComponent, children: [
            {path: '', redirectTo: 'purchase'},
            {path: 'purchase', component: TaskPurchaseComponent},
            {path: 'payment', component: TaskPaymentComponent},
            {path: 'job', component: TaskJobComponent},
            {path: 'maintenance', component: TaskMaintenanceComponent},
        ]}
    ]},
    {path: 'access-denied', component: AccessDeniedComponent},
    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes), UiSwitchModule, CommonModule, HttpModule, FormsModule
    ],
    declarations: [LoginComponent, SearchComponent, LoginInfoComponent,
        FiltersComponent, ActionsComponent, NavigationBarComponent, RegisterComponent, PageNotFoundComponent,
        ShellComponent, AdminComponent, AdminUsersComponent, AdminConfigComponent, AdminAuditComponent, TemplateComponent,
        TaskComponent, TaskPurchaseComponent, TaskPaymentComponent, TaskJobComponent, TaskMaintenanceComponent,
        AccessDeniedComponent, EqualValidator
    ],
    providers: [DataProvider, SlickGridProvider, Location, {provide: LocationStrategy, useClass: PathLocationStrategy}, ActionService, Auth]
})
export class MainRoutingModule {
}