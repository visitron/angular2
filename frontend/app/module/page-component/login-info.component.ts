import {Component, OnDestroy} from "@angular/core";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
    selector: 'login-info',
    templateUrl: 'mockup/parts/login-info.html'
})
export class LoginInfoComponent implements OnDestroy {

    private subscription: Subscription = null;

    constructor(private router: Router) {
        this.subscription = this.router.events.subscribe(event => {
            if (event.url.endsWith("/logout")) {
                router.navigate(["/login"]);
            }
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    public logout(): void {

    }

}