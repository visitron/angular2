import {Component, OnInit} from "@angular/core";
import {ItemAdvanced, Specialist} from "./Item";
import {ItemService} from "./Item.service";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    public item: ItemAdvanced = new ItemAdvanced;

    constructor(private itemService: ItemService) {}

    ngOnInit(): void {}

    hasSpecialist(): boolean {
        return this.item.specialist !== null;
    }

    hasAdditionalDetails(): boolean {
        return this.item.additionalDetails.length > 0;
    }

    switchSpecialist(enabled: boolean): void {
        if (enabled) {
            this.item.specialist = new Specialist;
        } else {
            this.item.specialist = null;
        }
    }

    saveItem() {
        console.log(this.item);
        this.itemService.saveItem(this.item).subscribe(res => console.log(res));
    }

}