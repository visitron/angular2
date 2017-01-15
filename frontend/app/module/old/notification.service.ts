import {Injectable, EventEmitter} from "@angular/core";
import {ItemAdvanced} from "./Item";
import "rxjs/add/operator/map";
import "rxjs/add/operator/share";

@Injectable()
export class NotificationService {
    private _itemEmitter: EventEmitter<ItemAdvanced> = new EventEmitter<ItemAdvanced>();

    public get itemEmitter(): EventEmitter<ItemAdvanced> {
        return this._itemEmitter
    }

    public set itemEmitter(emitter: EventEmitter<ItemAdvanced>) {
        throw new Error('Item Emitter cannot be replaced');
    }

}