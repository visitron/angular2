import {Component, OnInit, trigger, state, style, transition, animate} from "@angular/core";

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
            transition('true => false', animate('300ms ease-in')),
            transition('false => true', animate('300ms ease-out'))
        ])
    ]
})
export class SearchComponent implements OnInit {

    private search: boolean = false;
    private searchString: string = null;

    ngOnInit(): void {

    }

    toggle(): void {
        this.search = !this.search;
    }

    clear(): void {
        if (this.searchString === null) {
            this.toggle();
        } else {
            this.searchString = null;
        }
    }

}