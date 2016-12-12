import {UiSwitchModule} from "angular2-ui-switch";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {BrowserModule} from "@angular/platform-browser";
import {ItemListComponent} from "./component/ItemList.component";
import {AddItemComponent} from "./component/AddItem.component";
import {ItemService} from "./component/Item.service";
import {NotificationService} from "./component/notification.service";
import {Paths, PathsFactory} from "./component/path.component";

/**
 * App constants:
 * 'BACKEND_MODE' = 'mock' | 'real'
 */

const APP_PARAMS = {
    "BACKEND_MODE": "mock"
};

@NgModule({
    imports: [BrowserModule, FormsModule, UiSwitchModule, HttpModule],
    declarations: [ItemListComponent, AddItemComponent],
    providers: [
        ItemService,
        NotificationService,
        {provide: "APP_PARAMS", useValue: APP_PARAMS},
        {provide: Paths, useFactory: PathsFactory, deps: ["APP_PARAMS"]}
    ],
    bootstrap: [ItemListComponent]
})
export class MaintenanceModule {}