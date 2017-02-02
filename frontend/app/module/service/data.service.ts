import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";

@Injectable()
export class DataProvider {

    private mockMode: boolean = false;

    constructor(private http: Http) {}

    public getData(location: string, callback: (data: any) => void): void {
        if (this.ignoreURL(location)) return;

        let headers: Headers = new Headers;
        headers.append('Authorization', 'Basic ' + btoa('Vladimir:1'));
        this.http
            .get(this.toURL(location), {headers: headers})
            .map(data => data.json())
            .subscribe(callback);
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
            .get('http://localhost:3002/login/users')
            .map(data => data.json())
            .subscribe(callback);
    }

    public hasAdmin(callback: (data: any) => void): void {
        this.http
            .get('http://localhost:3002/register/hasAdmin')
            .map(data => data.json())
            .subscribe(callback);
    }

    private ignoreURL(location: string): boolean {
        return !(location.match(/\/task/) || location.match(/\/admin/));
    }

    private toURL(location: string, part?: string): string {
        part = _.isEmpty(part) ? '' : '-' + part;
        return this.mockMode ? 'mock/' + location + part + '.json' : 'http://localhost:3002' + location + part;
    }

    private toInternalUrl(location: string, part: string): string {
        return 'mock/' + location + '-' + part + '.json';
    }

}