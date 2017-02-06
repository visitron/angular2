import {Component, OnDestroy} from "@angular/core";
import {Location} from "@angular/common";
import {Router, NavigationEnd} from "@angular/router";
import {SlickGridProvider} from "../service/slick-grid.service";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: 'navigation-bar',
    templateUrl: 'mockup/parts/navigation-bar.html'
})
export class NavigationBarComponent implements OnDestroy {

    private show: boolean;
    private subscription: Subscription;

    constructor(private router: Router, private location: Location, private slickGridProvider: SlickGridProvider) {

        this.show = !location.path().endsWith('/config');

        this.subscription = router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.show = !event.url.endsWith('/config');
            }
        });

    }

    select(mode: "all" | "nothing" | "invert"): void {
        this.slickGridProvider.select(mode);
    }


    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}