import {Component, trigger, state, style, transition, animate, OnDestroy} from "@angular/core";
import {NavigationEnd, Router} from "@angular/router";
import {Location} from "@angular/common";
import {Subscription} from "rxjs/Subscription";
import {SlickGridProvider, Search} from "../service/slick-grid.service";

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

    constructor(private router: Router, private location: Location, private slickGridProvider: SlickGridProvider) {
        this.subscription = router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.show = !event.urlAfterRedirects.endsWith('/config');
            }
        });
    }

    toggle(): void {
        this.searchActivated = !this.searchActivated;
        this.searchInputState = this.searchActivated ? 'active' : 'inactive';
        this.searchBtnState = this.searchActivated ? 'inactive' : 'active';
        setTimeout(() => {
            if (this.searchActivated) $('#search-input-id').focus();
        }, 200)
    }

    keypress(ev: KeyboardEvent) {
        if (ev.keyCode === 27) {
            this.searchString ? this.setSearchString(null) : this.toggle();
        }
    }

    clear(): void {
        if (this.searchString === null || this.searchString == '') {
            this.toggle();
        } else {
            this.setSearchString(null);
        }
    }

    setSearchString(value: string): void {
        if (value === null || value === '') {
            this.clearSearchBtnName = 'Hide';
            this.searchString = null;
        } else {
            this.clearSearchBtnName = 'Clear';
            this.searchString = value.toLowerCase();
        }

        if (value === null || value.length < 3) {
            this.slickGridProvider.search(null);
        } else {
            let search: Search = new Search;
            search.value = value;
            search.fields = ['firstName', 'secondName'];
            this.slickGridProvider.search(search);
        }

    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}