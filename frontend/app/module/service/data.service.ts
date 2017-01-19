import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";

@Injectable()
export class DataProvider {

    private mockMode: boolean = true;

    constructor(private http: Http) {}

    public getData(location: string, callback: (data: any) => void): void {
        this.http
            .get(this.toURL(location))
            .map(data => data.json())
            .subscribe(callback);
    }

    public getPart(location: string, part: string, callback: (data: any) => void): void {
        this.http
            .get(this.toURL(location, part))
            .map(data => data.json())
            .subscribe(callback);
    }

    private toURL(location: string, part?: string): string {
        part = _.isEmpty(part) ? '' : '-' + part;
        return this.mockMode ? 'mock/' + location + part + '.json' : location + part;
    }

}