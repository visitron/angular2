import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import "rxjs/add/operator/map";
import {ConfigProvider} from "./config.service";
import {Auth} from "./auth.service";

@Injectable()
export class DataProvider {

    private host: string;

    constructor(private http: Http, private router: Router, private configProvider: ConfigProvider, private auth: Auth) {
        this.host = configProvider.host;
    }

    public getData(location: string, callback: (data: any) => void): void {
        if (this.ignoreURL(location)) return;

        localStorage.getItem("remember-me-token");

        let headers: Headers = new Headers;
        this.http
            .get(this.toURL(location), {headers: headers, withCredentials: true})
            .map(data => data.json())
            .subscribe(callback, this.errorCallback.bind(this));
    }

    public getPart(location: string, part: string, callback: (data: any) => void): void {
        if (this.ignoreURL(location)) return;

        this.http
            .get(this.toInternalUrl(location, part))
            .map(data => data.json())
            .subscribe(callback);
    }

    public getUsers(callback: (data: any) => void): void {
        this.http
            .get(`${this.host}/login/users`)
            .map(data => data.json())
            .subscribe(callback);
    }

    public hasAdmin(callback: (data: any) => void): void {
        this.http
            .get(`${this.host}/register/hasAdmin`)
            .map(data => data.json())
            .subscribe(callback);
    }

    private ignoreURL(location: string): boolean {
        return !(location.match(/\/task/) || location.match(/\/admin/));
    }

    private toURL(location: string, part?: string): string {
        part = _.isEmpty(part) ? '' : '-' + part;
        let url = this.host + location + part;
        if (this.configProvider.isMockMode) url += '.json';
        return url;
    }

    private toInternalUrl(location: string, part: string): string {
        return 'mock/' + location + '-' + part + '.json';
    }

    private errorCallback(error: any): void {
        this.auth.user = null;
        this.router.navigate(['login']);
    }

}