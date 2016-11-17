import {Component, OnInit} from "@angular/core";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";
import * as moment from "moment";
import * as Color from "color";
import {ItemService} from "./Item.service";
import {Item, InfoMapping} from "./Item";

@Component({
    selector: 'maintenanceApp',
    templateUrl: 'template/items-list.html'
})

export class ItemListComponent implements OnInit {
    constructor(private itemService: ItemService, private sanitizer: DomSanitizer) {}

    public items: Item[] = [];
    public animateClass: boolean;

    ngOnInit(): void {
        this.itemService.getItems().subscribe(items => this.items = items);
        setInterval(() => this.animateClass = !this.animateClass, 2000);
    }

    getGlyphicons(item: Item): SafeHtml {
        return this.sanitizer.bypassSecurityTrustHtml(InfoMapping.get(item.info));
    }

    calcDaysRemains(item: Item): number {
        let diff: number = moment().diff(moment(item.maintenanceDate), "days");
        return (item.lifecycle - diff) * 100 / item.lifecycle;
    }

    calcColorCode(item: Item): string {
        let baseColor: Color = Color({
            hue: 127,
            saturation: 0.7,
            lightness: 0.5});

        let hue: number = this.calcDaysRemains(item) * 1.27;
        hue = hue < 0 ? 0 : hue;
        baseColor = baseColor.setHue(hue);
        return baseColor.toRGB();
    }

    needWarn(item: Item): boolean {
        return this.calcDaysRemains(item) < 20;
    }

    formatDate(date: Date): String {
        return date.toISOString().slice(0, 10);
    }
}