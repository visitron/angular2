import {Component, OnInit} from "@angular/core";
import {ItemAdvanced} from "./Item";
import {ItemService} from "./Item.service";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    public item: ItemAdvanced = new ItemAdvanced;

    constructor(private itemService: ItemService) {}

    ngOnInit(): void {}

    saveItem() {
        console.log(this.item);
        this.itemService.saveItem(this.item).subscribe(res => console.log(res));
    }

}