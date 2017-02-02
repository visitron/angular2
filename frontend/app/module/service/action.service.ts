import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Observer} from "rxjs/Observer";
import {ActionContext} from "../page-component/actions.component";

@Injectable()
export class ActionService {
    private observable: Observable<ActionContext>;
    private observer: Observer<ActionContext>;

    constructor() {
        this.observable = Observable.create((observer: Observer<ActionContext>) => this.observer = observer);
    }

    public sendAction(context: ActionContext): void {
        this.observer.next(context);
    }

    public subscribe(callback: (context: ActionContext) => void): void {
        this.observable.subscribe(callback);
    }

}