import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Location} from "@angular/common";
import "rxjs/add/operator/map";

@Injectable()
export class DataProvider {

    private invalidated: boolean = true;
    private mockMode: boolean = true;

    constructor(private http: Http) {}

    public getData(location: Location, callback: (data: any) => void): void {
        this.http
            .get(this.toURL(location))
            .map(data => data.json())
            .subscribe(callback);
    }

    public getPart(location: Location, part: string, callback: (data: any) => void): void {
        this.http
            .get(this.toURL(location, part))
            .map(data => data.json())
            .subscribe(callback);
    }

    private toURL(location: Location, part?: string): string {
        part = _.isEmpty(part) ? '' : '-' + part;
        return this.mockMode ? 'mock/' + location.path() + part + '.json' : location.path() + part;
    }

}