import {Component, OnInit} from "@angular/core";
import {Item} from "./Item";
import {ItemService} from "./Item.service";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    public item: Item = new Item(null, null, null, null, []);

    constructor(private itemService: ItemService) {}

    ngOnInit(): void {}

    saveItem() {
        console.log(this.item);
        this.itemService.saveItem(this.item).subscribe(res => console.log(res));
    }

}