import "jquery";
import "slickgrid-jquery-event-drag";
import "tinycolor2";
import "bootstrap";
import "slickgrid-core";
import "slickgrid-grid";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ShellComponent} from "./shell.component";
import {MainRoutingModule} from "./app-routing.module";
/**
 * App constants:
 * 'BACKEND_MODE' = 'mock' | 'real'
 */

const APP_PARAMS = {
    "BACKEND_MODE": "mock"
};

@NgModule({
    imports: [
        BrowserModule,
        MainRoutingModule
        // RouterModule.forRoot([
        //     {path: 'login', component: LoginComponent},
        //     {path: 'register', component: RegisterComponent},
        //     {path: 'admin', component: AdminComponent},
        //     {path: '**', component: PageNotFoundComponent},
        //     {path: '', redirectTo: 'login', pathMatch: 'full'}
        // ])
    ],
    // declarations: [AdminUsersComponent],
    // declarations: [LoginComponent, SearchComponent, LoginInfoComponent, HeaderComponent, ContentComponent,
    //     FiltersComponent, ActionsComponent, NavigationBarComponent, RegisterComponent, PageNotFoundComponent,
    //     ShellComponent, AdminComponent],
    // imports: [BrowserModule, FormsModule, UiSwitchModule, HttpModule],
    // declarations: [ItemListComponent, AddItemComponent],
    // providers: [
    //     ItemService,
    //     NotificationService,
    //     {provide: "APP_PARAMS", useValue: APP_PARAMS},
    //     {provide: Paths, useFactory: PathsFactory, deps: ["APP_PARAMS"]}
    // ],
    bootstrap: [ShellComponent]
})
export class MainModule {
}