import {Component, trigger, state, style, transition, animate} from "@angular/core";

@Component({
    selector: 'search',
    templateUrl: 'mockup/parts/search.html',
    animations: [
        trigger('search', [
            state('false', style({
                display: 'none'
            })),
            state('true', style({
                display: 'block'
            })),
            transition('true => false', animate('3s ease-in')),
            transition('false => true', animate('3s ease-in'))
        ])
    ]
})
export class SearchComponent {

    private search: boolean = false;
    private searchString: string = null;
    private clearSearchBtnName: string = 'Hide';

    toggle(): void {
        this.search = !this.search;
    }

    clear(): void {
        if (this.searchString === null) {
            this.toggle();
        } else {
            this.setSearchString(null);
        }
    }

    private setSearchString(value: string): void {
        this.searchString = value;
        if (value === null || value === '') {
            this.clearSearchBtnName = 'Hide';
        } else {
            this.clearSearchBtnName = 'Clear';
        }
    }

}