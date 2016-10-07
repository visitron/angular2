import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ItemComponent} from "./component/itemsListComponent";
import {AddItemComponent} from "./component/addItemComponent";

@NgModule({
    imports: [BrowserModule],
    declarations: [ItemComponent, AddItemComponent],
    bootstrap: [ItemComponent]
})

export class MaintenanceModule {}