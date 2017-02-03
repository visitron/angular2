import {Component, OnDestroy} from "@angular/core";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Http, Headers} from "@angular/http";

@Component({
    selector: 'login-info',
    templateUrl: 'mockup/parts/login-info.html'
})
export class LoginInfoComponent implements OnDestroy {

    private subscription: Subscription = null;

    constructor(private router: Router, private http: Http) {
        console.log('LoginInfo component has been created');
    }

    ngOnDestroy(): void {
    }

    public logout(): void {
        let headers: Headers = new Headers;
        this.http.post('http://localhost:3002/logout', null, {headers: headers, withCredentials: true}).subscribe(
            data => {
                this.router.navigate(["/login"]);
            }
        );
    }

}