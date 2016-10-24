import {UiSwitchModule} from "angular2-ui-switch";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {ItemComponent} from "./component/items-list.component";
import {AddItemComponent} from "./component/add-item.component";

@NgModule({
    imports: [BrowserModule, FormsModule, UiSwitchModule],
    declarations: [ItemComponent, AddItemComponent],
    bootstrap: [ItemComponent]
})

export class MaintenanceModule {}