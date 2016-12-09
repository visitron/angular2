import {Component, OnInit, OnDestroy, ChangeDetectorRef, ChangeDetectionStrategy} from "@angular/core";
import {ItemAdvanced, ItemAdvancedWrapper, Item} from "./Item";
import {ItemService} from "./Item.service";
import {NotificationService} from "./notification.service";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})

export class AddItemComponent implements OnInit, OnDestroy {

    public itemWrapper: ItemAdvancedWrapper = new ItemAdvancedWrapper(new ItemAdvanced);
    // public freeParents: ItemAdvanced[] = [];
    public freeParents: Item[] = [];
    public items: Item[] = null;

    constructor(private itemService: ItemService, private notificationService: NotificationService, private ch: ChangeDetectorRef) {}

    ngOnInit(): void {
        this.itemService.getItems().subscribe(items => {
            this.items = items;
            this.calcFreeParents();
            this.ch.markForCheck();
        });

        this.notificationService.itemEmitter.subscribe((itemAdvanced: ItemAdvanced) => {
            if (itemAdvanced !== null) {
                itemAdvanced = JSON.parse(JSON.stringify(itemAdvanced));
            }
            this.itemWrapper = new ItemAdvancedWrapper(itemAdvanced);
            this.ch.markForCheck();
        })
    }

    ngOnDestroy(): void {
        //todo when it is invoked?
        //todo why _ doesn't work?
        console.log('Destroyed');
    }

    calcFreeParents(): void {
        if (this.items === null) return;

        this.items.forEach(value => {
            let advanced: ItemAdvanced = value as ItemAdvanced;
            if (advanced.specialist || advanced.additionalDetails) {
                this.freeParents.push(value);
            }
        });
    }

    saveItem() {
        console.log(this.itemWrapper.itemAdvanced);
        this.itemService.saveItem(this.itemWrapper.itemAdvanced).subscribe(res => console.log(res));
    }

}