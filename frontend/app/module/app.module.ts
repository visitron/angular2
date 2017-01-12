import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {LoginComponent} from "./component/login.component";
import "jquery";
import "slickgrid-jquery-event-drag";
import "tinycolor2";
import "bootstrap";
import "slickgrid-core";
import "slickgrid-grid";
import {SearchComponent} from "./component/search.component";
import {LoginInfoComponent} from "./component/login-info.component";
import {HeaderComponent} from "./component/page-header.component";
import {ContentComponent} from "./component/page-content.component";
import {FiltersComponent} from "./component/filters.component";
import {ActionsComponent} from "./component/actions.component";
import {NavigationBarComponent} from "./component/navigation-bar.component";
/**
 * App constants:
 * 'BACKEND_MODE' = 'mock' | 'real'
 */

const APP_PARAMS = {
    "BACKEND_MODE": "mock"
};

@NgModule({
    imports: [BrowserModule],
    declarations: [LoginComponent, SearchComponent, LoginInfoComponent, HeaderComponent, ContentComponent, FiltersComponent, ActionsComponent, NavigationBarComponent],
    // imports: [BrowserModule, FormsModule, UiSwitchModule, HttpModule],
    // declarations: [ItemListComponent, AddItemComponent],
    // providers: [
    //     ItemService,
    //     NotificationService,
    //     {provide: "APP_PARAMS", useValue: APP_PARAMS},
    //     {provide: Paths, useFactory: PathsFactory, deps: ["APP_PARAMS"]}
    // ],
    bootstrap: [LoginComponent]
})
export class MainModule {
}