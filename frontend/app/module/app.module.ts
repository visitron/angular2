import {UiSwitchModule} from "angular2-ui-switch";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {BrowserModule} from "@angular/platform-browser";
import {ItemListComponent} from "./component/ItemList.component";
import {AddItemComponent} from "./component/AddItem.component";
import {ItemService} from "./component/Item.service";
import {NotificationService} from "./component/notification.service";

@NgModule({
    imports: [BrowserModule, FormsModule, UiSwitchModule, HttpModule],
    declarations: [ItemListComponent, AddItemComponent],
    providers: [ItemService, NotificationService],
    bootstrap: [ItemListComponent]
})

export class MaintenanceModule {}