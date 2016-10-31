import {Injectable} from "@angular/core";
import {Http, Response, Headers} from "@angular/http";
import {Item} from "./Item";
import {Observable} from 'rxjs';

@Injectable()
export class ItemService {

    constructor(private http: Http) {};

    public getItems(): Observable<Response> {
        let headers: Headers = new Headers;
        headers.append("Access-Control-Allow-Origin", "*");
        return this.http.get("http://localhost:7001/home-maintenance/item/getAll", {headers: headers});
        // return this.http.get("/angular2/frontend/mock/items.json");
    }


}