import {Injectable, EventEmitter} from "@angular/core";
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import {Item} from "./Item";
import {Observable} from "rxjs";
import "rxjs/add/operator/map";
import "rxjs/add/operator/share";
import {Paths} from "./path.component";

@Injectable()
export class ItemService {

    private emitter: EventEmitter<Item[]> = new EventEmitter<Item[]>();

    constructor(private http: Http, private paths: Paths) {};

    public loadItems(): void {
        let headers: Headers = new Headers;
        headers.append("Access-Control-Allow-Origin", "*");
        let options: RequestOptions = new RequestOptions({headers: headers});
        this.http
            .get(this.paths.ITEM.GET_ALL, options)
            .map((response: Response) => response.json() as Item[])
            .subscribe(items => {
                this.emitter.emit(items);
            });
    }

    public getItems(): Observable<Item[]> {
        return this.emitter;
    }

    public saveItem(item: Item): Observable<boolean> {
        let headers: Headers = new Headers({'Content-Type': 'application/json'});
        let options: RequestOptions = new RequestOptions({headers: headers});
        return this.http
            .post(this.paths.ITEM.SAVE_ITEM, item, options)
            .map((response: Response) => response.json() as boolean);
    }


}