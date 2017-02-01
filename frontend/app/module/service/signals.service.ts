import {Injectable} from "@angular/core";
import {Observable, Observer} from "rxjs";

@Injectable()
export class Signals {
    private actions: Observable<any>;
    private observer: Observer<any>;

    constructor() {
        this.actions = Observable.create((observer: Observer<any>) => this.observer = observer);
    }

    sendAction(action: string): void {


        if (this.onActionSent(true)) {
            //post
        }
    }

    onActionSent(proceed: boolean): boolean {
        return proceed;
    }

}