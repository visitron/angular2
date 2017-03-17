import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from "@angular/router";
import {User} from "../login.component";
import * as _ from "underscore";

@Injectable()
export class Auth implements CanActivate {
    private _authorized: boolean = false;
    private _user: User;

    public set user(user: User) {
        this._user = user;
        if (_.isEmpty(user)) {
            this._authorized = false;
            localStorage.removeItem('homeManagementUser');
        } else {
            this._authorized = true;
        }
    }

    public get user(): User {
        return this._user;
    }

    public get authorized(): boolean {
        return this._authorized;
    }

    public get initialUrl(): string[] {
        return this.user.role === "ADMIN" ? ["/admin"] : ["/task"];
    }

    constructor(private router: Router) {
        let storedUser: User = JSON.parse(localStorage.getItem('homeManagementUser'));
        if (!_.isEmpty(storedUser)) {
            this.user = storedUser;
        }
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (!this.authorized && ['/login', '/register'].indexOf(state.url) !== -1) {
            return true;
        } else if (!this.authorized) {
            this.router.navigate(['/login']);
            return false;
        }

        if (this.authorized && ['/login', '/register'].indexOf(state.url) !== -1) {
            let url: string = this.user.role === "ADMIN" ? "/admin" : "/task";
            this.router.navigate([url]);
            return true;
        }

        if (route.url.findIndex(value => value.path == 'admin') !== -1 && this.user.role !== "ADMIN") {
            this.router.navigate(['/login']);
            return false;
        }

        if (route.url.findIndex(value => value.path == 'task') !== -1 && this.user.role !== "USER") {
            this.router.navigate(['/login']);
            return false;
        }

        return true;
    }

}