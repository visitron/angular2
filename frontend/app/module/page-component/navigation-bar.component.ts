import {Component} from "@angular/core";
import {Location} from "@angular/common";
import {Router, NavigationStart} from "@angular/router";
import {SlickGridProvider} from "../service/slick-grid.service";

@Component({
    selector: 'navigation-bar',
    templateUrl: 'mockup/parts/navigation-bar.html'
})
export class NavigationBarComponent {

    private show: boolean;

    constructor(private router: Router, private location: Location, private slickGridProvider: SlickGridProvider) {

        this.show = !location.path().endsWith('/config');

        router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.show = !event.url.endsWith('/config');
            }
        });

    }

    select(mode: "all" | "nothing" | "invert"): void {
        this.slickGridProvider.select(mode);
    }

}