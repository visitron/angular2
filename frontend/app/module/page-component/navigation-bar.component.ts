import {Component} from "@angular/core";
import {Location} from "@angular/common";
import {Router, NavigationStart} from "@angular/router";

@Component({
    selector: 'navigation-bar',
    templateUrl: 'mockup/parts/navigation-bar.html'
})
export class NavigationBarComponent {

    private show: boolean;

    constructor(private router: Router, private location: Location) {

        this.show = !location.path().endsWith('/config');

        router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.show = !event.url.endsWith('/config');
            }
        });

    }

}