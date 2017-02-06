import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Http, Headers} from "@angular/http";
import {ConfigProvider} from "../service/config.service";
import {Auth} from "../service/auth.service";

@Component({
    selector: 'login-info',
    templateUrl: 'mockup/parts/login-info.html'
})
export class LoginInfoComponent {

    private subscription: Subscription = null;

    constructor(private router: Router, private http: Http, private configProvider: ConfigProvider,
                private auth: Auth) {
        console.log('LoginInfo component has been created');
    }

    get loggedInUserName(): string {
        let userName: string = null;
        if (this.auth.authorized)
            userName = `${this.auth.user.firstName} ${this.auth.user.secondName}`;
        return userName;
    }

    public logout(): void {
        let headers: Headers = new Headers;
        this.http.post(`${this.configProvider.host}/logout`, null, {headers: headers, withCredentials: true}).subscribe(
            data => {
                this.auth.user = null;
                localStorage.removeItem('homeManagementUser');
                this.router.navigate(['/login']);
            }
        );
    }

}