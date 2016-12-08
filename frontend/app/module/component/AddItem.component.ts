import {Component, OnInit, ChangeDetectorRef} from "@angular/core";
import {ItemAdvanced, ItemAdvancedWrapper, Item} from "./Item";
import {ItemService} from "./Item.service";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    public itemWrapper: ItemAdvancedWrapper = new ItemAdvancedWrapper(new ItemAdvanced);
    // public freeParents: ItemAdvanced[] = [];
    public freeParents: Item[] = [];
    public items: Item[] = null;

    constructor(private itemService: ItemService, private ch: ChangeDetectorRef) {}

    ngOnInit(): void {
        this.itemService.getItems().subscribe(items => {
            this.items = items;
            this.calcFreeParents();
            this.ch.detectChanges();
        });
    }

    calcFreeParents(): void {
        if (this.items === null) return;

        this.items.forEach(value => {
            // if (value instanceof ItemAdvanced) {
                this.freeParents.push(value);
            // }
        });
    }

    saveItem() {
        console.log(this.itemWrapper.itemAdvanced);
        this.itemService.saveItem(this.itemWrapper.itemAdvanced).subscribe(res => console.log(res));
    }

}