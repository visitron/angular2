import {Component, trigger, state, style, transition, animate, OnDestroy} from "@angular/core";
import {NavigationEnd, Router} from "@angular/router";
import {Location} from "@angular/common";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: 'search',
    templateUrl: 'mockup/parts/search.html',
    animations: [
        trigger('searchInputState', [
            state('active', style({
                opacity: 1,
                display: 'block'
            })),
            state('inactive', style({
                opacity: 0,
                display: 'none'
            })),
            transition('active <=> inactive', animate('200ms ease-out'))
        ]),
        trigger('searchBtnState', [
            state('active', style({
                opacity: 1,
                display: 'block'
            })),
            state('inactive', style({
                opacity: 0,
                display: 'none'
            })),
            transition('active <=> inactive', animate('200ms ease-out'))
        ])
    ]
})
export class SearchComponent implements OnDestroy {

    private searchActivated: boolean = false;
    private searchInputState: string = 'inactive';
    private searchBtnState: string = 'active';
    private searchString: string = null;
    private clearSearchBtnName: string = 'Hide';
    private show: boolean;
    private subscription: Subscription;

    constructor(private router: Router, private location: Location) {
        this.show = !location.path().endsWith('/config');

        this.subscription = router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.show = !event.url.endsWith('/config');
            }
        });
    }

    toggle(): void {
        this.searchActivated = !this.searchActivated;
        this.searchInputState = this.searchActivated ? 'active' : 'inactive';
        this.searchBtnState = this.searchActivated ? 'inactive' : 'active';
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

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}